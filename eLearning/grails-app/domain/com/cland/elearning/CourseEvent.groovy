package com.cland.elearning

class CourseEvent {
	Date eventDate
	Person tutor
	Person counsellor
	Venue venue
	SubModule subModule
	Exam exam			//** This is defines the type of event 	
	static belongsTo = [course:Course]
	static constraints = {
		course()
		subModule()
		exam()
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

	String toString(){
		// TODO: make me interesting
	}
} //end of class
