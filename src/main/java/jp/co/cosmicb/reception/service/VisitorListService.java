package jp.co.cosmicb.reception.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import jp.co.cosmicb.reception.config.RetentionConfig;
import jp.co.cosmicb.reception.entity.OfficeVisit;
import jp.co.cosmicb.reception.exception.CantWriteFileException;
import jp.co.cosmicb.reception.exception.CustomException;
import jp.co.cosmicb.reception.exception.DatabaseException;
import jp.co.cosmicb.reception.exception.DirectoryNotFoundException;
import jp.co.cosmicb.reception.form.SearchModel;
import jp.co.cosmicb.reception.repository.VisitorListRepository;

@Service
public class VisitorListService {

	/**
	 *
	 */
	@Autowired
	private VisitorListRepository visitorListRepository;

	protected final static Logger logger = LoggerFactory.getLogger(VisitorListService.class);

	/** 初期設定(ページを開いた時のカレンダー日付やチェックボックスの状態を設定)
	 * @param searchM
	 * @return
	 */
	public SearchModel startPageInitialize(SearchModel searchM) {

		LocalDate maxDate = LocalDate.now();
		LocalDate minDate = maxDate.minusDays(7);

		searchM.setInputMinDate(minDate.toString());
		searchM.setInputMaxDate(maxDate.toString());
		searchM.setChecked(true);

		return searchM;
	}

	/** 検索処理
	 * @param searchM
	 * @param mongoConfig
	 * @return 検索結果
	 * @throws CustomException　投げます
	 */
	public List<OfficeVisit> search(SearchModel searchM)
			throws DatabaseException {
		//カレンダーおよび未退室チェックボックスの情報から検索する
		logger.info("★★★★★ Service - search called.");

		List<OfficeVisit> resultSearchList;

		LocalDateTime inputMinDateTime = toLocalDateTime(searchM.getInputMinDate() + " 00:00:00",
				"yyyy-MM-dd HH:mm:ss");
		LocalDateTime inputMaxDateTime = toLocalDateTime(searchM.getInputMaxDate() + " 00:00:00",
				"yyyy-MM-dd HH:mm:ss");
		boolean checked = searchM.isChecked();

		logger.debug("期間:" + inputMinDateTime + " - " + inputMaxDateTime);
		logger.debug("checked:" + checked);

		//クエリ上の都合でinputMaxDateTimeに＋1日する
		inputMaxDateTime = inputMaxDateTime.plusDays(1);

		try {

			if (checked) {
				//入室者検索
				//QueryMethod
				resultSearchList = visitorListRepository.findByVisitedAtBetweenAndPersonToVisitIsOrderByVisitedAtDesc(
						inputMinDateTime, inputMaxDateTime, "");

			} else {
				//全件検索
				//QueryMethod
				resultSearchList = visitorListRepository.findByVisitedAtBetweenOrderByVisitedAtDesc(
						inputMinDateTime, inputMaxDateTime);
			}
			return resultSearchList;

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw new DatabaseException("e.recep.ch.9003", e.getMessage().toString());
		}
	}

	/** 退室処理(更新)
	 * @param _id DB用ID
	 * @param personToVisit 訪問先
	 * @param mongoConfig mongoDB用モデル
	 */
	public OfficeVisit updateVisitorList(String _id, String personToVisit) throws DatabaseException {

		//更新対象のデータを取得
		OfficeVisit entity = visitorListRepository.findById(_id).get();
		entity.setPersonToVisit(personToVisit);
		entity.setLeftAt(LocalDateTime.now());

		//QueryMethod
		try {
			entity = visitorListRepository.save(entity);
		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw new DatabaseException("e.recep.ch.9003", e.getMessage().toString());
		}

		return entity;

	}

	/** 管理画面更新
	 * @param _id
	 * @param visitorName
	 * @param visitorOrg
	 * @return entity
	 * @throws DatabaseException
	 */
	public JSONObject updateVisitorLisetEdit(String _id, String visitorName, String visitorOrg)
			throws DatabaseException {

		//更新対象のデータを取得
		OfficeVisit entity = visitorListRepository.findById(_id).get();
		entity.setVisitorName(visitorName);
		entity.setVisitorOrg(visitorOrg);
		JSONObject json = new JSONObject();

		//QueryMethod
		try {
			entity = visitorListRepository.save(entity);
			json.put("_id", entity.getId());
			json.put("visitorName", entity.getVisitorName());
			json.put("visitorOrg", entity.getVisitorOrg());

		} catch (Exception e) {
			logger.error(e.toString());
			e.printStackTrace();
			throw new DatabaseException("e.recep.ch.900x", e.getMessage().toString());
		}

		return json;

	}

