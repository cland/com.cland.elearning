package com.cland.elearning.personrole

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.PersonRole

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def personRoleInstance = new PersonRole(self.params)
        if (!personRoleInstance.save(flush: true) && personRoleInstance.hasErrors()) {
            log.error personRoleInstance.errors
            self.renderErrors(bean: personRoleInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'personRole.label', default: 'PersonRole'), personRoleInstance.id])
            redirect(controller: "personRole", action: "list")
        }
    }
}