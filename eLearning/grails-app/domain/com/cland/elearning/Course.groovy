
package com.cland.elearning

class Course {

	String name
	Date startDate
	Date endDate
    static constraints = {
		name(blank:false)
		startDate()
		endDate()
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
	// TODO: make me interesting
	}
} //end of class
