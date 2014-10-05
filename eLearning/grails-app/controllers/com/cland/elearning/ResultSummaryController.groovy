package com.cland.elearning
import grails.converters.JSON
import groovy.time.DatumDependentDuration
import groovy.time.TimeCategory;
import groovy.time.TimeDuration
import grails.plugins.springsecurity.Secured
import pl.touk.excel.export.WebXlsxExporter
class ResultSummaryController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
//		def tmpInstance = new ResultSummary()
//		def statusList = tmpInstance?.constraints?.status.inList
//		return [statusList:statusList]
	}

	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def create = {
        def resultSummaryInstance = new ResultSummary()
        resultSummaryInstance.properties = params
        return [resultSummaryInstance: resultSummaryInstance,tutorList:getTutors()]
    }
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def edit = {
        def resultSummaryInstance = ResultSummary.get(params.id)
        if (!resultSummaryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultSummary.label', default: 'ResultSummary'), params.id])}"
            redirect(action: "list")
        }
        else {
			def tutorList = getTutors()
            return [resultSummaryInstance: resultSummaryInstance,tutorList:tutorList]
        }
    }
	
	def show = {
		def resultSummaryInstance = ResultSummary.get(params.id)
	
		//println(resultSummaryInstance.isExpired().toString() + " - " + resultSummaryInstance.getCurrentDuration())
		if (!resultSummaryInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultSummary.label', default: 'ResultSummary'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [resultSummaryInstance: resultSummaryInstance]
		}
	} //end show 
	
	def listresults = {
		
	}
	
	private List getTutors(){
		def tutorList = Person.findAllByFirstNameAndLastName("No","Tutor")
		def list = com.cland.elearning.PersonRole.findAllByRole(com.cland.elearning.Role.findByAuthority('TUTOR'))?.person
		tutorList.addAll(list)
		return tutorList
	}
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def jq_export_results_flat = {
		def status = "Completed"
		int rowcount = (params?.rows ? params.int('rows') : 30)
		int page = (params?.page ? params.int('page') : 1)
		String sidx = (params?.sidx ? params.sidx : "")
		String sord = (params?.sord ? params.sord : "")				
		boolean dosave = (params?.int('save') == 1 ? true : false)
		String reportType = (params?.rtype ? params.rtype : "1")
		if(dosave){
			//save to file
			def completed_data = null
			def inprogress_data = null
			def exempt_data = null
			def headers = ""
			def withProperties = ""
			if(reportType.equals("1")){
				//println(">> Exporting report type " + reportType)
				headers = ['Course Name','Student No', 'Name', 'Surname', 'Company','Email', 'Module','Start Date','Completion Date','Mode of Learning','Result','Status',
					'Test 1','Test 2','Test 3','Test 4','Test 5','Test 6','Test 7','Test 8','Test 9','Test 10',
					'Total','Out of','% Mark','Total Contribution','Registration Date']
				
				withProperties = ['course','student_number', 'firstname', 'lastname', 'company','email', 'module_name','module_startdate','module_enddate','submodule','result','status',
					'marks.test1','marks.test2','marks.test3','marks.test4','marks.test5','marks.test6','marks.test7','marks.test8','marks.test9','marks.test10',
					'marks.total','marks.maxtotal','marks.percent','marks.total_contribution','regdate'
					]
				completed_data = getResultsData("Completed",rowcount,page,sidx,sord)
				inprogress_data = getResultsData("In Progress",rowcount,page,sidx,sord)
				exempt_data = getResultsData("Exempt",rowcount,page,sidx,sord)
			}else{
			//println(">> Exporting OTHER report type.")
				headers = ['Course Name','Student No', 'Name', 'Surname', 'Company','Email', 'Module','Start Date','Completion Date','Result','Status',					
					LearningMode.CMA.getKey() + ' Total',LearningMode.CMA.getKey()+' Out Of',LearningMode.CMA.getKey() + ' Mark %',LearningMode.CMA.getKey()+' Contribution',
					LearningMode.PAX.getKey() + ' Total',LearningMode.PAX.getKey()+' Out Of',LearningMode.PAX.getKey() + ' Mark %',LearningMode.PAX.getKey()+' Contribution',
					LearningMode.TMA.getKey() + ' Total',LearningMode.TMA.getKey()+' Out Of',LearningMode.TMA.getKey() + ' Mark %',LearningMode.TMA.getKey()+' Contribution',
					LearningMode.ASS.toString() + ' Total',LearningMode.ASS.toString()+' Out Of',LearningMode.ASS.toString() + ' Mark %',LearningMode.ASS.toString()+' Contribution',
					]
				
				withProperties = ['course','student_number', 'firstname', 'lastname', 'company','email', 'module_name','module_startdate','module_enddate','result','status',					
					'submodules.CMA.total','submodules.CMA.maxtotal','submodules.CMA.mark','submodules.CMA.totalcontribution',
					'submodules.PAX.total','submodules.PAX.maxtotal','submodules.PAX.mark','submodules.PAX.totalcontribution',
					'submodules.TMA.total','submodules.TMA.maxtotal','submodules.TMA.mark','submodules.TMA.totalcontribution',
					'submodules.ASS.total','submodules.ASS.maxtotal','submodules.ASS.mark','submodules.ASS.totalcontribution'
					]
				completed_data = getResultsData2("Completed",rowcount,page,sidx,sord)
				//println(completed_data)
				inprogress_data = getResultsData2("In Progress",rowcount,page,sidx,sord)
				exempt_data = getResultsData2("Exempt",rowcount,page,sidx,sord)
			}
			new WebXlsxExporter().with {
			    setResponseHeaders(response)
				sheet('Completed').with {
					fillHeader(headers)
				    add(completed_data, withProperties)
				}
				sheet('In Progress').with {
					fillHeader(headers)
					add(inprogress_data, withProperties)
				}
				sheet('Exempt').with {
					fillHeader(headers)
					add(exempt_data, withProperties)
				}
			    save(response.outputStream)
			}
			
		}else{
			//return as simple JSON result
			def results = getResultsData2(status,rowcount,page,sidx,sord)			
			int max = rowcount
			
			int total = results?.size() //count
			int total_pages = (total > 0 ? Math.ceil(total/max) : 0)
			if(page > total_pages)	page = total_pages
			int offset = max*page-max //(page==1 ? 0 : page * rowcount)
			int upperLimit = findUpperIndex(offset, max, total)
			List filteredResults = results.getAt(offset..upperLimit)
			def paging_data = [page: page,total:total_pages,records:total,rows:filteredResults]		
			render paging_data as JSON
		}
		
	} //end export
	
	def getResultsData(String status,int rowcount,int page,String sidx,String sord){				
		
		def resultSummaryInstanceList = ResultSummary.createCriteria().list() {
			createAlias('register','reg')
			createAlias('tutor','tut')
			createAlias('reg.learner','l')
			order('reg.learner','asc')

			if(status){
				ilike('status',"%"+status+"%")
			}
		}	
		def allResults = []		
		//for each resultsummary get the results,person and module details
		for(ResultSummary resultSummaryInstance: resultSummaryInstanceList){
			def results = resultSummaryInstance.results.sort(false){[it.subModule.type,it.exam.testNumber]} //.groupBy({it.subModule.type})
			def person = resultSummaryInstance.register.learner
			def course = resultSummaryInstance.register.course
			
			def coursename = course.name
			def modulename = resultSummaryInstance.module.name
			
			def studentId = person.studentNo
			def firstname = person.firstName?.toLowerCase()?.capitalize()
			def lastname = person.lastName?.toLowerCase()?.capitalize()
			def company = person?.company?.name?.toLowerCase()?.capitalize()
			def email = person?.email?.toLowerCase()
			def startdate = resultSummaryInstance.startDate?.format("dd-MMM-yyyy")
			def enddate = resultSummaryInstance.endDate?.format("dd-MMM-yyyy")
			def regdate = resultSummaryInstance.register.regDate?.format("dd-MMM-yyyy")
			def result = resultSummaryInstance.result
			def result_status = resultSummaryInstance.status
			def totalMark = resultSummaryInstance.totalMark()  //overall total
			def totalMarkPercent = String.format( '%.1f',resultSummaryInstance.totalPercentMark()) //overall %
			def totalMax = resultSummaryInstance.totalMaxMark() //overall MaxMark
			
			def grouped = results.groupBy({it.subModule.type})
			grouped.each {key,value ->
				def marks = [:]
				def subresult = grouped[key].collect({it.toMap()})				
				def total = subresult?.sum { it?.mark }				
				def max_total = subresult?.sum { it?.max_mark }
				def percentage = String.format( '%.1f',((total/max_total) * 100))
				def total_contribution = String.format( '%.1f',subresult?.sum { it?.contribution_mark })
				def tests = [:]
				subresult.each{key1 ->					
					tests.put("test" + key1?.test, key1?.mark)
					tests.put("testdate" + key1?.test, key1?.exam_date)
				}
				
				for(int i=tests.size()+1;i<=10;i++){
					tests.put("test" + i, "--")					
				}
				tests.put("total", total)
				tests.put("maxtotal", max_total)
				tests.put("percent", percentage)
				tests.put("total_contribution",total_contribution)
				def jsonResults = [id:resultSummaryInstance.id,student_number:studentId,firstname:firstname,lastname:lastname,company:company,regdate:regdate,
					course:coursename,module_name: modulename,module_startdate:startdate,module_enddate:enddate,result:result,status:status,submodule:key,
					marks:tests,
					total_mark:totalMark,total_max:totalMax,percent:totalMarkPercent,email:email]
 
				allResults.add(jsonResults)
			}
		}
		
//		int total = allResults?.size()
//		int upperLimit = findUpperIndex(offset, max, total)
//		List filteredResults = allResults.getAt(offset..upperLimit)
//		println("Final count: " + filteredResults.size())
		return allResults //filteredResults
	} //function that works out the result data
	
	/*
	 * 
	 * 
	 */
	def getResultsData2(String status,int rowcount,int page,String sidx,String sord){
		
		def resultSummaryInstanceList = ResultSummary.createCriteria().list() {
			createAlias('register','reg')
			createAlias('tutor','tut')
			createAlias('reg.learner','l')
			order('reg.learner','asc')

			if(status){
				ilike('status',"%"+status+"%")
			}
		}
		def allResults = []
		//for each resultsummary get the results,person and module details
		for(ResultSummary resultSummaryInstance: resultSummaryInstanceList){
			def results = resultSummaryInstance.results.sort(false){[it.subModule.type,it.exam.testNumber]} //.groupBy({it.subModule.type})
			def person = resultSummaryInstance.register.learner
			def course = resultSummaryInstance.register.course
			
			def coursename = course.name
			def modulename = resultSummaryInstance.module.name
			
			def studentId = person.studentNo
			def firstname = person.firstName?.toLowerCase()?.capitalize()
			def lastname = person.lastName?.toLowerCase()?.capitalize()
			def company = person?.company?.name?.toLowerCase()?.capitalize()
			def email = person?.email?.toLowerCase()
			def startdate = resultSummaryInstance.startDate?.format("dd-MMM-yyyy")
			def enddate = resultSummaryInstance.endDate?.format("dd-MMM-yyyy")
			def regdate = resultSummaryInstance.register.regDate?.format("dd-MMM-yyyy")
			def result = resultSummaryInstance.result
			def result_status = resultSummaryInstance.status
			def totalMark = resultSummaryInstance.totalMark()  //overall total
			def totalMarkPercent = String.format( '%.1f',resultSummaryInstance.totalPercentMark()) //overall %
			def totalMax = resultSummaryInstance.totalMaxMark() //overall MaxMark
			
			def grouped = results.groupBy({it.subModule.type})
			def submodules = [:] 
			submodules.put(LearningMode.ASS.getKey(), ["name":LearningMode.ASS.toString(),"maxtotal":"--","total":"--","mark":"--","totalcontribution":"--"])
			submodules.put(LearningMode.CMA.getKey(), ["name":LearningMode.CMA.toString(),"maxtotal":"--","total":"--","mark":"--","totalcontribution":"--"])
			submodules.put(LearningMode.PAX.getKey(), ["name":LearningMode.PAX.toString(),"maxtotal":"--","total":"--","mark":"--","totalcontribution":"--"])
			submodules.put(LearningMode.TMA.getKey(), ["name":LearningMode.TMA.toString(),"maxtotal":"--","total":"--","mark":"--","totalcontribution":"--"])
			grouped.each {key,value ->				
				def submodule = [:]
				submodule.put("name",key)
				
				def subresult = grouped[key].collect({it.toMap()})
				def total = subresult?.sum { it?.mark }
				def max_total = subresult?.sum { it?.max_mark }
				def percentage = String.format( '%.1f',((total/max_total) * 100))
				def total_contribution = String.format( '%.1f',subresult?.sum { it?.contribution_mark })
				submodule.put("total", total)
				submodule.put("maxtotal", max_total)
				submodule.put("mark", percentage)
				submodule.put("totalcontribution", total_contribution)
				def tests = [:]
				subresult.each{key1 ->
					tests.put("test" + key1?.test, key1?.mark)
					tests.put("testdate" + key1?.test, key1?.exam_date)
				}				
				for(int i=tests.size()+1;i<=10;i++){
					tests.put("test" + i, "--")
				}
				tests.put("total", total)
				tests.put("maxtotal", max_total)
				tests.put("percent", percentage)
				tests.put("total_contribution",total_contribution)
				submodule.put("tests",tests)
				switch(key){
					case LearningMode.ASS.toString():
					submodules.put(LearningMode.ASS.getKey(), submodule)
					break;
					case LearningMode.CMA.toString():
					submodules.put(LearningMode.CMA.getKey(), submodule)
					break;
					case LearningMode.PAX.toString():
					submodules.put(LearningMode.PAX.getKey(), submodule)
					break;
					case LearningMode.TMA.toString():
					submodules.put(LearningMode.TMA.getKey(), submodule)
					break;
				}
				
			} //end grouped each
			def jsonResults = [id:resultSummaryInstance.id,student_number:studentId,firstname:firstname,lastname:lastname,company:company,regdate:regdate,
				course:coursename,module_name: modulename,module_startdate:startdate,module_enddate:enddate,result:result,status:status,
				submodules:submodules,
				total_mark:totalMark,total_max:totalMax,percent:totalMarkPercent,email:email]

			allResults.add(jsonResults)
		} //end for each ResultSummary
		
//		int total = allResults?.size()
//		int upperLimit = findUpperIndex(offset, max, total)
//		List filteredResults = allResults.getAt(offset..upperLimit)
//		println("Final count: " + filteredResults.size())
		return allResults //filteredResults
	} //function that works out the result data
	
	private static int findUpperIndex(int offset, int max, int total) {
		max = offset + max - 1
		if (max >= total) {
			max -= max - total + 1
		}
		return max
	} //end helper method findUpperIndex	
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def jq_export_results = {
		int offset = 0
		int max = ResultSummary.count
		def status = (params.status==null ? "Completed" : params.status)
		def resultSummaryInstanceList = ResultSummary.createCriteria().list() {
			createAlias('register','reg')
			createAlias('tutor','tut')
			createAlias('reg.learner','l')
			order('reg.learner','asc')

			if(status){
				ilike('status',"%"+status+"%")
			}
		}
	
		def allResults = []
		
		//for each resultsummary get the results,person and module details
		for(ResultSummary resultSummaryInstance: resultSummaryInstanceList){
			def results = resultSummaryInstance.results.sort(false){[it.subModule.type,it.exam.testNumber]} //.groupBy({it.subModule.type})
			def person = resultSummaryInstance.register.learner
			def course = resultSummaryInstance.register.course
			
			def coursename = course.name
			def modulename = resultSummaryInstance.module.name
			
			def studentId = person.studentNo
			def firstname = person.firstName
			def lastname = person.lastName
			def company = person?.company?.name
			def regdate = resultSummaryInstance.register.regDate?.format("dd MMM yyyy")
			def result = resultSummaryInstance.result
			def result_status = resultSummaryInstance.status
			def totalMark = resultSummaryInstance.totalMark()
			def totalMarkPercent = String.format( '%.1f',resultSummaryInstance.totalPercentMark())
			def totalMax = resultSummaryInstance.totalMaxMark()

			def marks = [:]
			def grouped = results.groupBy({it.subModule.type})
			grouped.each {key,value ->
				def tmp = grouped[key].collect({it.toMap()})
				marks.put(key, tmp)
			}
			
			def jsonResults = [id:resultSummaryInstance.id,student_number:studentId,firstname:firstname,lastname:lastname,company:company,regdate:regdate,
					course:coursename,module_name: modulename,result:result,status:status,marks:marks,
					total_mark:totalMark,total_max:totalMax,percent:totalMarkPercent]
 
			allResults.add(jsonResults)
		}
		
		render allResults as JSON
	} //end export
	


	def jq_list_results = {
		//get the exam summary instance
		def resultSummaryInstance = ResultSummary.get(params.resultSumId)
		if (!resultSummaryInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultSummary.label', default: 'ResultSummary'), params.resultSumId])}"
			redirect(action: "show",params:params)
		}
		
		def all_results = resultSummaryInstance.results.sort(false){[it.subModule.type,it.exam.testNumber]} //.reverse()
		int total = all_results?.size()
		if(total < 1){
			def t =[records:0,page:0]
			render  t as JSON
			return
		}
	//	def sortIndex = params.sidx ?: 'examDate'
	//	def sortOrder  = params.sord ?: 'asc'
		int max = (params?.rows ? params.int('rows') : 30)
		int page = (params?.page ? params.int('page') : 1)				
		
		int total_pages = (total > 0 ? Math.ceil(total/max) : 0)
		if(page > total_pages)	page = total_pages
		int offset = max*page-max
		
		int upperLimit = findUpperIndex(offset, max, total)
		List results = all_results.getAt(offset..upperLimit)
		def jsonCells = results.collect {
			[cell: [it.subModule.name,
					it.exam.testNumber,
					it.mark,
					it.exam.maxMark,
					String.format( '%.1f', it.percentMark) ,
					String.format( '%.1f', it.contributionMark),
				], id: it.id]
		}
		def jsonData= [rows: jsonCells,page:page,records:total,total:total_pages]
		render jsonData as JSON
	}
	
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def jq_edit_results = {
		//println("ResultSummaryController - jq_edit_results: " + params)
		def examResult = null
		def message = ""
		def state = "FAIL"
		def resultsType = "exam"
		def id
		switch (params.oper) {
			case 'add':
			break
			case 'del':
			break
			default: //edit
			examResult = ExamResult.get(params.id)
			if (examResult) {
				// set the properties according to passed in parameters
				//examResult.properties = params
				examResult.mark = params.mark as Integer
				if (! examResult.hasErrors() && examResult.save(flush:true)) {
					//println("Saved new result: " + examResult.mark + " - " +  examResult.maxMark() + " - " + examResult.contributionMark )
					message = "Result for ${examResult.subModule.name} ${examResult.subModule.type} exam test ${examResult.exam.testNumber} updated successfully"
					id = examResult.id
					state = "OK"
				} else {
					message = "Could Not Update Record"
					println(examResult.errors)
				}
			}
			break
		}
		
		def response = [message:message,state:state,id:id,grid_id:params.grid_id]
		render response as JSON
	}
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
} //end class
