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
		region(nullable:true)				
	}

	void computeResults(){
		
		//percentMark
		percentMark = ((mark/exam.maxMark) * 100)
		contributionMark = (percentMark * exam.weight)
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
		return exam.maxMark
	}
	def toMap(){
		[test: exam.testNumber,
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
