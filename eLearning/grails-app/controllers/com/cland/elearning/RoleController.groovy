package com.cland.elearning

class RoleController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def roleInstance = new Role()
        roleInstance.properties = params
        return [roleInstance: roleInstance]
    }

    def edit = {
        def roleInstance = Role.get(params.id)
        if (!roleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'role.label', default: 'Role'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [roleInstance: roleInstance]
        }
    }

}
