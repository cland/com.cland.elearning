package com.cland.elearning


import org.joda.time.DateTime
import org.joda.time.Instant
import grails.plugins.springsecurity.Secured
import grails.converters.JSON
import java.text.SimpleDateFormat
import org.codehaus.groovy.grails.plugins.springsecurity.*

class EventController {
    def eventService
	def courseService
	def springSecurityService
    def index = {

    }

    def list = {
       // def (startRange, endRange) = [params.long('start'), params.long('end')].collect { new Instant(it  * 1000L).toDate() }
		def (startRange, endRange) = [params.date('start','yyyy-MM-dd'), params.date('end','yyyy-MM-dd')] //.collect { new Instant(it  * 1000L).toDate() }
		def events = null
		if(SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")) {
			events = Event.withCriteria {
				or {
					and {
						eq("isRecurring", false)
						between("startTime", startRange, endRange)
					}
					and {
						eq("isRecurring", true)
						or {
							isNull("recurUntil")
							ge("recurUntil", startRange)
						}
					}
				}
			}
		}else{
			//get the current user's registration list
			def registerInstanceList = Registration.createCriteria().list() {
				eq "learner.id", springSecurityService.principal.id as long
				order('regDate','desc')
			}
			events = []
			registerInstanceList.each {reg ->
				events.addAll(reg?.course?.events?.findAll{
							((it.isRecurring==false) & (it.startTime >= startRange) & (it.startTime <= endRange)) |
							((it.isRecurring == true) & (it.recurUntil==null | it.recurUntil >= startRange))
						}
					)
			}

		}

        // iterate through to see if we need to add additional Event instances because of recurring
        // events
        def eventList = []

        def displayDateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")

        events.each {event ->

            def dates = eventService.findOccurrencesInRange(event, startRange, endRange)

            dates.each { date ->
                DateTime startTime = new DateTime(date)
                DateTime endTime = startTime.plusMinutes(event.durationMinutes)

                /*
                    start/end and occurrenceStart/occurrenceEnd are separate because fullCalendar will use the client's local timezone (which may be different than the server's timezone)
                    start/end are used to render the events on the calendar and the occurrenceStart/occurrenceEnd values are passed along to the show popup
                */

                eventList << [
                        id: event.id,
                        title: event.title,
                        allDay: false,
                        start: displayDateFormatter.format(startTime.toDate()),
                        end: displayDateFormatter.format(endTime.toDate()),
                        occurrenceStart: startTime.toInstant().millis,
                        occurrenceEnd: endTime.toInstant().millis
                ]
            }
        }

        withFormat {
            html {
                [eventInstanceList: eventList]
            }
            json {
                render eventList as JSON
            }
        }
    }

	
    def create = {
		if(SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")) {
	        def eventInstance = new Event()
	        eventInstance.properties = params
			def courseInstance = Course.get(params?.course?.id)
	        [eventInstance: eventInstance,courseInstance:courseInstance]
		}else{
			flash.message = "You do not have enough access rights to create an event."
			redirect(action: "index")
		}
    }


    def show = {
        def eventInstance = Event.get(params.id)
        def occurrenceStart = params.long('occurrenceStart') ?: new Instant(eventInstance?.startTime)
        def occurrenceEnd = params.long('occurrenceEnd') ?: new Instant(eventInstance?.endTime)

        if (!eventInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), params.id])}"
            redirect(action: "index")
        }
        else {
            def model = [eventInstance: eventInstance, occurrenceStart: occurrenceStart, occurrenceEnd: occurrenceEnd]

            if (request.xhr) {
                render(template: "showPopup", model: model)
            }
            else {
                model
            }
        }

    }

    def save = {	
		if(SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")) {
	        def eventInstance = new Event(params)
	        if (eventInstance.save(flush: true)) {
				//attach to a related object
				Course courseInstance = Course.get(params?.course.id)
				if(courseInstance){
					courseInstance?.addToEvents(eventInstance)
					//courseInstance?.save(flush:true)				
				}
	            flash.message = "${message(code: 'default.created.message', args: [message(code: 'event.label', default: 'Event'), eventInstance.id])}"
	            redirect(action: "show", id: eventInstance.id)
	        }
	        else {
	            render(view: "create", model: [eventInstance: eventInstance, 'course.id':params?.course.id])
	        }
		}else{
			flash.message = "You do not have enough access rights to save an event."
			redirect(action: "index")
		}

    }

    def edit = {
		if(SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")) {
	        def eventInstance = Event.get(params.id)
	        def (occurrenceStart, occurrenceEnd) = [params.long('occurrenceStart'), params.long('occurrenceEnd')]
	
	        if (!eventInstance) {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), params.id])}"
	            redirect(action: "index")
	        }
	        else {
	            [eventInstance: eventInstance, occurrenceStart: occurrenceStart, occurrenceEnd: occurrenceEnd]
	        }
		}else{
			flash.message = "You do not have enough access rights to edit an event."
			redirect(action: "index")
		}
    }

    def update = {
		
        def eventInstance = Event.get(params.id)
		
        EventRecurActionType editType = params.editType ? params.editType.toUpperCase() as EventRecurActionType : null

        Date occurrenceStartTime = params.date('startTime', ['dd/MM/yyyy hh:mm a'])
        Date occurrenceEndTime = params.date('endTime', ['dd/MM/yyyy hh:mm a'])

        def result = eventService.updateEvent(eventInstance, editType, occurrenceStartTime, occurrenceEndTime, params)

        if (!result.error) {
			Course curCourseInstance = courseService?.findCourseForEvent(eventInstance)
			if(curCourseInstance){
				//replace related object if different
				if(params?.course.id != curCourseInstance?.id){
					Course courseInstance = Course.get(params?.course.id)
					if(courseInstance){
						courseInstance?.addToEvents(eventInstance)
						//courseInstance?.save(flush:true)						
					}
					//the id is different so we remove the object that existed
					curCourseInstance.events.remove(eventInstance)
				}				
			}else{
				//attach to a new related object
				Course courseInstance = Course.get(params?.course.id)
				if(courseInstance){
					courseInstance?.addToEvents(eventInstance)
					//courseInstance?.save(flush:true)
				}
			}
			
            flash.message = "${message(code: 'default.updated.message', args: [message(code: 'event.label', default: 'Event'), eventInstance.id])}"
            redirect(action: "index")
        }
        if (result.error == 'not.found') {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), params.id])}"
            redirect(action: "index")
        }
        else if (result.error == 'has.errors') {
            render(view: "edit", model: [eventInstance: eventInstance])
        }

    }

    def delete = {
		if(SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")) {
	        def eventInstance = Event.get(params.id)
	
	        EventRecurActionType deleteType = params.editType ? params.deleteType.toUpperCase() as EventRecurActionType : null
	        Date occurrenceStart = new Instant(params.long('occurrenceStart')).toDate()
	
	        def result = eventService.deleteEvent(eventInstance, deleteType, occurrenceStart)
	
	        if (!result.error) {
	            redirect(action: "index")
	        }
	        if (result.error == 'not.found') {
	            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'event.label', default: 'Event'), params.id])}"
	            redirect(action: "index")
	        }
	        else if (result.error == 'has.errors') {
	            redirect(action: "index")
	        }
		}else{
			flash.message = "You do not have enough access rights to delete an event."
			redirect(action: "index")
		}
    }

    
}