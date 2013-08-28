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
		username blank: false, unique: true
		password blank: false
		firstName(blank:false)
		lastName(blank:false)
		middleName(nullable:true)
		knownAsName(nullable:true)
		homeLanguage(nullable:true)
		salutation(inList:["Mr","Mrs","Ms","Miss"],nullable:true)
		idNo()
		dateOfBirth()
		gender(inList:["M", "F"])
		race(nullable:true)
		maritalStatus(nullable:true,inList:["Single","Married","Divorced","Widowed"])
		disabilityYN(inList:["Yes","No"],nullable:true)
		disabilityList(nullable:true)
		communicationMode(nullable:true,inList:["Email","SMS"])
		schoolQualification(nullable:true)
		tertiaryQualification(nullable:true) 
		address()	
		postalAddress(nullable:true)
		postalCode(nullable:true)
		contactNoHome(nullable:true)
		city(nullable:true)
		region(nullable:true)
		country(nullable:true)
		contactNo()
		email(email:true)
		company(nullable:true)
		numOfYears(min:0)
		department(nullable:true)
		jobTitle(nullable:true)
		dateCreated()
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

} //end class
