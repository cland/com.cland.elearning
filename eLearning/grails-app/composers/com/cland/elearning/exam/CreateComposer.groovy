package com.cland.elearning.exam

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Exam

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def examInstance = new Exam(self.params)
        if (!examInstance.save(flush: true) && examInstance.hasErrors()) {
            log.error examInstance.errors
            self.renderErrors(bean: examInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'exam.label', default: 'Exam'), examInstance.id])
            redirect(controller: "exam", action: "list")
        }
    }
}