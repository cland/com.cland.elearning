package com.cland.elearning
import grails.plugins.springsecurity.Secured
@Secured(["hasRole('ADMIN')"])
class RegionController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def regionInstance = new Region()
        regionInstance.properties = params
        return [regionInstance: regionInstance]
    }

    def edit = {
        def regionInstance = Region.get(params.id)
        if (!regionInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'region.label', default: 'Region'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [regionInstance: regionInstance]
        }
    }

}
