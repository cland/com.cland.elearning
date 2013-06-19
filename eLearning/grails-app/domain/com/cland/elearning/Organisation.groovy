package com.cland.elearning

class Organisation {

	String name
	String phoneNo
	String email
	String phyAddress
	String phyPostCode
	String postalAddress
	String postalPostCode
	String contactPerson
	String city
	Region region
	Country country
	String comments
	String isMember
	//static hasMany = [people:Person]
	
    static constraints = {
		name()
		phoneNo()
		email(email:true)
		phyAddress(blank:true, nullable:true)
		region(nullable:true)
		country(nullable:true)
		isMember(nullable:true,inList:["Yes","No"])
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
