package com.cland.elearning
import org.joda.time.*
import org.joda.time.format.*

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
	
	def uploadLearnerData = {
		boolean hasErrors = false
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		def f = request.getFile( 'filecsv' )
		if (f.empty) {
			flash.message = 'file cannot be empty'
			render(view: 'index')
			return
		}
		// 18 columns
		int count = 0
		def failedList = []
		f.inputStream.splitEachLine(';') { row ->
			def studentno = ""
			def companyName = ""
			def countryName = ""
			def regionName = ""
			def person_values = [:]
			def org_values = [:]
			if(count > 0){
				row.eachWithIndex {col,index ->
					
					def value = col?.trim()?.replaceAll("\"", "")?.replaceAll("'","")
					switch(index){
						case 0:						
							studentno = value
						break;
						case 1:
							companyName = value						
						break;
						case 2:
							if(value != "") person_values.put("gender",value)
						break
						case 3:
							if(value != "") person_values.put("salutation",value.toLowerCase().split()*.capitalize().join(" "))
						break
						case 4: 
							if(value != ""){
								DateTime dt = null
								try{
									dt = formatter.parseDateTime(value);
									person_values.put("dateOfBirth", dt.toDate())
								}catch(Exception e){
									println("Failed to parse date '" + value + "'")	
								}	
							}							 
						break
						case 5:
							if(value != "") person_values.put("contactNo", value)
						break
						case 6:
							if(value != "") person_values.put("cellNo", value)
						break
						case 7:
							if(value != "") person_values.put("email", value)
						break
						case 8: //supervisor??						
							if(value != "") org_values.put("contactPerson", value)
						break
						case 9:
							if(value != "") person_values.put("jobTitle", value)
						break
						case 10:
							if(value != "") person_values.put("department", value)
						break
						case 11:
							if(value != "") person_values.put("schoolQualification", value)
						break
						case 12:
							if(value != "") person_values.put("postalAddress", value)
						break
						case 13:
							if(value != "") person_values.put("postalCode", value)
						break
						case 14:
							if(value != "") person_values.put("address", value)
						break
						case 15:
							if(value != "") person_values.put("addressCode", value)
						break
						case 16:
							if(value != "") {
								def region = Region.findByName(value)
								if(region)person_values.put("region", region)
							}
						break
						case 17:
							if(value != "") {
								def country = Country.findByName(value)
								if(country)person_values.put("country", country)
							}
						break
					} //end switch
				} //end row.eachWithIndex
				
				//save the values
				Person p = Person.findByStudentNo(studentno)
				if(p){
					//exists					
					Organisation org = Organisation.findByName(companyName)
					if(org){
						org.properties = org_values
						org.save()
						person_values.put("company",org)
					}else{
						println("No organisation found with name '" + companyName + "'" )
					}
					//save the person details
					p.properties = person_values
					p.save(flush:true)
					if(p.hasErrors()) {
						println(p.errors)
						failedList.add(studentno)
					}
				}else{
					//new
					println("Person NOT found with student no '" + studentno + "'")
					failedList.add(studentno)				
				}
			} //end if count > 0
			count++		
		} //end splitEachLine
		println("Done processing!")
		def result = "<h1>Done processing!</h1><br/>"
		if(failedList.size() > 0) {
			result + "FAILED: " + failedList.size() + " <br/>"
			failedList.eachWithIndex{val,index ->
				result += (index+1) + ") " + val + "<br/>" 
			}
		}
		render result
	} //end uploadData
	
	def registerLearners = {
		def separator = ";"
		DateTimeFormatter formatter = DateTimeFormat.forPattern("yyyy-MM-dd");
		def f = request.getFile( 'learnerRegistrationCsv' )
		if (f.empty) {
			flash.message = 'file cannot be empty'
			render(view: 'index')
			return
		}
		// 18 columns
		int count = 0
		def failedList = []
		f.inputStream.splitEachLine(separator) { row ->
			def learner = null
			def studentno = ""
			Date dateRegistered = null
			def course = null
			if(count > 0){
				row.eachWithIndex {col,index ->
					
					def value = col?.trim()?.replaceAll("\"", "")?.replaceAll("'","")
					switch(index){
						case 0:
							learner = Person.findByStudentNo(value)
							studentno = value
						break;
						case 1:
							if(value != ""){
								DateTime dt = null
								try{
									dt = formatter.parseDateTime(value);
									dateRegistered = dt.toDate()
								}catch(Exception e){
									println("Failed to parse date '" + value + "'")
								}
							}
						break						
						case 2:
							if(value != "") {
								def venue = Venue.findByName(value)
								if(venue){
									
								}else{
									venue = new Venue(name:value)
								}
							}
						break
						case 3:
							if(value != "") {
								course = Course.findByName(value)								
							}
						break
					} //end switch
				} //end row.eachWithIndex
				
				
				if(course !=null &&  learner != null){
					//Do the registration
					def registration = new Registration(course:course,learner:learner,regDate:dateRegistered,dateCreated:new Date())
					registration.save(flush:true)
					if(registration.hasErrors()){
						failedList.add(studentno)
						println(registration.errors)
					}else{
						createResultStubs(registration)
					}
				}else{
				 	failedList.add(studentno)
				}
				
			} //end if count > 0
			count++
		} //end splitEachLine
		println("Done processing!")
		def result = "<h1>Done processing!</h1><br/>"
		if(failedList.size() > 0) {
			result + "FAILED: " + failedList.size() + " <br/>"
			failedList.eachWithIndex{val,index ->
				result += (index+1) + ") " + val + "<br/>"
			}
		}
		render result
	} //end closure registerLearners
	
	void createResultStubs(Registration registrationInstance){
		def courseInstance = registrationInstance.course
		def modules = courseInstance.modules
		
		def resultSummary = null
		for(Module m : modules){
			
			resultSummary = createResultSummary(m, registrationInstance)
			//create the examresult stubs
			//createExamResults(resultSummary)
			
			registrationInstance.addToResults(resultSummary)
			if(registrationInstance.save(flush:true) && registrationInstance.hasErrors()){
				println("Errors!")
				log.error registrationInstance.errors
			}else{
				//println("added to register for course module ${m.name}!! Size: ${registrationInstance.results.size()} - \n ${resultSummary.toString()}")
				resultSummary.save(flush:true)
				if(resultSummary.hasErrors()){
					println(resultSummary.errors)
				}else{
					//create the examresult stubs
					//createExamResults(resultSummary)
				}
			}
		} //end for all modules
	} //end method
	
	ResultSummary createResultSummary(Module module, Registration register){
		//TODO: use a default none tutor.
		def defaultTutor = Person.findByUsername("default.tutor")
		def resultSummary = new ResultSummary(
				status:"Not Started",
				result:"None",
				module:module,
				tutor:defaultTutor,
				certNumber:""
				)
		//def module = resultSummary.module
		def submodules = module.submodules
	//	println("Submodules size: " + submodules.size())
		def examResult = null
		def exams = null
		for(SubModule submod : submodules){
	//		println(">> SubModule:" + submod.name)
			exams = submod?.exams
			for(Exam e : exams){
				if(e.status == 'Active'){
					examResult = new ExamResult(
						examDate:new Date(),
						mark:0,
						percentMark:0.0,
						contributionMark:0.0,
						tutor:defaultTutor,  //fix
						subModule:submod,
						exam:e,
						region:"",
						venue:"",
						testNumber:e.testNumber,maxMark:e.maxMark,weight:e.weight,factor:e.factor,factorOperand:e.factorOperand
						)
				}

				resultSummary.addToResults(examResult)
			} //end for all exam
		} //end for all subModules
		return resultSummary
	} //end createExamResults
} //end class
