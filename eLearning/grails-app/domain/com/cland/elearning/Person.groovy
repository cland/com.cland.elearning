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
	String firstName
	String lastName
	String idNo
	Date dateOfBirth
	String gender
	String address
	String city	
	String region
	String country
	String email	
	String contactNo
	Date dateCreated
	Organisation company
	static mappedBy = [tutorRegistrations:'tutor',learnerRegistrations:'learner']
	static hasMany = [
		tutorRegistrations:Registration,
		learnerRegistrations:Registration,
		results:ResultSummary,
		//worksFor:Organisation		
		]
	static constraints = {
		username blank: false, unique: true
		password blank: false
		firstName(blank:false)
		lastName(blank:false)
		idNo()
		dateOfBirth()
		gender(inList:["M", "F"])
		address()	
		city()
		region()
		country()
		contactNo()
		email(email:true)
		company(nullable:true)
		dateCreated()
	}

	static mapping = {
		password column: '`password`'
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
