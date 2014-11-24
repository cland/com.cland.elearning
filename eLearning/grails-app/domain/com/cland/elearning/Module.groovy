package com.cland.elearning

import java.text.NumberFormat

class Module {

	String name
	int duration
	String durationUnit	
	Date expiryDate
	String description
	int valid		//the Certification valid period
	String validUnit // unit in days, months years etc
	
	
	static hasMany = [submodules:SubModule]
	static mapping = {
		duration defaultValue: 0
		//durationUnit defaultValue:'months'
	}
    static constraints = {
		name(blank:false)
		duration(nullable:true)
		expiryDate(nullable:true)
		durationUnit() //inList:["hours","days","weeks","months","years"]		
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
		def total = submodules?.sum { it?.totalMaxMark() }		
		if(!total) total = 0
		total
	}
	Integer totalMaxMark(def etype){
		def total = submodules?.sum { it?.totalMaxMark(etype) }
		if(!total) total = 0
		total
	}
	BigDecimal totalWeight(){
		BigDecimal total = submodules?.sum { it?.totalWeight() }
		if(!total) return  new BigDecimal(0, 2)
		total.setScale(2, BigDecimal.ROUND_HALF_EVEN)
	}
	BigDecimal totalWeight(def etype){
	
		BigDecimal total = submodules?.sum { it?.totalWeight(etype) }
		if(!total) return  new BigDecimal(0, 2)
		total.setScale(2, BigDecimal.ROUND_HALF_EVEN)
	}
	String toString(){
		"${name}"
	}
} //end of class
