package com.cland.elearning

import groovy.time.TimeCategory;
import groovy.time.TimeDuration

import java.util.Date;

class ResultSummary {
	String status	
	String result
	String certNumber	
	Module module
	Date startDate  //Date module started
	Date endDate	//Date module completed
	
	Person tutor
	static hasMany =[results:ExamResult]
	static belongsTo = [register:Registration]
	static transients = ['isExpired','currentDuration','currentTimeDuration']
    static constraints = {
		status(inList:["Not Started","In Progress","Completed","Exempt"])
		result(inList:["Pass","Fail","None","Exempt"])
		certNumber(blank:true)
		module()
		startDate(nullable:true) 
		endDate(nullable:true)
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
		def total = results?.sum { it?.mark }
		if(!total) total = 0
		total
	}
	Integer totalContributionMark(){
		def total = results?.sum { it?.contributionMark }
		if(!total) total = 0
		total
	}
	Integer totalMaxMark(){
		def total = module?.totalMaxMark()
		if(!total) total = 0
		total
	}
	public TimeDuration getCurrentTimeDuration(){
		Date tmp_start = null
		if(startDate != null){
			tmp_start = startDate.getAt(Calendar.DATE)
		}else{
			tmp_start = new Date().getAt(Calendar.DATE)
		}
		Date tmp_end = null
		if(endDate != null){
			tmp_end = endDate.getAt(Calendar.DATE)
		}else{
			tmp_end = new Date().getAt(Calendar.DATE)
		}
		
		TimeDuration diff = TimeCategory.minus(tmp_end, tmp_start)
		
		return diff
	}
	public int getCurrentDuration(){
		int max_duration_unit = "months"
		int duration = 0
		if(module?.durationUnit) max_duration_unit = module?.durationUnit
		TimeDuration td = getCurrentTimeDuration()
		switch(max_duration_unit){
			case "hours":
				duration = diff.hours
			break;
			case "days":
				duration = diff.days
			break;
			case "weeks":
				duration = diff.days/7
			break;
			case "months":
				duration = diff.months
			break;
			case "years":
				duration = diff.years
			break;
		}
		return 
	}
	public boolean isExpired(){
		
		if(!status.equalsIgnoreCase("in progress")){
			return false
		}
		//otherwise check if there
		int max_duration = 6
		if(module?.duration) max_duration = module?.duration
		if(getCurrentDuration() < max_duration){
			return false
		} 
		
		return true
	} //end function
	Double totalPercentMark(){
		((totalMark()/totalMaxMark()) * 100)
	}
	String toString(){
		"Learner: ${register.learner.toString()} - Course: ${register.course.name} - Module: ${module.name} Status: ${status} - Result: ${result} "
	}
} //end of class
