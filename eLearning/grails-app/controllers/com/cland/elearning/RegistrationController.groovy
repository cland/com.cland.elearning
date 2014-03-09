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
					it.startDate?.format("dd-MMM-yyy"),
					it.endDate?.format("dd-MMM-yyy"),
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
		def id = params.id
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
			// Work out the list of pre-modules and for each test if it's completed
			def premodule = PreModule.createCriteria().get(){
				createAlias("course","c")
				eq ("current.id",resultSummary.module.id)
				and { eq ("c.id",resultSummary.register.course.id as Long) }
			}
			
			if((premodule) && !(params.status.equalsIgnoreCase("not started"))){				
				//check all the pre-requisites
				for(Module m: premodule.prerequisites){				
					def resSummary = ResultSummary.createCriteria().get(){
						createAlias("register","reg")
						eq ("module.id",m.id)
						and { eq ("reg.id",resultSummary.register.id as Long) }
					}
					
					
					if(resSummary){
						println(resSummary.status)
						// & !(resSummary.status.equalsIgnoreCase("exempt"))
						if(!(resSummary.status.equalsIgnoreCase("completed"))){							
							message = "Pre-requisite '${m.name}' needs to be completed first."
							println(message)
							params.status =resultSummary.status
							def result  =[message:message,state:state,id:id]
							render result as JSON
							return
						}
					}
				}
			}//end for loop checking for pre-requisites
			tutorInstance = Person.get(params.tutor.id as Long) //selected tutor
			if (resultSummary) {
				// set the properties according to passed in parameters	
				resultSummary.properties = params
				if(params?.startDate) {
					bindData(resultSummary, params, [exclude: 'startDate'])
					bindData(resultSummary, ['startDate': params.date('startDate', ['dd-MMM-yyyy'])], [include: 'startDate'])
					//if status is not in-progress, then change it to in progress
					if(!params?.status?.equalsIgnoreCase("In Progress")){
						resultSummary.status = "In Progress" 
					}
					if(params?.endDate) {
						bindData(resultSummary, params, [exclude: 'endDate'])
						bindData(resultSummary, ['endDate': params.date('endDate', ['dd-MMM-yyyy'])], [include: 'endDate'])
						if(!params?.status?.equalsIgnoreCase("Completed")){
							resultSummary.status = "Completed"
						}
					}
				}else{
					resultSummary.status = "Not Started"
				}		
				
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
					message = "Error! Could not update record."
					println(resultSummary.errors)
				}
			}
			
			break
		}
		
		def response = [message:message,state:state,id:id]
		render response as JSON
	} //end jq_edit_results
} //end class
