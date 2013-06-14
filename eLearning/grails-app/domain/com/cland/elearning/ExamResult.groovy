package com.cland.elearning

class ExamResult {
	Date examDate
	int mark
	double percentMark		//** % mark
	double contributionMark //** % contribution to overall mark
	
	Person tutor
	Person counsellor
	Venue venue
	String region
	SubModule subModule
	Exam exam			//** This is defines the type of event 	
	
	static belongsTo = [resultSummary:ResultSummary]
	static constraints = {
		examDate()
		mark(min:0)
		percentMark(min:new Double(0.0),max:new Double(100.0))
		contributionMark(min:new Double(0.0),max:new Double(100.0))
		
		subModule()
		exam()
		
		tutor(nullable:true)
		counsellor(nullable:true)
		venue(nullable:true)
		region(inList:["Western Cape","Gauteng","KZN","Northern Province","Free State","Eastern Cape","Limpopo"], nullable:true)
		
		
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

	int maxMark(){
		return exam.maxMark
	}
	
	String toString(){
		"${mark}" 
	}
} //end of class
