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
