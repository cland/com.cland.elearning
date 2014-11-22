package com.cland.elearning


class AdminController {
	def beforeInterceptor = [action:this.&auth]
	def auth() {
		
	}
	def index = {}
	
	def resetExamResults = {
		//For all the Exam Results, copy Exam data to ExamResult
		def examresults = ExamResult.list()
		def msg = ""
		examresults.each {er ->
			
			//testNumber:1,maxMark:75,weight:0.3,factor:1,factorOperand:"Divide",
			Exam e = er.exam
			def values = [testNumber:e.testNumber,maxMark:e.maxMark,weight:e.weight,factor:e.factor,factorOperand:e.factorOperand]
			er.properties = values
			er.save(flush:true)
			if(er.hasErrors()){
				msg = msg + "<br/> >> Exam Result: " + er.id + " => " + er.errors
			}
		}
		println("Done processing!")
		render "<h1>Done processing!</h1><br/>" + msg
	}
} //end class
