package com.cland.elearning.courseresult

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.CourseResult

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def courseResultInstance = new CourseResult(self.params)
        if (!courseResultInstance.save(flush: true) && courseResultInstance.hasErrors()) {
            log.error courseResultInstance.errors
            self.renderErrors(bean: courseResultInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'courseResult.label', default: 'CourseResult'), courseResultInstance.id])
            redirect(controller: "courseResult", action: "list")
        }
    }
}