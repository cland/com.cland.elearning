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
	static transients = ["createdByName","lastUpdatedByName"]
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
	def toMap(){
		return [id:id,
			modulename:getModuleName() ,
			certno:certno,
			person_idno:getPersonIdNo(),
			person_studentno:getPersonStudentNo(),
			person_name:getPersonName(),
			date_generated:dateCreated.format("dd-MMM-yyyy"),
			generated_by:getCreatedByName() ]
	}
	def toAutoCompleteMap(){
		return [id:id,
		label:this.toString() ,
		value:id,
		certificate:toMap(),
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
	String getCreatedByName(){
		Person user = Person.get(createdBy)
		return (user==null?"unknown":user?.toString())
	}
	String getLastUpdatedByName(){
		Person user = Person.get(lastUpdatedBy)
		return (user==null?"unknown":user?.toString())
	}
	String getModuleName(){
		return resultSummary?.module?.name
	}
	String getPersonName(){
		return resultSummary?.register?.learner.toString()
	}
	String getPersonIdNo(){
		return resultSummary?.register?.learner.idNo
	}
	String getPersonStudentNo(){
		return resultSummary?.register?.learner.studentNo
	}
} //end class
