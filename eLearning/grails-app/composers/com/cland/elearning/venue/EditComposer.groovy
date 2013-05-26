package com.cland.elearning.venue

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Venue

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def venueInstance = Venue.get(params.id)
        if (venueInstance) {
            if (params.version != null) {
                def version = params.version
                if (venueInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'venue.label', default: 'Venue')],default:"Another user has updated this ${venueInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            venueInstance.properties = params
            if (!venueInstance.hasErrors() && venueInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'venue.label', default: 'Venue'), venueInstance.id])
                redirect(controller: "venue", action: "edit", id: venueInstance.id)
            }else {
                log.error venueInstance.errors
                self.renderErrors(bean: venueInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'venue.label', default: 'Venue'), params.id])
            redirect(controller: "venue",action: "list")
        }

    }
}