/**
 *
 */
jQuery(function($) {
	$("[id^=org]").click(function() {
		if (!$(this).hasClass('on')) {
			$(this).addClass('on');
			var txt = $(this).text();
			if(this.parentNode.cells.exitime.outerText != ""){
				//退室時間が入力されていたら処理を中断する
				exit;
			}
			$(this).html('<input type="text" value="' + txt + '" />');
			$('[id^=org] > input').focus().blur(function() {
				var inputVal = $(this).val();
				var _id = this.parentNode.parentNode.cells["diff"].attributes.name.value;
				var visitor_name = this.parentNode.parentNode.cells["name"].outerText;
				if (inputVal == ""){
					inputVal = txt;
				}
				$(this).parent().removeClass('on').text(inputVal);

				//AJAX処理
				$.ajax({
					url : "getJsonData",
					type : "GET",
					data :{
						"visitor_org": inputVal,
						"visitor_name": visitor_name,
						"visitor_id": _id
					},
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					timeout : 5000
				})
				.done(function(data, textStatus, jqXHR){
					console.log("done! AJAX is Success!")
					console.log(data);
				})
				.fail(function(jqXHR, textStatus, errorThrown){
					console.log("fail! AJAX is Fail!");
					console.log(jqXHR);
					//失敗した場合、編集を元に戻す
					$(this).parent().removeClass('on').text(txt);
				})
			});
		}
	});
});

jQuery(function($) {
	$("[id^=name]").click(function() {
		if (!$(this).hasClass('on')) {
			$(this).addClass('on');
			var txt = $(this).text();
			if(this.parentNode.cells.exitime.outerText != ""){
				//退室時間が入力されていたら処理を中断する
				exit;
			}
			$(this).html('<input type="text" value="' + txt + '" />');
			$('[id^=name] > input').focus().blur(function() {
				var inputVal = $(this).val();
				var _id = this.parentNode.parentNode.cells["diff"].attributes.name.value;
				var visitor_org = this.parentNode.parentNode.cells["org"].outerText;

				if (inputVal == ""){
					inputVal = txt;
				}
				$(this).parent().removeClass('on').text(inputVal);

				//AJAX処理
				$.ajax({
					url : "getJsonData",
					type : "GET",
					data :{
						"visitor_org": visitor_org,
						"visitor_name": inputVal,
						"visitor_id": _id
					},
					dataType : "json",
					contentType : "application/json; charset=utf-8",
					timeout : 5000
				})
				.done(function(data, textStatus, jqXHR){
					console.log("done! AJAX is Success!")
					console.log(data);
				})
				.fail(function(jqXHR, textStatus, errorThrown){
					console.log("fail! AJAX is Fail!");
					console.log(jqXHR);
					//失敗した場合、編集を元に戻す
					$(this).parent().removeClass('on').text(txt);
				})
			});
		}
	});
});