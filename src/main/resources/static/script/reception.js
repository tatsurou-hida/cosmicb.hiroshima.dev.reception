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

$(function() {
	var $inputTarget;

	var canvas = $('canvas.handwriting')[0];

	var ctx = canvas.getContext('2d');
	var moveflg = 0;
	var Xpoint;
	var Ypoint;
	var ox, oy, x, y;
	var isMoving = false;

	var defSize = 3, defColor = "#222";

	// PC対応
	canvas
			.addEventListener('mousedown', onMouseDown,
					false);
	canvas
			.addEventListener('mousemove', onMouseMove,
					false);
	canvas.addEventListener('mouseup', onMouseUp, false);
	// スマホ対応
	canvas.addEventListener('touchstart', onDown, false);
	canvas.addEventListener('touchmove', onMove, false);
	canvas.addEventListener('touchend', onUp, false);

	resetCanvas();

	$('button.handwriting-reset').on('click', resetCanvas);

	function onDown(event) {
		event.preventDefault();
		isMoving = true;
		ox = event.touches[0].pageX
				- event.target.getBoundingClientRect().left;
		oy = event.touches[0].pageY
				- event.target.getBoundingClientRect().top;
		event.stopPropagation();
	}
	function onMouseDown(event) {
		event.preventDefault();
		ox = event.clientX
				- event.target.getBoundingClientRect().left;
		oy = event.clientY
				- event.target.getBoundingClientRect().top;
		isMoving = true;
	}

	function onMove(event) {
		if (isMoving) {
			x = event.touches[0].pageX
					- event.target.getBoundingClientRect().left;
			y = event.touches[0].pageY
					- event.target.getBoundingClientRect().top;
			drawLine();
			ox = x;
			oy = y;
			event.preventDefault();
			event.stopPropagation();
		}
	}
	function onMouseMove(event) {
		if (isMoving) {
			x = event.clientX
					- event.target.getBoundingClientRect().left;
			y = event.clientY
					- event.target.getBoundingClientRect().top;
			drawLine();
			ox = x;
			oy = y;
		}
	}

	function onUp(event) {
		isMoving = false;
		event.stopPropagation();
	}
	function onMouseUp(event) {
		isMoving = false;
	}

	function resetCanvas() {
		ctx.clearRect(0, 0, ctx.canvas.clientWidth,
				ctx.canvas.clientHeight);
	}

	function convertToDataURL() {
		$('input.handwriting-dataurl').val(
				canvas.toDataURL());
	}

	function draw(src) {
		var img = new Image();
		img.src = src;

		img.onload = function() {
			ctx.drawImage(img, 0, 0);
		}
	}

	function drawLine() {
		ctx.beginPath();
		ctx.moveTo(ox, oy);
		ctx.lineTo(x, y);
		ctx.stroke();
	}

	$("#modalOK").click(
			function(e) {
				if ($inputTarget) {
					let dataUrl = canvas.toDataURL();
					$inputTarget.val(dataUrl);
					$inputTarget.prevAll("img").attr("src",
							dataUrl);
					resetCanvas();
				}
			});

	$("#inputNameButton").click(
			function(e) {
				$("#inputModalLabel").html(
						"お名前をタッチペンで記入してください");
				$inputTarget = $("#inputName");
			});

	$("#inputCompanyButton").click(
			function(e) {
				$("#inputModalLabel").html(
						"所属／会社をタッチペンで記入してください");
				$inputTarget = $("#inputCompany");
			});
});
