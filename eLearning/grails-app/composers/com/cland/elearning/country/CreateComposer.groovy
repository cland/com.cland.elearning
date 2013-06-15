package com.cland.elearning.country

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Country

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def countryInstance = new Country(self.params)
        if (!countryInstance.save(flush: true) && countryInstance.hasErrors()) {
            log.error countryInstance.errors
            self.renderErrors(bean: countryInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'country.label', default: 'Country'), countryInstance.id])
            redirect(controller: "country", action: "list")
        }
    }
}