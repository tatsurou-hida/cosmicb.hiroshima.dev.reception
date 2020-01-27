$(function() {
	// 初期画面でのクリック動作
	$('#btn-init-accept').on('click', function() {
		$('#carousel-main').carousel('next');

	});

	// 受付画面の名前入力チェック
	$('#btn-submit').on('click', function() {
		var name = this.form.inputName.value;

		if(name=="") {
			alert("お名前をお願いします");

		} else {
			// 制御をconrollerへ（データ登録）
			$('#receform').submit();

			// ３枚目画面へ遷移
			$('#carousel-main').carousel('next');

		}
	});
	// ３枚目の画面のインターバルを制御したいが断念
//	$( '#carousel-main' ).on( 'slid.bs.carousel', function() {
//	    if ( $( '#announce' ).hasClass( 'active' ) ) {
//			$('#carousel-main').carousel({
//				interval: 5000
//			});
//	    }
//	});
});
