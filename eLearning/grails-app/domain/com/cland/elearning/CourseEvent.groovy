package com.cland.elearning

class CourseEvent {
	Date eventDate
	Person tutor
	Person counsellor
	Venue venue
	String region
	SubModule subModule
	Exam exam			//** This is defines the type of event 	
	static belongsTo = [course:Course]
	static hasMany = [results:EventResult]
	static constraints = {
		course()
		subModule(blank:false)
		exam()
		region(inList:["Western Cape","Gauteng","KZN","Northern Province","Free State","Eastern Cape","Limpopo"])
		eventDate()
		tutor()
		counsellor()
		venue()			
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

	int maxMark(){
		return exam.maxMark
	}
	
	String toString(){
		"${subModule.toString()}: Exam ${exam.testNumber} - (${course.name})" 
	}
} //end of class
