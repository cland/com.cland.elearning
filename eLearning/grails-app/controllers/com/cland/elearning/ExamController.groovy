package com.cland.elearning

class ExamController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def examInstance = new Exam()
        examInstance.properties = params
        return [examInstance: examInstance]
    }

    def edit = {
        def examInstance = Exam.get(params.id)
        if (!examInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'exam.label', default: 'Exam'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [examInstance: examInstance]
        }
    }

}
