
import com.cland.elearning.Person
import com.cland.elearning.Role
import com.cland.elearning.PersonRole
import grails.util.*

class BootStrap {

	def init = { servletContext ->
		println "Bootstrap > environment: " + Environment.getCurrent()
		switch(Environment.getCurrent()){
			case "DEVELOPMENT":
				def adminRole = new Role(authority: 'ROLE_ADMIN').save(flush: true)
				def userRole = new Role(authority: 'ROLE_USER').save(flush: true)

				def adminUser = new Person(username: 'admin', enabled: true, password: 'password')
				adminUser.save()
				if(adminUser.hasErrors()){
					println adminUser.errors
				}
				PersonRole.create(adminUser, adminRole, true)

				assert Person.count() == 1
				assert Role.count() == 2
				assert PersonRole.count() == 1
				break
			case "PRODUCTION" :

				break
		}
	} //end init


	def destroy = {
	}
} //end class
