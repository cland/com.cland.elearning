package com.cland.elearning.home


import java.security.Provider.Service;

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.*
import grails.plugins.springsecurity.Secured

class HomeComposer {
	Grid myResultsGrid
	Grid myRegGrid
	Paging paging
	
	ListModelList listModel = new ListModelList()
	ListModelList reglistModel = new ListModelList()
	//def courseService
	def springSecurityService
	
	
	def afterCompose = {Component comp ->

		if(!springSecurityService.isLoggedIn()) return;
		myResultsGrid.setRowRenderer(rowRenderer as RowRenderer)
		myResultsGrid.setModel(listModel)
		myRegGrid.setRowRenderer(rowRegRenderer as RowRenderer)
		myRegGrid.setModel(reglistModel)
	
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
	private rowRegRenderer = {Row row, Object id, int index ->
		def regInstance = Registration.get(id)
		row << {
			//a(href: g.createLink(controller:"registration",action:'edit',id:id), label: regInstance.course.name)			
			a(href: g.createLink(controller:"course",action:'show',id:regInstance.course.id), label: regInstance.course.name)
			label(value: regInstance.course.startDate.format('dd/MM/yyyy'))
			label(value: regInstance.learner.firstLastName())
			
		}
	}
} //end home composer class