package com.cland.elearning
import grails.plugins.springsecurity.Secured
import grails.converters.JSON
class OrganisationController {

    def index = {
        redirect(action: "list", params: params)
    }
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def test = {
		println "hello world";
		render "Hello world"
	}
    def list = {}
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def create = {
        def organisationInstance = new Organisation()
        organisationInstance.properties = params
        return [organisationInstance: organisationInstance]
    }
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def dialogcreate = {
		def organisationInstance = new Organisation()
		organisationInstance.properties = params
		return [organisationInstance: organisationInstance]
	}
	
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def dialogsave = {
		//println ("Saving...${params}");
		def message = ""
		def organisationInstance = new Organisation(params)
		if (!organisationInstance.save(flush: true)) {
			println organisationInstance.errors
		//	render(view: "create", model: [organisationInstance: organisationInstance])
		//	return
			message = "Error: " + organisationInstance.errors
			def response = [message:message,id:'nothing']
			render response as JSON
			return
		}
		
	//	flash.message = message(code: 'default.created.message', args: [message(code: 'organisationInstance.label', default: 'organisation'), organisationInstance.name])
	//	redirect(action: "show", id: organisationInstance.id)
		message = "success " 
		def response = [message:message,id:organisationInstance.id]
		render response as JSON
	}
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def edit = {
        def organisationInstance = Organisation.get(params.id)
        if (!organisationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'organisation.label', default: 'Organisation'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [organisationInstance: organisationInstance]
        }
    }

}
