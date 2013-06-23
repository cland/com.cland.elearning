package com.cland.elearning
import grails.plugins.springsecurity.Secured
class OrganisationController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def create = {
        def organisationInstance = new Organisation()
        organisationInstance.properties = params
        return [organisationInstance: organisationInstance]
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
