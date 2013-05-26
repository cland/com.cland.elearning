package com.cland.elearning.module

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Module

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def moduleInstance = new Module(self.params)
        if (!moduleInstance.save(flush: true) && moduleInstance.hasErrors()) {
            log.error moduleInstance.errors
            self.renderErrors(bean: moduleInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'module.label', default: 'Module'), moduleInstance.id])
            redirect(controller: "module", action: "list")
        }
    }
}