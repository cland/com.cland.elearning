package com.cland.elearning

import org.springframework.dao.DataIntegrityViolationException
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

@Secured(["hasAnyRole('ADMIN','TUTOR')"])
class PreModuleController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [preModuleInstanceList: PreModule.list(params), preModuleInstanceTotal: PreModule.count()]
    }

    def create() {
        [preModuleInstance: new PreModule(params)]
    }
	
    def save() {
        def preModuleInstance = new PreModule(params)
        if (!preModuleInstance.save(flush: true)) {
            render(view: "create", model: [preModuleInstance: preModuleInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'preModule.label', default: 'PreModule'), preModuleInstance.id])
        redirect(action: "show", id: preModuleInstance.id)
    }
	


    def show(Long id) {
        def preModuleInstance = PreModule.get(id)
        if (!preModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "list")
            return
        }

        [preModuleInstance: preModuleInstance]
    }

    def edit(Long id) {
        def preModuleInstance = PreModule.get(id)
        if (!preModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "list")
            return
        }

        [preModuleInstance: preModuleInstance]
    }

    def update(Long id, Long version) {
        def preModuleInstance = PreModule.get(id)
        if (!preModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (preModuleInstance.version > version) {
                preModuleInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'preModule.label', default: 'PreModule')] as Object[],
                          "Another user has updated this PreModule while you were editing")
                render(view: "edit", model: [preModuleInstance: preModuleInstance])
                return
            }
        }

        preModuleInstance.properties = params

        if (!preModuleInstance.save(flush: true)) {
            render(view: "edit", model: [preModuleInstance: preModuleInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'preModule.label', default: 'PreModule'), preModuleInstance.id])
        redirect(action: "show", id: preModuleInstance.id)
    }

    def delete(Long id) {
		println(params)
        def preModuleInstance = PreModule.get(id)
        if (!preModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "list")
            return
        }

        try {
            preModuleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
			if(params.course.id){
				redirect(controller:"course", action: "show", id:params.course.id)
			}else{
            	redirect(action: "list")
			}
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "show", id: id)
        }
    }
	
	/** CUSTOM FUNCTIONS **/
	def dialogcreate(){
		//println("Dialog create/open: " + params)
		def premoduleInstance = PreModule.get(params.id)
		//first check if one already exists
		if(!premoduleInstance) premoduleInstance = new PreModule(params) 
		[preModuleInstance: premoduleInstance]
	}
	def dialogsave() {
		//println("Dialog save: " + params)
		//TODO: Check for circular reference Module1>>Module2 WHILE Module2>>Module1  => should be NOT allowed
		def message = ""
		def state = "FAIL"
		//def preModuleInstance = new PreModule(params)
		def preModuleInstance = PreModule.get(params.id)
		//first check if one already exists
		if(!preModuleInstance) {
			preModuleInstance = new PreModule(params)
		}else{
			preModuleInstance.properties = params
		}
		
		//make sure that the current module is not a pre-requisite of itself
		def currentModuleInstance = Module.get(params.current.id)
		if(currentModuleInstance){
			if(preModuleInstance.prerequisites?.contains(currentModuleInstance)){
				println("Module CANNOT pre-requisite itself!")
				preModuleInstance.prerequisites.remove(currentModuleInstance)
			}
		}
		if (!preModuleInstance.save(flush: true)) {
			render(view: "dialogcreate", model: [preModuleInstance: preModuleInstance])
			return
		}
		state = "OK"
		message = "Pre-requisites for module: '" + preModuleInstance.current.name + "' have been set successfully!" 
		flash.message = message // message(code: 'default.created.message', args: [message(code: 'preModule.label', default: 'PreModule'), preModuleInstance.id])
		redirect(controller:"course",action: "show", id: preModuleInstance.course.id)
	//	def response = [message:message,state:state,id:params.id]
	//	render response as JSON
	}
} //end class
