package com.cland.elearning.courseresult

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.CourseResult

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def courseResultInstance = CourseResult.get(params.id)
        if (courseResultInstance) {
            if (params.version != null) {
                def version = params.version
                if (courseResultInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'courseResult.label', default: 'CourseResult')],default:"Another user has updated this ${courseResultInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            courseResultInstance.properties = params
            if (!courseResultInstance.hasErrors() && courseResultInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'courseResult.label', default: 'CourseResult'), courseResultInstance.id])
                redirect(controller: "courseResult", action: "edit", id: courseResultInstance.id)
            }else {
                log.error courseResultInstance.errors
                self.renderErrors(bean: courseResultInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'courseResult.label', default: 'CourseResult'), params.id])
            redirect(controller: "courseResult",action: "list")
        }

    }
}