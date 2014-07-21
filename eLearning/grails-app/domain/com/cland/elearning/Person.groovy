package com.cland.elearning

import java.util.Date;

class Person {

	transient springSecurityService

	String username
	String password	
	boolean enabled
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired
	//* Custom fields
	String salutation
	String firstName
	String lastName
	String middleName
	String knownAsName
	String idNo	
	String studentNo
	String homeLanguage
	Date dateOfBirth
	String gender
	String maritalStatus
	Race race
	String address
	String postalAddress
	String postalCode
	String city	
	Region region
	Country country
	String email	
	String contactNo
	String contactNoHome
	String cellNo
	String communicationMode //sms,email
	Date dateCreated
	Organisation company
	int numOfYears
	String department
	String jobTitle
	String tertiaryQualification
	String schoolQualification
	String disabilityYN
	String disabilityList
	String examType		//The type of exam that the learner can take standard/oral only/etc
	static transients = ['firstLastName','lastFirstName','age','isLearner','isTutor' ]
	static mappedBy = [tutorRegistrations:'tutor',learnerRegistrations:'learner']
	static hasMany = [
		tutorRegistrations:Registration,
		learnerRegistrations:Registration,
		results:ResultSummary,
		attachments:AttachmentHolder
		//worksFor:Organisation		
		]
	static constraints = {
		attachments(nullable:true)
		username nullable:true, blank: false, unique: true
		password nullable:true, blank: false
		firstName(blank:false)
		lastName(blank:false)
		middleName(nullable:true)
		knownAsName(nullable:true)
		homeLanguage(nullable:true)
		salutation(inList:["Mr","Mrs","Ms","Miss"],nullable:true)
		idNo(nullable:true)
		dateOfBirth(nullable:true)
		gender(inList:["M", "F"])
		race(nullable:true)
		maritalStatus(nullable:true,inList:["Single","Married","Divorced","Widowed"])
		disabilityYN(inList:["Yes","No"],nullable:true)
		disabilityList(nullable:true)
		communicationMode(nullable:true,inList:["Email","SMS"])
		schoolQualification(nullable:true)
		tertiaryQualification(nullable:true) 
		address(nullable:true)	
		postalAddress(nullable:true)
		postalCode(nullable:true)
		contactNoHome(nullable:true)
		city(nullable:true)
		region(nullable:true)
		country(nullable:true)
		contactNo(nullable:true)
		email(nullable:true,email:true)
		company(nullable:true)
		numOfYears(min:0)
		department(nullable:true)
		jobTitle(nullable:true)
		dateCreated()
		studentNo(nullable:true)
		cellNo(nullable:true)
		examType(nullable:true,inList:["Standard","Oral"])
	}

	static mapping = {
		password column: '`password`'
		numOfYears defaultValue: 0
	}

	Set<Role> getAuthorities() {
		PersonRole.findAllByPerson(this).collect { it.role } as Set
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}
	boolean isTutor(){
		getAuthorities()?.toListString()?.contains("TUTOR")
	}
	boolean isLearner(){
		getAuthorities()?.toListString()?.contains("LEARNER")
	}
	boolean isCurrentLearner(){
		if(!isLearner()) return false;
		def inprogress_states = ['In Progress']
		def regList = getRegistrations(inprogress_states)
		if(regList.size() > 0 ) return true; return false
		
	} //end function
	List getRegistrations(states){
		if(!isLearner()) return []
		def registrationList = Registration.createCriteria().list(){
			createAlias('learner',"l")
			eq('l.id',id)
			results {
				'in'('status',states)
			}
		}
		return registrationList;
	}
	def toMap(){
		[firstname: firstName,
			lastname:lastName,
			studentno:studentNo,
			company:company,
			email:email,
			role:getAuthorities(),
			examtype:examType,
			disabled:disabilityYN,
			gender:gender,
			iscurrentlearner:(isCurrentLearner()?'yes':'no'),
			islearner:(isLearner()?'yes':'no'),
			istutor:(isTutor()?'yes':'no')]
	}
	protected void encodePassword() {
		password = springSecurityService.encodePassword(password)
	}
	String lastFirstName(String s = " "){
		"${lastName}" + s + "${firstName}"
	}
	String firstLastName(String s = " "){
		"${firstName}" + s + "${lastName}"
	}
	String toString(){
		"${firstName} ${lastName}"
	}
	public getAge(){
		if(dateOfBirth == null){
			return 0
		}
		def now = new GregorianCalendar()
		Integer birthMonth = dateOfBirth.getAt(Calendar.MONTH)
		Integer birthYear = dateOfBirth.getAt(Calendar.YEAR)
		Integer birthDate = dateOfBirth.getAt(Calendar.DATE)
		Integer yearNow = now.get(Calendar.YEAR)

		def offset = new GregorianCalendar(
				yearNow,
				birthMonth-1,
				birthDate)
		return (yearNow - birthYear - (offset > now ? 1 : 0))
	}
} //end class
