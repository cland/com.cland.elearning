package com.cland.elearning.race

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Race

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def raceInstance = new Race(self.params)
        if (!raceInstance.save(flush: true) && raceInstance.hasErrors()) {
            log.error raceInstance.errors
            self.renderErrors(bean: raceInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'race.label', default: 'Race'), raceInstance.id])
            redirect(controller: "race", action: "list")
        }
    }
}