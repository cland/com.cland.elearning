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
		
		if(params?.save){
			
			def headers = ['Student No', 'Name', 'Surname', 'Company', 'Module','Mode of Learning','Result','Status',
				'Test 1','Test1Date','Test 2','Test2Date','Test 3','Test3Date','Test 4','Test4Date','Test 5','Test5Date','Test 6','Test6Date','Test 7','Test7Date','Test 8','Test8Date','Test 9','Test9Date','Test 10','Test10Date',
				'Total','Out of','% Mark','Total Contribution','Course Name','Registration Date','Email']
			
			def withProperties = ['student_number', 'firstname', 'lastname', 'company', 'module_name','submodule','result','status',
				'marks.test1','marks.testdate1','marks.test2','marks.testdate2','marks.test3','marks.testdate3','marks.test4','marks.testdate4','marks.test5','marks.testdate5','marks.test6','marks.testdate6','marks.test7','marks.testdate7','marks.test8','marks.testdate8','marks.test9','marks.testdate9','marks.test10','marks.testdate10',
				'marks.total','marks.maxtotal','marks.percent','marks.total_contribution','course','regdate','email'
				]
			def completed_data = getResultsData("Completed",rowcount,page,sidx,sord)
			def inprogress_data = getResultsData("In Progress",rowcount,page,sidx,sord)
			def exempt_data = getResultsData("Exempt",rowcount,page,sidx,sord)
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
				sheet('Exmpt').with {
					fillHeader(headers)
					add(exempt_data, withProperties)
				}
			    save(response.outputStream)
			}
			
		}else{
			def results = getResultsData(status,rowcount,page,sidx,sord)			
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
				def total_contribution = subresult?.sum { it?.contribution_mark }
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
					course:coursename,module_name: modulename,result:result,status:status,submodule:key,
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
	//	def sortIndex = params.sidx ?: 'examDate'
	//	def sortOrder  = params.sord ?: 'asc'
		int max = (params?.rows ? params.int('rows') : 30)
		int page = (params?.page ? params.int('page') : 1)				
		int total = all_results?.size() 
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
