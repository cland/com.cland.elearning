package com.cland.elearning
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class RegistrationController {

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
            redirect(action: "list")
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
			tutorInstance = Person.get(params.tutor.id as Long)
			if (resultSummary) {
				// set the properties according to passed in parameters
				resultSummary.properties = params
				if(tutorInstance) resultSummary.tutor=tutorInstance
				if (! resultSummary.hasErrors() && resultSummary.save()) {
					message = "ResultSummary  ${resultSummary.toString()} Updated"
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
