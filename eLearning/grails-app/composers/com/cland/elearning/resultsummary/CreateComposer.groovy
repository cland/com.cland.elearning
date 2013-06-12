package com.cland.elearning.resultsummary

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.ResultSummary

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def resultSummaryInstance = new ResultSummary(self.params)
        if (!resultSummaryInstance.save(flush: true) && resultSummaryInstance.hasErrors()) {
            log.error resultSummaryInstance.errors
            self.renderErrors(bean: resultSummaryInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'resultSummary.label', default: 'ResultSummary'), resultSummaryInstance.id])
            redirect(controller: "resultSummary", action: "list")
        }
    }
}