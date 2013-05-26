package com.cland.elearning.eventresult

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.EventResult

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def eventResultInstance = EventResult.get(params.id)
        if (eventResultInstance) {
            if (params.version != null) {
                def version = params.version
                if (eventResultInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'eventResult.label', default: 'EventResult')],default:"Another user has updated this ${eventResultInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            eventResultInstance.properties = params
            if (!eventResultInstance.hasErrors() && eventResultInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'eventResult.label', default: 'EventResult'), eventResultInstance.id])
                redirect(controller: "eventResult", action: "edit", id: eventResultInstance.id)
            }else {
                log.error eventResultInstance.errors
                self.renderErrors(bean: eventResultInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'eventResult.label', default: 'EventResult'), params.id])
            redirect(controller: "eventResult",action: "list")
        }

    }
}