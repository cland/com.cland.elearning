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

var cland_datepickers = {
		reset_picker : function resetPicker(picker_id,altfield_id, type){
			$("#" + picker_id).multiDatesPicker('resetDates',type);
			$("#" + altfield_id).prop("value","")
		},
		init_datepicker : function initDatePicker(picker_id,altfield_id, frmdate, todate){
			var el = $("#" + picker_id)
			var altEl = $("#" + altfield_id)
			var datelist = altEl.prop("value")
			if(!frmdate) frmdate="+0";
			if(!todate) todate="+3M +5D";
			el.multiDatesPicker({
				dateFormat: "yy-mm-dd",
				altField: '#' + altfield_id,			
				altFormat:"yy-mm-dd",
				minDate:frmdate,
				maxDate:todate
				//beforeShowDay: $.datepicker.noWeekends
				//maxPicks: 1		
			});
			if(datelist) {
				altEl.prop("value",datelist)
				datelist = datelist.split(",");			
				el.multiDatesPicker('addDates',datelist);
			}	
		},
		init_datepicker_single_future : function initDatePickerSingleFuture(picker_id,fmt, frmdate, todate){
			var el = $(picker_id)
			var datelist = el.prop("value")
			if(!frmdate) frmdate="+0";
			if(!todate) todate="+3M +5D";
			el.multiDatesPicker({
				dateFormat: fmt,
				minDate:frmdate,
				maxDate:todate,
				maxPicks: 1
			});

			if(datelist) {
				el.prop("value",datelist)
				datelist = datelist.split(",");			
				el.multiDatesPicker('addDates',datelist);
			}
	
			
		},init_datepicker_single_past : function initDatePickerSinglePast(picker_id,fmt,frmdate,todate){
			var el = $(picker_id)
			var datelist = el.prop("value")
			if(!frmdate) frmdate="-100y";
			if(!todate) todate="+0";
			$(picker_id).multiDatesPicker({
				dateFormat: fmt,
				maxDate:todate,
				maxPicks: 1
			});

			if(datelist) {
				el.prop("value",datelist)
				datelist = datelist.split(",");			
				el.multiDatesPicker('addDates',datelist);
			}	
		},
		init_datepicker_single_dob : function initDatePickerSingleDob(picker_id,fmt,frmdate,todate){
			var el = $(picker_id)
			var datelist = el.prop("value")
			if(!frmdate) frmdate="-100y";
			if(!todate) todate="+0";
			$(picker_id).multiDatesPicker({
				dateFormat: fmt,
				maxDate:todate,
				defaultDate: "-18y",
				maxPicks: 1
			});

			if(datelist) {
				el.prop("value",datelist)
				datelist = datelist.split(",");			
				el.multiDatesPicker('addDates',datelist);
			}	
		},
		
		init_datepicker_single_standard : function initDatePickerSingleStandard(picker_id,fmt,frmdate,todate,default_date){
			var el = $(picker_id)
			var datelist = el.prop("value")
			if(!frmdate) frmdate="-100y";
			if(!todate) todate="+0";
			if(!default_date) default_date="+0"
			$(picker_id).multiDatesPicker({
				dateFormat: fmt,
				maxDate:todate,
				defaultDate: default_date,
				maxPicks: 1
			});

			if(datelist) {
				el.prop("value",datelist)
				datelist = datelist.split(",");			
				el.multiDatesPicker('addDates',datelist);
			}	
		}		
} //end cland_datepicker
