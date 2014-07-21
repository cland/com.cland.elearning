
package com.cland.elearning

class Course {

	String name
	String code
	//Date startDate
	//Date endDate
	int duration
	Region region
	String status
	String comments
	static hasMany = [modules:Module,registrations:Registration,premodules:PreModule,events:CourseEvent]
    static constraints = {
		name(blank:false)
	//	startDate(nullable:true) //validator: {return (it >= new Date())})
	//	endDate(nullable:true) //validator: {return (it >= new Date())})
		modules(blank:false)
		region(nullable:true)
		status(inList:["active","inactive","open","closed"])
		comments(nullable:true)
		duration(nullable:true)
    }
	static mapping = {
		duration defaultValue: 24
	
	//	modules lazy:false
	//	registrations lazy:false
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
		"${name} (${code})"
	}
} //end of class
