package com.cland.elearning

import grails.converters.JSON
import org.springframework.dao.DataIntegrityViolationException

class CertificateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [certificateInstanceList: Certificate.list(params), certificateInstanceTotal: Certificate.count()]
    }

    def create() {
        [certificateInstance: new Certificate(params)]
    }

    def save() {
        def certificateInstance = new Certificate(params)
        if (!certificateInstance.save(flush: true)) {
            render(view: "create", model: [certificateInstance: certificateInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'certificate.label', default: 'Certificate'), certificateInstance.id])
        redirect(action: "show", id: certificateInstance.id)
    }

    def show(Long id) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        [certificateInstance: certificateInstance]
    }

    def edit(Long id) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        [certificateInstance: certificateInstance]
    }

    def update(Long id, Long version) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (certificateInstance.version > version) {
                certificateInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'certificate.label', default: 'Certificate')] as Object[],
                          "Another user has updated this Certificate while you were editing")
                render(view: "edit", model: [certificateInstance: certificateInstance])
                return
            }
        }

        certificateInstance.properties = params

        if (!certificateInstance.save(flush: true)) {
            render(view: "edit", model: [certificateInstance: certificateInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'certificate.label', default: 'Certificate'), certificateInstance.id])
        redirect(action: "show", id: certificateInstance.id)
    }

    def delete(Long id) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        try {
            certificateInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def generate(){
		//params: resultSummary.id
		//certno should be yyyymm-10001
		def today = new Date()
		Certificate certInstance = new Certificate(params).save()
		if(certInstance){
			Long n = certInstance?.id
			n = n + 10000
			String certNo = g.formatDate(date:today,format:"yyyymm") + "-" + n.toString()
			certInstance.certno = certNo
			certInstance.save(flush:true)
		}
		render certInstance?.toAutoCompleteMap() as JSON
	}
}//end class
