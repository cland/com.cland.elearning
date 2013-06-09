package com.cland.elearning

class RegistrationController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def registrationInstance = new Registration()
        registrationInstance.properties = params
        return [registrationInstance: registrationInstance]
    }
	def register = {
		def registrationInstance = new Registration()
		registrationInstance.properties = params
		return [registrationInstance: registrationInstance]
	}
    def edit = {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [registrationInstance: registrationInstance]
        }
    }

}
