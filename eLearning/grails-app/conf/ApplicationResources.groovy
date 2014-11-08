modules = {
    application {
        resource url:'js/application.js'
    }
	core {
		resource url: 'js/jquery-1.9.1.js', disposition: 'head'
		resource url: 'js/jquery-ui-1.10.3.custom.min.js', disposition: 'head'

		resource url: '/css/south-street/jquery-ui-1.10.3.custom.min.css'
	}
	jquerymin{
		resource url: 'js/jquery-1.9.1.js', disposition: 'head'
	}
	jquery11{
		resource url: 'js/jquery-1.11.1.min.js', disposition: 'head'
	}
	jquerygrid{
		resource url: 'js/jquery.jqGrid.min.js', disposition: 'head'
	}
	jquerygridlocale{
		resource url: 'js/i18n/grid.locale-en.js', disposition: 'head'
	}
	
	jqueryuilatest{
		resource url: 'js/jquery-ui-1.10.3.custom.min.js', disposition: 'head'
	}
	multidatepicker{
		resource url: 'js/jquery-ui.multidatespicker.js', disposition: 'head'
	}
	jqueryfiledownload{
		resource url: 'js/jquery.fileDownload.js', disposition: 'head'
	}
	
	datePicker {
		dependsOn 'core'
		resource url: '/js/jquery-ui-timepicker-addon.js'
	}


	fullCalendar {
		dependsOn 'core'
		
		resource url: '/js/moment.min.js' 
		resource url: '/js/fullcalendar.min.js'
		resource url: '/css/fullcalendar.css'
	}

	qtip {
		dependsOn 'core'

		resource url: '/js/jquery.qtip.min.js'
		resource url: '/css/jquery.qtip.min.css'
	}


	calendar {
		dependsOn 'fullCalendar, jqueryuilatest,datePicker, qtip'

		resource url: '/js/calendar.js'
		resource url: '/css/calendar.css'
	}
}