package com.cland.elearning.home


import java.security.Provider.Service;

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.*
import grails.plugins.springsecurity.Secured

class HomeComposer {
	Grid myCourseGrid
	Paging paging
	ListModelList listModel = new ListModelList()
	//def courseService
	def springSecurityService
	Person curPerson
	def afterCompose = {Component comp ->
		
		if(!springSecurityService.isLoggedIn()) return;
		myCourseGrid.setRowRenderer(rowRenderer as RowRenderer)
		myCourseGrid.setModel(listModel)
		//		curPerson = (Person) springSecurityService.currentUser
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


		def courseInstanceList = Course.createCriteria().list(offset: offset, max: max) {
			order('name','desc')
			//	if (idLongbox.value) {
			//		eq('id', idLongbox.value)
			//	}
		}
		paging.totalSize = courseInstanceList.totalCount
		listModel.clear()
		listModel.addAll(courseInstanceList.id)

	}

	private rowRenderer = {Row row, Object id, int index ->
		def courseInstance = Course.get(id)
		row << {
			a(href: g.createLink(controller:"course",action:'edit',id:id), label: courseInstance.name)
			
			label(value: courseInstance.startDate.format('dd/MM/yyyy'))
			label(value: courseInstance.endDate.format('dd/MM/yyyy'))
		//	hlayout{
		//		toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "course", action: 'edit', id: id))
		//		toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
		//			Course.get(id).delete(flush: true)
		//			listModel.remove(id)
		//		})
		//	}
		}
	}
} //end home composer class
