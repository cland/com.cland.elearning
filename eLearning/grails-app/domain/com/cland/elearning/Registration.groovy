package com.cland.elearning

class Registration {

	Date dateCreated	
	Date regDate
	Person tutor
	static belongsTo = [learner:Person,course:Course]
	static hasMany = [results:ResultSummary]

    static constraints = {
		learner()
		course()
		tutor()
		dateCreated()
		regDate()
		tutor(nullable:true)	
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
	def regStatusMap(){		
		int in_progress = countResultsByStatus("In Progress")		
		int completed = countResultsByStatus("Completed")		
		int exempted  = countResultsByStatus("Exempt")		
		int not_started  = countResultsByStatus("Not Started")
		return [inprogress:in_progress,completed:completed,exempted:exempted,notstarted:not_started]		
	} //	
	int countResultsByStatus(String testStatus){
		def tmplist = results.findByStatus(testStatus)
		return (tmplist ? tmplist.size():0)
	}
	int countResultsByResult(String testResult){
		def tmplist = results.findByResult(testResult)
		return (tmplist ? tmplist.size():0)
	}
	Integer totalMark(){
		def total = results?.totalMark()
		if(!total) total = 0
		total
	}
	Integer totalContributionMark(){
		def total = results?.totalContributionMark()
		if(!total) total = 0
		total
	}
	Integer totalMaxMark(){
		def total = results?.totalMaxMark()
		if(!total) total = 0
		total
	}
	String toString(){
		"${learner.firstName} ${learner.lastName} Registered on: ${dateCreated.format('dd/MM/yyyy hh:mm')} - Tutor: ${tutor?.firstName} ${tutor?.lastName} (${course.name})"
	}
} //end of class
