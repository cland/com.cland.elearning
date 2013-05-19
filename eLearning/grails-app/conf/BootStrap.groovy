
import com.cland.elearning.Exam
import com.cland.elearning.Module
import com.cland.elearning.Person
import com.cland.elearning.Role
import com.cland.elearning.PersonRole
import com.cland.elearning.SubModule
import grails.util.*

class BootStrap {

	def init = { servletContext ->
		println "Bootstrap > environment: " + Environment.getCurrent()
		switch(Environment.getCurrent()){
			case "DEVELOPMENT":
				def adminRole = new Role(authority: 'ADMIN').save(flush: true)
				def learnerRole = new Role(authority: 'LEARNER').save(flush: true)
				def tutorRole = new Role(authority: 'TUTOR').save(flush: true)
				def counsellorRole = new Role(authority: 'COUNSELLOR').save(flush: true)

			//* Admin user
				def adminUser = new Person(username: 'admin',
				enabled: true,
				password: 'password',
				firstName: 'System',
				lastName: 'Admin',
				dateOfBirth:(new Date() - 365*30),
				gender:"M",
				address:"123 Main St",
				city:"Cape Town",
				email:"jay@whereever.com")

				adminUser.save()
				if(adminUser.hasErrors()){
					println adminUser.errors
				}
				PersonRole.create(adminUser, adminRole, true)

			//** ordinary user
				def someUser = new Person(username: 'mary',
				enabled: true,
				password: 'password',
				firstName: 'Mary',
				lastName: 'Donga',
				dateOfBirth:(new Date() - 365*30),
				gender:"F",
				address:"12 Main1 St",
				city:"Gauteng",
				email:"user1@whereever.com")

				someUser.save()
				if(someUser.hasErrors()){
					println someUser.errors
				}
				PersonRole.create(someUser, learnerRole, true)
				

				//** tutor/counsellor user
				 def staffUser = new Person(username: 'kris',
				 enabled: true,
				 password: 'password',
				 firstName: 'Kristen',
				 lastName: 'Ter',
				 dateOfBirth:(new Date() - 365*30),
				 gender:"F",
				 address:"12 Main1 St",
				 city:"Durban",
				 email:"kris1@whereever.com")
 
				 staffUser.save()
				 if(staffUser.hasErrors()){
					 println staffUser.errors
				 }
				 PersonRole.create(staffUser, tutorRole, true)
				 PersonRole.create(staffUser, counsellorRole, true)
				// assert Person.count() == 2
				// assert Role.count() == 2
				// assert PersonRole.count() == 2
 
			//** Module-Submodule-exam

				def exam = new Exam(testNumber:1,maxMark:60,weight:0.4,factor:1,factorOperand:"Divide")
			//exam.save()
				def submodule = new SubModule(name:"Gravity",description:"Take home assignment", type:"Assignment")
			//submodule.save()
				submodule.addToExams(exam)
				def module = new Module(name:"Module01",description:"First module")
				module.addToSubmodules(submodule)
				module.save()


				break
			case "PRODUCTION" :

				break
		}
	} //end init


	def destroy = {
	}
} //end class
