package com.cland.elearning

class Registration {

	Date dateCreated
	static belongsTo = [person:Person,course:Course]
    static constraints = {
		person()
		course()
		dateCreated()
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
		"${dateCreated.format('dd/MM/yyyy')}"
	}
} //end of class
