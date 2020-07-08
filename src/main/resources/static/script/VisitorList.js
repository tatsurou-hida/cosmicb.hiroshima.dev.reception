/**
 *
 */


jQuery(function($) {
	$("#addData")
			.click(
					function(event) {
						//データ追加ボタンがクリックされたら要素を書き換えて、データ追加ボタンを使用不可にする
						$("#addData").replaceWith("<button type='button' id='addData' class='btn btn-secondary btn-md' disabled>データ追加</button>");

							var today = new Date();
							var hour = today.getHours();
							var minute = today.getMinutes();
							var second = today.getSeconds();
							hour = ('0' + hour).slice(-2);
							minute = ('0' + minute).slice(-2);
							second = ('0' + second).slice(-2);

							$("#visitorlist")
									.append(
											"<tr>"
													+ "<td class='align-middle mx-auto'>"
													+ hour
													+ ":"
													+ minute
													+ ":"
													+ second
													+ "</td>"
													+ "<td colspan='2'><input id = 'visitorOrg' type='text' class='form-control' name='visitorOrg' value='' placeholder='会社名を入力'></td>" // 所属欄
													+ "<td colspan='2'><input id = 'visitorName' type='text' class='form-control' name='visitorName' value='' placeholder='名前を入力' required></td></td>" // 名前欄
													+ "<td  id = 'visitor_count'>"//人数欄だけcssで幅をIDで設定。幅がある程度広くないと人数プルダウンの中身が表示できないため
													+ "		<select id='visitorCount' class='form-control' >"
													+ "			<option value='1'>1</option>"
													+ "			<option value='2'>2</option>"
													+ "			<option value='3'>3</option>"
													+ "			<option value='4'>4</option>"
													+ "			<option value='5'>5</option>"
													+ "			<option value='6'>6</option>"
													+ "			<option value='7'>7</option>"
													+ "			<option value='8'>8</option>"
													+ "		</select>"
													+ "</td>"//人数欄
													+ "<td></td>" // 経過時間欄
													//追加処理ボタンをクリックしたときに送信されるフォームを追加　非表示テキスト（会社、名前、人数）
													//行タグ<tr>にフォームをセットできなかったため、いったん非表示テキストに画面入力項目をセットし、その非表示テキストをコントローラーに渡す													+ "<td>"
													+ "<td>"
													+ "		<form action='/btnAdd'method='post'>"
													+ "			<input hidden id = 'hiddenVisitorOrg' type='text' class='form-control' name='hiddenVisitorOrg' value='' placeholder='（非表示）会社名'>"
													+ "			<input hidden id = 'hiddenVisitorName' type='text' class='form-control' name='hiddenVisitorName' value='' placeholder='（非表示）名前'>"
													+ "			<input hidden id = 'hiddenVisitorCount' type='text' class='form-control' name='hiddenVisitorCount' value='' placeholder='（非表示）人数'>"
													+ "			<button type='submit' id='btnAdd' class='btn btn-outline-secondary'>追加処理</button>"
													+ "		</form>"
													+ "</td>"
													+ "<td>"
													+ "</td>" // 退室時間
													+ "</tr>");
					});
	$("td[id^=org]")
			.click(
					function() {
						if (!$(this).hasClass('on')) {
							$(this).addClass('on');
							var txt = $(this).text();
							if (this.parentNode.cells.exitime.outerText != "") {
								// 退室時間が入力されていたら処理を中断する
								exit;
							}
							$(this)
									.html(
											'<input type="text" value="' + txt
													+ '" />');
							$('[id^=org] > input')
									.focus()
									.blur(
											function() {
												var inputVal = $(this).val();
												var _id = this.parentNode.parentNode.cells["diff"].attributes.name.value;
												var visitor_name = this.parentNode.parentNode.cells["name"].outerText;
												if (inputVal == "") {
													inputVal = txt;
												}
												$(this).parent().removeClass(
														'on').text(inputVal);

												// AJAX処理
												$
														.ajax(
																{
																	url : "getJsonData",
																	type : "GET",
																	data : {
																		"visitor_org" : inputVal,
																		"visitor_name" : visitor_name,
																		"visitor_id" : _id
																	},
																	dataType : "json",
																	contentType : "application/json; charset=utf-8",
																	timeout : 5000
																})
														.done(
																function(
																		data,
																		textStatus,
																		jqXHR) {
																	console
																			.log("done! AJAX is Success!")
																	console
																			.log(data);
																})
														.fail(
																function(
																		jqXHR,
																		textStatus,
																		errorThrown) {
																	console
																			.log("fail! AJAX is Fail!");
																	console
																			.log(jqXHR);
																	// 失敗した場合、編集を元に戻す
																	$(this)
																			.parent()
																			.removeClass(
																					'on')
																			.text(
																					txt);
																})
											});
						}
					});

	$("[id^=name]")
			.click(
					function() {
						if (!$(this).hasClass('on')) {
							$(this).addClass('on');
							var txt = $(this).text();
							if (this.parentNode.cells.exitime.outerText != "") {
								// 退室時間が入力されていたら処理を中断する
								exit;
							}
							$(this)
									.html(
											'<input type="text" value="' + txt
													+ '" />');
							$('[id^=name] > input')
									.focus()
									.blur(
											function() {
												var inputVal = $(this).val();
												var _id = this.parentNode.parentNode.cells["diff"].attributes.name.value;
												var visitor_org = this.parentNode.parentNode.cells["org"].outerText;

												if (inputVal == "") {
													inputVal = txt;
												}
												$(this).parent().removeClass(
														'on').text(inputVal);

												// AJAX処理
												$
														.ajax(
																{
																	url : "getJsonData",
																	type : "GET",
																	data : {
																		"visitor_org" : visitor_org,
																		"visitor_name" : inputVal,
																		"visitor_id" : _id
																	},
																	dataType : "json",
																	contentType : "application/json; charset=utf-8",
																	timeout : 5000
																})
														.done(
																function(
																		data,
																		textStatus,
																		jqXHR) {
																	console
																			.log("done! AJAX is Success!")
																	console
																			.log(data);
																})
														.fail(
																function(
																		jqXHR,
																		textStatus,
																		errorThrown) {
																	console
																			.log("fail! AJAX is Fail!");
																	console
																			.log(jqXHR);
																	// 失敗した場合、編集を元に戻す
																	$(this)
																			.parent()
																			.removeClass(
																					'on')
																			.text(
																					txt);
																})
											});
						}
					});

	// 画像のmodal表示
	$("img.toggle-modal").click(function() {
		$("#modal-display").html($(this).prop('outerHTML'));
		$("#modal-display").fadeIn(200);
	});
	$("#modal-display, #modal-display img").click(function() {
		$("#modal-display").fadeOut(200);
	});
});

$(document).on("click", "#btnAdd", function() {
	// 動的に作成した追加処理ボタンクリック時の処理

	//画面で入力or選択した会社、名前、人数を追加処理ボタンクリック時に送信されるフォーム（非表示テキスト）にいったんセットする
	document.getElementById( "hiddenVisitorOrg" ).value = document.getElementById( "visitorOrg" ).value
	document.getElementById( "hiddenVisitorName" ).value = document.getElementById( "visitorName" ).value
	document.getElementById( "hiddenVisitorCount" ).value = document.getElementById( "visitorCount" ).value
});
