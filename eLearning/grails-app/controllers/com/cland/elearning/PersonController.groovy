package com.cland.elearning
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
class PersonController {
	def springSecurityService
    def index = {
        redirect(action: "list", params: params)
    }
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def list = {}
	@Secured(["hasRole('ADMIN')"])
    def create = {
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance]
    }
	@Secured(["hasRole('ADMIN')"])
    def edit = {
        def personInstance = Person.get(params.id)
        if (!personInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
            redirect(action: "list")
        }
        else {
			//get the roles
			def roleMap = personInstance.getAuthorities()
			
            return [personInstance: personInstance,roleMap:roleMap]
        }
    } //end def

	def show = {
		def personInstance = Person.get(params.id)
		if (!personInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
			redirect(action: "list")
		}
		else {
			//get the roles
			def roleMap = personInstance.getAuthorities()
			
			return [personInstance: personInstance,roleMap:roleMap]
		}
	} //end def
	
	/** Custom jquery function
	 * Lists all the courses that the person.id is registered for.
	 *
	 ***/
	def jq_list_courses = {
		//println("jq_list_courses: ${params}")
		def personInstance = Person.get(params.personid)
		if (!personInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'person.label', default: 'Person'), params.id])}"
			redirect(action: "show",params:params)
		}
		
		def registrations = personInstance.learnerRegistrations
		
		def jsonCells =	registrations.collect {
			[cell: [it.course.name,
					it.course.code,
					it.regDate.format('dd MMM yyyy'),					
				], id: it.id]  // this is the id of the registration NOT the person. so when de-registering we simply delete this registration using this id.
		}
		
		def jsonData= [rows: jsonCells] //,page:currentPage,records:totalRows,total:numberOfPages]

		render jsonData as JSON
		
	} //end jq_list_courses
	@Secured(["hasRole('ADMIN')"])
	def jq_remove_course = {
		def message = ""
		def state = "FAIL"
	//println("jq_remove_course: ${params}")
		
		def reg = Registration.get(params.id) 
		if(reg){
			reg.delete()
			message = reg.learner.toString() + " has been de-registered from course '${reg.course.name}'!"
			state = "OK"
		}else{
			println("reg is null " + reg)
			message = "No registration found!"
		}
		def response = [message:message,state:state,id:params.id]
		render response as JSON
		
	}
	
} //end class
