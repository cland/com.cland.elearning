
import com.cland.elearning.Course
import com.cland.elearning.CourseEvent
import com.cland.elearning.EventResult
import com.cland.elearning.Exam
import com.cland.elearning.Module
import com.cland.elearning.Person
import com.cland.elearning.Registration
import com.cland.elearning.Role
import com.cland.elearning.PersonRole
import com.cland.elearning.SubModule
import com.cland.elearning.Venue
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
				idNo :"3456753463453",
				region:"Western Cape",
				contactNo : "021334232",
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
				idNo :"1234567890123",
				region:"Western Cape",
				contactNo : "011834232",
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
				lastName: 'Terb',
				region:"Western Cape",
				idNo :"1234567890123",
				contactNo : "0314334232",
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

				
			//** Module-Submodule-exam 01

				def exam = new Exam(testNumber:1,maxMark:60,weight:0.4,factor:1,factorOperand:"Divide")
				def exam2 = new Exam(testNumber:2,maxMark:90,weight:0.8,factor:1,factorOperand:"Divide")

				def submodule = new SubModule(name:"Gravity",description:"Take home assignment", type:"Assignment")

				submodule.addToExams(exam)
				submodule.addToExams(exam2)
				def module = new Module(name:"Module01",description:"First module")
				module.addToSubmodules(submodule)
				module.save()

			//** Module-Submodule-exam 02

				def exam3 = new Exam(testNumber:1,maxMark:75,weight:0.3,factor:1,factorOperand:"Divide")
				def exam4 = new Exam(testNumber:2,maxMark:88,weight:0.7,factor:1,factorOperand:"Divide")

				def submodule2 = new SubModule(name:"Security",description:"Practical Assignment", type:"Practical Attendance Exercises")

				submodule2.addToExams(exam3)
				submodule2.addToExams(exam4)
				def module2 = new Module(name:"Module02",description:"Second module")
				module2.addToSubmodules(submodule2)
				module2.save()

			//venues
				def venue1 = new Venue(name:"Venue One")
				venue1.save()
				def venue2 = new Venue(name:"Venue Two")
				venue2.save()


			//** course
				def course = new Course(name:"Physics 101",startDate:new Date(),endDate: new Date() + 1 )
				course.addToModules(module)
							
				course.save()
				if(course.hasErrors()){
					println course.errors
				}
			//** Person registration
				def regtutor = new Registration(regType:tutorRole)
				def reguser = new Registration(regType:learnerRole)
				staffUser.addToRegistrations(regtutor)
				someUser.addToRegistrations(reguser)
				
				course.addToRegistrations(regtutor)
				course.addToRegistrations(reguser)

				//** Course Event 
				//	def courseEvent = new CourseEvent(eventDate:new Date(),tutor:staffUser,counsellor:staffUser,venue:venue1,subModule:submodule,exam:exam)
				//	def eventResult = new EventResult(mark:59,percentMark:66.0,contributionMark:5.6)
				//	courseEvent.addToEventresults(eventResult)
	
				//	course.addToCourseevents(courseEvent)
				break
			case "PRODUCTION" :

				break
		}
	} //end init


	def destroy = {
	}
} //end class
