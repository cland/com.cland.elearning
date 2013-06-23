package com.cland.elearning

import org.springframework.dao.DataIntegrityViolationException

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
        def preModuleInstance = PreModule.get(id)
        if (!preModuleInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "list")
            return
        }

        try {
            preModuleInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'preModule.label', default: 'PreModule'), id])
            redirect(action: "show", id: id)
        }
    }
}
