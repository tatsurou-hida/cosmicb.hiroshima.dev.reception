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
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.example.demo.OfficeVisit;
import com.example.demo.config.RetentionConfig;
import com.example.demo.config.SpringDataMongoDBConfig;
import com.example.demo.form.SearchModel;
import com.example.demo.repository.VisitorListRepository;
import com.mongodb.client.MongoClients;

@Service
public class VisitorListService {

	@Autowired
	private VisitorListRepository visitorListRepository;

	protected final static Logger logger = LoggerFactory.getLogger(VisitorListService.class);

	public void startPageInitialize(SearchModel searchM) {

		LocalDate maxDate = LocalDate.now();
		LocalDate minDate = maxDate.minusDays(7);

		searchM.setInputMinDate(minDate.toString());
		searchM.setInputMaxDate(maxDate.toString());
		searchM.setChecked(true);
	}


	public List<OfficeVisit> search(SearchModel searchM, SpringDataMongoDBConfig mongoConfig) {
		//カレンダーおよび未退室チェックボックスの情報から検索する
		System.out.println("★★★★★ Service - search called.");

		List<OfficeVisit> resultSearchList;

		LocalDateTime inputMinDateTime = toLocalDateTime(searchM.getInputMinDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		LocalDateTime inputMaxDateTime = toLocalDateTime(searchM.getInputMaxDate() + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
		boolean checked = searchM.isChecked();

		System.out.println(inputMaxDateTime);
		System.out.println(inputMinDateTime);
		System.out.println(checked);

		//クエリ上の都合でinputMaxDateTimeに＋1日する
		inputMaxDateTime = inputMaxDateTime.plusDays(1);

		Query query = new Query();

		if (checked) {
			//入室者検索

			query.addCriteria(Criteria.
					where("person_to_visit").is("")
					.andOperator(
						Criteria.where("visited_at").gte(inputMinDateTime),
						Criteria.where("visited_at").lt(inputMaxDateTime)
					));

		} else {
			//全件検索
			query.addCriteria(new Criteria()
					.andOperator(
						Criteria.where("visited_at").gte(inputMinDateTime),
						Criteria.where("visited_at").lt(inputMaxDateTime)
					));
		}

		query.with(Sort.by(Sort.Direction.DESC, "visited_at"));
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()), mongoConfig.getDatabase());
		resultSearchList = mongoOps.find(query, OfficeVisit.class);

		return resultSearchList;

	}


	public void updateVisitorList(String id, String personToVisit, SpringDataMongoDBConfig mongoConfig) {

		Query query = new Query();
		query.addCriteria(Criteria.where("_id").is(id));

		Update update = new Update();
		update.set("left_at", LocalDateTime.now());
		update.set("person_to_visit", personToVisit);

		//DB接続
		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()), mongoConfig.getDatabase());
		//DB更新
		mongoOps.updateFirst(query, update, OfficeVisit.class);

	}


	public String deleteVisitorList(SpringDataMongoDBConfig mongoConfig, RetentionConfig rConfig) {

		List<OfficeVisit> resultSearchList;


		//保存期間を設定ファイルから取得
		int period = rConfig.getPersontovisit().getPeriod();
		LocalDateTime startingDate = LocalDateTime.now().minusMonths(period);
		//保存先を設定ファイルから取得
		String logFileName = rConfig.getPersontovisit().getLogfilepath();
		File file = new File(logFileName);

		//消去対象ログを取得
		Query query = new Query();
		query.addCriteria(new Criteria()
				.andOperator(
					Criteria.where("visited_at").lt(startingDate),
					Criteria.where("person_to_visit").ne("")
				));
		query.with(Sort.by(Sort.Direction.DESC, "visited_at"));

		MongoOperations mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()), mongoConfig.getDatabase());
		resultSearchList = mongoOps.find(query, OfficeVisit.class);

		//取得したログに対してそれぞれ消去していく
		for (int i=0; i < resultSearchList.size(); i++) {

			query = new Query();
			query.addCriteria(Criteria
					.where("_id").is(resultSearchList.get(i).get_id())
					);
			mongoOps = new MongoTemplate(MongoClients.create(mongoConfig.getUri()), mongoConfig.getDatabase());
			mongoOps.remove(query, OfficeVisit.class);

			//ログ書き込み
			try
				(BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));){

				if (checkFileState(file)) {

					bw.write(LocalDateTime.now().format(
							DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + ",");
					bw.write(resultSearchList.get(i).get_id() + ",");
					bw.write(resultSearchList.get(i).getVisited_at().format(
							DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")) + ",");
					bw.write(resultSearchList.get(i).getVisitor_org() + ",");
					bw.write(resultSearchList.get(i).getVisitor_name() + ",");
					bw.write(resultSearchList.get(i).getVisitor_count() + ",");
					bw.write(resultSearchList.get(i).getPerson_to_visit() + ",");
					bw.write(resultSearchList.get(i).getLeft_at().format(
							DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
					bw.newLine();
					bw.close();
				} else {
					//ログファイルに書き込みできない場合
					return "Can't write logfile";
				}

			} catch (FileNotFoundException e) {

				return "FileNotFoundException ";

			} catch (IOException e) {
				e.printStackTrace();
				return "IOException";
			}
		}
		return "success (消去対象件数: " + resultSearchList.size() + "件)";
	}

	private boolean checkFileState(File f) {

		int count =0;

		if (f.canWrite()) {
			return true;
		} else {
			while (f.canWrite()==false) {
				count ++;
				if (count > 100) {
					return false;
				}
			}
			return true;
		}
	}


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
