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
			def tutorList = Person.findAllByFirstNameAndLastName("No","Tutor")
			tutorList.addAll(PersonRole.findAllByRole(Role.findByAuthority('TUTOR'))*.person)
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
	
		def all_results = regInstance.results.sort(false){[it.module.name]} //.reverse()
		int total = all_results?.size()
		if(total < 1){
			def t =[records:0,page:0]
			render  t as JSON
			return
		}
		int max = (params?.rows ? params.int('rows') : 30)
		int page = (params?.page ? params.int('page') : 1)		
		int total_pages = (total > 0 ? Math.ceil(total/max) : 0)
		if(page > total_pages)	page = total_pages
		int offset = max*page-max
		
		int upperLimit = findUpperIndex(offset, max, total)
		List results = all_results.getAt(offset..upperLimit)
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
		def jsonData= [rows: jsonCells,page:page,records:total,total:total_pages]
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
			def curstatus = resultSummary.status
			def curresult = resultSummary.result
			def curTutor = resultSummary?.tutor
			//If it's a tutor, verify if they are the tutors of the current resultSummary
			if (SpringSecurityUtils.ifAnyGranted("TUTOR") && curTutor){
				
				if(curTutor?.id !=  springSecurityService.principal.id){
					//not allow
					println("Not allowed to edit this record!")
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
				println(">> checking pre-requisites....")			
				//check all the pre-requisites
				for(Module m: premodule.prerequisites){				
					def resSummary = ResultSummary.createCriteria().get(){
						createAlias("register","reg")
						eq ("module.id",m.id)
						and { eq ("reg.id",resultSummary.register.id as Long) }
					}

					if(resSummary){
						if(!(resSummary?.status?.equalsIgnoreCase("completed")) 
							& !(resSummary?.status?.equalsIgnoreCase("exempt"))){							
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
				//ensure dates are sorted if there are available
				if(params?.startDate) {
					bindData(resultSummary, params, [exclude: 'startDate'])
					bindData(resultSummary, ['startDate': params.date('startDate', ['dd-MMM-yyyy'])], [include: 'startDate'])
				}
				if(params?.endDate) {
					bindData(resultSummary, params, [exclude: 'endDate'])
					bindData(resultSummary, ['endDate': params.date('endDate', ['dd-MMM-yyyy'])], [include: 'endDate'])
				}
				
				//check for the various states set by the user and validate
				if(params?.status?.equalsIgnoreCase("exempt")){
					resultSummary.result = "Exempt"
					resultSummary.status = "Exempt"
				}else if(params?.status?.equalsIgnoreCase("In Progress")){
					if(params?.startDate){
						//ensure that we reset the result to "None"
						resultSummary.result = "None"						
					}else{							
						message = "Error! Please specify the date that the module started! "
						println(message)
						resultSummary.status = curstatus
						def result  =[message:message,state:state,id:id]
						render result as JSON
						return						
					}					
				}					
				else if(params?.status?.equalsIgnoreCase("Completed")){
					try{
						if(!params?.endDate?.trim().equals("") & !params?.startDate?.trim().equals("") & !params?.result?.trim().equalsIgnoreCase("none")){
							resultSummary.status = "Completed"
						}else{
							message = "Error! To set status to 'Completed', verify that start and completion dates are entered as well as the result is at least NOT 'None'"
							println(message)
							resultSummary.status = curstatus
							def result  =[message:message,state:state,id:id]
							render result as JSON
							return
						}						
					}catch(Exception e){
						e.printStackTrace()
						message = "Error! Failed to update record."
						resultSummary.status = curstatus
						def result  =[message:message,state:state,id:id]
						render result as JSON
						return
					}										
				}else if(params?.status?.equalsIgnoreCase("Not Started")){
					resultSummary.results = "None"
					resultSummary.startDate = null;
					resultSummary.endDate = null;					
				}		
				
				if(SpringSecurityUtils.ifAnyGranted("ADMIN")){
					if(tutorInstance) resultSummary.tutor=tutorInstance
				}else{
					if(curTutor) resultSummary.tutor=curTutor
				}
				
				//saving the resultSummary to the new values
				println("Saving the resultSummary object... ")
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
	
	private static int findUpperIndex(int offset, int max, int total) {
		max = offset + max - 1
		if (max >= total) {
			max -= max - total + 1
		}
		return max
	} //end helper method
} //end class
