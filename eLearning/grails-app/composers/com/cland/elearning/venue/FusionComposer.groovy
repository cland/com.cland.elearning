package com.cland.elearning.venue

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.Venue

class FusionComposer {
	Window self
	Grid grid
	ListModelList listModel = new ListModelList()
	Paging paging
	//Longbox idLongbox
	Textbox keywordBox
	Textbox nameBox
	Longbox idBox
	Longbox versionBox
	Button createButton
	Button updateButton
	Button cancelButton
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
		def venueInstanceList = Venue.createCriteria().list(offset: offset, max: max) {
			order('id','desc')
			//            if (idLongbox.value) {
			//                eq('id', idLongbox.value)
			//            }
			if(keywordBox.value){
				ilike('name',"%"+keywordBox.value+"%")
			}
		}
		paging.totalSize = venueInstanceList.totalCount
		listModel.clear()
		listModel.addAll(venueInstanceList.id)
	}

	private rowRenderer = {Row row, Object id, int index ->
		def venueInstance = Venue.get(id)
		row << {
			a(href: g.createLink(controller:"venue",action:'edit',id:id), label: venueInstance.id)
			label(value: venueInstance.name)
			hlayout{
				toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',
				onClick:{
					idBox.value = venueInstance.id
					versionBox.value = venueInstance.version
					nameBox.value = venueInstance.name
					updateButton.visible = true
					createButton.visible = false
					cancelButton.visible = true
				})
				toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()",
				onClick: {
					Venue.get(id).delete(flush: true)
					listModel.remove(id)
				})
			}
		}
	} //end method

	//** CUSTOM TESTS
	void onChanging_keywordBox(InputEvent e) {
		keywordBox.value = e.value
		redraw()
	}

	void onClick_createButton(Event e) {
		def venueInstance = new Venue(self.params)
		if (!venueInstance.save(flush: true) && venueInstance.hasErrors()) {
			log.error venueInstance.errors
			self.renderErrors(bean: venueInstance)
		} else {
			flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'venue.label', default: 'Venue name'), venueInstance.id])
			//redirect(controller: "venue", action: "list")
		}
		redraw()
		clearInput()

	} //end method

	void clearInput(){
		nameBox.value = null
	}
	void onClick_updateButton(Event e) {
		def params=self.params
		def venueInstance = Venue.get(params.id)
		if (venueInstance) {
			if (params.version != null) {
				def version = params.version
				if (venueInstance.version > version) {
					String failureMessage = g.message(code:"default.optimistic.locking.failure",args:[g.message(code: 'venue.label', default: 'Venue')],default:"Another user has updated this ${venueInstance} while you were editing")
					Messagebox.show(failureMessage, g.message(code:'error',default:'Error'), Messagebox.YES, Messagebox.ERROR)
					return
				}
			}
			venueInstance.properties = params
			if (!venueInstance.hasErrors() && venueInstance.save(flush: true)) {
				flash.message = g.message(code: 'default.updated.message', args: [g.message(code: 'venue.label', default: 'Venue'), venueInstance.id])
				//redirect(controller: "venue", action: "edit", id: venueInstance.id)
			}else {
				log.error venueInstance.errors
				self.renderErrors(bean: venueInstance)
			}
		}
		else {
			flash.message = g.message(code: 'default.not.found.message', args: [g.message(code: 'venue.label', default: 'Venue'), params.id])
			//	redirect(controller: "venue",action: "list")
		}
		redraw()
		clearInput()
		updateButton.visible = false
		createButton.visible = true
	} //end update function
	void onClick_cancelButton(Event e) {
		clearInput()
		updateButton.visible = false
		cancelButton.visible = false
		createButton.visible = true
	}
} //end calss