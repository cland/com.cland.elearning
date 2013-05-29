package com.cland.elearning

class Country {
	String name
	static hasMany = [regions:Region]
	static constraints = {
		name(blank:false)
	}
	def beforeInsert = {
		// your code goes here
	}
	def beforeUpdate = {
		// your code goes here
	}
	def beforeDelete = {
		// your code goes here
	}
	def onLoad = {
		// your code goes here
	}

	String toString(){
		"${name}"
	}
} //end of class
