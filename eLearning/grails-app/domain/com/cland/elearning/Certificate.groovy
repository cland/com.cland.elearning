package com.cland.elearning

import java.util.Date;

class Certificate {
	transient springSecurityService
	String certno	
	ResultSummary resultSummary
	long createdBy
	long lastUpdatedBy
	Date dateCreated
	Date lastUpdated
	static belongsTo = [ResultSummary]
	static constraints = {
		certno unique:true, nullable:true
		lastUpdatedBy nullable:true, editable:false
		createdBy nullable:true, editable:false
	}
	def beforeInsert = {
		createdBy = getCurrentUserId()
	}
	def beforeUpdate = {
		lastUpdatedBy = getCurrentUserId()
	}
	def beforeDelete = {
		// your code goes here
	}
	def onLoad = {
		// your code goes here
	}

	def toAutoCompleteMap(){
		return [id:id,
		label:this.toString() ,
		value:id,
		certificate:this,
		category:"Certificate" ]
	}
	String toString(){
		certno + " (" + resultSummary?.module?.name + ") | " + resultSummary?.register?.learner.toString() 
	}
	private Long getCurrentUserId(){
		long userId = 0 //.currentUser?.id //
		if(springSecurityService.isLoggedIn()){
			Person user = springSecurityService.getCurrentUser()
			if(user)
				userId = user?.id
		}
		return userId
	}
} //end class
