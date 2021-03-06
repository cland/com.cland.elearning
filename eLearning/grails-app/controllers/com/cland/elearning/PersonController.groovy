package com.cland.elearning
import org.springframework.security.core.userdetails.User.AuthorityComparator;

import grails.converters.JSON
import pl.touk.excel.export.WebXlsxExporter
import grails.plugins.springsecurity.Secured
import java.text.SimpleDateFormat
class PersonController {
	def springSecurityService
	
    def index = {
        redirect(action: "list", params: params)
    }
	@Secured(["hasAnyRole('ADMIN','TUTOR')"])
    def list = {}
	def learners = {}
	def tutors = {}
	@Secured(["hasRole('ADMIN')"])
    def create = {
		
        def personInstance = new Person()
        personInstance.properties = params
		def orgs = getOrgs() //Organisation.list()
        return [personInstance: personInstance,orgList:orgs]
		
    }
	List getOrgs (){
		return Organisation.list()
	}
	def orgOptions(){
		def orgs = getOrgs()
		String options = "<option value='9999'>My Value</option>"
		for(Organisation org: orgs){
			options += "<option value='${org.id}'>${org.name}</option>"
		}
		render options
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
		
		def all_registrations = personInstance.learnerRegistrations.sort(false){[it.regDate]}
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
			[cell: [it.course.name,
					it.course.code,
					it.regDate.format('dd MMM yyyy'),					
				], id: it.id]  // this is the id of the registration NOT the person. so when de-registering we simply delete this registration using this id.
		}
		
		def jsonData= [rows: jsonCells,page:page,records:total,total:total_pages]

		render jsonData as JSON
		
	} //end jq_list_courses
	@Secured(["hasRole('ADMIN')"])
	def jq_remove_course = {
		def message = ""
		def state = "FAIL"
		
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
		
	} //end jq_remove_course
	
	def jq_export_learners = {
		
		String filter = null
		def states = []
		def status = "Completed"		
		boolean dosave = (params?.int('save') == 1 ? true : false)
		String reportType = (params?.rtype ? params.rtype : "1")
		def startdate = params?.startDate_Completed
		def enddate = params?.endDate_Completed
		if(startdate != null & startdate != "") startdate = parseDate(startdate) else startdate = null
		if(enddate != null  & startdate != "") enddate = parseDate(enddate) else null
		long moduleid = (params?.module.id ? params?.module.id?.toLong(): 0)
		def learnerId = params?.learnerid
		if(learnerId == "") learnerId = null
		
		def personList = null
		def tutorList = null
		if(startdate != null || enddate != null || moduleid > 0 || learnerId != null){			
			def results = getResultsData(null,startdate,enddate,moduleid,learnerId)
			personList = results?.collect{it?.register?.learner}.unique()
			tutorList = results?.collect{it?.register?.tutor}.unique()
		}else{
			personList = PersonRole.findAllByRole(Role.findByAuthority('LEARNER'))?.person
			tutorList = PersonRole.findAllByRole(Role.findByAuthority('TUTOR'))?.person
		}
				
		def data = personList?.collect({it?.toMap()})			 
		def headers = ['Firstname', 'Lastname', 'Student No.', 'Company', 'Email','Exam Type','Disabled','Gender','Current Learner',			
			'Id No.',
			'Date of Birth',
			'Marital Status',
			'Race',
			'Address',
			'Postal Address',
			'Postal Code',
			'City',
			'Region',
			'Country',
			'Contact No.',
			'Home No.',
			'Cell No.',
			'Comm Mode',
			'Career Years',
			'Job Title',
			'Department',
			'Tertiary Qualifications',
			'School Qualifications',
			'Disabilities',
			'Date Created'
			]
		def withProperties = ['firstname','lastname','studentno','company','email','examtype','disabled','gender','iscurrentlearner',
			'idno',
			'dateofbirth',
			'maritalstatus',
			'race',
			'addreess',
			'postaladdress',
			'postalcode',
			'city',
			'region',
			'country',
			'contactno',
			'homeno',
			'cellno',
			'communicationmode',
			'numyears',
			'jobtitle',
			'department',
			'tertiaryqualification',
			'schoolqualification',
			'disability',
			'created']
		
		def tutor_data = tutorList?.collect({it?.toMap()})
		def tutor_headers = ['Firstname', 'Lastname','Company', 'Email','Gender',
			'Id No.',
			'Date of Birth',
			'Marital Status',
			'Race',
			'Address',
			'Postal Address',
			'Postal Code',
			'City',
			'Region',
			'Country',
			'Contact No.',
			'Home No.',
			'Cell No.',
			'Comm Mode',
			'Career Years',
			'Job Title',
			'Department',
			'Tertiary Qualifications',
			'School Qualifications',
			'Disabilities',
			'Date Created']
		def tutor_withProperties = ['firstname','lastname','company','email','gender',
			'idno',
			'dateofbirth',
			'maritalstatus',
			'race',
			'addreess',
			'postaladdress',
			'postalcode',
			'city',
			'region',
			'country',
			'contactno',
			'homeno',
			'cellno',
			'communicationmode',
			'numyears',
			'jobtitle',
			'department',
			'tertiaryqualification',
			'schoolqualification',
			'disability',
			'created']
		
		new WebXlsxExporter().with {
			setResponseHeaders(response)
			sheet('Learners').with {
				fillHeader(headers)
				add(data, withProperties)
			}
			sheet('Tutors').with {
				fillHeader(tutor_headers)
				add(tutor_data, tutor_withProperties)
			}
			save(response.outputStream)
		}
	} //end method jq_export_people
	
	private static int findUpperIndex(int offset, int max, int total) {
		max = offset + max - 1
		if (max >= total) {
			max -= max - total + 1
		}
		return max
	} //end helper method findUpperIndex
	def getResultsData(String status,def period_startdate, def period_enddate, long moduleid, String learnerId){
		
		def resultSummaryInstanceList = ResultSummary.createCriteria().list() {
			createAlias('register','reg')
			createAlias('tutor','tut')
			createAlias('reg.learner','l')
			createAlias('module','module')
			order('reg.learner','asc')

			if(status){
				ilike('status',"%"+status+"%")
			}
			//test for date module was completed
			if(period_startdate != null & period_enddate != null){
				between('endDate', period_startdate, period_enddate)
			}
			if(moduleid > 0) {
				eq('module.id',moduleid)
			}
			if(learnerId){
				like("l.studentNo",learnerId)
			}
		}
		return resultSummaryInstanceList
	}
	private parseDate(date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
		return (!date) ? new Date() : df.parse(date)
   }
} //end class
