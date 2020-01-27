package com.example.demo.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.OfficeVisit;
import com.example.demo.config.RetentionConfig;
import com.example.demo.config.SpringDataMongoDBConfig;
import com.example.demo.form.SearchModel;
import com.example.demo.repository.VisitorListRepository;

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
	 */
	public List<OfficeVisit> search(SearchModel searchM, SpringDataMongoDBConfig mongoConfig) {
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

		//Query query = new Query();

		if (checked) {
			//入室者検索

			resultSearchList = visitorListRepository.findByVisitedAtBetweenAndPersonToVisitIsOrderByVisitedAtDesc(
					inputMinDateTime, inputMaxDateTime, "");

			//			query.addCriteria(Criteria.where("person_to_visit").is("")
			//					.andOperator(
			//							Criteria.where("visited_at").gte(inputMinDateTime),
			//							Criteria.where("visited_at").lt(inputMaxDateTime)));

		} else {
			//全件検索
			resultSearchList = visitorListRepository.findByVisitedAtBetweenOrderByVisitedAtDesc(
					inputMinDateTime,
					inputMaxDateTime);
			//			query.addCriteria(new Criteria()
			//					.andOperator(
			//							Criteria.where("visited_at").gte(inputMinDateTime),
			//							Criteria.where("visited_at").lt(inputMaxDateTime)));
		}

		//		query.with(Sort.by(Sort.Direction.DESC, "visited_at"));
		//		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()),
		//				mongoConfig.getDatabase());
		//		resultSearchList = mongoOps.find(query.limit(2000), OfficeVisit.class);

		return resultSearchList;

	}

	/** 退室処理(更新)
	 * @param _id DB用ID
	 * @param personToVisit 訪問先
	 * @param mongoConfig mongoDB用モデル
	 */
	public void updateVisitorList(String _id, String personToVisit, SpringDataMongoDBConfig mongoConfig) {

		OfficeVisit officeVisit = new OfficeVisit();
		officeVisit.set_id(_id);
		officeVisit.setPerson_to_visit(personToVisit);
		officeVisit.setLeft_at(LocalDateTime.now());
		officeVisit.setVisitor_name(officeVisit.getVisitor_name());

		officeVisit = visitorListRepository.save(officeVisit);

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(_id));

		Update update = new Update();
		update.set("left_at", LocalDateTime.now());
		update.set("person_to_visit", personToVisit);

		//DB接続
		//MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()),
		//mongoConfig.getDatabase());
		//DB更新
		//mongoOps.updateFirst(query, update, OfficeVisit.class);

	}

	/** データ消去処理
	 * @param mongoConfig application.yml - mongoDB設定関連のモデル
	 * @param rConfig application.yml - データ削除設定関連のモデル
	 * @return 消去結果
	 */
	public String eraseVisitorList(SpringDataMongoDBConfig mongoConfig, RetentionConfig rConfig) {

		List<OfficeVisit> resultSearchList;

		//保存期間を設定ファイルから取得
		int period = rConfig.getPersontovisit().getPeriod();
		LocalDateTime startingDate = LocalDateTime.now().minusMonths(period);
		//保存先を設定ファイルから取得
		String logFileName = rConfig.getPersontovisit().getLogfilepath();
		File file = new File(logFileName);

		//消去対象ログを取得
		resultSearchList = visitorListRepository.findByVisitedAtLessThanAndPersonToVisitNot(startingDate, "");

		//		Query query = new Query();
		//		query.addCriteria(new Criteria()
		//				.andOperator(
		//						Criteria.where("visited_at").lt(startingDate),
		//						Criteria.where("person_to_visit").ne("")));
		//		query.with(Sort.by(Sort.Direction.DESC, "visited_at"));
		//
		//		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()),
		//				mongoConfig.getDatabase());
		//		resultSearchList = mongoOps.find(query, OfficeVisit.class);

		if (resultSearchList.size() > 0) {
			//取得したログに対してそれぞれ消去していく
			for (int i = 0; i < resultSearchList.size(); i++) {

				visitorListRepository.deleteById(resultSearchList.get(i).get_id());

				//				query = new Query();
				//				query.addCriteria(Criteria
				//						.where("_id").is(resultSearchList.get(i).get_id()));
				//				mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()), mongoConfig.getDatabase());
				//				mongoOps.remove(query, OfficeVisit.class);

				//ログ書き込み
				try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));) {

					if (checkFileState(file)) {

						bw.write(LocalDateTime.now().format(
								DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + ","); //消去日時
						bw.write(resultSearchList.get(i).get_id() + ","); //対象データ：ID
						bw.write(resultSearchList.get(i).getVisited_at().format(
								DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + ","); //対象データ：訪問日時
						bw.write(resultSearchList.get(i).getVisitor_org() + ","); //対象データ：会社名
						bw.write(resultSearchList.get(i).getVisitor_name() + ","); //対象データ：名前
						bw.write(resultSearchList.get(i).getVisitor_count() + ","); //対象データ：人数
						bw.write(resultSearchList.get(i).getPerson_to_visit() + ","); //対象データ：訪問先
						bw.write(resultSearchList.get(i).getLeft_at().format(
								DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"))); //対象データ：退出日時
						bw.newLine();
						bw.close();
					} else {
						//ログファイルに書き込みできない場合
						logger.error("Can't write logfile");
						return "Can't write logfile";
					}

				} catch (FileNotFoundException e) {
					logger.error("FileNotFoundException");
					e.printStackTrace();
					return "FileNotFoundException";

				} catch (IOException e) {
					logger.error("IOException");
					e.printStackTrace();
					return "IOException";
				}
			}
			return "success (削除対象件数: " + resultSearchList.size() + "件)";

		} else {
			//データ証拠件数が0件の場合
			return "削除対象のデータはありませんでした。";

		}

	}

	/** テキストファイル書込時の排他処理
	 * @param f ファイル情報
	 * @return boolean
	 */
	private boolean checkFileState(File f) {

		int count = 0;

		if (f.canWrite()) {
			return true;
		} else {
			while (f.canWrite() == false) {
				count++;
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

}
