package com.cland.elearning

class Registration {

	Date dateCreated	
	Date regDate
	Person tutor
	static belongsTo = [learner:Person,course:Course]
	static hasMany = [results:ResultSummary]

    static constraints = {
		learner()
		course()
		tutor()
		dateCreated()
		regDate()
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
		"${learner.firstName} ${learner.lastName} Registered on: ${dateCreated.format('dd/MM/yyyy hh:mm')} - Tutor: ${learner.firstName} ${learner.lastName} (${course.name})"
	}
} //end of class
