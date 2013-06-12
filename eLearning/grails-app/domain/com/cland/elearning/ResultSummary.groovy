package com.cland.elearning

class ResultSummary {
	String status	
	String result
	String certNumber	
	Module module
	Person learner
	static hasMany =[results:ExamResult]
	static belongsTo = [course:Course,learner:Person]
    static constraints = {
		status(inList:["Not Started","In Progress","Completed"])
		result(inList:["Pass","Fail","None"])
		certNumber()
		module()
		learner()
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
		"Status: ${status}, Result: ${result} "
	}
} //end of class
