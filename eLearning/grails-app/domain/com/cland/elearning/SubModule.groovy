package com.cland.elearning

class SubModule {

	String name
	String type	//CMA/PAX/ASS/TMA
	String description
		
	static transients = ['moduleName','subModuleName','subModuleType']
	static hasMany = [exams:Exam]
	static belongsTo = [module:Module]
    static constraints = {
		name(blank:false)
		type(inList:LearningMode.list())
//		type(inList:["Assignment",
//			"Computer Marked Asessment",
//			"Practical Attendance Exercises",
//			"Tutor Marked Assessment"])
    }
	static mapping = {
	//	exams lazy:false
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
	
	Integer totalMaxMark(){
		def total = exams?.sum { it?.maxMark }
		if(!total) total = 0
		total
	}
	Integer totalMaxMark(def etype){
		def col = exams.findAll {type == etype}
		def total = col?.sum { it?.maxMark }
		if(!total) total = 0
		total
	}
	BigDecimal totalWeight(){
		 
		BigDecimal total = exams?.sum { it?.weight }
		if(!total) return new BigDecimal(0, 2)
		total.setScale(2, BigDecimal.ROUND_HALF_EVEN)
	}
	BigDecimal totalWeight(def etype){
		def col = exams.findAll {type == etype}
		BigDecimal total = col?.sum { it?.weight }
		if(!total)  return new BigDecimal(0, 2)
		total.setScale(2, BigDecimal.ROUND_HALF_EVEN)
	}
	String getSubModuleType(){
		return type
	}
	String getSubModuleName(){
		return name
	}
	String getModuleName(){
		return "${module.name}"
	}
	String toString(){		
		"${moduleName} - ${type}"
	}
} //end of class
