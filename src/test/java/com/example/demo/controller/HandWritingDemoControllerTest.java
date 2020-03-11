package com.example.demo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import jp.co.cosmicb.reception.ReceptionApplication;

@AutoConfigureMockMvc // MockMvcを利用するため
@SpringBootTest(classes = ReceptionApplication.class) // @SpringBootApplicationアノテーションが付与されているクラス
class HandWritingDemoControllerTest {

	//mockMvc TomcatサーバへデプロイすることなくHttpリクエスト・レスポンスを扱うためのMockオブジェクト
	@Autowired
	private MockMvc mockMvc;

	@Test
	void testIndex() throws Exception {

		// レスポンスのHTTPステータスコードは正しいか？
		// 指定のviewを返すか？
		// modelに正しい変数を詰められているか？
		this.mockMvc.perform(get("/handwriting_demo"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(view().name("handwriting_demo"))
				.andExpect(model().attributeExists("form"));

	}

	@Test
	void testPost() throws Exception {
		//this.mockMvc.perform(post("/handwriting_demo")).andDo(print())
		//		.andExpect(status().isOk()).andExpect(view().name("handwriting_demo"))
		//		.andExpect(model().attributeExists("form"));
	}

}
