package com.cland.elearning;

public enum LearningMode {
	/*
	 * "Assignment",
			"Computer Marked Asessment",
			"Practical Attendance Exercises",
			"Tutor Marked Assessment"
	 */
	ASS("Assigment"),
	PAX("Practical Attendance Exercises"),
	CMA("Computer Marked Assessment"),
	TMA("Tutor Marked Assessment")
	
	final String value
	
	LearningMode(String value) {
		this.value = value;
	}
	
	String toString(){
		value;
	}
	
	String getKey(){
		name()
	}
	static list() {
		[ASS.toString(),PAX.toString(),CMA.toString(),TMA.toString()]
	}
	static stringKeyValuePair(){
		ASS.toString() +":" + ASS.getKey() + ";" +
		PAX.toString() +":" + PAX.getKey() + ";" +
		CMA.toString() +":" + CMA.getKey() + ";" +
		TMA.toString() +":" + TMA.getKey() 
	}
	static listKeys() {
		[ASS,PAX,CMA,TMA]
	}
}
