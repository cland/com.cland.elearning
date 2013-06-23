package com.cland.elearning

class Exam {

	int testNumber
	int maxMark
	double weight //contribution percentage e.g 0.7 (70%) of final mark
	int factor
	String status
	String factorOperand
	static belongsTo = [submodule:SubModule]
	
    static constraints = {
		testNumber(blank:false,min:1)
		factor(nullable:true)
		factorOperand(inList:["Divide",
			"Multiply",
			"Subtract",
			"Add"])
		status(inList:["Active","Inactive"])
    }
	static mapping = {
		factor defaultValue:1
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
		"${submodule.toString()} : ${testNumber}. Max Mark: ${maxMark}, Weight: (${weight})"
	}
} //end of class
