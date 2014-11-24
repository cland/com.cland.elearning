package com.cland.elearning.person

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.event.Event;

import com.cland.elearning.*
import org.codehaus.groovy.grails.plugins.springsecurity.*

class TutorsComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
       //Longbox idLongbox
	
	Textbox keywordBox
	Textbox keywordBoxLastname
	Textbox keywordBoxId
	def springSecurityService
	boolean canEdit = false
	boolean canView = true
	boolean canCreate = false
	boolean canDelete = false
	void setActionRights(){
		if(SpringSecurityUtils.ifAnyGranted("ADMIN")) {
			canEdit=true
			canCreate=true
			canDelete=true
		}
	}
    def afterCompose = {Component comp ->
        grid.setRowRenderer(rowRenderer as RowRenderer)
        grid.setModel(listModel)
        setActionRights()
       redraw()
    }

    void onClick_searchButton(Event e) {
        redraw()
    }
	void onClick_clearButton(Event e) {
		clearBoxes()
		redraw()
	}
    void onPaging_paging(ForwardEvent fe) {
        def event = fe.origin
        redraw(event.activePage)
    }

    private redraw(int activePage = 0) {

        int offset = activePage * paging.pageSize
        int max = paging.pageSize
		int totalCount = 0

		def tutorIds = PersonRole.findAllByRole(Role.findByAuthority('TUTOR'))*.person*.id
		
		def tutors = Person.createCriteria().list(offset: offset, max: max){
			'in' ("id",tutorIds)
			order('firstName','asc')
			if(keywordBox.value){
				ilike('firstName',keywordBox.value+"%")
			}
			ne("username","default.tutor")
			if(keywordBoxLastname.value){
				ilike('lastName',keywordBoxLastname.value+"%")
			}
		}
		
		totalCount = tutors?.totalCount //  check.size()
		//def tmp = personInstanceList.findAll{it.isTutor() == true & it.username != "default.tutor"}
        paging.totalSize = totalCount
        listModel.clear()
        listModel.addAll(tutors.id) //personInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def personInstance = Person.get(id)
		def roles = personInstance.getAuthorities()
        row << {                                             
                label(value: personInstance.firstName)
                label(value: personInstance.lastName)
				a(href: g.createLink(controller:"person",action:'show',id:id), label: personInstance.username)
				
				label(value: roles.toListString())
                hlayout{
					if(canView)toolbarbutton(label: g.message(code: 'default.button.view.label', default: 'View'),image:'/images/skin/database_table.png',href:g.createLink(controller: "person", action: 'show', id: id))
                   if(canEdit) toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "person", action: 'edit', id: id))
                   if(canDelete){ toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                       def p =Person.get(id)
					   PersonRole.removeAll(p)
					   //PersonRole.findByPerson(id)?.delete() 
					   p?.delete(flush: true)
                        listModel.remove(id)
                    })
                   }
                }
        }
    }
	//** CUSTOM TESTS
	 void onChanging_keywordBox(InputEvent e) {
		 keywordBox.value = e.value
		// redraw()
	 }
	 private void clearBoxes(){
		 keywordBox.value = ""
		 //keywordBoxId.value = ""
		 keywordBoxLastname.value = ""
	 }
} //end class