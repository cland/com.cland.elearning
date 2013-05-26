package com.cland.elearning.role

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Role

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def roleInstance = new Role(self.params)
        if (!roleInstance.save(flush: true) && roleInstance.hasErrors()) {
            log.error roleInstance.errors
            self.renderErrors(bean: roleInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'role.label', default: 'Role'), roleInstance.id])
            redirect(controller: "role", action: "list")
        }
    }
}