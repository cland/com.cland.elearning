package com.cland.elearning

class CourseResult {
	Date resultDate
	Person tutor
	Person counsellor
	Venue venue
	String region
	SubModule subModule
	Exam exam			//** This is defines the type of event 	
	int mark
	double percentMark		//** % mark
	double contributionMark //** % contribution to overall mark
	static constraints = {
		learner()
		course()
		subModule(blank:false)
		exam()
		region(inList:["Western Cape","Gauteng","KZN","Northern Province","Free State","Eastern Cape","Limpopo"])
		resultDate()
		tutor()
		counsellor()
		venue()
		mark(min:0)
		percentMark(min:new Double(0.0),max:new Double(100.0))
		contributionMark(min:new Double(0.0),max:new Double(100.0))
	}
	static belongsTo = [course:Course,learner:Person]
	
	static hasMany = [results:EventResult]
	
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

	int maxMark(){
		return exam.maxMark
	}
	
	String toString(){
		"${subModule.toString()}: Exam ${exam.testNumber} - (${course.name})" 
	}
} //end of class
