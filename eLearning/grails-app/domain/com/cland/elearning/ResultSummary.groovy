package com.cland.elearning

class ResultSummary {
	String status	
	String result
	String certNumber	
	Module module
	Person tutor
	static hasMany =[results:ExamResult]
	static belongsTo = [register:Registration]
    static constraints = {
		status(inList:["Not Started","In Progress","Completed","Exempt"])
		result(inList:["Pass","Fail","None","Exempt"])
		certNumber(blank:true)
		module()
		register()
		tutor(nullable:true)
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
		"Learner: ${register.learner.toString()} - Course: ${register.course.name} - Module: ${module.name} Status: ${status} - Result: ${result} "
	}
} //end of class
