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
	Exam exam			//** This is defines the type of event 	-- 19/11/2014 will be discontinued. values to be copied to this domain
	int testNumber
	int maxMark
	double weight //contribution percentage e.g 0.7 (70%) of final mark
	int factor
	String factorOperand
	
	static belongsTo = [resultSummary:ResultSummary]
	static constraints = {
		examDate()
		mark(min:0)
		percentMark(min:new Double(0.0))
		contributionMark(min:new Double(0.0),max:new Double(100.0))
		
		subModule()
		exam(nullable:true)
		testNumber(nullable:true,blank:true,min:1)
		factor(nullable:true,blank:true)
		factorOperand(nullable:true,blank:true)
		maxMark nullable:true,blank:true
		weight nullable:true,blank:true
		tutor(nullable:true)
		counsellor(nullable:true)
		venue(nullable:true)
		region(nullable:true)				
	}
	static mapping = {
		factor defaultValue:1
		//factorOperand defaultValue:'Divide'
		testNumber defaultValue:0
		maxMark defaultValue:100
		weight defaultValue:1
	}
	void computeResults(){
		
		//percentMark
		percentMark = ((mark/maxMark) * 100)
		contributionMark = (percentMark * weight)
	}
	def beforeInsert = {
		computeResults()
	}
	def beforeUpdate = {
		computeResults()
	}
	def beforeDelete = {
		// your code goes here
	}
	def onLoad = {
		// your code goes here
	}

	int maxMark(){
		return maxMark
	}
	def toMap(){
		[test: testNumber,
			mark:mark,
			percent_mark:percentMark,
			contribution_mark:contributionMark,
			max_mark:maxMark(),
			submodulename:subModule.name,
			type:subModule.type,
			exam_date:examDate?.format("dd-MMM-yyyy")]
	}
	String toString(){
		"${mark}" 
	}
} //end of class
