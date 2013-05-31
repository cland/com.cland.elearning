package com.cland.elearning

class ModuleController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def moduleInstance = new Module()
        moduleInstance.properties = params
        return [moduleInstance: moduleInstance]
    }

    def edit = {
        def moduleInstance = Module.get(params.id)
        if (!moduleInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [moduleInstance: moduleInstance]
        }
    }
	def show = {
		def moduleInstance = Module.get(params.id)
		if (!moduleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [moduleInstance: moduleInstance]
		}
	}
}
