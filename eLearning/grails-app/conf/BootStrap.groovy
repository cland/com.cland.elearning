
import com.cland.elearning.*
import grails.util.*
import org.springframework.web.context.support.*;
import org.codehaus.groovy.grails.commons.*;
import groovy.ui.Console;

class BootStrap {

	def init = { servletContext ->
		/**
		 * Launch the console to allow us to run scripts etc while site is running
		 */

		boolean showGroovyConsole = false

		if (Environment.getCurrent() == Environment.DEVELOPMENT && showGroovyConsole) {

			def appCtx = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext)

			def grailsApp = appCtx.getBean(GrailsApplication.APPLICATION_ID);

			Binding b = new Binding();

			b.setVariable("ctx", appCtx);

			def console = new Console(grailsApp.classLoader, b);

			console.run()

		}

		println "Bootstrap > environment: " + Environment.getCurrent()
		switch(Environment.getCurrent()){
			case "DEVELOPMENT":
				def adminRole = new Role(authority: 'ADMIN').save(flush: true, failOnError:true)
				def learnerRole = new Role(authority: 'LEARNER').save(flush: true, failOnError:true)
				def tutorRole = new Role(authority: 'TUTOR').save(flush: true, failOnError:true)
				def counsellorRole = new Role(authority: 'COUNSELLOR').save(flush: true, failOnError:true)

				def sa = new Country(name:"South Africa")
				sa.addToRegions(new Region(name:"Western Cape"))
				sa.addToRegions(new Region(name:"KZN"))
				sa.addToRegions(new Region(name:"Limpopo"))
				sa.addToRegions(new Region(name:"Mpumalanga"))
				sa.addToRegions(new Region(name:"Gauteng"))
				sa.addToRegions(new Region(name:"North West"))
				sa.addToRegions(new Region(name:"Free State"))
				sa.addToRegions(new Region(name:"Eastern Cape"))
				sa.addToRegions(new Region(name:"Northern Cape"))
				sa.save()
				if(sa.hasErrors()){
					println(sa.errors)
				}
				
				new Race(name:"Black").save()
				new Race(name:"White").save()
				new Race(name:"Indian").save()
				new Race(name:"Other").save()
				
			//* Admin user
				def adminUser = new Person(username: 'admin',
				enabled: true,
				password: 'password',
				firstName: 'System',
				lastName: 'Admin',
				idNo :"3456753463453",
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
				def learnerUser1 = new Person(username: 'mary',
				enabled: true,
				password: 'password',
				firstName: 'Mary',
				lastName: 'Brown',
				idNo :"1234567890123",
				contactNo : "011834232",
				dateOfBirth:(new Date() - 365*30),
				gender:"F",
				address:"12 Main1 St",
				city:"Gauteng",
				email:"user1@whereever.com")

				learnerUser1.save()
				if(learnerUser1.hasErrors()){
					println learnerUser1.errors
				}
				PersonRole.create(learnerUser1, learnerRole, true)
				def learnerUser2 = new Person(username: 'john',
					enabled: true,
					password: 'password',
					firstName: 'John',
					lastName: 'Smith',
					idNo :"6234887800123",					
					contactNo : "011834232",
					dateOfBirth:(new Date() - 365*30),
					gender:"F",
					address:"12 Main1 St",
					city:"Gauteng",
					email:"user1@whereever.com")
	
					learnerUser2.save()
					if(learnerUser2.hasErrors()){
						println learnerUser2.errors
					}
					PersonRole.create(learnerUser2, learnerRole, true)

			//** tutor/counsellor user
					def tutorDefault = new Person(username: 'default.tutor',
						enabled: true,
						password: 'password',
						firstName: 'No',
						lastName: 'Tutor',
						idNo :"44444",
						contactNo : "0000",
						dateOfBirth:(new Date() - 365*30),
						gender:"F",
						address:"none",
						city:"Durban",
						email:"default.tutor@whereever.com")
		
						tutorDefault.save()
						if(tutorDefault.hasErrors()){
							println tutorDefault.errors
						}
						PersonRole.create(tutorDefault, tutorRole, true)
				def tutorUser1 = new Person(username: 'tutor1',
				enabled: true,
				password: 'password',
				firstName: 'Tutor1',
				lastName: 'Tutor1',				
				idNo :"1234567890123",
				contactNo : "0314334232",
				dateOfBirth:(new Date() - 365*30),
				gender:"F",
				address:"12 Main1 St",
				city:"Durban",
				email:"tut1@whereever.com")

				tutorUser1.save()
				if(tutorUser1.hasErrors()){
					println tutorUser1.errors
				}
				PersonRole.create(tutorUser1, tutorRole, true)
				PersonRole.create(tutorUser1, counsellorRole, true)
				
				def tutorUser2 = new Person(username: 'tutor2',
					enabled: true,
					password: 'password',
					firstName: 'Tutor2',
					lastName: 'Tutor2',	
					idNo :"9234567890123",
					contactNo : "0314334232",
					dateOfBirth:(new Date() - 365*30),
					gender:"F",
					address:"12 Main1 St",
					city:"Pretoria",
					email:"tut2@whereever.com")
	
					tutorUser2.save()
					if(tutorUser2.hasErrors()){
						println tutorUser2.errors
					}
					PersonRole.create(tutorUser2, tutorRole, true)
			// assert Person.count() == 2
			// assert Role.count() == 2
			// assert PersonRole.count() == 2

				
			//** Module-Submodule-exam 01

				def exam = new Exam(testNumber:1,maxMark:60,weight:0.4,factor:1,factorOperand:"Divide", status:"Active")
				def exam2 = new Exam(testNumber:2,maxMark:90,weight:0.8,factor:1,factorOperand:"Divide",status:"Active")

				def submodule = new SubModule(name:"Gravity",description:"Take home assignment", type:"Assignment")
				def submodule3 = new SubModule(name:"Tutor Tester",description:"Tutor Marked Assessment", type:"Tutor Marked Assessment")
				submodule.addToExams(exam)
				submodule.addToExams(exam2)
				def module = new Module(name:"Module01",description:"First module")
				module.addToSubmodules(submodule)
				module.addToSubmodules(submodule3)
				module.save()
				if(module.hasErrors()){
					println module.errors
				}

			//** Module-Submodule-exam 02

				def exam3 = new Exam(testNumber:1,maxMark:75,weight:0.3,factor:1,factorOperand:"Divide",status:"Active")
				def exam4 = new Exam(testNumber:2,maxMark:88,weight:0.7,factor:1,factorOperand:"Divide",status:"Active")

				def submodule2 = new SubModule(name:"Security",description:"Practical Assignment", type:"Practical Attendance Exercises")
				
				submodule2.addToExams(exam3)
				submodule2.addToExams(exam4)
				def module2 = new Module(name:"Module02",description:"Second module")
				module2.addToSubmodules(submodule2)
				
				module2.save()
				if(module2.hasErrors()){
					println module2.errors
				}
				
			//venues
				def venue1 = new Venue(name:"Venue One",address:"",geoLocation:"-33,423",contactName:"contact 1",contactNumber:"021334345",region:"Western Cape",directions:"")
				venue1.save()
				def venue2 = new Venue(name:"Venue Two",address:"",geoLocation:"-231,223",contactName:"contact 2",contactNumber:"031464345",region:"KZN",directions:"")
				venue2.save()


			//** course
				def course = new Course(name:"Introduction To Paint 101",startDate:new Date(),endDate: new Date() + 1,status:"active",code:"INTRO-PAINT-KZN101" )
				course.addToModules(module)
							
				course.save()
				if(course.hasErrors()){
					println course.errors
				}
				
				def course2 = new Course(name:"How To Paint 221",startDate:new Date(),endDate: new Date() + 1, status:"active",code:"HW-PAINT-WC221" )
				course2.addToModules(module)
							
				course2.save()
				if(course2.hasErrors()){
					println course2.errors
				}
			
				break
			case "PRODUCTION" :
				
				//else do the initial setup
//				def adminRole = new Role(authority: 'ADMIN').save(flush: true, failOnError:true)
//				def learnerRole = new Role(authority: 'LEARNER').save(flush: true, failOnError:true)
//				def tutorRole = new Role(authority: 'TUTOR').save(flush: true, failOnError:true)
//				def counsellorRole = new Role(authority: 'COUNSELLOR').save(flush: true, failOnError:true)
//
//				def sa = new Country(name:"South Africa")
//				sa.addToRegions(new Region(name:"Western Cape"))
//				sa.addToRegions(new Region(name:"KZN"))
//				sa.addToRegions(new Region(name:"Limpopo"))
//				sa.addToRegions(new Region(name:"Mpumalanga"))
//				sa.addToRegions(new Region(name:"Gauteng"))
//				sa.addToRegions(new Region(name:"North West"))
//				sa.addToRegions(new Region(name:"Free State"))
//				sa.addToRegions(new Region(name:"Eastern Cape"))
//				sa.addToRegions(new Region(name:"Northern Cape"))
//				sa.save()
//				if(sa.hasErrors()){
//					println(sa.errors)
//				}
//				
//				new Race(name:"Black").save()
//				new Race(name:"White").save()
//				new Race(name:"Indian").save()
//				new Race(name:"Other").save()
//				
//			//* Admin user
//				def adminUser = new Person(username: 'admin',
//				enabled: true,
//				password: 'password',
//				firstName: 'System',
//				lastName: 'Admin',
//				idNo :"3456753463453",
//				contactNo : "021334232",
//				dateOfBirth:(new Date() - 365*30),
//				gender:"M",
//				address:"123 Main St",
//				city:"Cape Town",
//				email:"jay@whereever.com")
//
//				adminUser.save()
//				if(adminUser.hasErrors()){
//					println adminUser.errors
//				}
//				PersonRole.create(adminUser, adminRole, true)
				break
		}
	} //end init


	def destroy = {
	}
} //end class
