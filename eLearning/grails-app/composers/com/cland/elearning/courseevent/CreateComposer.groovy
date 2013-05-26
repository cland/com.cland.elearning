package com.cland.elearning.courseevent

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.CourseEvent

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def courseEventInstance = new CourseEvent(self.params)
        if (!courseEventInstance.save(flush: true) && courseEventInstance.hasErrors()) {
            log.error courseEventInstance.errors
            self.renderErrors(bean: courseEventInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'courseEvent.label', default: 'CourseEvent'), courseEventInstance.id])
            redirect(controller: "courseEvent", action: "list")
        }
    }
}