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

		postToSlack();

		// 制御をconrollerへ（データ登録）
		window.setTimeout(function() {
			$('#receform').submit();
		}, 3000);

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

	function postToSlack() {
		var uniteTwoImages = function(img1, img2, img1height) {
			var context = document.createElement('canvas').getContext('2d');
			context.drawImage(img1, 0, 0);
			context.drawImage(img2, 0, img1height);
			return context.canvas.toDataURL();
		}

		var createImage = function(base64) {
			var image = new Image;
			image.src = base64;
			return image;
		}

		var toBlob = function(base64) {
			var bin = atob(base64.replace(/^.*,/, ''));
			var buffer = new Uint8Array(bin.length);
			for (var i = 0; i < bin.length; i++) {
				buffer[i] = bin.charCodeAt(i);
			}

			try {
				var blob = new Blob([ buffer.buffer ], {
					type : 'image/png'
				});
			} catch (e) {
				return false;
			}
			return blob;
		}

		var postAjax = function(formData) {
			$.ajax({
				type : "POST",
				url : document.forms["slack"].elements["url"].value,
				data : formData,
				enctype : 'multipart/form-data',
				processData : false,
				contentType : false,
				error : function(response) {
					console.error("failed to post a message to Slack");
					console.error(response);
				}
			});
		}

		var dateFormat = {
				  _fmt : {
				    "yyyy": function(date) { return date.getFullYear() + ''; },
				    "MM": function(date) { return ('0' + (date.getMonth() + 1)).slice(-2); },
				    "dd": function(date) { return ('0' + date.getDate()).slice(-2); },
				    "hh": function(date) { return ('0' + date.getHours()).slice(-2); },
				    "mm": function(date) { return ('0' + date.getMinutes()).slice(-2); },
				    "ss": function(date) { return ('0' + date.getSeconds()).slice(-2); }
				  },
				  _priority : ["yyyy", "MM", "dd", "hh", "mm", "ss"],
				  format: function(date, format){
				    return this._priority.reduce((res, fmt) => res.replace(fmt, this._fmt[fmt](date)), format)
				  }
				};

		let suffix = "png";

		var getImageFileName = function() {
			return dateFormat.format(new Date, "yyyyMMddhhmmss") + "." + suffix;
		}

		const SLACK_TOKEN = document.forms["slack"].elements["token"].value;
		const SLACK_CHANNEL_ID = document.forms["slack"].elements["channel"].value;

		var base64Company = $("#inputCompany").val();
		var base64Name = $("#inputName").val();

		var formData = new FormData();
		formData.append("token", SLACK_TOKEN);
		formData.append("channels", SLACK_CHANNEL_ID);
		formData.append("file", toBlob(base64Company));
		formData.append("filename", "com_" + getImageFileName());
		formData.append("filetype", suffix);
		formData.append("title", "所属／会社");
		formData.append("initial_comment", "受付しました: " + $("#inputNum").val() + "名様");

		postAjax(formData);

		formData = new FormData();
		formData.append("token", SLACK_TOKEN);
		formData.append("channels", SLACK_CHANNEL_ID);
		formData.append("file", toBlob(base64Name));
		formData.append("filename", "name_" + getImageFileName());
		formData.append("filetype", suffix);
		formData.append("title", "お名前");

		postAjax(formData);
	}
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
