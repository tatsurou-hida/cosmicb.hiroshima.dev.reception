$(function() {

	$('.justify-height').each(function(index, elem) {
		$(elem).css('height', ($(window).height() - 300) + 'px');
	});

	// events

	$('.carousel-next').on('click', function(e) {
		var convertToUrlTarget = $(e.target).data('convert-to-url');
		if (convertToUrlTarget) {
			var canvasSelector = $(e.target).data('canvas');
			var dataUrl = $(canvasSelector)[0].toDataURL();
			$(convertToUrlTarget).val(dataUrl);
		}

		$('#carousel-main').carousel('next');
	});

	$('#btn-submit').on('click', function() {

		// 制御をconrollerへ（データ登録）
		$('#receform').submit();

		// ３枚目画面へ遷移
		$('#carousel-main').carousel('next');

	});

	$('#carousel-main').on('slid.bs.carousel', function() {
		if ($('.carousel-item.active').hasClass('show-introduction')) {
			$('#introduction').modal('show');
		}
	});

	$('.btn-input-number').on('focus', function(e) {
		$('.btn-input-number').removeClass('active');
		$(e.target).addClass('active');
		$('#inputNum').val($(e.target).data('value'));
	});
});

$(function() {

	// var canvas = $('canvas.handwriting')[0];

	$('canvas.handwriting').each(
			function(index, elem) {
				var canvas = elem;
				$(canvas).attr('width', ($(window).width() - 300));
				$(canvas).attr('height', ($(window).height() - 300));

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

});
