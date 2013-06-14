package com.cland.elearning

class ExamResultController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def examResultInstance = new ExamResult()
        examResultInstance.properties = params
        return [examResultInstance: examResultInstance]
    }

    def edit = {
        def examResultInstance = ExamResult.get(params.id)
        if (!examResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'examResult.label', default: 'ExamResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [examResultInstance: examResultInstance]
        }
    }

}
