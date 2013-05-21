package com.cland.elearning

class EventResult {
	int mark
	double percentMark		//** % mark
	double contributionMark //** % contribution to overall mark
	static constraints = {
		mark(min:0)
		percentMark(min:new Double(0.0),max:new Double(100.0))
		contributionMark(min:new Double(0.0),max:new Double(100.0))
	}
	static belongsTo = [courseEvent:CourseEvent,learner:Person]
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

	Exam courseExam(){
		return courseEvent.exam
	}
	String toString(){
		"Result for ${learner.toString()} - Mark: ${mark}/${courseEvent.maxMark()} (${percentMark}%) : ${courseEvent.toString()})"
	}
} //end of class
