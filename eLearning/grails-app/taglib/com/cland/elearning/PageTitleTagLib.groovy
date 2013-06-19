package com.cland.elearning

class PageTitleTagLib {
	def appTitle = {attrs, body ->
		out << "eLearning " + attrs.title
		out << " : " + body()
		}
	
}
