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
	String email	
	String contactNo
	Date dateCreated
	static hasMany = [registrations:Registration,eventResults:EventResult]
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
	
	String toString(){
		"${lastName}, ${firstName}"
	}

} //end class
