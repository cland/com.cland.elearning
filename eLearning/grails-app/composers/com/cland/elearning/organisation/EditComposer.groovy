package com.cland.elearning.organisation

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Organisation

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def organisationInstance = Organisation.get(params.id)
        if (organisationInstance) {
            if (params.version != null) {
                def version = params.version
                if (organisationInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'organisation.label', default: 'Organisation')],default:"Another user has updated this ${organisationInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            organisationInstance.properties = params
            if (!organisationInstance.hasErrors() && organisationInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'organisation.label', default: 'Organisation'), organisationInstance.id])
                redirect(controller: "organisation", action: "edit", id: organisationInstance.id)
            }else {
                log.error organisationInstance.errors
                self.renderErrors(bean: organisationInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'organisation.label', default: 'Organisation'), params.id])
            redirect(controller: "organisation",action: "list")
        }

    }
}