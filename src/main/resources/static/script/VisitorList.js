/**
 *
 */
jQuery(function($) {
	$("#addData")
			.click(
					function(event) {
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
												+ "<td><input type='text' class='form-control' name='visitorOrg' value='' placeholder='会社名を入力'></td>" // 所属欄
												+ "<td><input type='text' class='form-control' name='visitorName' value='' placeholder='名前を入力' required></td></td>" // 名前欄
												+ "<td></td>" // 経過時間欄
												+ "<td><button type='submit' id='btnAdd' class='btn btn-outline-secondary'>追加処理</button></td>"
												+ "<td></td>" // 退室時間
												+ "</tr>");
					});
});

$(document).on("click", "#btnAdd", function() {
	//動的に作成した追加処理の処理（発火確認済み）


});

jQuery(function($) {
	$("[id^=org]")
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
});

jQuery(function($) {
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
});