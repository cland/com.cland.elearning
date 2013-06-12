package com.cland.elearning

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
		render "ok"
	}
	def jq_edit_results = {
		println("jq_edit_results" + params)
		render "ok"
	}
} //end class
