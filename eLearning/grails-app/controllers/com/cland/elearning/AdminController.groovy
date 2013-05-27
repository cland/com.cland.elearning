package com.cland.elearning

class AdminController {
	def beforeInterceptor = [action:this.&auth]
	def auth() {
		
	}
	def index = {}
} //end class
