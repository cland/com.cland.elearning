if (typeof jQuery !== 'undefined') {
	(function($) {
		$('#spinner').ajaxStart(function() {
			$(this).fadeIn();
		}).ajaxStop(function() {
			$(this).fadeOut();
		});
	})(jQuery);
}

function setAllRadio(group,value,checked){
	$(group).each(function() {
		var el = $(this)        
        if(el.val() == value){           
          el.prop("checked",checked)
        } 	
    });
}
function setAllCheckbox(group,state){
	$(group).each(function() {		          
		if(state=='true') $(this).prop("checked",true)  
		if(state=='false') $(this).prop("checked",false)
    });
}
