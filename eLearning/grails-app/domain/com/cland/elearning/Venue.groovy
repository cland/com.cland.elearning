package com.cland.elearning

class Venue {
	String name
	String address
	String geoLocation //Longitude,latitude
	String contactName
	String contactNumber
	String directions
	String region

	static constraints = {
		name(blank:false)
		address()
		geoLocation()
		contactName()
		contactNumber()
		directions()
		region()			
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
