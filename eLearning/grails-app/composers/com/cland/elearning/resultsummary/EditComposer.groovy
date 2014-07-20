package com.cland.elearning.resultsummary

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zul.*
import com.cland.elearning.*
import org.codehaus.groovy.grails.plugins.springsecurity.*

class EditComposer {
	def springSecurityService
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
    }

    void onClick_saveButton(Event e) {
        def params=self.params
		def tutorInstance = null
		def noTutor = Person.findByFirstNameAndLastName("No","Tutor")
        def resultSummaryInstance = ResultSummary.get(params.id)
        if (resultSummaryInstance) {
            if (params.version != null) {
                def version = params.version
                if (resultSummaryInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'resultSummary.label', default: 'ResultSummary')],default:"Another user has updated this ${resultSummaryInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            } //end checking if theres no conflict editing.
			
			def curstatus = resultSummaryInstance.status
			def curresult = resultSummaryInstance.result
			def curTutor = (resultSummaryInstance?.tutor ? resultSummaryInstance?.tutor : noTutor)
			
			//If it's a tutor, verify if they are the tutors of the current resultSummaryInstance
			if (SpringSecurityUtils.ifAnyGranted("TUTOR") && curTutor){
				
				if(curTutor?.id !=  springSecurityService.principal.id){
					//not allow
					println("Not allowed to edit this record!")					
					String failureMessage = g.message(code:"default.failure",args:[g.message(code: 'resultSummary.label', default: 'resultSummary')],default:"You do not have sufficient rights to edit this record!")
					Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
					return
				}
			} //end check for tutor
			
			// Work out the list of pre-modules and for each test if it's completed
			def premodule = PreModule.createCriteria().get(){
				createAlias("course","c")
				eq ("current.id",resultSummaryInstance.module.id)
				and { eq ("c.id",resultSummaryInstance.register.course.id as Long) }
			}
			
			if((premodule) && !(params.status.equalsIgnoreCase("not started"))){
				println(">> checking pre-requisites....")
				//check all the pre-requisites
				for(Module m: premodule.prerequisites){
					def resSummary = resultSummaryInstance.createCriteria().get(){
						createAlias("register","reg")
						eq ("module.id",m.id)
						and { eq ("reg.id",resultSummaryInstance.register.id as Long) }
					}

					if(resSummary){
						if(!(resSummary?.status?.equalsIgnoreCase("completed"))
							& !(resSummary?.status?.equalsIgnoreCase("exempt"))){
							def message = "Pre-requisite '${m.name}' needs to be completed first."
							println(message)
							params.status =resultSummaryInstance.status							
							String failureMessage = g.message(code:"default.failure",args:[g.message(code: 'resultSummary.label', default: 'resultSummary')],default:message)
							Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
							return
						}
					}
				}
			}//end for loop checking for pre-requisites
			Long selTutorId = params.long('tutor.id')
			
			if(selTutorId){
				tutorInstance = Person.get(selTutorId)
			} else {
			 	tutorInstance = curTutor//selected tutor
			}
			
			// set the properties according to passed in parameters
			resultSummaryInstance.properties = params
			//ensure dates are sorted if there are available
//			if(params?.startDate) {
//				bindData(resultSummaryInstance, params, [exclude: 'startDate'])
//				bindData(resultSummaryInstance, ['startDate': params.date('startDate', ['dd-MMM-yyyy'])], [include: 'startDate'])
//			}
//			if(params?.endDate) {
//				bindData(resultSummaryInstance, params, [exclude: 'endDate'])
//				bindData(resultSummaryInstance, ['endDate': params.date('endDate', ['dd-MMM-yyyy'])], [include: 'endDate'])
//			}
			
			//check for the various states set by the user and validate
			println(">> checking the various possible states set by user and validating... ")
			if(params?.status?.equalsIgnoreCase("exempt")){
				resultSummaryInstance.result = "Exempt"
				resultSummaryInstance.status = "Exempt"
			}else if(params?.status?.equalsIgnoreCase("In Progress")){
				if(params?.startDate){
					//ensure that we reset the result to "None"
					resultSummaryInstance.result = "None"
				}else{
					def message = "Error! Please specify the date that the module started! "
					println(message)
					resultSummaryInstance.status = curstatus										
					String failureMessage = g.message(code:"default.failure",args:[g.message(code: 'resultSummary.label', default: 'resultSummary')],default:message)
					Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
					return
				}
			}
			else if(params?.status?.equalsIgnoreCase("Completed")){
				try{
					if(params?.endDate != null & params?.startDate != null & !params?.result?.trim().equalsIgnoreCase("none")){
						resultSummaryInstance.status = "Completed"
					}else{
						def message = "Error! To set status to 'Completed', verify that start and completion dates are entered as well as the result is at least NOT 'None'"
						println(message)
						resultSummaryInstance.status = curstatus										
						String failureMessage = g.message(code:"default.failure",args:[g.message(code: 'resultSummary.label', default: 'resultSummary')],default:message)
						Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
						return
					}
				}catch(Exception ex){
					ex.printStackTrace()
					def message = "Error! Failed to update record."
					resultSummaryInstance.status = curstatus
					println(message)										
					String failureMessage = g.message(code:"default.failure",args:[g.message(code: 'resultSummary.label', default: 'resultSummary')],default:message)
					Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
					return
				}
			}else if(params?.status?.equalsIgnoreCase("Not Started")){
				resultSummaryInstance.result = "None"
				resultSummaryInstance.startDate = null;
				resultSummaryInstance.endDate = null;
			}
			
			if(SpringSecurityUtils.ifAnyGranted("ADMIN")){				
				if(tutorInstance) resultSummaryInstance.tutor=tutorInstance
			}else{
				if(curTutor) resultSummaryInstance.tutor=curTutor
			}
			
			//saving the resultSummaryInstance to the new values
			println("Saving the resultSummary object... ")
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