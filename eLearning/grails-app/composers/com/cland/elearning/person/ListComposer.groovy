package com.cland.elearning.person

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.Person
import org.codehaus.groovy.grails.plugins.springsecurity.*

class ListComposer {
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

    void onPaging_paging(ForwardEvent fe) {
        def event = fe.origin
        redraw(event.activePage)
    }

    private redraw(int activePage = 0) {
        int offset = activePage * paging.pageSize
        int max = paging.pageSize
        def personInstanceList = Person.createCriteria().list(offset: offset, max: max) {
		
            order('firstName','asc')
//            if (idLongbox.value) {
//                eq('id', idLongbox.value)
//            }
			or {
			if(keywordBoxId.value){
				ilike('studentNo',keywordBoxId.value+"%")
			}
			if(keywordBox.value){
				ilike('firstName',keywordBox.value+"%")
			}
			if(keywordBoxLastname.value){
				ilike('lastName',keywordBoxLastname.value+"%")
			}
            }
        }		
		
        paging.totalSize = personInstanceList.totalCount
        listModel.clear()
        listModel.addAll(personInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def personInstance = Person.get(id)
		def roles = personInstance.getAuthorities()
        row << {                                             
                label(value: personInstance.firstName)
                label(value: personInstance.lastName)
				a(href: g.createLink(controller:"person",action:'show',id:id), label: personInstance.username)
							
				if(roles.toListString().contains("LEARNER")){
					label(value: personInstance.studentNo)
				}else{
					label(value: "--")
				}
				label(value: roles.toListString())
                hlayout{
					if(canView)toolbarbutton(label: g.message(code: 'default.button.view.label', default: 'View'),image:'/images/skin/database_table.png',href:g.createLink(controller: "person", action: 'show', id: id))
                   if(canEdit) toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "person", action: 'edit', id: id))
                   if(canDelete){ toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        Person.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                   }
                }
        }
    }
	//** CUSTOM TESTS
	 void onChanging_keywordBox(InputEvent e) {
		 keywordBox.value = e.value
		 redraw()
	 }
} //end class