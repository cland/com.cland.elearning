package com.cland.elearning

import grails.plugins.springsecurity.Secured

class HomeController {
	def index = {
		if (!isLoggedIn()) {
			redirect(controller:"Login")
			return false;
		}
		//redirect(action: "inde", params: params)
	}

} //end of class
