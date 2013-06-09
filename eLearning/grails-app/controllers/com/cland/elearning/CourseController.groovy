package com.cland.elearning
import grails.converters.JSON
import grails.plugins.springsecurity.Secured
class CourseController {

    def index = {

        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def courseInstance = new Course()
        courseInstance.properties = params
        return [courseInstance: courseInstance]
    }
	def addmodule = {
		//println("addmodule: ${params}")
		def courseInstance = Course.get(params.id as Long)
        if (!courseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [courseInstance: courseInstance]
        }
	}
    def edit = {
        def courseInstance = Course.get(params.id)
        if (!courseInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [courseInstance: courseInstance]
        }
    }
	def show = {
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [courseInstance: courseInstance]
		}
	} //end show
	
	/*
	 * modules_list_url : "../jq_list_modules",
		learners_list_url : "../jq_list_learners",
		tutors_list_url	: "../jq_list_tutors",
		events_list_url	: "../jq_list_events",
	 */
	def jq_list_modules = {
		//println("jq_list_modules: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		
		def modules = courseInstance.modules //Module.createCriteria().list(max:maxRows, offset:rowOffset) {
		
		def jsonCells =	modules.collect {
			[cell: [it.name,
					it.description,
				], id: it.id]			
		}
		
		def jsonData= [rows: jsonCells] //,page:currentPage,records:totalRows,total:numberOfPages]
		//println(jsonData)
		render jsonData as JSON
		
	} //end jq_list_modules
	
	def jq_remove_module = {
		println("jq_list_modules: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		println("Removing module: ")
		def module = Module.get(params.id)
		if(module){
			courseInstance.modules.remove(module)
			courseInstance.save(flush:true)
		}
		//def modules = courseInstance.modules
		render "ok"
	}
	def jq_remove_learner = {
		println("jq_remove_learner: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		println("Removing learner: " + params.id + " - course id: " + params.courseid)
		def reg = Registration.get(params.id) //findAllWhere("learner.id":params.id as Long,"course.id":params.courseid as Long)
		if(reg){
			reg.delete()
		}else{
			println("reg is null " + reg)
		}
		render "ok"
	}
	def jq_list_learners = {
		//println("jq_list_learners: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {			
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		
		def registrations = courseInstance.registrations //Module.createCriteria().list(max:maxRows, offset:rowOffset) {
		
		def jsonCells =	registrations.collect {
			[cell: [it.learner.firstName,
					it.learner.lastName,
					it.regDate.format('dd MMM yyyy'),
					it.tutor.lastFirstName()					
				], id: it.id]  // this is the id of the registratin NOT the person. so when de-registering we simply delete this registration using this id.
		}
		
		def jsonData= [rows: jsonCells] //,page:currentPage,records:totalRows,total:numberOfPages]
		//println(jsonData)
		render jsonData as JSON
		
	}
	def jq_list_tutors = {
		render "tutors list  - coming soon!"
	}
	def jq_list_events = {
		render "events list  - coming soon!"
	}
} //end class
