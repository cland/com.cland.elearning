
package com.cland.elearning

class Course {

	String name
	Date startDate
	Date endDate
	String region
	String status
	static hasMany = [modules:Module,registrations:Registration,results:ResultSummary, events:CourseEvent]
    static constraints = {
		name(blank:false)
		startDate() //validator: {return (it >= new Date())})
		endDate() //validator: {return (it >= new Date())})
		modules(blank:false)
		region(inList:["Western Cape","Gauteng","KZN","Northern Province","Free State","Eastern Cape","Limpopo"])
		status(inList:["active","inactive","open","closed"])
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
		"${name}, ${startDate.format('dd/MM/yyyy')} - ${endDate.format('dd/MM/yyyy')}"
	}
} //end of class
