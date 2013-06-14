package com.cland.elearning.person

import javax.annotation.security.RolesAllowed;
import com.cland.elearning.*;

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
//import com.cland.elearning.Person

class EditComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if (params.version != null) {
                def version = params.version
                if (personInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'person.label', default: 'Person')],default:"Another user has updated this ${personInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            personInstance.properties = params
            if (!personInstance.hasErrors() && personInstance.save(flush: true)) {
				//update the roles here
				PersonRole.removeAll(personInstance)				
				def roles = Role.list()				
				for(Role r : roles){					
					def tmp = params.list("role_${r.authority}")
					if (tmp[0]) PersonRole.create(personInstance, r, true)					
				}				
								
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.id])
                redirect(controller: "person", action: "edit", id: personInstance.id)
            }else {
                log.error personInstance.errors
                self.renderErrors(bean: personInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'person.label', default: 'Person'), params.id])
            redirect(controller: "person",action: "list")
        }

    }
	
} //end class 