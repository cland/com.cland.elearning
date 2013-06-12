package com.cland.elearning

class CourseEvent {
	String title
	String description
	Date startDate
	Date endDate	
	Venue venue
	String region
	SubModule subModule
	Person facilitator
	Exam exam			//** This is defines the type of event 	
	static belongsTo = [course:Course]
	
	static constraints = {
		title()
		startDate()
		endDate()
		course()
		subModule(blank:false)
		exam()
		region(inList:["Western Cape","Gauteng","KZN","Northern Province","Free State","Eastern Cape","Limpopo"])		
		venue()			
		facilitator()
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
