package com.cland.elearning

class SubModuleController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def subModuleInstance = new SubModule()
        subModuleInstance.properties = params
        return [subModuleInstance: subModuleInstance]
    }

    def edit = {
        def subModuleInstance = SubModule.get(params.id)
        if (!subModuleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'subModule.label', default: 'SubModule'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [subModuleInstance: subModuleInstance]
        }
    }
	def show = {
		def subModuleInstance = SubModule.get(params.id)
		if (!subModuleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'submodule.label', default: 'SubModule'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [submoduleInstance: subModuleInstance]
		}
	}
} //end class
