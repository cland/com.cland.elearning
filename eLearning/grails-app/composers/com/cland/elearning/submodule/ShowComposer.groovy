package com.cland.elearning.submodule

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.*

class ShowComposer {
    Window self
	Grid examGrid
	Paging paging
	def springSecurityService
	Person curPerson
	ListModelList listModel = new ListModelList()
    def afterCompose = {Component comp ->
        if(!springSecurityService.isLoggedIn()) return;
		examGrid.setRowRenderer(rowRenderer as RowRenderer)
		examGrid.setModel(listModel)
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
		
		def examInstanceList = Exam.createCriteria().list(offset:offset,max:max) {
			eq "submodule.id", params.id as long
			order('testNumber','asc')
		}
	
		paging.totalSize = examInstanceList.totalCount
		listModel.clear()
		listModel.addAll(examInstanceList.id)
				
		
	}//end function

	private rowRenderer = {Row row, Object id, int index ->
		def examInstance = Exam.get(id)
		row << {
			a(href: g.createLink(controller:"Exam",action:'edit',id:id), label: "Exam ${examInstance.testNumber}")
			label(value: examInstance.maxMark)
			label(value: examInstance.weight)
			label(value: examInstance.status)
			label(value: examInstance.factor)
			label(value: examInstance.factorOperand)
				hlayout{
					toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "exam", action: 'edit', id: id))
					toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
						Exam.get(id).delete(flush: true)
						listModel.remove(id)
					})
				}
		}
	} //end rowRenderer

    void onClick_addExamButton(Event e) {
        def params=self.params
        def submoduleInstance = SubModule.get(params.id)
        if (submoduleInstance) {
			           
		}
    }
} //end classs