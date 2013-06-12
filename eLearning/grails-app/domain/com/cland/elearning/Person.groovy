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
	static mappedBy = [tutorRegistrations:'tutor',learnerRegistrations:'learner']
	static hasMany = [
		tutorRegistrations:Registration,
		learnerRegistrations:Registration,
		results:ResultSummary
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
		region(inList:["Western Cape","Gauteng","KZN","Northern Province","Free State","Eastern Cape","Limpopo"])
		country()
		contactNo()
		email(email:true)
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
