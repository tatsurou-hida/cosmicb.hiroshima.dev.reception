/**
 *
 */
jQuery(function($) {
	$("[id^=org]").click(function() {
		if (!$(this).hasClass('on')) {
			$(this).addClass('on');
			var txt = $(this).text();
			$(this).html('<input type="text" value="' + txt + '" />');
			$('[id^=org] > input').focus().blur(function() {
				var inputVal = $(this).val();
				$(this).parent().removeClass('on').text(inputVal);
			});
		}
	});
});

jQuery(function($) {
	$("[id^=name]").click(function() {
		if (!$(this).hasClass('on')) {
			$(this).addClass('on');
			var txt = $(this).text();
			$(this).html('<input type="text" value="' + txt + '" />');
			$('[id^=name] > input').focus().blur(function() {
				var inputVal = $(this).val();
				$(this).parent().removeClass('on').text(inputVal);
			});
		}
	});
});