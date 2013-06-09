package com.cland.elearning.course

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.*
import grails.converters.JSON
class CreateComposer {
    Window self
	
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
	
	def onClick_addModuleButton(Event e) {
		def message = ""
		def state = "FAIL"
		def params = self.params
		println(params);	
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			//not such course
			println("No such course")
			return;
		} 
		def module = null
		def idlist = params.list("modlist")
		
		idlist = JSON.parse(idlist.toString())
		
		for(int i=0;i<idlist.size();i++){							
			module = Module.get(idlist[i] as Long)
			if (module){
				
				courseInstance.addToModules(module)
				courseInstance.save(flush:true)
				if(courseInstance.hasErrors()){
					println courseInstance.errors
					message = "Error saving the course after adding module"
				}else{
					message = "Module added successfully!"
					state = "OK"
				}
				
			}else{
				println("No module found")
				message = "could not find the module with id "
			}
		}
						
		def response = [message:message,state:state]
		
		println(message + " - " + state)
		flash.message = response //g.message(code: 'default.created.message', args: [g.message(code: 'course.label', default: 'Course'), courseInstance.id])
		//return response // as JSON
		
	} //end addModuleButton 
}// end class