	/** データ消去処理
	 * @param mongoConfig application.yml - mongoDB設定関連のモデル
	 * @param rConfig application.yml - データ削除設定関連のモデル
	 * @return 消去結果
	 */
	public List<OfficeVisit> eraseVisitorList(RetentionConfig rConfig)
			throws CustomException, DirectoryNotFoundException, CantWriteFileException {

		List<OfficeVisit> resultSearchList;

		//保存期間を設定ファイルから取得
		int period = rConfig.getPersontovisit().getPeriod();
		LocalDateTime startingDate = LocalDateTime.now().minusMonths(period);
		//保存先を設定ファイルから取得
		String logFileName = rConfig.getPersontovisit().getLogfilepath();
		File file = new File(logFileName);
		File directory = new File(file.getParent());

		//保存先のディレクトリが存在するかチェックする
		if (directory.exists() == false) {
			throw new DirectoryNotFoundException("e.recep.ch.9001", directory.getPath());
		}

		//消去対象ログを取得
		//QueryMethod
		resultSearchList = visitorListRepository.findByVisitedAtLessThanAndPersonToVisitNot(startingDate, "");

		if (resultSearchList.size() > 0) {
			//取得したログに対してそれぞれ消去していく
			for (OfficeVisit entity : resultSearchList) {

				//消去処理
				visitorListRepository.deleteById(entity.getId());

				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));) {

					if (checkFileState(file)) {

						bw.write(LocalDateTime.now().format(
								DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + ","); //消去日時
						bw.write(entity.getId() + ","); //対象データ：ID
						bw.write(entity.getVisitedAt().format(
								DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + ","); //対象データ：訪問日時
						bw.write(entity.getVisitorOrg() + ","); //対象データ：会社名
						bw.write(entity.getVisitorName() + ","); //対象データ：名前
						bw.write(entity.getVisitorCount() + ","); //対象データ：人数
						bw.write(entity.getPersonToVisit() + ","); //対象データ：訪問先
						bw.write(entity.getLeftAt().format(
								DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))); //対象データ：退出日時
						bw.newLine();
						bw.close();
					} else {
						//ログファイルに書き込みできない場合
						logger.error("Can't write logfile");
						throw new CantWriteFileException("w.recep.ch.5000", String.valueOf(resultSearchList.size()));

					}

				} catch (IOException e) {
					logger.error(e.toString());
					e.printStackTrace();
					throw new CustomException("e.recep.ch.9002", String.valueOf(resultSearchList.size()));

				}
			}
			return resultSearchList;

		} else {
			//データ証拠件数が0件の場合
			return resultSearchList;
		}

	}

	/** テキストファイル書込時の排他処理
	 * @param f ファイル情報
	 * @return boolean
	 */
	private boolean checkFileState(File f) {

		int count = 0;

		if (f.canWrite()) {
			//書込可ならtrueをreturn
			return true;
		} else {
			//書込不可の間繰り返す
			while (f.canWrite() == false) {
				//カウントのインクリメント
				count++;

				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
				}
				if (count > 100) {
					return false;
				}
			}
			return true;
		}
	}

	/**	String型日時からLocalDateTime型への変換
	 * @param date 日付
	 * @param format パターン
	 * @return LocalDateTime
	 */
	public LocalDateTime toLocalDateTime(String date, String format) {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
		return LocalDateTime.parse(date, dtf);
	}

	public Date localDatetimeToDate(LocalDateTime localdatetime) {

		localdatetime = localdatetime.minusHours(9);
		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(localdatetime, zone);
		Instant instant = zonedDateTime.toInstant();

		return Date.from(instant);
	}


	/**	追加行を登録する処理
	 * @param org 会社名
	 * @param name 名前
	 * @param number 人数
	 */
	public void insertVisitorAdd(String org, String name, Integer number) {

		LocalDateTime date = LocalDateTime.now();
		LocalDateTime epoch = LocalDateTime.of(2000, 1, 1, 0, 0, 0);

		OfficeVisit officeVisit = new OfficeVisit();

		officeVisit.setVisitorOrg(org);
		officeVisit.setVisitorName(name);
		officeVisit.setVisitorCount(number);
		officeVisit.setVisitedAt(date);
		officeVisit.setPersonToVisit("");
		officeVisit.setLeftAt(epoch);

		visitorListRepository.save(officeVisit);

	}

}
