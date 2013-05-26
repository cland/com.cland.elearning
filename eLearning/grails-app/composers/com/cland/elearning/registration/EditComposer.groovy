package com.cland.elearning.registration

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Registration

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def registrationInstance = Registration.get(params.id)
        if (registrationInstance) {
            if (params.version != null) {
                def version = params.version
                if (registrationInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'registration.label', default: 'Registration')],default:"Another user has updated this ${registrationInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            registrationInstance.properties = params
            if (!registrationInstance.hasErrors() && registrationInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
                redirect(controller: "registration", action: "edit", id: registrationInstance.id)
            }else {
                log.error registrationInstance.errors
                self.renderErrors(bean: registrationInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'registration.label', default: 'Registration'), params.id])
            redirect(controller: "registration",action: "list")
        }

    }
}