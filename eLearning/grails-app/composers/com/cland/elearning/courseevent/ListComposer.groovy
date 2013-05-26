package com.cland.elearning.courseevent

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.CourseEvent

class ListComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
    Longbox idLongbox

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
        def courseEventInstanceList = CourseEvent.createCriteria().list(offset: offset, max: max) {
            order('id','desc')
            if (idLongbox.value) {
                eq('id', idLongbox.value)
            }
        }
        paging.totalSize = courseEventInstanceList.totalCount
        listModel.clear()
        listModel.addAll(courseEventInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def courseEventInstance = CourseEvent.get(id)
        row << {
                a(href: g.createLink(controller:"courseEvent",action:'edit',id:id), label: courseEventInstance.id)
                label(value: courseEventInstance.course)
                label(value: courseEventInstance.subModule)
                label(value: courseEventInstance.exam)
                label(value: courseEventInstance.region)
                label(value: courseEventInstance.eventDate)
                hlayout{
                    toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "courseEvent", action: 'edit', id: id))
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        CourseEvent.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    }
}