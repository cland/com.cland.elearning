package com.cland.elearning.exam

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.Exam
import org.codehaus.groovy.grails.plugins.springsecurity.*

class ListComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
    Textbox keywordBoxModule
	Textbox keywordBoxSubmodule
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
        def examInstanceList = Exam.createCriteria().list(offset: offset, max: max) {			
			createAlias('submodule','sm')
			createAlias('sm.module','m')
            order('m.name','asc')
			order('sm.name','asc')
			order('testNumber','asc')
			if(keywordBoxModule.value){
				ilike('m.name',""+keywordBoxModule.value+"%")
			}
			if(keywordBoxSubmodule.value){
				ilike('sm.type',""+keywordBoxSubmodule.value+"%")
			}
        }
        paging.totalSize = examInstanceList.totalCount
        listModel.clear()
        listModel.addAll(examInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def examInstance = Exam.get(id)
        row << {
				a(href: g.createLink(controller:"module",action:'show',id:examInstance.submodule?.module?.id), label: examInstance.submodule?.module?.name)
				label(value: examInstance.submodule.type)
				//a(href: g.createLink(controller:"subModule",action:'show',id:examInstance.submodule?.id), label: examInstance.submodule?.type)
                a(href: g.createLink(controller:"exam",action:'show',id:id), label: "Exam " + examInstance.testNumber)
				label(value: examInstance.maxMark)
				label(value: examInstance.weight)
				label(value: examInstance.factor)
                label(value: examInstance.factorOperand) 
				label(value: examInstance.status)
                hlayout{
                    //toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "exam", action: 'edit', id: id))
					toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',client_onClick: "alert('view exam')",onClick:{
					
						//href:g.createLink(controller: "exam", action: 'edit', id: id)	
					})
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        Exam.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    } //end method rowRenderer
	
	//** CUSTOM TESTS
	 void onChanging_keywordBoxModule(InputEvent e) {
		 keywordBoxModule.value = e.value
		 redraw()
	 }
	 void onChanging_keywordBoxSubmodule(InputEvent e) {
		 keywordBoxSubmodule.value = e.value
		 redraw()
	 }
	 
} //end class