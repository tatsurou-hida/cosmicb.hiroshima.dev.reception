/**
 *
 */
$(function() {

	// canvasのサイズ調整
//	var $wrapper = $('.canvas-wrapper');
//	$wrapper.children('canvas').attr('width', $wrapper.width()).attr('height',
//			$wrapper.height());

	// var canvas = document.getElementById('canvassample');
	var canvas = $('canvas.handwriting')[0];

	var ctx = canvas.getContext('2d');
	var moveflg = 0;
	var Xpoint;
	var Ypoint;
	var ox, oy, x, y;
	var isMoving = false;

	var defSize = 3, defColor = "#222";

	// PC対応
	canvas.addEventListener('mousedown', onMouseDown, false);
	canvas.addEventListener('mousemove', onMouseMove, false);
	canvas.addEventListener('mouseup', onMouseUp, false);
	// スマホ対応
	canvas.addEventListener('touchstart', onDown, false);
	canvas.addEventListener('touchmove', onMove, false);
	canvas.addEventListener('touchend', onUp, false);

	resetCanvas();

	$('button.handwriting-reset').on('click', resetCanvas);
	$('#doOCR').on('click', convertToDataURL)

	// 画像のmodal表示
	$("img.toggle-modal").click(function() {
		$("#modal-display").html($(this).prop('outerHTML'));
		$("#modal-display").fadeIn(200);
	});
	$("#modal-display, #modal-display img").click(function() {
		$("#modal-display").fadeOut(200);
	});

	function onDown(event) {
		event.preventDefault();
		isMoving = true;
		ox = event.touches[0].pageX - event.target.getBoundingClientRect().left;
		oy = event.touches[0].pageY - event.target.getBoundingClientRect().top;
		event.stopPropagation();
	}
	function onMouseDown(event) {
		event.preventDefault();
		ox = event.clientX - event.target.getBoundingClientRect().left;
		oy = event.clientY - event.target.getBoundingClientRect().top;
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
			x = event.clientX - event.target.getBoundingClientRect().left;
			y = event.clientY - event.target.getBoundingClientRect().top;
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
		ctx.clearRect(0, 0, ctx.canvas.clientWidth, ctx.canvas.clientHeight);
	}

	function convertToDataURL() {
		$('input.handwriting-dataurl').val(canvas.toDataURL());
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

});
