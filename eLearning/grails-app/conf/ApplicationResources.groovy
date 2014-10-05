modules = {
    application {
        resource url:'js/application.js'
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
}