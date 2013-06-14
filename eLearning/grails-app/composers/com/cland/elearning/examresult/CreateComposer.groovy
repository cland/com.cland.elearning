package com.cland.elearning.examresult

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.ExamResult

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def examResultInstance = new ExamResult(self.params)
        if (!examResultInstance.save(flush: true) && examResultInstance.hasErrors()) {
            log.error examResultInstance.errors
            self.renderErrors(bean: examResultInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'examResult.label', default: 'ExamResult'), examResultInstance.id])
            redirect(controller: "examResult", action: "list")
        }
    }
}