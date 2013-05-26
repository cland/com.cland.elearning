package com.cland.elearning.venue

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Venue

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def venueInstance = new Venue(self.params)
        if (!venueInstance.save(flush: true) && venueInstance.hasErrors()) {
            log.error venueInstance.errors
            self.renderErrors(bean: venueInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'venue.label', default: 'Venue'), venueInstance.id])
            redirect(controller: "venue", action: "list")
        }
    }
}