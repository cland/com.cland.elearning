package com.cland.elearning

class Exam {

	int testNumber
	int maxMark
	double weight //contribution percentation e.g 0.7 (70%) of final mark
	
	static belongsTo = [submodule:SubModule]
	
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
		"${testNumber}, ${maxMark} (${weight})"
	}
} //end of class
