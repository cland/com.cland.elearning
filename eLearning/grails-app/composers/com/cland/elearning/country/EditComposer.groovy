package com.cland.elearning.country

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Country

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def countryInstance = Country.get(params.id)
        if (countryInstance) {
            if (params.version != null) {
                def version = params.version
                if (countryInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'country.label', default: 'Country')],default:"Another user has updated this ${countryInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            countryInstance.properties = params
            if (!countryInstance.hasErrors() && countryInstance.save(flush: true)) {
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'country.label', default: 'Country'), countryInstance.id])
                redirect(controller: "country", action: "edit", id: countryInstance.id)
            }else {
                log.error countryInstance.errors
                self.renderErrors(bean: countryInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'country.label', default: 'Country'), params.id])
            redirect(controller: "country",action: "list")
        }

    }
}