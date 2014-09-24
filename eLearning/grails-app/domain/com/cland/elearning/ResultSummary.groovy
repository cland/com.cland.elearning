package com.cland.elearning

import groovy.time.DatumDependentDuration;
import groovy.time.TimeCategory;
import groovy.time.TimeDuration

import java.util.Date;

class ResultSummary {
	String status	
	String paymentStatus
	String result
	String certNumber	
	Certificate certificate
	Module module
	Date startDate  //Date module started
	Date endDate	//Date module completed
	
	Person tutor
	
	static hasMany =[results:ExamResult]
	static belongsTo = [register:Registration]
	static transients = ['isExpired','currentDuration','currentTimeDuration']
    static constraints = {
		certificate nullable:true
		status(inList:["Not Started","In Progress","Completed","Exempt","Re-Write"])
		paymentStatus(nullable:true,inList:["Not-Paid","Paid","Exempt"])
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
	public DatumDependentDuration getCurrentTimeDuration(){
		Date tmp_start = null
		if(startDate != null){
			tmp_start = startDate //.getAt(Calendar.DATE)
		}else{
			tmp_start = new Date() //.getAt(Calendar.DATE)
		}
		Date tmp_end = null
		if(endDate != null){
			tmp_end = endDate //.getAt(Calendar.DATE)
		}else{
			tmp_end = new Date() //.getAt(Calendar.DATE)
		}
		
		return  getAge(tmp_start, tmp_end)
	}//end function getCurrentTimeDuration()
	public DatumDependentDuration getCurrentTimeDuration(Date startdate, Date enddate){
		Date tmp_start = (startdate == null ? new Date():startdate)
		Date tmp_end = (enddate == null ? new Date():enddate)		
		return  getAge(tmp_start, tmp_end)
	}
	public int getCurrentDuration(){
		String max_duration_unit = "days"
		if(module?.durationUnit) max_duration_unit = module?.durationUnit
		DatumDependentDuration diff = getCurrentTimeDuration()
		return decodeDuration(diff, max_duration_unit)
	} //end getCurrentDuration()
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
	
	public int getCurrentCertDuration(){
		String max_duration_unit = "years"
		if(module?.validUnit) max_duration_unit = module?.validUnit
		
		//TODO: we want to check if enddate is null here and perhaps use the lastmodified date instead 
		DatumDependentDuration diff = getCurrentTimeDuration(endDate,new Date())
		return decodeDuration(diff, max_duration_unit)		
	} //end getCurrentCertDuration()
	
	public boolean isCertExpired(){
		//TODO: Fix the certification expiration date duration check
		//Do we check for case if "Exempt"? Perhaps we look at when last updated or exempted.
		if(status.equalsIgnoreCase("completed")){
			int max_duration = 3  //cert duration to get from the module using years for now.
			if(module?.valid) max_duration = module?.valid
			if(getCurrentCertDuration() < max_duration){
				return false
			}
			return true
		}
		//otherwise this result certificate has expired				
		return false
	} //end function
	
	private int decodeDuration(DatumDependentDuration diff, String opt){
		int duration = 0;
		switch(opt){
			case "hours":
				duration = diff.hours
			break;
			case "days":
				duration = diff.years*365
				duration += diff.months * 30
				duration += diff.days
			break;
			case "weeks":
				duration = diff.years*12*4
				duration += diff.months*4
				duration += diff.days/7
			break;
			case "months":
				duration = diff.years*12
				duration += diff.months
				duration += (diff.days/30)
			break;
			case "years":
				duration = diff.years
				duration += diff.months/12
			break;
		}
		return duration
	} //end method for decode basic date difference into years/month/weeks/days etc
	private DatumDependentDuration getAge( Date dob, Date now = new Date() ) {
		dob.clearTime()
		now.clearTime()
		assert dob < now
		Calendar.instance.with { c ->
		  c.time = dob
		  def (years, months, days) = [ 0, 0, 0 ]
	  
		  while( ( c[ YEAR ] < now[ YEAR ] - 1 ) ||
				 ( c[ YEAR ] < now[ YEAR ] && c[ MONTH ] <= now[ MONTH ] ) ) {
			c.add( YEAR, 1 )
			years++
		  }
	  
		  while( ( c[ YEAR ] < now[ YEAR ] ) ||
				 ( c[ MONTH ] < now[ MONTH ] && c[ DAY_OF_MONTH ] <= now[ DAY_OF_MONTH ] ) ) {
			// Catch when we are wrapping the DEC/JAN border and would end up beyond now
			if( c[ YEAR ] == now[ YEAR ] - 1 &&
				now[ MONTH ] == JANUARY && c[ MONTH ] == DECEMBER &&
				c[ DAY_OF_MONTH ] > now[ DAY_OF_MONTH ] ) {
			  break
			}
			c.add( MONTH, 1 )
			months++
		  }
	  
		  while( c[ DAY_OF_YEAR ] != now[ DAY_OF_YEAR ] ) {
			c.add( DAY_OF_YEAR, 1 )
			days++
		  }
	  
		  new DatumDependentDuration( years, months, days, 0, 0, 0, 0 )
		}
	  }
	Double totalPercentMark(){
		((totalMark()/totalMaxMark()) * 100)
	}
	String toString(){
		"Learner: ${register.learner.toString()} - Course: ${register.course.name} - Module: ${module.name} Status: ${status} - Result: ${result} "
	}
} //end of class
