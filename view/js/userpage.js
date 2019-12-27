$(document).ready(function() {
	
	$('.hover_bkgr_fricc').hide();
	
	$('td').click(function() {
		
		var id = $(this).attr('id');
		
		$("#work_id").attr("value", id);
		
		if (id != 'cell')
			$('.hover_bkgr_fricc').show();
		
	});
	
	$('.popupCloseButton').click(function() {
		
		$('.hover_bkgr_fricc').hide();
		
	});
});

$(document).ready(function() {
	$('td').each(function() {
		if ($(this).attr('id') != 'cell') {

			if ($(this).attr('class') == '0')
				$(this).css('backgroundColor', '#999999');

			if ($(this).attr('class') == '1')
				$(this).css('backgroundColor', '#FFFFCC');

			if ($(this).attr('class') == '2')
				$(this).css('backgroundColor', '#FF3300');

			if ($(this).attr('class') == '3')
				$(this).css('backgroundColor', '#00CCCC');
		}
	});
});
