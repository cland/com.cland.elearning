package com.cland.elearning

class PreModule {

	Module current
	static hasMany =[prerequisites:Module]
	static belongsTo = [course:Course]
    static constraints = {
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
		prerequisites.collect {
			it.name			
		}				
	}
} //end of class
