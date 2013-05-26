package com.cland.elearning

class CourseEventController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def courseEventInstance = new CourseEvent()
        courseEventInstance.properties = params
        return [courseEventInstance: courseEventInstance]
    }

    def edit = {
        def courseEventInstance = CourseEvent.get(params.id)
        if (!courseEventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'courseEvent.label', default: 'CourseEvent'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [courseEventInstance: courseEventInstance]
        }
    }

}
