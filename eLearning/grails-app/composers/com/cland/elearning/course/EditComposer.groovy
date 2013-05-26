package com.cland.elearning.course

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Course

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def courseInstance = Course.get(params.id)
        if (courseInstance) {
            if (params.version != null) {
                def version = params.version
                if (courseInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'course.label', default: 'Course')],default:"Another user has updated this ${courseInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            courseInstance.properties = params
            if (!courseInstance.hasErrors() && courseInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'course.label', default: 'Course'), courseInstance.id])
                redirect(controller: "course", action: "edit", id: courseInstance.id)
            }else {
                log.error courseInstance.errors
                self.renderErrors(bean: courseInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'course.label', default: 'Course'), params.id])
            redirect(controller: "course",action: "list")
        }

    }
}