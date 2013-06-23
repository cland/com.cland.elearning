package com.cland.elearning
import grails.plugins.springsecurity.Secured
class RaceController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}
	@Secured(["hasRole('ADMIN')"])
    def create = {
        def raceInstance = new Race()
        raceInstance.properties = params
        return [raceInstance: raceInstance]
    }
	@Secured(["hasRole('ADMIN')"])
    def edit = {
        def raceInstance = Race.get(params.id)
        if (!raceInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'race.label', default: 'Race'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [raceInstance: raceInstance]
        }
    }

}
