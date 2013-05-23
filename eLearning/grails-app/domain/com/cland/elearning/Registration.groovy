package com.cland.elearning

class Registration {

	Date dateCreated	
	Date regDate
	Role regType
	static belongsTo = [person:Person,course:Course]
    static constraints = {
		person()
		course()
		dateCreated()
		regDate()
		regType()
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
		"${regType} - ${person.firstName} ${person.lastName} Registered on: ${dateCreated.format('dd/MM/yyyy hh:mm')} (${course.name})"
	}
} //end of class
