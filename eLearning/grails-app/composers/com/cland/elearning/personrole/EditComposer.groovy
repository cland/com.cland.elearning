package com.cland.elearning.personrole

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.PersonRole

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def personRoleInstance = PersonRole.get(params.id)
        if (personRoleInstance) {
            if (params.version != null) {
                def version = params.version
                if (personRoleInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'personRole.label', default: 'PersonRole')],default:"Another user has updated this ${personRoleInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            personRoleInstance.properties = params
            if (!personRoleInstance.hasErrors() && personRoleInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'personRole.label', default: 'PersonRole'), personRoleInstance.id])
                redirect(controller: "personRole", action: "edit", id: personRoleInstance.id)
            }else {
                log.error personRoleInstance.errors
                self.renderErrors(bean: personRoleInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'personRole.label', default: 'PersonRole'), params.id])
            redirect(controller: "personRole",action: "list")
        }

    }
}