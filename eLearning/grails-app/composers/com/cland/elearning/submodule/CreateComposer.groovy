package com.cland.elearning.submodule

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.SubModule

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def subModuleInstance = new SubModule(self.params)
        if (!subModuleInstance.save(flush: true) && subModuleInstance.hasErrors()) {
            log.error subModuleInstance.errors
            self.renderErrors(bean: subModuleInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'subModule.label', default: 'SubModule'), subModuleInstance.id])
            redirect(controller: "subModule", action: "list")
        }
    }
}