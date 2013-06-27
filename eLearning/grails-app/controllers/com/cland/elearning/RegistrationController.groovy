package com.cland.elearning
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
import org.codehaus.groovy.grails.plugins.springsecurity.*
class RegistrationController {

	def springSecurityService
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}
	
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def create = {
        def registrationInstance = new Registration()
        registrationInstance.properties = params
        return [registrationInstance: registrationInstance]
    }
	@Secured(["hasRole('ADMIN')"])
	def register = {
		def registrationInstance = new Registration()
		registrationInstance.properties = params
		return [registrationInstance: registrationInstance]
	}
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def edit = {
        def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [registrationInstance: registrationInstance]
        }
    } //end edit method
	
	def show ={
		  def registrationInstance = Registration.get(params.id)
        if (!registrationInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.id])}"
            redirect(action: "list")''''''
        }
        else { 
			def tutorList = PersonRole.findAllByRole(Role.findByAuthority('TUTOR'))*.person
			
			def tmp = tutorList.collect {
				it.id +":"+ it.toString()					
			}
			tmp = tmp.toString().replaceAll(", ", ";").replace("[","\"").replace("]", "\"")			
            return [registrationInstance: registrationInstance,tutorList:tmp]
        }
	}
	
	def jq_list_results = {
		//println("jq_list_results" + params)
		//get the registration instance
		def regInstance = Registration.get(params.regId)
		if (!regInstance) {			
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'registration.label', default: 'Registration'), params.regId])}"
			redirect(action: "show",params:params)
		}		
	
		def results = regInstance.results.sort(false){[it.module.name]} //.reverse()

		def jsonCells = results.collect {
			[cell: [it.module?.name ,
					it.result,
					it.status,
					it.tutor?.id,
					it.certNumber,
				], id: it.id]
		}
		def jsonData= [rows: jsonCells]  //,page:currentPage,records:totalRows,total:numberOfPages]
		render jsonData as JSON
	}
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def jq_edit_results = {
		//println("jq_edit_resultSummary" + params)
		def resultSummary = null
		def tutorInstance = null
		def message = ""
		def state = "FAIL"
		def id
		switch (params.oper) {
			case 'add':
			break
			case 'del':
			break
			default: //edit
			resultSummary = ResultSummary.get(params.id)
			def curTutor = resultSummary?.tutor
			//If it's a tutor, verify if they are the tutors of the current resultSummary
			if (SpringSecurityUtils.ifAnyGranted("TUTOR") && curTutor){
				if(curTutor?.id !=  springSecurityService.principal.id){
					//not allow
					println("Not allow to edit this record!")
					message = "You do not have sufficient rights to edit this record!"
					break;
				}
			}	
			tutorInstance = Person.get(params.tutor.id as Long) //selected tutor
			if (resultSummary) {
				// set the properties according to passed in parameters
				resultSummary.properties = params
				if(SpringSecurityUtils.ifAnyGranted("ADMIN")){
					if(tutorInstance) resultSummary.tutor=tutorInstance
				}else{
					if(curTutor) resultSummary.tutor=curTutor
				}
				
				
				if (! resultSummary.hasErrors() && resultSummary.save()) {
					message = "Summary result for ${resultSummary.module.name} has been updated successfully!"
					id = resultSummary.id									
					state = "OK"
				} else {
					message = "Could Not Update Record"
					println(resultSummary.errors)
				}
			}
			
			break
		}
		
		def response = [message:message,state:state,id:id]
		render response as JSON
	} //end jq_edit_results
} //end class
