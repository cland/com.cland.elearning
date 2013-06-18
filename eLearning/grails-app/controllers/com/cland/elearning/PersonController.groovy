package com.cland.elearning
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
class PersonController {
	def springSecurityService
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def personInstance = new Person()
        personInstance.properties = params
        return [personInstance: personInstance]
    }

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
					it.tutor.lastFirstName()
				], id: it.id]  // this is the id of the registration NOT the person. so when de-registering we simply delete this registration using this id.
		}
		
		def jsonData= [rows: jsonCells] //,page:currentPage,records:totalRows,total:numberOfPages]

		render jsonData as JSON
		
	}
} //end class
