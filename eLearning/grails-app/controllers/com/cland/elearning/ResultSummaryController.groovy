package com.cland.elearning
import grails.converters.JSON
import groovy.time.DatumDependentDuration
import groovy.time.TimeCategory;
import groovy.time.TimeDuration
import grails.plugins.springsecurity.Secured
class ResultSummaryController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def create = {
        def resultSummaryInstance = new ResultSummary()
        resultSummaryInstance.properties = params
        return [resultSummaryInstance: resultSummaryInstance]
    }
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def edit = {
        def resultSummaryInstance = ResultSummary.get(params.id)
        if (!resultSummaryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultSummary.label', default: 'ResultSummary'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [resultSummaryInstance: resultSummaryInstance]
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
	}

	def jq_list_results = {
		//println("jq_list_results" + params)
		//get the exam summary instance
		def resultSummaryInstance = ResultSummary.get(params.resultSumId)
		if (!resultSummaryInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultSummary.label', default: 'ResultSummary'), params.resultSumId])}"
			redirect(action: "show",params:params)
		}
		
	//	def sortIndex = params.sidx ?: 'examDate'
	//	def sortOrder  = params.sord ?: 'asc'

	//	def maxRows = Integer.valueOf(params.rows)
	//	def currentPage = Integer.valueOf(params.page) ?: 1

	//	def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

		def results = resultSummaryInstance.results.sort(false){[it.subModule.type,it.exam.testNumber]} //.reverse() 
	//	def results = ExamResult.createCriteria().list(max:maxRows, offset:rowOffset) {
			// first name case insensitive where the field begins with the search term
			//if (params.name)
			//	ilike('name',params.name + '%')
			// set the order and direction
//			order(sortIndex, sortOrder)
//		}
//		def totalRows = results.totalCount
//		def numberOfPages = Math.ceil(totalRows / maxRows)

		def jsonCells = results.collect {
			[cell: [it.subModule.type + ": " + it.subModule.name,
					it.exam.testNumber,
					it.mark,
					it.exam.maxMark,
					String.format( '%.1f', it.percentMark) ,
					String.format( '%.1f', it.contributionMark),
				], id: it.id]
		}
		def jsonData= [rows: jsonCells]  //,page:currentPage,records:totalRows,total:numberOfPages]
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
