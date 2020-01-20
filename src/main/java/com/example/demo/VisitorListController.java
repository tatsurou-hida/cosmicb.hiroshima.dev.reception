package com.example.demo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.demo.config.RetentionConfig;

@Controller
@EnableAutoConfiguration
@ComponentScan
public class VisitorListController {

	@Autowired
	private VisitorListService visitorListService;
	@Autowired
	private RetentionConfig rConfig;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String index(Model model) {

		SearchModel searchM = new SearchModel();
		DeleteModel delM = new DeleteModel();
		VisitorListExitSendModel sendModel = new VisitorListExitSendModel();

		System.out.println("★★★★★ Controller called.");
		visitorListService.startPageInitialize(searchM);

		//保存期間の設定を読み込む
		visitorListService.setDeletePeriod(rConfig, delM);

		//検索
		visitorListService.search(searchM);

		// 検索結果entityをmodelに設定
		List<RowDataModel>modelList = new ArrayList<RowDataModel>();
		modelList = makeList(searchM, modelList);

		model.addAttribute("delM",delM);
		model.addAttribute("searchM", searchM);
		model.addAttribute("sendModel", sendModel);
		model.addAttribute("list", modelList);

		//Thymeleaf「VisitorList.html」を表示する;
		return "VisitorList";
	}


	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public String search(@ModelAttribute SearchModel searchM, Model model) {

		//検索
		visitorListService.search(searchM);

		//form内の部品に値を入れる(入力内容の保持)
		model.addAttribute("searchM", searchM);
		//Thymeleafを表示する
		return "VisitorList";

	}


	@RequestMapping(value = "/exit/{id}", method = RequestMethod.POST)
	private String exit(@PathVariable("id") String _id,
						@ModelAttribute VisitorListExitSendModel sendModel, Model model) {

		//退室処理のパラメータをサービスに渡す
		visitorListService.updateVisitorLeft(_id, sendModel.getPerson_to_visit());

		index(model);

		//Thymeleafを表示する;
		return "VisitorList";
	}


	private List<RowDataModel> makeList(SearchModel searchM, List<RowDataModel>list) {

		for (OfficeVisit e : searchM.getResultSearchList()) {
			RowDataModel m = new RowDataModel();

			String s1 = e.getVisited_at().format(DateTimeFormatter.ISO_LOCAL_DATE);
			String s2= LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE);

			//入室時間の書式変更⇒文字列化
			if (e.getVisited_at().format(DateTimeFormatter.ISO_LOCAL_DATE)
					.equals(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE))) {
				//同じ日付であれば、時間部分(HH:mm)のみ
				m.setVisited_at(e.getVisited_at().format(DateTimeFormatter.ofPattern("HH:mm")));

			} else {
				//違う日付であれば、「yyyy/MM/dd HH:mm」形式で表示
				m.setVisited_at(e.getVisited_at().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm")));
			}

			String s3 = e.getLeft_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

			//退室時間の書式変更⇒文字列化
			if (e.getLeft_at().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
					.equals("2000-01-01 09:00:00")) {
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
			hrs = (int)minutes / 60;
			minutes = Math.abs(minutes % 60);
			//ケタ埋めしてセットする
			m.setDiffTime(String.format("%02d", hrs) + ":" + String.format("%02d", minutes));

			list.add(m);
		}


		return list;
	}



}
