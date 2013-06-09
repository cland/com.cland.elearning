package com.cland.elearning.registration

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Registration

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def registrationInstance = new Registration(self.params)
        if (!registrationInstance.save(flush: true) && registrationInstance.hasErrors()) {
            log.error registrationInstance.errors
            self.renderErrors(bean: registrationInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
            redirect(controller: "registration", action: "list")
        }
    }
	
	void onClick_saveDialogButton(Event e) {
		def registrationInstance = new Registration(self.params)
		if (!registrationInstance.save(flush: true) && registrationInstance.hasErrors()) {
			log.error registrationInstance.errors
			self.renderErrors(bean: registrationInstance)
		} else {
			flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
			//redirect(controller: "registration", action: "list")
		}
	}
	
}