package com.cland.elearning

class Organisation {

	String name
	String phoneNo
	String email
	String address
	//static hasMany = [people:Person]
	
    static constraints = {
		name()
		phoneNo()
		email(email:true)
		address(blank:true, nullable:true)
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
		"${name}"
	}
} //end of class
