package com.cland.elearning.region

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Region

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def regionInstance = new Region(self.params)
        if (!regionInstance.save(flush: true) && regionInstance.hasErrors()) {
            log.error regionInstance.errors
            self.renderErrors(bean: regionInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'region.label', default: 'Region'), regionInstance.id])
            redirect(controller: "region", action: "list")
        }
    }
}