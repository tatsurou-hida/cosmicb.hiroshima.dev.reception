package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.terasoluna.gfw.common.message.ResultMessages;

import com.example.demo.CantWriteFileException;
import com.example.demo.CustomException;
import com.example.demo.DatabaseException;
import com.example.demo.DirectoryNotFoundException;
import com.example.demo.config.RetentionConfig;
import com.example.demo.entity.OfficeVisit;
import com.example.demo.form.EraseModel;
import com.example.demo.form.RowDataModel;
import com.example.demo.form.SearchModel;
import com.example.demo.form.VisitorListExitSendModel;
import com.example.demo.service.VisitorListService;

@Controller
@EnableAutoConfiguration
@ComponentScan
@SessionAttributes("s")
public class VisitorListController {

	@Autowired
	private VisitorListService visitorListService;
	@Autowired
	private RetentionConfig rConfig;

	protected final static Logger logger = LoggerFactory.getLogger(VisitorListController.class);

	@ModelAttribute("s")
	public SearchModel getNewInstance() {

		//初期化
		SearchModel init = new SearchModel();
		init = visitorListService.startPageInitialize(init);

		logger.debug(init.toString());

		return init;

	}

	/**
	 * @param s
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(@ModelAttribute("s") SearchModel s, Model model) {

		logger.info("★★★★★ Controller called.");

		EraseModel eraseM = new EraseModel();
		VisitorListExitSendModel sendM = new VisitorListExitSendModel();
		ResultMessages messages = null;

		//検索・表示処理
		DisplayWithSearch(s, model, eraseM, sendM, messages);

		//		//保存期間の設定を読み込んでDeleteModelにセットする
		//		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());
		//
		//		//検索
		//		List<OfficeVisit> list;
		//
		//		try {
		//			list = visitorListService.search(s);
		//			// 検索結果entityをmodelに設定
		//			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
		//			modelList = makeList(list);
		//			model.addAttribute("eraseM", eraseM);
		//			model.addAttribute("sendM", sendM);
		//			model.addAttribute("list", modelList);
		//
		//		} catch (DatabaseException e) {
		//			// TODO 自動生成された catch ブロック
		//			ResultMessages messages = ResultMessages.error().add(e.getCode(), e.getArgString());
		//			model.addAttribute(messages);
		//		}

		logger.debug(model.getAttribute("s").toString());

		//Thymeleaf「VisitorList.html」を表示する;
		return "VisitorList";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute("s") SearchModel s, Model model) {

		EraseModel eraseM = new EraseModel();
		ResultMessages messages = null;

		VisitorListExitSendModel sendM = new VisitorListExitSendModel();

		//検索・表示処理
		DisplayWithSearch(s, model, eraseM, sendM, messages);

		//		//保存期間の設定を読み込んでDeleteModelにセットする
		//		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());
		//
		//		//検索
		//		List<OfficeVisit> list;
		//
		//		try {
		//			list = visitorListService.search(s);
		//			// 検索結果entityをmodelに設定
		//			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
		//			modelList = makeList(list);
		//
		//			messages = ResultMessages.info().add("i.recep.ch.0005", modelList.size());
		//
		//			model.addAttribute("eraseM", eraseM);
		//			model.addAttribute("sendModel", sendModel);
		//			model.addAttribute("list", modelList);
		//		} catch (DatabaseException e) {
		//			// TODO: handle exception
		//			messages = ResultMessages.error().add(e.getCode(), e.getArgString());
		//		}

		logger.debug(model.getAttribute("s").toString());

		return "VisitorList";

	}

	//FIX:_Idをhiddenができないためaction名をURLに付加して取得している
	@RequestMapping(value = "/exit/{id}", method = RequestMethod.POST)
	public String exit(@PathVariable("id") String _id,
			@ModelAttribute("sendModel") VisitorListExitSendModel sendM,
			@ModelAttribute("s") SearchModel s,
			Model model) {

		EraseModel eraseM = new EraseModel();
		OfficeVisit entity = null;
		ResultMessages messages = null;

		//退室処理のパラメータをサービスに渡す
		try {
			entity = visitorListService.updateVisitorList(_id, sendM.getPerson_to_visit());
			messages = ResultMessages.info().add("i.recep.ch.0002", entity.getVisitor_name());
		} catch (DatabaseException e) {
			// データベース関連の例外
			e.printStackTrace();
			messages = ResultMessages.error().add(e.getCode(), e.getArgString());
		}

		//検索・表示処理
		DisplayWithSearch(s, model, eraseM, sendM, messages);

		//		//保存期間の設定を読み込んでDeleteModelにセットする
		//		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());
		//
		//		//検索
		//		List<OfficeVisit> list;
		//
		//		try {
		//			list = visitorListService.search(s);
		//			// 検索結果entityをmodelに設定
		//			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
		//			modelList = makeList(list);
		//
		//			model.addAttribute("eraseM", eraseM);
		//			model.addAttribute("sendModel", sendModel);
		//			model.addAttribute("list", modelList);
		//
		//			messages = ResultMessages.info().add("i.recep.ch.0002", entity.getVisitor_name());
		//			logger.debug(model.getAttribute("s").toString());
		//
		//		} catch (DatabaseException e) {
		//			// TODO: handle exception
		//			messages = ResultMessages.error().add(e.getCode(), e.getArgString());
		//		}

		model.addAttribute(messages);
		//Thymeleaf「VisitorList.html」を表示する;
		return "VisitorList";

	}

	@RequestMapping(value = "/erase", method = RequestMethod.POST)
	public String delete(@ModelAttribute("s") SearchModel s, Model model) {

		EraseModel eraseM = new EraseModel();
		VisitorListExitSendModel sendM = new VisitorListExitSendModel();
		ResultMessages messages = null;
		List<OfficeVisit> resultSearchList;

		//削除処理
		try {
			resultSearchList = visitorListService.eraseVisitorList(rConfig);

			if (resultSearchList.size() == 0) {
				messages = ResultMessages.warning().add("w.recep.ch.5001");
			} else {
				messages = ResultMessages.warning().add("i.recep.ch.0004", resultSearchList.size());
			}

		} catch (DirectoryNotFoundException e) {
			//消去ログのディレクトリが存在しない(e.recep.ch.9001, 設定値)
			messages = ResultMessages.error().add(e.getMessage(), e.getArgString());

		} catch (CantWriteFileException e1) {
			// logファイルに書き込みできない。排他処理の失敗(w.recep.ch.5000, 消去件数)
			e1.printStackTrace();
			messages = ResultMessages.warning().add(e1.getMessage(), e1.getArgString());
		} catch (CustomException e2) {
			// IOExcepion発生時 e.recep.ch.9002
			messages = ResultMessages.warning().add(e2.getMessage(), e2.getArgString());
		}

		model.addAttribute(messages);

		//検索・表示処理
		DisplayWithSearch(s, model, eraseM, sendM, messages);

		//		//保存期間の設定を読み込んでDeleteModelにセットする
		//		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());
		//
		//		//検索
		//		List<OfficeVisit> list;
		//		try {
		//			list = visitorListService.search(s);
		//			// 検索結果entityをmodelに設定
		//			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
		//			modelList = makeList(list);
		//
		//			model.addAttribute("eraseM", eraseM);
		//			model.addAttribute("sendM", sendM);
		//			model.addAttribute("list", modelList);
		//			model.addAttribute(messages);
		//
		//		} catch (Exception e) {
		//			// TODO: handle exception
		//		}

		//Thymeleafを表示する;
		return "VisitorList";

	}

	/**検索結果をVIEW用に書式変更を行うメソッド
	 * OfficeVisit型⇒RowDataModelへの変換
	 * @param list
	 * @return <RowDataModel>型のList
	 */
	private List<RowDataModel> makeList(List<OfficeVisit> list) {

		List<RowDataModel> rowDataList = new ArrayList<RowDataModel>();

		for (OfficeVisit e : list) {
			RowDataModel m = new RowDataModel();

			//入室時間の書式変更⇒文字列化
			if (e.getVisited_at().format(DateTimeFormatter.ISO_LOCAL_DATE)
					.equals(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))) {
				//同じ日付であれば、時間部分(HH:mm)のみ
				m.setVisited_at(e.getVisited_at().format(DateTimeFormatter.ofPattern("HH:mm")));

			} else {
				//違う日付であれば、「yyyy/MM/dd HH:mm」形式で表示
				m.setVisited_at(e.getVisited_at().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
			}

			//退室時間の書式変更⇒文字列化
			if (e.getLeft_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					.equals("2000-01-01 00:00:00")) {
				//退室時間されていない場合
				m.setLeft_at("");
			} else {
				//退室されている場合
				if (e.getLeft_at().format(DateTimeFormatter.ISO_LOCAL_DATE)
						.equals(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))) {
					//同じ日付であれば、時間部分(HH:mm)のみ
					m.setLeft_at(e.getLeft_at().format(DateTimeFormatter.ofPattern("HH:mm")));

				} else {
					//違う日付であれば、「yyyy/MM/dd HH:mm」形式で表示
					m.setLeft_at(e.getLeft_at().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
				}
			}
			//_id
			m.set_id(e.get_id());
			//名前
			m.setVisitor_name(e.getVisitor_name());
			//会社
			m.setVisitor_org(e.getVisitor_org());
			//人数
			m.setVisitor_count(e.getVisitor_count());
			//訪問先
			m.setPerson_to_visit(e.getPerson_to_visit());
			//滞在時間
			int hrs;
			long minutes;
			if (m.getLeft_at() == "") {
				//現在時刻との差をとる
				minutes = ChronoUnit.MINUTES.between(e.getVisited_at(), LocalDateTime.now());

			} else {
				//退室時間との差をとる
				minutes = ChronoUnit.MINUTES.between(e.getVisited_at(), e.getLeft_at());
			}
			m.setDiffMinutes(minutes);

			hrs = (int) minutes / 60;
			minutes = Math.abs(minutes % 60);
			//ケタ埋めしてセットする
			m.setDiffTime(String.format("%02d", hrs) + ":" + String.format("%02d", minutes));

			rowDataList.add(m);
		}

		return rowDataList;
	}

	public void DisplayWithSearch(@ModelAttribute("s") SearchModel s, Model model, EraseModel eraseM,
			VisitorListExitSendModel sendM, ResultMessages messages) {

		List<OfficeVisit> list;

		//保存期間の設定を読み込んでDeleteModelにセットする
		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());

		//検索
		try {
			list = visitorListService.search(s);

			// 検索結果entityをmodelに設定
			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
			modelList = makeList(list);

			if (messages == null) {
				messages = ResultMessages.info().add("i.recep.ch.0005", modelList.size());
			} else {
				messages.add("i.recep.ch.0005", modelList.size());
			}

			model.addAttribute("eraseM", eraseM);
			model.addAttribute("sendM", sendM);
			model.addAttribute("list", modelList);
			model.addAttribute(messages);

		} catch (DatabaseException e) {
			// TODO: handle exception
			messages = ResultMessages.error().add("e.recep.ch.9003");
			model.addAttribute(messages);
		}

	}
}
