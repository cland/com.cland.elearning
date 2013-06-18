package com.cland.elearning.registration

import org.apache.jasper.compiler.Node.ParamsAction;
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
	//	println(params)
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
			//if new one has been created successfully, generate the resultsummary and examresult stubs
			createResultStubs(registrationInstance)
			flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
			redirect(controller: "course", action: "show", params:[id:courseId as Long,tab:active_tab])
		}
	} //end
	
	void createResultStubs(Registration registrationInstance){
		def courseInstance = registrationInstance.course
		def modules = courseInstance.modules
		
		def resultSummary = null
		for(Module m : modules){
			
			resultSummary = createResultSummary(m, registrationInstance)
			//create the examresult stubs
			//createExamResults(resultSummary)
			
			registrationInstance.addToResults(resultSummary)
			if(registrationInstance.save(flush:true) && registrationInstance.hasErrors()){
				println("Errors!")
				log.error registrationInstance.errors
			}else{
				//println("added to register for course module ${m.name}!! Size: ${registrationInstance.results.size()} - \n ${resultSummary.toString()}")
				resultSummary.save(flush:true)
				if(resultSummary.hasErrors()){
					println(resultSummary.errors)
				}else{
					//create the examresult stubs
					//createExamResults(resultSummary)
				}
			}
		} //end for all modules
	} //end method
	
	ResultSummary createResultSummary(Module module, Registration register){
		def resultSummary = new ResultSummary(
				status:"Not Started",
				result:"None",
				module:module,
				certNumber:""
				)	
		//def module = resultSummary.module
		def submodules = module.submodules
	//	println("Submodules size: " + submodules.size())
		def examResult = null
		def exams = null
		for(SubModule submod : submodules){
	//		println(">> SubModule:" + submod.name)
			exams = submod.exams		
			for(Exam e : exams){
				examResult = new ExamResult(
					examDate:new Date(),
					mark:0,
					percentMark:0.0,
					contributionMark:0.0,
					tutor:register.tutor,
					subModule:submod,
					exam:e,
					region:"",
					venue:""					
					)

				resultSummary.addToResults(examResult)
			} //end for all exam						
		} //end for all subModules
		return resultSummary
	} //end createExamResults
	
} //end class