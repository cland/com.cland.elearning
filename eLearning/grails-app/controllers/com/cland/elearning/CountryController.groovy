package com.cland.elearning
import grails.plugins.springsecurity.Secured
@Secured(["hasRole('ADMIN')"])
class CountryController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def countryInstance = new Country()
        countryInstance.properties = params
        return [countryInstance: countryInstance]
    }

    def edit = {
        def countryInstance = Country.get(params.id)
        if (!countryInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'country.label', default: 'Country'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [countryInstance: countryInstance]
        }
    }

}
