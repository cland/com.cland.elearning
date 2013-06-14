package com.cland.elearning
import grails.converters.JSON
class ResultSummaryController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def resultSummaryInstance = new ResultSummary()
        resultSummaryInstance.properties = params
        return [resultSummaryInstance: resultSummaryInstance]
    }

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
		if (!resultSummaryInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'resultSummary.label', default: 'ResultSummary'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [resultSummaryInstance: resultSummaryInstance]
		}
	}

	def jq_list_results = {
		println("jq_list_results" + params)
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

		def results = resultSummaryInstance.results.sort(false){it.subModule.type} //.reverse() 
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
					it.percentMark,
					it.tutor.toString(),
				], id: it.id]
		}
		def jsonData= [rows: jsonCells]  //,page:currentPage,records:totalRows,total:numberOfPages]
		render jsonData as JSON
	}
	
	def jq_edit_results = {
		println("jq_edit_results" + params)
		render "ok"
	}
} //end class
