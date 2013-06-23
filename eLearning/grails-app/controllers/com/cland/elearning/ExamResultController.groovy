package com.cland.elearning
import grails.plugins.springsecurity.Secured
class ExamResultController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}
	@Secured(["hasRole('ADMIN')","hasRole('TUTOR')"])
    def create = {
        def examResultInstance = new ExamResult()
        examResultInstance.properties = params
        return [examResultInstance: examResultInstance]
    }
	@Secured(["hasRole('ADMIN')","hasRole('TUTOR')"])
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
