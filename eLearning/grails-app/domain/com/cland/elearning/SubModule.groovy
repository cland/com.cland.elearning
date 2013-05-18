package com.cland.elearning

class SubModule {

	String name
	String type	//CMA/PAX/ASS/TMA
	String description
		
	static hasMany = [exams:Exam]
	static belongsTo = [module:SubModule]
    static constraints = {
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
		"${name}, ${type}"
	}
} //end of class
