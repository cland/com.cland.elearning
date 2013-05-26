package com.cland.elearning.exam

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Exam

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def examInstance = Exam.get(params.id)
        if (examInstance) {
            if (params.version != null) {
                def version = params.version
                if (examInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'exam.label', default: 'Exam')],default:"Another user has updated this ${examInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            examInstance.properties = params
            if (!examInstance.hasErrors() && examInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'exam.label', default: 'Exam'), examInstance.id])
                redirect(controller: "exam", action: "edit", id: examInstance.id)
            }else {
                log.error examInstance.errors
                self.renderErrors(bean: examInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'exam.label', default: 'Exam'), params.id])
            redirect(controller: "exam",action: "list")
        }

    }
}