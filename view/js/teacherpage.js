$(document).ready(function() {
	$('td').each(function() {
		if ($(this).attr('id') != 'cell') {

			if ($(this).attr('class') == '0'){
				$(this).css('backgroundColor', '#CCCCCC');
				$(this).find('input').css('backgroundColor', '#CCCCCC')
			}

			if ($(this).attr('class') == '1'){
				$(this).css('backgroundColor', '#FFFFCC');
				$(this).find('input').css('backgroundColor', '#FFFFCC')
			}

			if ($(this).attr('class') == '2'){
				$(this).css('backgroundColor', '#FF3300');
				$(this).find('input').css('backgroundColor', '#FF3300')
			}

			if ($(this).attr('class') == '3'){
				$(this).css('backgroundColor', '#00CCCC');
				$(this).find('input').css('backgroundColor', '#00CCCC')
			}
		}
	});
});

$(document).ready(function() {
	
	$('.hover_bkgr_fricc').hide();
	
	$('td').dblclick(function() {
		
		var id = $(this).attr('id');
		var _class = $(this).attr('class');
		
	  	if(id != 'cell' && _class != '0'){
	  		
	  		var value = id + "__" + $('#work_check').attr('value');
	  		var value1 = id + "__" + $('#work_check1').attr('value');
	  		
	  		$('#work_check').attr('value', value);
	  		$('#work_check1').attr('value', value1);
	  		
	  		
	  		$(this).css('border', '2px solid #330099');
	  		
	  		$('#work_id').attr("value", id);
			$('#btn_report').trigger('click');
	  		
			$('.hover_bkgr_fricc').show();
  		}
	  });
	
	$('.popupCloseButton').click(function(){
		$('.hover_bkgr_fricc').hide();
	});
});