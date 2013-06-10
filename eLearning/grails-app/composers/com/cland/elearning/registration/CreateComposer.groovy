package com.cland.elearning.registration

import org.apache.jasper.compiler.Node.ParamsAction;
import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.Registration

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def registrationInstance = new Registration(self.params)
        if (!registrationInstance.save(flush: true) && registrationInstance.hasErrors()) {
            log.error registrationInstance.errors
            self.renderErrors(bean: registrationInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
            redirect(controller: "registration", action: "list")
        }
    }
	
	void onClick_saveDialogButton(Event e) {
		def params = self.params
		
	//	for(def key: params.keySet()){
	//		println(key + " - " + params.getAt(key))
	//	}
				
		Long active_tab = 1
		def courseId = params.getAt("course.id")
		def learnerId = params.getAt("learner.id")
		def registrationInstance = Registration.findAllWhere("learner.id":learnerId as Long,"course.id":courseId as Long)
		if(registrationInstance){
			flash.message = registrationInstance.learner.toString() + " is already registered!" //g.message(code: 'default.exists.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
			redirect(controller: "course", action: "show", params:[id:courseId as Long, tab:active_tab])
			return
		}
		
		//otherwise create a new one
		registrationInstance = new Registration(params)
		if (!registrationInstance.save(flush: true) && registrationInstance.hasErrors()) {
			log.error registrationInstance.errors
			self.renderErrors(bean: registrationInstance)
		} else {
			flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
			redirect(controller: "course", action: "show", params:[id:courseId as Long,tab:active_tab])
		}
	}
	
}