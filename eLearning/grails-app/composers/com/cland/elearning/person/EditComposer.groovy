package com.cland.elearning.person

import javax.annotation.security.RolesAllowed;
import com.cland.elearning.*;

import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.*
import org.zkoss.zul.*
//import com.cland.elearning.Person

class EditComposer {
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
        def params=self.params
		//println(params)
        def personInstance = Person.get(params.id)
        if (personInstance) {
            if (params.version != null) {
                def version = params.version
                if (personInstance.version > version) {
                    String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'person.label', default: 'Person')],default:"Another user has updated this ${personInstance} while you were editing")
                    Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
                    return
                }
            }
            personInstance.properties = params
            if (!personInstance.hasErrors() && personInstance.save(flush: true)) {
				//update the roles here
				PersonRole.removeAll(personInstance)				
				def roles = Role.list()				
				for(Role r : roles){					
					def tmp = params.list("role_${r.authority}")
					if (tmp[0]) PersonRole.create(personInstance, r, true)					
				}				
			
                flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'person.label', default: 'Person'), personInstance.toString()])
                redirect(controller: "person", action: "show", id: personInstance.id)
            }else {
                log.error personInstance.errors
                self.renderErrors(bean: personInstance)
            }
        }
        else {
            flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'person.label', default: 'Person'), params.id])
            redirect(controller: "person",action: "list")
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