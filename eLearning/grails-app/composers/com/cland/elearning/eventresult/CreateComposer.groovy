package com.cland.elearning.eventresult

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.EventResult

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def eventResultInstance = new EventResult(self.params)
        if (!eventResultInstance.save(flush: true) && eventResultInstance.hasErrors()) {
            log.error eventResultInstance.errors
            self.renderErrors(bean: eventResultInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'eventResult.label', default: 'EventResult'), eventResultInstance.id])
            redirect(controller: "eventResult", action: "list")
        }
    }
}