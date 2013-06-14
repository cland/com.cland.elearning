package com.cland.elearning.examresult

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.ExamResult

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def examResultInstance = ExamResult.get(params.id)
        if (examResultInstance) {
            if (params.version != null) {
                def version = params.version
                if (examResultInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'examResult.label', default: 'ExamResult')],default:"Another user has updated this ${examResultInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            examResultInstance.properties = params
            if (!examResultInstance.hasErrors() && examResultInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'examResult.label', default: 'ExamResult'), examResultInstance.id])
                redirect(controller: "examResult", action: "edit", id: examResultInstance.id)
            }else {
                log.error examResultInstance.errors
                self.renderErrors(bean: examResultInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'examResult.label', default: 'ExamResult'), params.id])
            redirect(controller: "examResult",action: "list")
        }

    }
}