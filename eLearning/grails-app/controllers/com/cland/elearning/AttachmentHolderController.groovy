package com.cland.elearning

import org.springframework.dao.DataIntegrityViolationException
import com.macrobit.grails.plugins.attachmentable.domains.Attachment;
class AttachmentHolderController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [attachmentHolderInstanceList: AttachmentHolder.list(params), attachmentHolderInstanceTotal: AttachmentHolder.count()]
    }

    def create() {
        [attachmentHolderInstance: new AttachmentHolder(params)]
    }

    def save() {
        def attachmentHolderInstance = new AttachmentHolder(params)
        if (!attachmentHolderInstance.save(flush: true)) {
            render(view: "create", model: [attachmentHolderInstance: attachmentHolderInstance])
            return
        }
		attachUploadedFilesTo(attachmentHolderInstance)
        flash.message = message(code: 'default.created.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), attachmentHolderInstance.id])
        redirect(action: "show", id: attachmentHolderInstance.id)
    }

    def show(Long id) {
        def attachmentHolderInstance = AttachmentHolder.get(id)
        if (!attachmentHolderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), id])
            redirect(action: "list")
            return
        }

        [attachmentHolderInstance: attachmentHolderInstance]
    }

    def edit(Long id) {
        def attachmentHolderInstance = AttachmentHolder.get(id)
        if (!attachmentHolderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), id])
            redirect(action: "list")
            return
        }

        [attachmentHolderInstance: attachmentHolderInstance]
    }

    def update(Long id, Long version) {
        def attachmentHolderInstance = AttachmentHolder.get(id)
        if (!attachmentHolderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (attachmentHolderInstance.version > version) {
                attachmentHolderInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'attachmentHolder.label', default: 'AttachmentHolder')] as Object[],
                          "Another user has updated this AttachmentHolder while you were editing")
                render(view: "edit", model: [attachmentHolderInstance: attachmentHolderInstance])
                return
            }
        }

        attachmentHolderInstance.properties = params

        if (!attachmentHolderInstance.save(flush: true)) {
            render(view: "edit", model: [attachmentHolderInstance: attachmentHolderInstance])
            return
        }
		attachUploadedFilesTo(attachmentHolderInstance)
        flash.message = message(code: 'default.updated.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), attachmentHolderInstance.id])
        redirect(action: "show", id: attachmentHolderInstance.id)
    }

    def delete(Long id) {
        def attachmentHolderInstance = AttachmentHolder.get(id)
        if (!attachmentHolderInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), id])
            redirect(action: "list")
            return
        }

        try {
            attachmentHolderInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'attachmentHolder.label', default: 'AttachmentHolder'), id])
            redirect(action: "show", id: id)
        }
    }
}
