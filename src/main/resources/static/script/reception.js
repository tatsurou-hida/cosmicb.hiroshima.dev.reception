$(function() {
	// 初期画面でのクリック動作
	$('#btn-init-accept').on('click', function() {
		$('#carousel-main').carousel('next');
		});
	$('#btn-submit').on('click', function() {
		var name = this.form.inputName.value;
		if(name=="") {
			alert("お名前をお願いします");
		} else {
			$('#receform').submit();
			$("#carousel-main").attr('data-interval', '5000');
			$('#carousel-main').carousel('next');
		}
	});
});