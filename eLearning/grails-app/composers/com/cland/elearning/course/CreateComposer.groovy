package com.cland.elearning.course

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Course

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def courseInstance = new Course(self.params)
        if (!courseInstance.save(flush: true) && courseInstance.hasErrors()) {
            log.error courseInstance.errors
            self.renderErrors(bean: courseInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'course.label', default: 'Course'), courseInstance.id])
            redirect(controller: "course", action: "list")
        }
    }
}