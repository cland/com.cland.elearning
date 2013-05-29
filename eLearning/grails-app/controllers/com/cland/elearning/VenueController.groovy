package com.cland.elearning

class VenueController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def venueInstance = new Venue()
        venueInstance.properties = params
        return [venueInstance: venueInstance]
    }

    def edit = {
        def venueInstance = Venue.get(params.id)
        if (!venueInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'venue.label', default: 'Venue'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [venueInstance: venueInstance]
        }
    }

	def fusion = {
		def venueInstance = new Venue()
		venueInstance.properties = params
		return [venueInstance: venueInstance]
	}
} //end class
