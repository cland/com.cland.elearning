package com.cland.elearning.resultsummary

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.ResultSummary

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def resultSummaryInstance = ResultSummary.get(params.id)
        if (resultSummaryInstance) {
            if (params.version != null) {
                def version = params.version
                if (resultSummaryInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'resultSummary.label', default: 'ResultSummary')],default:"Another user has updated this ${resultSummaryInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            resultSummaryInstance.properties = params
            if (!resultSummaryInstance.hasErrors() && resultSummaryInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'resultSummary.label', default: 'ResultSummary'), resultSummaryInstance.id])
                redirect(controller: "resultSummary", action: "show", id: resultSummaryInstance.id)
            }else {
                log.error resultSummaryInstance.errors
                self.renderErrors(bean: resultSummaryInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'resultSummary.label', default: 'ResultSummary'), params.id])
            redirect(controller: "resultSummary",action: "list")
        }

    }
}