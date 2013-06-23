package com.cland.elearning.race

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.Race
import org.codehaus.groovy.grails.plugins.springsecurity.*
class ListComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
    Longbox idLongbox
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
        def raceInstanceList = Race.createCriteria().list(offset: offset, max: max) {
            order('id','desc')
            if (idLongbox.value) {
                eq('id', idLongbox.value)
            }
        }
        paging.totalSize = raceInstanceList.totalCount
        listModel.clear()
        listModel.addAll(raceInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def raceInstance = Race.get(id)
        row << {
                a(href: g.createLink(controller:"race",action:'edit',id:id), label: raceInstance.id)
                label(value: raceInstance.name)
                hlayout{
                    toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "race", action: 'edit', id: id))
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        Race.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    }
}