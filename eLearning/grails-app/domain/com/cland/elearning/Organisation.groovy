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
	String vatNumber
	//static hasMany = [people:Person]
	
    static constraints = {
		name()
		phoneNo()
		email(nullable:true,email:true)
		phyAddress(blank:true, nullable:true)
		region(nullable:true)
		country(nullable:true)
		isMember(nullable:true,inList:["Yes","No"])
		city(nullable:true)
		comments(nullable:true)
		contactPerson(nullable:true)
		phyPostCode(nullable:true)
		postalAddress(nullable:true)
		postalPostCode(nullable:true)
		vatNumber(nullable:true)
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
	def toAutoCompleteMap(){
		return [id:id,
		label:name + " | " + phoneNo + " | " + email,
		value:id,
		phoneno:phoneNo,
		email:email]
	}
} //end of class
