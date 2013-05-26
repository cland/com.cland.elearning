package com.cland.elearning

class EventResultController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def eventResultInstance = new EventResult()
        eventResultInstance.properties = params
        return [eventResultInstance: eventResultInstance]
    }

    def edit = {
        def eventResultInstance = EventResult.get(params.id)
        if (!eventResultInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'eventResult.label', default: 'EventResult'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [eventResultInstance: eventResultInstance]
        }
    }

}
