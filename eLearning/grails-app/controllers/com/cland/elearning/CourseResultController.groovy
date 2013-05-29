package com.cland.elearning

class CourseResultController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def courseResultInstance = new CourseResult()
        courseResultInstance.properties = params
        return [courseResultInstance: courseResultInstance]
    }

    def edit = {
        def courseResultInstance = CourseResult.get(params.id)
        if (!courseResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'courseResult.label', default: 'CourseResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [courseResultInstance: courseResultInstance]
        }
    }

}
