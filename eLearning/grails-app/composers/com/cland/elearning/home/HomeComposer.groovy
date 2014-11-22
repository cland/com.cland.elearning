package com.cland.elearning.home


import java.security.Provider.Service;

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.*
import grails.plugins.springsecurity.Secured
import org.joda.time.DateTime
import org.joda.time.Instant
import java.text.SimpleDateFormat

class HomeComposer {
	def eventService
	Grid myResultsGrid
	Grid myRegGrid
	Grid myEventsGrid
	Paging paging
	
	ListModelList listModel = new ListModelList()
	ListModelList reglistModel = new ListModelList()
	ListModelList eventlistModel = new ListModelList()
	//def courseService
	def springSecurityService
	
	
	def afterCompose = {Component comp ->

		if(!springSecurityService.isLoggedIn()) return;
		myResultsGrid.setRowRenderer(rowRenderer as RowRenderer)
		myResultsGrid.setModel(listModel)
		myRegGrid.setRowRenderer(rowRegRenderer as RowRenderer)
		myRegGrid.setModel(reglistModel)
		myEventsGrid.setRowRenderer(rowEventRenderer as RowRenderer)
		myEventsGrid.setModel(eventlistModel)
		redraw()
	}

	void onPaging_paging(ForwardEvent fe) {
		def event = fe.origin
		redraw(event.activePage)
	}

	private redraw(int activePage = 0) {

		//def session = sessionFactory.currentSession
		int offset = activePage * paging.pageSize
		int max = paging.pageSize
		

		//	String queryString = "SELECT distinct c.* FROM registration as r, course as c, person as p where p.id= :currentUserId and p.id=r.person_id and r.course_id=c.id order by c.name asc"
		//	def courseInstanceList = session.createSQLQuery(queryString)
		//	courseInstanceList.addEntity(com.cland.elearning.Course.class)
		//	courseInstanceList.setLong("currentUserId",curPerson.id)
		//	courseInstanceList = courseInstanceList.list()
		//def reglist = Registration.findAllByPerson(curPerson)


		//def courseInstanceList = Course.findAll("Course as c, registration r, person p where p.id=? and p.id=r.person_id and r.course_id=c.id order by c.name asc",[curPerson.id],[offset:offset,max:max])

		def registerInstanceList = Registration.createCriteria().list(offset:offset,max:max) {
			eq "learner.id", springSecurityService.principal.id as long
			order('regDate','desc')
		}
	//	def courseInstanceList = Course.createCriteria().list(offset: offset, max: max) {
		//	order('name','desc')
			//	if (idLongbox.value) {
			//		eq('id', idLongbox.value)
			//	}
	//	}
		paging.totalSize = registerInstanceList.totalCount
		reglistModel.clear()
		reglistModel.addAll(registerInstanceList.id)
		
		//for the results
		def resultSummaryInstanceList = ResultSummary.createCriteria().list() {
			createAlias("register","reg")
			//eq "reg.learner.id", springSecurityService.principal.id as long  
			eq "tutor.id", springSecurityService.principal.id as long 
			order('module','asc')
		}
		listModel.clear()
		listModel.addAll(resultSummaryInstanceList.id)
		
		
		//get the events here
		
		
		def events = listEvents(registerInstanceList)
		eventlistModel.clear()
		eventlistModel.addAll(events)
	}//end function

	private rowRenderer = {Row row, Object id, int index ->
		def resultSummaryInstance = ResultSummary.get(id)
		row << {
			label(value: resultSummaryInstance.register.learner.firstLastName())
			label(value: resultSummaryInstance.register.course.name)
			label(value: resultSummaryInstance.module.name)
			a(href: g.createLink(controller:"ResultSummary",action:'show',id:id), label: resultSummaryInstance.result)
			hlayout{
				toolbarbutton(label: g.message(code: 'default.button.view.label', default: 'View'),image:'/images/skin/database_table.png',href:g.createLink(controller: "registration", action: 'show', id: resultSummaryInstance.register.id))
			}
		//	label(value: resultSummaryInstance.status)
			
		//	label(value: resultSummaryInstance.certNumber)
			
			//	hlayout{
			//		toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "course", action: 'edit', id: id))
			//		toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
			//			Course.get(id).delete(flush: true)
			//			listModel.remove(id)
			//		})
			//	}
		}
	}
	private rowEventRenderer = {Row row, Object obj, int index ->
		def eventInstance = obj
		row << {
			a(href: g.createLink(controller:"event",action:'show',id:eventInstance.id), label: eventInstance.title)
			label(value: eventInstance?.startTime + " - " + eventInstance?.endTime)
			//label(value: eventInstance?.region)
			label(value: eventInstance?.location)
			
		}
	
	}// 
	private rowRegRenderer = {Row row, Object id, int index ->
		def regInstance = Registration.get(id)
		row << {
			//a(href: g.createLink(controller:"registration",action:'edit',id:id), label: regInstance.course.name)			
			a(href: g.createLink(controller:"course",action:'show',id:regInstance.course.id), label: regInstance.course.name)
			label(value: regInstance?.regDate?.format('dd/MM/yyyy'))
			label(value: regInstance?.learner?.firstLastName())
			
		}
	} //end 
	
	def listEvents(def registerInstanceList) {
		
		// def (startRange, endRange) = [params.long('start'), params.long('end')].collect { new Instant(it  * 1000L).toDate() }
		// def (startRange, endRange) = [params.date('start','yyyy-MM-dd'), params.date('end','yyyy-MM-dd')] //.collect { new Instant(it  * 1000L).toDate() }
		def today = new Date()
		def startRange = today
		def endRange = today.plus(60)
		def events = []
		registerInstanceList.each {reg ->
			events.addAll(reg?.course?.events?.findAll{
						((it.isRecurring==false) & (it.startTime >= startRange) & (it.startTime <= endRange)) |	
						((it.isRecurring == true) & (it.recurUntil==null | it.recurUntil >= startRange))							
					}
				)
		} 

//		def eventsOther = com.cland.elearning.Event.withCriteria {
//			 or {
//				 and {
//					 eq("isRecurring", false)
//					 between("startTime", startRange, endRange)
//				 }
//				 and {
//					 eq("isRecurring", true)
//					 or {
//						 isNull("recurUntil")
//						 ge("recurUntil", startRange)
//					 }
//				 }
//			 }
//		 }
 

		 // iterate through to see if we need to add additional Event instances because of recurring
		 // events
		 def eventList = []
 
		 def displayDateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm a")
 
		 events.each {event ->
 
			 def dates = eventService.findOccurrencesInRange(event, startRange, endRange)
 
			 dates.each { date ->
				 DateTime startTime = new DateTime(date)
				 DateTime endTime = startTime.plusMinutes(event.durationMinutes)
 
				 /*
					 start/end and occurrenceStart/occurrenceEnd are separate because fullCalendar will use the client's local timezone (which may be different than the server's timezone)
					 start/end are used to render the events on the calendar and the occurrenceStart/occurrenceEnd values are passed along to the show popup
				 */
				 //eventList.add(event)
				 eventList << [
						 id: event.id,
						 title: event.title,
						 allDay: false,
						 startTime: displayDateFormatter.format(startTime.toDate()),
						 endTime: displayDateFormatter.format(endTime.toDate()),
						 occurrenceStart: startTime.toInstant().millis,
						 occurrenceEnd: endTime.toInstant().millis,
						 region:event.region,
						 location:event.location
				 ]
			 }
		 }
		// return events
		 return eventList.sort{it.occurrenceStart}
	 } //end
} //end home composer class