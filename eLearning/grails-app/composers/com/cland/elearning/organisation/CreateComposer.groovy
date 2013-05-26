package com.cland.elearning.organisation

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Organisation

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def organisationInstance = new Organisation(self.params)
        if (!organisationInstance.save(flush: true) && organisationInstance.hasErrors()) {
            log.error organisationInstance.errors
            self.renderErrors(bean: organisationInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'organisation.label', default: 'Organisation'), organisationInstance.id])
            redirect(controller: "organisation", action: "list")
        }
    }
}