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

}
