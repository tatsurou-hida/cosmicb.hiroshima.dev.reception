/**
 *
 */
$(function() {

	// canvasのサイズ調整
	var $wrapper = $('.canvas-wrapper');
	$wrapper.children('canvas').attr('width', $wrapper.width()).attr('height',
			$wrapper.height());

	// var canvas = document.getElementById('canvassample');
	var canvas = $('canvas.handwriting')[0];
	var ctx = canvas.getContext('2d');
	var moveflg = 0;
	var Xpoint;
	var Ypoint;

	// 初期値（サイズ、色、アルファ値）の決定
	var defSize = 3, defColor = "#222";

	// ストレージの初期化
	var myStorage = localStorage;
	window.onload = initLocalStorage();

	// PC対応
	canvas.addEventListener('mousedown', startPoint, false);
	canvas.addEventListener('mousemove', movePoint, false);
	canvas.addEventListener('mouseup', endPoint, false);
	// スマホ対応
	canvas.addEventListener('touchstart', startPoint, false);
	canvas.addEventListener('touchmove', movePoint, false);
	canvas.addEventListener('touchend', endPoint, false);

	$('button.handwriting-reset').on('click', clearCanvas);
	$('#doOCR').on('click', convertToDataURL)

	// 画像のmodal表示
	$("img.toggle-modal").click(function() {
		$("#modal-display").html($(this).prop('outerHTML'));
		$("#modal-display").fadeIn(200);
	});
	$("#modal-display, #modal-display img").click(function() {
		$("#modal-display").fadeOut(200);
	});

	function startPoint(e) {
		e.preventDefault();
		ctx.beginPath();

		Xpoint = e.layerX;
		Ypoint = e.layerY;

		ctx.moveTo(Xpoint, Ypoint);
	}

	function movePoint(e) {
		if (e.buttons === 1 || e.witch === 1 || e.type == 'touchmove') {
			Xpoint = e.layerX;
			Ypoint = e.layerY;
			moveflg = 1;

			ctx.lineTo(Xpoint, Ypoint);
			ctx.lineCap = "round";
			ctx.lineWidth = defSize * 2;
			ctx.strokeStyle = defColor;
			ctx.stroke();

		}
	}

	function endPoint(e) {

		if (moveflg === 0) {
			ctx.lineTo(Xpoint - 1, Ypoint - 1);
			ctx.lineCap = "round";
			ctx.lineWidth = defSize * 2;
			ctx.strokeStyle = defColor;
			ctx.stroke();

		}
		moveflg = 0;
		setLocalStoreage();
	}

	function clearCanvas() {
		initLocalStorage();
		temp = [];
		resetCanvas();
	}

	function resetCanvas() {
		ctx.clearRect(0, 0, ctx.canvas.clientWidth, ctx.canvas.clientHeight);
	}

	function convertToDataURL() {
		$('input.handwriting-dataurl').val(canvas.toDataURL());
	}

	function initLocalStorage() {
		myStorage.setItem("__log", JSON.stringify([]));
	}
	function setLocalStoreage() {
		var png = canvas.toDataURL();
		var logs = JSON.parse(myStorage.getItem("__log"));

		setTimeout(function() {
			logs.unshift({
				0 : png
			});

			myStorage.setItem("__log", JSON.stringify(logs));

			currentCanvas = 0;
			temp = [];
		}, 0);
	}

	function draw(src) {
		var img = new Image();
		img.src = src;

		img.onload = function() {
			ctx.drawImage(img, 0, 0);
		}
	}
});
