package com.cland.elearning.courseevent

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.CourseEvent

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def courseEventInstance = CourseEvent.get(params.id)
        if (courseEventInstance) {
            if (params.version != null) {
                def version = params.version
                if (courseEventInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'courseEvent.label', default: 'CourseEvent')],default:"Another user has updated this ${courseEventInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            courseEventInstance.properties = params
            if (!courseEventInstance.hasErrors() && courseEventInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'courseEvent.label', default: 'CourseEvent'), courseEventInstance.id])
                redirect(controller: "courseEvent", action: "edit", id: courseEventInstance.id)
            }else {
                log.error courseEventInstance.errors
                self.renderErrors(bean: courseEventInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'courseEvent.label', default: 'CourseEvent'), params.id])
            redirect(controller: "courseEvent",action: "list")
        }

    }
}