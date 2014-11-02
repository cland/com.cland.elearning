package com.cland.elearning

import java.util.Date;

class RegistrationForm {
	static attachmentable = true
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
	
	int numOfYears
	String department
	String jobTitle
	String tertiaryQualification
	String schoolQualification
	String disabilityYN
	String disabilityList
	String examType		//The type of exam that the learner can take standard/oral only/etc
	static transients = ['firstLastName','lastFirstName','age' ]

	String companyName
	String companyPhoneNo
	String companyEmail
	String companyPhyAddress
	String companyPhyPostCode
	String companyPostalAddress
	String companyPostalPostCode
	String companyContactPerson
	String companyCity
	Region companyRegion
	Country companyCountry
	String companyComments
	String isMember  //the company
	String vatNumber
	
	String lineManager
	String lineManagerEmail
	String hrManager
	String hrManagerEmail
	String trainingManager
	String trainingManagerEmail
	String seniorManager
	String seniorManageEmail
	
	String paintModulesCompleted
	String rawModulesCompleted
	String decoModulesCompleted
	
	static hasMany = [paintModules:Module, rawModules:Module,decoModules:Module]
	static constraints = {		
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
		numOfYears(min:0)
		department(nullable:true)
		jobTitle(nullable:true)
		dateCreated()
		studentNo(nullable:true)
		cellNo(nullable:true)
		examType(nullable:true,inList:["Standard","Oral"])
	}

	static mapping = {		
		numOfYears defaultValue: 0
	}


	def beforeInsert() {
		
	}

	def beforeUpdate() {
		
	}
	
	def toMap(){
		[firstname: firstName,
			lastname:lastName,
			salutation:salutation,
			studentno:studentNo,
			company:company,
			email:email,
			examtype:examType,
			disabled:disabilityYN,
			gender:gender,
			idno:idNo,
			dateofbirth:dateOfBirth?.format("dd MMM yyyy"),			
			maritalstatus:maritalStatus,
			race:race.toString(),
			address:address,
			postaladdress:postalAddress,
			postalcode:postalCode,
			city:city,
			region:region?.toString(),
			country:country?.toString(),
			contactno:contactNo,
			homeno:contactNoHome,
			cellno:cellNo,
			communicationmode:communicationMode,
			numyears:numOfYears,
			tertiaryqualification:tertiaryQualification,
			schoolqualification:schoolQualification,
			disability:disabilityList,
			created:dateCreated.format("dd MMM yyyy"),
			jobtitle:jobTitle,
			department:department,
			language:homeLanguage,
			knownas:knownAsName
			]
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
	/**
	 * To ensure that all attachments are removed when the "onwer" domain is deleted.
	 */
	transient def beforeDelete = {
		withNewSession{
		  removeAttachments()
		}
	  }
} //end class
