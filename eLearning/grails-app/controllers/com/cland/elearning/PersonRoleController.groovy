package com.cland.elearning


class PersonRoleController {
	
	
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def personRoleInstance = new PersonRole()
        personRoleInstance.properties = params
        return [personRoleInstance: personRoleInstance]
    }

    def edit = {
        def personRoleInstance = PersonRole.get(params.id)
        if (!personRoleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'personRole.label', default: 'PersonRole'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [personRoleInstance: personRoleInstance]
        }
    }

	
} //end class
