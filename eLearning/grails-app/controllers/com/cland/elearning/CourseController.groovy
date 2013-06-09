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
		println("addmodule: ${params}")
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
		println("jq_list_modules: ${params}")
		def courseInstance = Course.get(params.id)
		if (!courseInstance) {
			println("null course instance")
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
		println(jsonData)
		render jsonData as JSON
		
	} //end jq_list_modules
	
	def jq_list_learners = {
		println("jq_list_learners: ${params}")
		def courseInstance = Course.get(params.id)
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
				], id: it.id]
		}
		
		def jsonData= [rows: jsonCells] //,page:currentPage,records:totalRows,total:numberOfPages]
		println(jsonData)
		render jsonData as JSON
		
	}
	def jq_list_tutors = {
		render "tutors list  - coming soon!"
	}
	def jq_list_events = {
		render "events list  - coming soon!"
	}
} //end class
