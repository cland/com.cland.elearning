package com.cland.elearning
import grails.converters.JSON
class RegistrationController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def registrationInstance = new Registration()
        registrationInstance.properties = params
        return [registrationInstance: registrationInstance]
    }
	def register = {
		def registrationInstance = new Registration()
		registrationInstance.properties = params
		return [registrationInstance: registrationInstance]
	}
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
            return [registrationInstance: registrationInstance]
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
			[cell: [it.module.name ,
					it.result,
					it.status,
					it.certNumber,
				], id: it.id]
		}
		def jsonData= [rows: jsonCells]  //,page:currentPage,records:totalRows,total:numberOfPages]
		render jsonData as JSON
	}
	
	def jq_edit_results = {
		//println("jq_edit_results" + params)
		def resultSummary = null
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
			if (resultSummary) {
				// set the properties according to passed in parameters
				resultSummary.properties = params
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
