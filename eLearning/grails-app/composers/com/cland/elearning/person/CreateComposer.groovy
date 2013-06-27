package com.cland.elearning.person

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.*

class CreateComposer {

    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {	
		
        def personInstance = new Person(self.params)
        if (!personInstance.save(flush: true) && personInstance.hasErrors()) {			
            log.error personInstance.errors
            self.renderErrors(bean: personInstance)
        } else {
		//add the selected roles here		
		PersonRole.removeAll(personInstance)
		def roles = Role.list()
		for(Role r : roles){
			def tmp = self.params.list("role_${r.authority}")			
			if (tmp[0]) PersonRole.create(personInstance, r, true)		
		}
				
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.id])			
            redirect(controller: "person", action: "list")
        }
    }
}