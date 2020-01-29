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

import com.example.demo.OfficeVisit;
import com.example.demo.ShirahataException;
import com.example.demo.config.RetentionConfig;
import com.example.demo.config.SpringDataMongoDBConfig;
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
	private SpringDataMongoDBConfig mongoConfig;
	@Autowired
	private RetentionConfig rConfig;

	protected final static Logger logger = LoggerFactory.getLogger(VisitorListController.class);

	@ModelAttribute("s")
	public SearchModel getNewInstance() {

		logger.trace("=================================");
		logger.trace("Create new instance for a session");

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

		EraseModel eraseM = new EraseModel();
		VisitorListExitSendModel sendModel = new VisitorListExitSendModel();

		logger.info("★★★★★ Controller called.");

		//保存期間の設定を読み込んでDeleteModelにセットする
		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());

		//検索
		List<OfficeVisit> list;

		try {
			list = visitorListService.search(s, mongoConfig);
			// 検索結果entityをmodelに設定
			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
			modelList = makeList(list);
			model.addAttribute("eraseM", eraseM);
			model.addAttribute("sendModel", sendModel);
			model.addAttribute("list", modelList);

		} catch (ShirahataException e) {
			// TODO 自動生成された catch ブロック
			ResultMessages messages = ResultMessages.error().add(e.getCode(), e.getArgString());
			model.addAttribute(messages);
		}

		logger.debug(model.getAttribute("s").toString());

		//Thymeleaf「VisitorList.html」を表示する;
		return "VisitorList";
	}

	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute("s") SearchModel s, Model model) {

		EraseModel eraseM = new EraseModel();

		VisitorListExitSendModel sendModel = new VisitorListExitSendModel();

		//保存期間の設定を読み込んでDeleteModelにセットする
		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());

		//検索
		List<OfficeVisit> list;
		try {
			list = visitorListService.search(s, mongoConfig);
			// 検索結果entityをmodelに設定
			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
			modelList = makeList(list);

			model.addAttribute("eraseM", eraseM);
			model.addAttribute("sendModel", sendModel);
			model.addAttribute("list", modelList);
		} catch (Exception e) {
			// TODO: handle exception
		}

		logger.debug(model.getAttribute("s").toString());

		//Thymeleaf「VisitorList.html」を表示する;
		return "VisitorList";

	}

	@RequestMapping(value = "/exit/{id}", method = RequestMethod.POST)
	public String exit(@PathVariable("id") String _id,
			@ModelAttribute("sendModel") VisitorListExitSendModel sendModel,
			@ModelAttribute("s") SearchModel s,
			Model model) {

		//FIX:_Idをhiddenができないためaction名をURLに付加して取得している

		EraseModel eraseM = new EraseModel();

		//退室処理のパラメータをサービスに渡す
		visitorListService.updateVisitorList(_id, sendModel.getPerson_to_visit(), mongoConfig);

		//保存期間の設定を読み込んでDeleteModelにセットする
		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());

		//検索
		List<OfficeVisit> list;

		try {
			list = visitorListService.search(s, mongoConfig);
			// 検索結果entityをmodelに設定
			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
			modelList = makeList(list);

			model.addAttribute("eraseM", eraseM);
			model.addAttribute("sendModel", sendModel);
			model.addAttribute("list", modelList);

			logger.debug(model.getAttribute("s").toString());

		} catch (Exception e) {
			// TODO: handle exception
		}

		//Thymeleaf「VisitorList.html」を表示する;
		return "VisitorList";

	}

	@RequestMapping(value = "/erase", method = RequestMethod.POST)
	public String delete(@ModelAttribute("s") SearchModel s, Model model) {

		EraseModel eraseM = new EraseModel();
		VisitorListExitSendModel sendModel = new VisitorListExitSendModel();

		//削除処理
		String msg = visitorListService.eraseVisitorList(mongoConfig, rConfig);
		if (msg.equals("No exist directory")) {
			msg = "Error: " + msg + "\n"
					+ "設定ファイル指定のパスにディレクトリが存在しません。" + "\n"
					+ "設定値: " + rConfig.getPersontovisit().getLogfilepath();
		} else if (msg.equals("IOException")) {
			msg = "Error: " + msg + "\n"
					+ "ファイルやネットワークなど入出力に関係する問題を検出しました。";
		} else if (msg.equals("Can't write logfile")) {
			msg = "Error: " + msg + "\n"
					+ "ログファイルにデータを書き込むことができませんでした。" + "\n"
					+ "消去対象データはデータベースから消去されている場合があります。";
		}

		//保存期間の設定を読み込んでDeleteModelにセットする
		eraseM.setPeriod(rConfig.getPersontovisit().getPeriod());

		//検索
		List<OfficeVisit> list;
		try {
			list = visitorListService.search(s, mongoConfig);
			// 検索結果entityをmodelに設定
			List<RowDataModel> modelList = new ArrayList<RowDataModel>();
			modelList = makeList(list);

			model.addAttribute("eraseM", eraseM);
			model.addAttribute("sendModel", sendModel);
			model.addAttribute("list", modelList);
			model.addAttribute("msg", msg);

		} catch (Exception e) {
			// TODO: handle exception
		}

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
}
