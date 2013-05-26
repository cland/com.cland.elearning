package com.cland.elearning.role

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Role

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def roleInstance = Role.get(params.id)
        if (roleInstance) {
            if (params.version != null) {
                def version = params.version
                if (roleInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'role.label', default: 'Role')],default:"Another user has updated this ${roleInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            roleInstance.properties = params
            if (!roleInstance.hasErrors() && roleInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'role.label', default: 'Role'), roleInstance.id])
                redirect(controller: "role", action: "edit", id: roleInstance.id)
            }else {
                log.error roleInstance.errors
                self.renderErrors(bean: roleInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'role.label', default: 'Role'), params.id])
            redirect(controller: "role",action: "list")
        }

    }
}