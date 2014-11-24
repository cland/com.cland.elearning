package com.cland.elearning.person

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.*
import org.codehaus.groovy.grails.plugins.springsecurity.*

class LearnersComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
       //Longbox idLongbox
	
	Textbox keywordBox
	Textbox keywordBoxLastname
	Textbox keywordBoxId
	Textbox keywordBoxTutor
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
		def tmp = []
		int totalCount = 0
		if(!keywordBoxTutor.value){
			def learnerIds = PersonRole.findAllByRole(Role.findByAuthority('LEARNER'))*.person*.id
			
			tmp = Person.createCriteria().list(offset: offset, max: max){
				'in' ("id",learnerIds)
				order('firstName','asc')
				if(keywordBoxId.value){
					ilike('studentNo',keywordBoxId.value+"%")
				}

				if(keywordBoxLastname.value){
					ilike('lastName',keywordBoxLastname.value+"%")
				}
			}
			
			totalCount = tmp?.totalCount			
			
		}else{
			def resultSummaryInstanceList = ResultSummary.createCriteria().list(offset: offset, max: max) {
				createAlias('register','reg')
				createAlias('tutor','tut')
				createAlias('reg.learner','l')
				order('reg.learner','asc')
	
				if(keywordBoxTutor.value){
					or{
						ilike('tut.lastName',"%"+keywordBoxTutor.value+"%")
						ilike('tut.firstName',"%"+keywordBoxTutor.value+"%")
					}
					
				}
			}
			totalCount = resultSummaryInstanceList.totalCount
			resultSummaryInstanceList.each {rs ->
				if(!tmp.contains(rs?.register?.learner)) tmp.add(rs?.register?.learner)
			}
		}
		
		
        paging.totalSize = totalCount //personInstanceList.totalCount
        listModel.clear()
        listModel.addAll(tmp.id) //personInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def personInstance = Person.get(id)
		
		def registerInstanceList = Registration.findAll{learner.id == personInstance?.id}
        row << {                                             
                label(value: personInstance.firstName)
                label(value: personInstance.lastName)
				a(href: g.createLink(controller:"person",action:'show',id:id), label: personInstance.username)
							
				if(personInstance.isLearner()){
					label(value: personInstance.studentNo)
				}else{
					label(value: "--")
				}
				vlayout{
					int i = 0
					registerInstanceList.each {reg ->
						i+=1
						a(href: g.createLink(controller:"registration",action:'show',id:reg?.id), label: i + ") " +  reg?.course?.name)
					}
				}
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
		 //redraw()
	 }
	 void onChanging_keywordBoxLastname(InputEvent e) {
		  keywordBoxLastname.value = e.value
		 // redraw()
	  }
	 void onChanging_keywordBoxTutor(InputEvent e) {
		  keywordBoxTutor.value = e.value
		 // redraw()
	  }
	 private void clearBoxes(){		 
		 keywordBoxTutor.value = ""		 
		 keywordBoxId.value = ""
		 keywordBoxLastname.value = ""		 
	 }
} //end class