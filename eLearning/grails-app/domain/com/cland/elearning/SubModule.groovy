package com.cland.elearning

class SubModule {

	String name
	String type	//CMA/PAX/ASS/TMA
	String description
		
	static hasMany = [exams:Exam]
	static belongsTo = [module:Module]
    static constraints = {
		type(inList:["Assignment",
			"Computer Marked Asessment",
			"Practical Attendance Exercises",
			"Tutor Marked Assessment"])
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
	
	String moduleName(){
		return "${module.name}"
	}
	String toString(){
		"Module: ${moduleName()} - ${name}, ${type}"
	}
} //end of class
