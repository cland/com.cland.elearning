package com.cland.elearning.region

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Region

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def regionInstance = Region.get(params.id)
        if (regionInstance) {
            if (params.version != null) {
                def version = params.version
                if (regionInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'region.label', default: 'Region')],default:"Another user has updated this ${regionInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            regionInstance.properties = params
            if (!regionInstance.hasErrors() && regionInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'region.label', default: 'Region'), regionInstance.id])
                redirect(controller: "region", action: "edit", id: regionInstance.id)
            }else {
                log.error regionInstance.errors
                self.renderErrors(bean: regionInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'region.label', default: 'Region'), params.id])
            redirect(controller: "region",action: "list")
        }

    }
}