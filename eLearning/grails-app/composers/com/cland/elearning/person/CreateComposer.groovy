package com.cland.elearning.person

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.*
import org.zkoss.zul.*
import com.cland.elearning.*


class CreateComposer {

    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
		companyListBox.setItemRenderer(companyListBoxRowRenderer as ListitemRenderer)
		companyListBox.setModel(companyListBoxModel)
		redrawCompanyListBox()
		
    }
	ListModelList companyListBoxModel = new ListModelList()
	Bandbox companyBandbox
	Listbox companyListBox
	Longbox companyIdBox
	Paging companyPaging
	Textbox companySearch
	Toolbarbutton clearCompanyListBoxSearch
	Toolbarbutton newCompany
	String searchCompanyListBoxStr = ""
	
    void onClick_saveButton(Event e) {	
		
        def personInstance = new Person(self.params)
        if (!personInstance.save(flush: true) && personInstance.hasErrors()) {			
            log.error personInstance.errors
            self.renderErrors(bean: personInstance)
        } else {
			//if it's a LEARNER then generate a student number
			def today = new Date()
			Long n = personInstance?.id
			n = n + 1100
			String studentNo = n.toString() + "/" + g.formatDate(date:today,format:"yy")
			
			personInstance?.studentNo = studentNo
			if (!personInstance.save(flush: true) && personInstance.hasErrors()) {
				println ("Failed to save student number '" + studentNo + "'")
				log.error personInstance.errors
				
			}
			//add the selected roles here		
			PersonRole.removeAll(personInstance)
			def roles = Role.list()
			for(Role r : roles){
				def tmp = self.params.list("role_${r.authority}")			
				if (tmp[0]) PersonRole.create(personInstance, r, true)		
			}
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.id])			
            redirect(controller: "person", action: "list")
        }
    }
	
	//Begin Company bandbox

	void onSelect_companyListBox(Event e){
		companyIdBox.value = companyListBox.selectedItem.value
		companyBandbox.value=companyListBox.selectedItem.label
		companyBandbox.close()
	}
	
	private companyListBoxRowRenderer = {Listitem listitem, clientInstanceId, int index ->
		Organisation clientInstance = Organisation.read(clientInstanceId)
		listitem << { listcell(label: clientInstance.name) }
		listitem.setValue(clientInstanceId)
	}

	void onPaging_companyPaging(ForwardEvent fe) {
		def event = fe.origin
		redrawCompanyListBox(event.activePage)
	}

	private redrawCompanyListBox(int activePage = 0) {

		int offset = activePage * companyPaging.pageSize

		int max = companyPaging.pageSize
		def clientInstanceList = Organisation.createCriteria().list(offset: offset, max: max) {
			order('name')
			if (!searchCompanyListBoxStr.isEmpty()) {
				or {
					ilike('name', "%" + searchCompanyListBoxStr + "%")
				}
			}
		}
		companyPaging.totalSize = clientInstanceList.totalCount
		companyListBoxModel.clear()
		companyListBoxModel.addAll(clientInstanceList.id)
	}

	void onChanging_companySearch(InputEvent e) {
		searchCompanyListBoxStr = e.value
		redrawCompanyListBox()
	}

	void onClick_clearCompanyListBoxSearch(Event e){
		companySearch.value = ""
		searchCompanyListBoxStr = ""
		redrawCompanyListBox()
	}
	
	void onClick_newCompany(Event e){
		companyBandbox.close()
	}

	//end of Client bandbox
	
} //end class