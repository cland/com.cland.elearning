package com.cland.elearning.module

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Module

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def moduleInstance = Module.get(params.id)
        if (moduleInstance) {
            if (params.version != null) {
                def version = params.version
                if (moduleInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'module.label', default: 'Module')],default:"Another user has updated this ${moduleInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            moduleInstance.properties = params
            if (!moduleInstance.hasErrors() && moduleInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'module.label', default: 'Module'), moduleInstance.id])
                redirect(controller: "module", action: "edit", id: moduleInstance.id)
            }else {
                log.error moduleInstance.errors
                self.renderErrors(bean: moduleInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'module.label', default: 'Module'), params.id])
            redirect(controller: "module",action: "list")
        }

    }
}