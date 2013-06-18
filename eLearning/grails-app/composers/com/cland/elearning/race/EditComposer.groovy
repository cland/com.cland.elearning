package com.cland.elearning.race

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Race

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def raceInstance = Race.get(params.id)
        if (raceInstance) {
            if (params.version != null) {
                def version = params.version
                if (raceInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'race.label', default: 'Race')],default:"Another user has updated this ${raceInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            raceInstance.properties = params
            if (!raceInstance.hasErrors() && raceInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'race.label', default: 'Race'), raceInstance.id])
                redirect(controller: "race", action: "edit", id: raceInstance.id)
            }else {
                log.error raceInstance.errors
                self.renderErrors(bean: raceInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'race.label', default: 'Race'), params.id])
            redirect(controller: "race",action: "list")
        }

    }
}