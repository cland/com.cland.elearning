package com.cland.elearning.submodule

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import org.zkoss.zk.ui.*
import com.cland.elearning.*

class ShowComposer {
    Window self
	Window examView
	Grid examGrid
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
		examGrid.setRowRenderer(rowRenderer as RowRenderer)
		examGrid.setModel(listModel)
		curPerson = (Person) springSecurityService.currentUser
		examView.setVisible(false)
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
	println("Total: ${examInstanceList.totalCount}")
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
					toolbarbutton(label: g.message(code: 'default.button.view.label', default: 'View'),image:'/images/skin/database_table.png',client_onClick: "",onClick:{
						println("Clicked!")
						
					})
					toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "exam", action: 'edit', id: id))
					
					toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
						Exam.get(id).delete(flush: true)
						listModel.remove(id)
					})
				}
		}
	} //end rowRenderer

	def onClck_view(Event e){
		println("Clicked!!!!!!")
		def window = new Window(title: "Job Show", border: "normal",
			width: "460px", height: "420px",
			closable: true, droppable: true)
			window.setPage(e.getPage())
			window.mode = 'modal'
	
					 window << {
				include(src: "/exam/edit/1")
			}
	}
	void onClick_closeBtn(Event e){
		println("Close dialog - submodule show")
		//def params=self.params
		examView.detach()
	}
	void createModalWindow(Event event){
		def window = new Window(title: "Alterar Ponto", border: "normal", width: "500px", closable: true)
		//window.setPage(event.page)
		window.setMode("modal")

		def include = new Include("/view/exam/edit.gsp")
		include.setDynamicProperty("id", params.id)
		//include.setDynamicProperty("version", version)
		window.doModal()
	}
    void onClick_addExamButton(Event e) {
		println("Add exam button clicked!")
        def params=self.params
        def submoduleInstance = SubModule.get(params.id)
        if (submoduleInstance) {
			           
		}
    }
} //end classs