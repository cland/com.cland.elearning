package com.cland.elearning

class ResultSummary {
	String status	
	String result
	String certNumber	
	Module module
	static hasMany =[results:ExamResult]
	static belongsTo = [register:Registration]
    static constraints = {
		status(inList:["Not Started","In Progress","Completed"])
		result(inList:["Pass","Fail","None"])
		certNumber(blank:true)
		module()
		register()
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
