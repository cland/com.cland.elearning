package com.cland.elearning.course

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.*
import grails.converters.JSON
class CreateComposer {
    Window self
	Checkbox cbox
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def courseInstance = new Course(self.params)
        if (!courseInstance.save(flush: true) && courseInstance.hasErrors()) {
            log.error courseInstance.errors
            self.renderErrors(bean: courseInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'course.label', default: 'Course'), courseInstance.id])
            redirect(controller: "course", action: "list")
        }
    } //end onClic_saveButton
	

	void onClick_addModuleButton(Event e) {
		def message = ""
		def state = "FAIL"
		def params = self.params
		//println(params);		
		
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			//not such course
			println("No such course")
			return;
		} 
		
		def module = null
		params.each {
			//println(it.key + " = "+ it.value)
			if(it.key.contains("modlist_")){
				if(it.value){
					def modid = (it.key - "modlist_") as Long
					module = Module.get(modid)
					if (module){
						if(courseInstance.modules.contains(module)){
							//message += (i+1) + ") Module '" + module.toString() + "' already exists<br/>"
						}else{
							courseInstance.addToModules(module)
							courseInstance.save(flush:true)
							if(courseInstance.hasErrors()){
								println courseInstance.errors
								message += "Error saving the course after adding module!"
								state = "FAIL"
							}else{
								message += " Module '" + module.toString() + "' added successfully!<br/>"
								state = "OK"
								//TODO: For all already registered users, create result stubs for these new modules - if they don't exist already.
								
							}
						} //end else does not already exists
					}else{
						println("No module found")
						message += " Could not find the module with id '" + modid + "' <br/>"
					}
				}
			} //end if contains modlist_
		} //end params.each
		
		
						
		def response = [message:message,state:state]
		
		flash.message = message + " - " + state 
		//return response // as JSON
		redirect(controller: "course", action: "show", params:[id:courseInstance.id, tab:0])
	} //end addModuleButton 
}// end class