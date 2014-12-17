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
				println("Row: " + count)
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
									println(studentno + " >> Failed to parse date of birth '" + value + "'")	
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
						if(companyName.trim() != ""){
							println(studentno +  " >> No organisation found with name '" + companyName + "'" )
							
//							org = new Organisation(name:companyName)
//							org.save()
//							if(org){
//								person_values.put("company",org)
//							}
						}
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
	
	/**
	 * Upload learners and creates register -> resultsummary -> examresults
	 * The learner should already exist with a studentno
	 */
	def loadLearnerResults = {
		def separator = params.delimeter //";"
		if(!separator) separator = ";"
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-yy");
		def f = request.getFile( 'loadLearnerResultsCsv' )
		if (f.empty) {
			flash.message = 'file cannot be empty'
			render(view: 'index')
			return
		}

		int count = 0
		def failedList = []
		f.inputStream.splitEachLine(separator) { row ->
			def learner = null
			def studentno = ""
			Date dateRegistered = null
			Date endDate = null
			Registration registration = null
			ResultSummary resultSummary = null
			Course course = null
			Module module = null
			SubModule submodule = null
			Person tutor = null
			String status = ""
			String result = ""
			def test1 = [:]
			def test2 = [:]
			def test3 = [:]
			def test4 = [:]
			def test5 = [:]
			def test6 = [:]
			def test7 = [:]
			def test8 = [:]
			def test9 = [:]
			def test10 = [:]
			def test11 = [:]
			def test12 = [:]
			
			if(count > 0){
				println("Row: " + count)
				row.eachWithIndex {col,index ->
					//set the data
					def value = col?.trim()?.replaceAll("\"", "")?.replaceAll("'","")
					switch(index){
						case 0:
							course = Course.findByName(value)							
						break;
						case 1:
							learner = Person.findByStudentNo(value)
							studentno = value							
						break
						case 2:
							//module name
							def tmp = value.split(" ")
							def nval = ""
							tmp.each{i->
								if(i!=""){
									if(nval == "") nval = i.trim(); else nval = nval + " " + i.trim();
								}
							}
							
							module = Module.findByName(nval)
							//println("...find module '" + nval + "' ... => " + module)
						break
						case 3:
							//submodule
							submodule = module?.submodules?.find{it?.type==fixType(value)}	
							//println("Submodule: '" + fixType(value) + "' => " + submodule )						
						break
						case 4:
							//tutor
							def t_fname =  value
							def t_lname = ""
							try{
								if(t_fname.indexOf(" ")> 0){
									t_fname =  value.substring(0, value.indexOf(" "))?.trim()
									t_lname = value.substring(value.indexOf(" ")+1)
								}
								//println("Looking for tutor: " + t_fname + ", " + t_lname )
								tutor = Person.findByFirstNameAndLastName(t_fname,t_lname)
							}catch(Exception ex){
								tutor = Person.findByUsername("default.tutor")
							}
							def tutorRole = Role.findByAuthority('TUTOR')
							if(!tutor) {
								tutor = new Person(username: t_fname + "." + t_lname,
									enabled: true,
									password: 'Learning1Tutor',
									firstName: t_fname,
									lastName: t_lname,
									idNo :"tut" + new Date().format("yyyyddHHmmss").toString(),
									contactNo : "0000",
									dateOfBirth:(new Date() - 365*30),
									gender:"F",
									address:"Unknown",
									city:"Durban",
									email:t_fname + "." + t_lname + "@whereever.com")	
								tutor.save(flush:true)
								if(tutor.hasErrors()) {
									//println("Tutor Errors: " + tutor.errors)
									tutor = Person.findByUsername("default.tutor")
								}else{
									PersonRole.create(tutor, tutorRole, true)
								}														
							}else{
								tutor = Person.findByUsername("default.tutor")
							}
						break
						case 5:
							//resultsummary status
							result = value
							status = getResultStatus(result)
						break
						case 6:
							//start date / enrolment date
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
						case 7:
							if(value != ""){
								DateTime dt = null
								try{
									dt = formatter.parseDateTime(value);
									endDate = dt.toDate()
								}catch(Exception e){
									println("Failed to parse date '" + value + "'")
								}
							}
						break
						case 8:
							
								int testNum = 1
								Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
								if(e?.id){
									if(value != ""){test1.put("mark", value)}else test1.put("mark", 0)																					
									test1.put("factor", 1)
									test1.put("examDate", new Date())
									test1.put("subModule", submodule)
									test1.put("exam", e)  //????
									test1.put("testNumber",testNum)
									test1.put("factorOperand", "Divide")
								}
						break
						case 9:
							if(value != "") test1.put("maxMark", new Integer(value))
						break
						case 10:
							if(value != "") test1.put("weight",new BigDecimal(value))
						break
						case 11:
							
								int testNum = 2
								Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
								if(e?.id){
									if(value != ""){test2.put("mark", value)}else test2.put("mark", 0)																					
									test2.put("factor", 1)
									test2.put("examDate", new Date())
									test2.put("subModule", submodule)
									test2.put("exam", e)  //????
									test2.put("testNumber",testNum)
									test2.put("factorOperand", "Divide")
								}					
						break
						case 12:
							if(value != "") test2.put("maxMark", new Integer(value))
						break
						case 13:
							if(value != "") test2.put("weight", new BigDecimal(value))
						break
						case 14:
						
							int testNum = 3
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
							if(e?.id){
								if(value != ""){test3.put("mark", value)}else test3.put("mark", 0)																					
								test3.put("factor", 1)
								test3.put("examDate", new Date())
								test3.put("subModule", submodule)
								test3.put("exam", e)  //????
								test3.put("testNumber",testNum)
								test3.put("factorOperand", "Divide")
							}
						break
						case 15:
							if(value != "") test3.put("maxMark", new Integer(value))
						break
						case 16:
							if(value != "") test3.put("weight", new BigDecimal(value))
						break
						
						case 17:
						
							int testNum = 4
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
							if(e?.id){
								if(value != ""){test4.put("mark", value)}else test4.put("mark", 0)																					
								test4.put("factor", 1)
								test4.put("examDate", new Date())
								test4.put("subModule", submodule)
								test4.put("exam", e)  //????
								test4.put("testNumber",testNum)
								test4.put("factorOperand", "Divide")
							}
						break
						case 18:
							if(value != "") test4.put("maxMark", new Integer(value))
						break
						case 19:
							if(value != "") test4.put("weight", new BigDecimal(value))
						break
						case 20:
						
							int testNum = 5
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
							if(e?.id){
								if(value != ""){test5.put("mark", value)}else test5.put("mark", 0)																					
								test5.put("factor", 1)
								test5.put("examDate", new Date())
								test5.put("subModule", submodule)
								test5.put("exam", e)  //????
								test5.put("testNumber",testNum)
								test5.put("factorOperand", "Divide")
							}
						break
						case 21:
							if(value != "") test5.put("maxMark", new Integer(value))
						break
						case 22:
							if(value != "") test5.put("weight", new BigDecimal(value))
						break
						
						case 23:

							int testNum = 6
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
							if(e?.id){
								if(value != ""){test6.put("mark", value)}else test6.put("mark", 0)																					
								test6.put("factor", 1)
								test6.put("examDate", new Date())
								test6.put("subModule", submodule)
								test6.put("exam", e)  //????
								test6.put("testNumber",testNum)
								test6.put("factorOperand", "Divide")
							}
						break
						case 24:
							if(value != "") test6.put("maxMark", new Integer(value))
						break
						case 25:
							if(value != "") test6.put("weight", new BigDecimal(value))
						break
						
						case 26:

							int testNum = 7
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}
							if(e?.id){
								if(value != ""){test7.put("mark", value)}else test7.put("mark", 0)
								test7.put("factor", 1)
								test7.put("examDate", new Date())
								test7.put("subModule", submodule)
								test7.put("exam", e)  //????
								test7.put("testNumber",testNum)
								test7.put("factorOperand", "Divide")
							}
						break
						case 27:
							if(value != "") test7.put("maxMark", new Integer(value))
						break
						case 28:
							if(value != "") test7.put("weight", new BigDecimal(value))
						break
						
						case 29:
							int testNum = 8
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}
							if(e?.id){
								if(value != ""){test8.put("mark", value)}else test8.put("mark", 0)
								test8.put("factor", 1)
								test8.put("examDate", new Date())
								test8.put("subModule", submodule)
								test8.put("exam", e)  //????
								test8.put("testNumber",testNum)
								test8.put("factorOperand", "Divide")
							}
						break
						case 30:
							if(value != "") test8.put("maxMark", new Integer(value))
						break
						case 31:
							if(value != "") test8.put("weight", new BigDecimal(value))
						break
						
						case 32:
						
							int testNum = 9
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
							if(e?.id){
								if(value != ""){test9.put("mark", value)}else test9.put("mark", 0)																					
								test9.put("factor", 1)
								test9.put("examDate", new Date())
								test9.put("subModule", submodule)
								test9.put("exam", e)  //????
								test9.put("testNumber",testNum)
								test9.put("factorOperand", "Divide")
							}
						
						break
						case 33:
							if(value != "") test9.put("maxMark", new Integer(value))
						break
						case 34:
							if(value != "") test9.put("weight", new BigDecimal(value))
						break
						
						case 35:
						
							int testNum = 10
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}
							if(e?.id){
								if(value != ""){test10.put("mark", value)}else test10.put("mark", 0)
								test10.put("factor", 1)
								test10.put("examDate", new Date())
								test10.put("subModule", submodule)
								test10.put("exam", e)  //????
								test10.put("testNumber",testNum)
								test10.put("factorOperand", "Divide")
							}
						break
						case 36:
							if(value != "") test10.put("maxMark", new Integer(value))
						break
						case 37:
							if(value != "") test10.put("weight", new BigDecimal(value))
						break
						
						case 38:
						
							int testNum = 11
							Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
							if(e?.id){
								if(value != ""){test11.put("mark", value)}else test11.put("mark", 0)																					
								test11.put("factor", 1)
								test11.put("examDate", new Date())
								test11.put("subModule", submodule)
								test11.put("exam", e)  //????
								test11.put("testNumber",testNum)
								test11.put("factorOperand", "Divide")
							}
					
						break
						case 39:
							if(value != "") test11.put("maxMark", new Integer(value))
						break
						case 40:
							if(value != "") test11.put("weight", new BigDecimal(value))
						break
						
						case 41:
						int testNum = 12
						Exam e = submodule?.exams?.find{it?.testNumber==testNum}						
						if(e?.id){
							if(value != ""){test12.put("mark", value)}else test12.put("mark", 0)																					
							test12.put("factor", 1)
							test12.put("examDate", new Date())
							test12.put("subModule", submodule)
							test12.put("exam", e)  //????
							test12.put("testNumber",testNum)
							test12.put("factorOperand", "Divide")
						}
						break
						case 42:							
							if(value != "") test12.put("maxMark", new Integer(value))
						break
						case 43:
							if(value != "") test12.put("weight", new BigDecimal(value))
						break
					} //end switch
				} //end row.eachWithIndex								
				
				if(course !=null &&  learner != null){
					//lookup the registration
					//println(studentno + ": looking for: regDate: " + dateRegistered )
					registration = Registration.find {it.course.id == course.id & it.learner.id == learner.id }
					//Do the registration
					if(registration){
						
						//add the results
						//create the resultSummary if one does not exist already
					//println(studentno + " >> " + module?.id + " - " + registration?.id + " > " + registration)
					def test = ResultSummary.withCriteria {
							createAlias("module","mod")
							createAlias("register","r")
							eq("mod.id",module?.id)
							eq("r.id",registration.id)
							
						}
					
					if(test.size()>0) resultSummary = test.get(0)
										
						//println("ResultSummary found??:> " + resultSummary?.id)
						if(!resultSummary?.id){
							//println(studentno + " - row " + count + " >> Adding new resultSummary...module: " + module)
							resultSummary = new ResultSummary(
									status:status,
									result:"None",
									module:module,
									tutor:tutor,
									certNumber:"",
									startDate:dateRegistered,
									endDate:endDate
								)
							//resultSummary.save(flush:true)	
							//if(resultSummary.hasErrors()){
							//	println(studentno + " >> Failed " + resultSummary.errors)
							//}else{
								//println(studentno + " >> Adding new resultSummary to registration... " + resultSummary?.id)
								registration.addToResults(resultSummary)
								
								if(registration?.hasErrors()){
									println(studentno + " - row " + count + " >> failed to create result summary..." + registration?.errors)
								}else{
								registration.save(flush:true)
								}
							//}
						}
						//println("ResultSummary confirm:> " + resultSummary?.id)
						//for each of the tests add toe results
						if(resultSummary?.id){
							
							//println(studentno + " >> adding examresult... to result summary " + resultSummary?.id )
							addExamResult(resultSummary, test1)
							addExamResult(resultSummary, test2)
							addExamResult(resultSummary, test3)
							addExamResult(resultSummary, test4)
							addExamResult(resultSummary, test5)
							addExamResult(resultSummary, test6)
							addExamResult(resultSummary, test7)
							addExamResult(resultSummary, test8)
							addExamResult(resultSummary, test9)
							addExamResult(resultSummary, test10)
							addExamResult(resultSummary, test11)
							addExamResult(resultSummary, test12)
							println(studentno + " - row " + count + " >> Done adding results")
							resultSummary.save()
							if(resultSummary.hasErrors()){
								println (studentno + " - row " + count + " >> error saving examresults: " + resultSummary?.errors)
								failedList.add(studentno + " - row " + count + " >> error saving examresults: " + resultSummary?.errors)
							}
						//	registration.save()
						}else{
							failedList.add(studentno + " - row " + count + " >> resultSummary prob" )
						}
					} //end else all is good with registration instance
					
				}else{
					 failedList.add(studentno + " - row " + count + " >> No course or learner")
				}
				
			} //end if count > 0
			//println("Done with row " + count)
			count++
		} //end splitEachLine
		println("Done processing - loadLearnerResults!")
		def result = "<h1>Done processing - loadLearnerResults!</h1><br/>"
		if(failedList.size() > 0) {
			result + "FAILED: " + failedList.size() + " <br/>"
			failedList.eachWithIndex{val,index ->
				result += (index+1) + ") " + val + "<br/>"
			}
		}
		render result
	} //end closure loadLearnerResults
	
	private void addExamResult(def resultSummary, def data){
		if(!data.isEmpty() & data?.mark != null) {
			//println("D: " + data)
			def tmpexam = new ExamResult(data)
			//tmpexam.save()
			
			//println("EXAM: >> " + tmpexam?.id + " => " + tmpexam + " => " + tmpexam?.hasErrors())
			if(tmpexam?.hasErrors() | !tmpexam) {
				println("D: " + data)
				println(tmpexam.errors) 
			}else {
				def testResult = resultSummary.results.find{it.testNumber == tmpexam.testNumber & it?.subModule?.id ==  tmpexam?.subModule?.id}
				//print ">> " + testResult
				if(!testResult) {
					resultSummary.addToResults(tmpexam)
					//println ">> Added result."
				}
			}
		}
	}//end function addExamResult
	
	/**
	 * registerLearners:
	 * Simply create a Registration instance for a give learner and course WITHOUT the results stubs
	 */
	
	def registerLearners = {
		println("Bulk registration: " + params?.delimeter)
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
			Person learner = null
			def studentno = ""
			Date dateRegistered = null
			def registration = null
			Course course = null
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
								if(!venue){
									venue = new Venue(name:value)
									venue.save()
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
					//lookup the registration
					//Do the registration
					registration = registration = Registration.find {it.course.id == course.id & it.learner.id == learner.id & regDate == dateRegistered}
					if(!registration) registration = new Registration(course:course,learner:learner,regDate:dateRegistered,dateCreated:new Date())
					registration.save(flush:true)
					if(registration.hasErrors()){
						failedList.add(studentno)
						println(registration.errors)
					}else{
						println("Student '" + studentno + "' registered!")
						//set the results
						//createResultStubs(registration)
						
					}
				}else{
					println("!! Failed: " + studentno)
				 	failedList.add(studentno)
				}
				
			} //end if count > 0
			count++
		} //end splitEachLine
		println("Done processing - registerLearners!")
		def result = "<h1>Done processing - registerLearners!</h1><br/>"
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
	
	private String fixType(String value){
		if(value.equals("CMA")) return LearningMode.CMA.toString()
		if(value.equals("ASS")) return LearningMode.ASS.toString()
		if(value.equals("PAX")) return LearningMode.PAX.toString()
		if(value.equals("TMA")) return LearningMode.TMA.toString()
		return value
	}
	private String getResultStatus(String value){
		
		if(value.equalsIgnoreCase("Completed")|value.equalsIgnoreCase("Pass Distinction")|value.equalsIgnoreCase("Pass")|value.equalsIgnoreCase("Pass Merit")|value.equalsIgnoreCase("Fail PAX Redo work")|value.equalsIgnoreCase("Fail Ass Redo Work")|value.equalsIgnoreCase("Dropped Out")|value.equalsIgnoreCase("Suspend")||value.equalsIgnoreCase("W/draw")) return "Completed"
		if(value.equalsIgnoreCase("Assign O/Standing")|value.equalsIgnoreCase("Current")|value.equalsIgnoreCase("Did Not Write TMA")|value.equalsIgnoreCase("Incom (Work)")|value.equalsIgnoreCase("Incom Write TMA")|value.equalsIgnoreCase("To Finish Work")) return "In Progress"
		if(value.equalsIgnoreCase("Unspecified")|value.equalsIgnoreCase("Defer")|value.equalsIgnoreCase("Defer Recd")|value.equalsIgnoreCase("Defer Adv")) return "Not Started"
		if(value.equalsIgnoreCase("Exempt")) return "Exempt"
		if(value.equalsIgnoreCase("F/TMA and Redo Work")|value.equalsIgnoreCase("F/TMA-Rewrite")|value.equalsIgnoreCase("Redo Work")|value.equalsIgnoreCase("Incom & Rewrite TMA")|value.equalsIgnoreCase("F/Mod Re-Enrol")|value.equalsIgnoreCase("Re-Enrol")) return "Re-Write"
		
		return "Completed"
	}
		
} //end class
