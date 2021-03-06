package com.cland.elearning.submodule

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.SubModule

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def subModuleInstance = SubModule.get(params.id)
        if (subModuleInstance) {
            if (params.version != null) {
                def version = params.version
                if (subModuleInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'subModule.label', default: 'SubModule')],default:"Another user has updated this ${subModuleInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            subModuleInstance.properties = params
            if (!subModuleInstance.hasErrors() && subModuleInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'subModule.label', default: 'SubModule'), subModuleInstance.id])
                redirect(controller: "subModule", action: "edit", id: subModuleInstance.id)
            }else {
                log.error subModuleInstance.errors
                self.renderErrors(bean: subModuleInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'subModule.label', default: 'SubModule'), params.id])
            redirect(controller: "subModule",action: "list")
        }

    }
}