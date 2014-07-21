package com.cland.elearning
import grails.converters.JSON
import grails.plugins.springsecurity.Secured

class CourseController {

    def index = {

        redirect(action: "list", params: params)
    }

    def list = {}
	@Secured(["hasRole('ADMIN')"])
    def create = {
        def courseInstance = new Course()
        courseInstance.properties = params
        return [courseInstance: courseInstance]
    }
	@Secured(["hasRole('ADMIN')"])
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
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
	def addmodule = {
		//Used to just instantiate the dialog box. On submit of that dialog box is handled by the onClick_addModuleButton course CreateComposer
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
	def jq_list_modules = {
		//println("jq_list_modules: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		
		def all_modules = courseInstance.modules.sort(false){it.name} //Module.createCriteria().list(max:maxRows, offset:rowOffset) {
		int total = all_modules?.size()
		if(total < 1){
			def t =[records:0,page:0]
			render  t as JSON
			return
		}
		// -- Pagination calculations
		int max = (params?.rows ? params.int('rows') : 30)
		int page = (params?.page ? params.int('page') : 1)
		
		int total_pages = (total > 0 ? Math.ceil(total/max) : 0)
		if(page > total_pages)	page = total_pages
		int offset = max*page-max		
		int upperLimit = findUpperIndex(offset, max, total)
		
		List modules = all_modules.getAt(offset..upperLimit)
		
		//Get premodules for the final list of modules
		def premodules = courseInstance.premodules
		def premapId = [:]
		def premapValues = [:]
		for(Module mod : modules){			
			for(PreModule premod:premodules){
				if(premod.current.name.equalsIgnoreCase(mod.name)){
					premapId.putAt(mod.name ,premod.id as Long)
					premapValues.putAt(mod.name ,premod.toString())
				}
			}
		}				
		
		def jsonCells =	modules.collect {
			[cell: [it.name,
					it.description,
					premapValues.get(it.name),
					premapId.get(it.name)
				], id: it.id]			
		}
		
		def jsonData= [rows: jsonCells,page:page,records:total,total:total_pages] 
		//println(jsonData)
		render jsonData as JSON
		
	} //end jq_list_modules
	@Secured(["hasRole('ADMIN')"])
	def jq_remove_module = {
		def message = ""
		def state = "FAIL"
		//println("jq_list_modules: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		
		def module = Module.get(params.id)
		def premodule = null
		if(module){
			courseInstance.modules.remove(module)
			//premodule = PreModule.find("from PreModule where current.id=:module.id and course.id=:courseInstance.id")
			premodule = PreModule.findByCurrentAndCourse(module,courseInstance)
			if(premodule){
				courseInstance.premodules.remove(premodule)
				premodule.delete()
			}else{
				println("No premodules found!")
			}
			courseInstance.save(flush:true)
			message = module.toString() + " has been removed!"
			state = "OK"
		}
		
		def response = [message:message,state:state,id:params.id]		
		render response as JSON
	}
	@Secured(["hasRole('ADMIN')"])
	def jq_remove_learner = {
		def message = ""
		def state = "FAIL"
	//println("jq_remove_learner: ${params}")
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {
			//println("null course instance")
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		//println("Removing learner: " + params.id + " - course id: " + params.courseid)
		def reg = Registration.get(params.id) //findAllWhere("learner.id":params.id as Long,"course.id":params.courseid as Long)
		if(reg){
			reg.delete()
			message = reg.learner.toString() + " has been de-registered!"
			state = "OK"
		}else{
			println("reg is null " + reg)
			message = "No registration found!"
		}
		def response = [message:message,state:state,id:params.id]		
		render response as JSON
		
	}

	def jq_list_learners = {

		String sidx = (params?.sidx ? params.sidx : "")
		String sord = (params?.sord ? params.sord : "")
		
		def courseInstance = Course.get(params.courseid)
		if (!courseInstance) {			
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'course.label', default: 'Course'), params.id])}"
			redirect(action: "show",params:params)
		}
		
		def all_registrations = courseInstance.registrations?.sort{
				if(sidx?.equalsIgnoreCase("lastName")){
					it.learner?.lastName
				}else if(sidx?.equalsIgnoreCase("regDate")){
					it.regDate
				}else{
					it.learner?.firstName
				}			
			} //Module.createCriteria().list(max:maxRows, offset:rowOffset) {
		if(sord?.equalsIgnoreCase("desc")){
			all_registrations.reverse(true)
		}else{
			all_registrations.reverse(false)
		}
		int total = all_registrations?.size()
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
		List registrations = all_registrations.getAt(offset..upperLimit)
		
		def jsonCells =	registrations.collect {
			[cell: [it.learner.firstName,
					it.learner.lastName,
					it.regDate.format('dd MMM yyyy'),
					//it.tutor.lastFirstName()					
				], id: it.id]  // this is the id of the registratin NOT the person. so when de-registering we simply delete this registration using this id.
		} 
		
		def jsonData= [rows: jsonCells,page:page,records:total,total:total_pages,sord:sord,sidx:sidx]
		
		render jsonData as JSON
		
	}
	def jq_list_tutors = {
		render "tutors list  - coming soon!"
	}
	def jq_list_events = {
		render "events list  - coming soon!"
	}
	
	private static int findUpperIndex(int offset, int max, int total) {
		max = offset + max - 1
		if (max >= total) {
			max -= max - total + 1
		}
		return max
	} //end helper method findUpperIndex
} //end class
