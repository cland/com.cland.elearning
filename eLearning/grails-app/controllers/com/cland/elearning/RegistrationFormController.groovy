package com.cland.elearning

import org.springframework.dao.DataIntegrityViolationException

class RegistrationFormController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [registrationFormInstanceList: RegistrationForm.list(params), registrationFormInstanceTotal: RegistrationForm.count()]
    }

    def create() {
        [registrationFormInstance: new RegistrationForm(params)]
    }

    def save() {
        def registrationFormInstance = new RegistrationForm(params)
        if (!registrationFormInstance.save(flush: true)) {
            render(view: "create", model: [registrationFormInstance: registrationFormInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), registrationFormInstance.id])
        redirect(action: "show", id: registrationFormInstance.id)
    }

    def show(Long id) {
        def registrationFormInstance = RegistrationForm.get(id)
        if (!registrationFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), id])
            redirect(action: "list")
            return
        }

        [registrationFormInstance: registrationFormInstance]
    }

    def edit(Long id) {
        def registrationFormInstance = RegistrationForm.get(id)
        if (!registrationFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), id])
            redirect(action: "list")
            return
        }

        [registrationFormInstance: registrationFormInstance]
    }

    def update(Long id, Long version) {
        def registrationFormInstance = RegistrationForm.get(id)
        if (!registrationFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (registrationFormInstance.version > version) {
                registrationFormInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'registrationForm.label', default: 'RegistrationForm')] as Object[],
                          "Another user has updated this RegistrationForm while you were editing")
                render(view: "edit", model: [registrationFormInstance: registrationFormInstance])
                return
            }
        }

        registrationFormInstance.properties = params

        if (!registrationFormInstance.save(flush: true)) {
            render(view: "edit", model: [registrationFormInstance: registrationFormInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), registrationFormInstance.id])
        redirect(action: "show", id: registrationFormInstance.id)
    }

    def delete(Long id) {
        def registrationFormInstance = RegistrationForm.get(id)
        if (!registrationFormInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), id])
            redirect(action: "list")
            return
        }

        try {
            registrationFormInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'registrationForm.label', default: 'RegistrationForm'), id])
            redirect(action: "show", id: id)
        }
    }
}
