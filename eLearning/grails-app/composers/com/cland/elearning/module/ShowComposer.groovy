package com.cland.elearning.module

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.*

class ShowComposer {
    Window self
	Grid submoduleGrid
	Paging paging
	def springSecurityService
	Person curPerson
	ListModelList listModel = new ListModelList()
    def afterCompose = {Component comp ->
       if(!springSecurityService.isLoggedIn()){
			println("Not logged in...")
			 return;
        }else{
		println("Welcome... logged in...")
        }
		submoduleGrid.setRowRenderer(rowRenderer as RowRenderer)
		submoduleGrid.setModel(listModel)
		curPerson = (Person) springSecurityService.currentUser
		redraw()
    } //end after Compose
	
	void onPaging_paging(ForwardEvent fe) {
		def event = fe.origin
		redraw(event.activePage)
	}

	private redraw(int activePage = 0) {

		//def session = sessionFactory.currentSession
		int offset = activePage * paging.pageSize
		int max = paging.pageSize
		
		def submodInstanceList = SubModule.createCriteria().list(offset:offset,max:max) {
			eq "module.id", params.id as long
			order('type','asc')
		}
	
		paging.totalSize = submodInstanceList.totalCount
		listModel.clear()
		listModel.addAll(submodInstanceList.id)
				
		
	}//end function

	private rowRenderer = {Row row, Object id, int index ->
		def submodInstance = SubModule.get(id)
		row << {
			a(href: g.createLink(controller:"SubModule",action:'edit',id:id), label: submodInstance.toString())

			label(value: submodInstance.type)
			label(value: "--")
				hlayout{
					toolbarbutton(label: g.message(code: 'default.button.view.label', default: 'View'),image:'/images/skin/database_table.png',href:g.createLink(controller: "subModule", action: 'show', id: id))
					toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "subModule", action: 'edit', id: id))
					toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
						SubModule.get(id).delete(flush: true)
						listModel.remove(id)
					})
				}
		}
	} //end rowRenderer

    void onClick_addSubmoduleButton(Event e) {
        def params=self.params
        def moduleInstance = Module.get(params.id)
        if (moduleInstance) {
			           
		}
    }
} //end classs