package com.cland.elearning.registration

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import org.codehaus.groovy.grails.plugins.springsecurity.*
import com.cland.elearning.*

class ListComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
   //Longbox idLongbox
	Textbox keywordBox
	def springSecurityService
	boolean canEdit = false
	boolean canView = true
	boolean canCreate = false
	boolean canDelete = false
	void setActionRights(){
		if(SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")) {
			canEdit=true
			canCreate=true
			canDelete=true
		}
	}
    def afterCompose = {Component comp ->
        grid.setRowRenderer(rowRenderer as RowRenderer)
        grid.setModel(listModel)
        redraw()
    }

    void onClick_searchButton(Event e) {
        redraw()
    }

    void onPaging_paging(ForwardEvent fe) {
        def event = fe.origin
        redraw(event.activePage)
    }

    private redraw(int activePage = 0) {
        int offset = activePage * paging.pageSize
        int max = paging.pageSize
        def registrationInstanceList = Registration.createCriteria().list(offset: offset, max: max) {
			createAlias('learner','p')
            order('learner','asc')
//            if (idLongbox.value) {
//                eq('id', idLongbox.value)
//            }
			if(keywordBox.value){
				println("searching... '" + keywordBox.value + "'")
				ilike('p.firstName',"%"+keywordBox.value+"%")
			}
        }
        paging.totalSize = registrationInstanceList.totalCount
        listModel.clear()
        listModel.addAll(registrationInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def registrationInstance = Registration.get(id)
        row << {
                a(href: g.createLink(controller:"registration",action:'show',id:id), label: registrationInstance.learner.firstLastName())                
                label(value: registrationInstance.course.name + " (" + registrationInstance.course.code + ")")
               // label(value: registrationInstance?.tutor?.firstLastName())
				label(value: registrationInstance.regDate.format("dd MMM yyyy"))
                label(value: registrationInstance.dateCreated.format("dd MMM yyyy"))                
                hlayout{
					if(canView) toolbarbutton(label: g.message(code: 'default.button.view.label', default: 'View'),image:'/images/skin/database_table.png',href:g.createLink(controller: "registration", action: 'show', id: id))
                    if(canEdit) toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "registration", action: 'edit', id: id))
                    if(canDelete) {toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        Registration.get(id).delete(flush: true)
                        listModel.remove(id)
                    })}
                }
        }
    }
	//** CUSTOM TESTS
	 void onChanging_keywordBox(InputEvent e) {
		 keywordBox.value = e.value
		 redraw()
	 }
} //end class