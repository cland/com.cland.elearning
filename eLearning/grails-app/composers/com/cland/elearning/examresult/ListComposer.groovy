package com.cland.elearning.examresult

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*
import com.cland.elearning.ExamResult

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
        def examResultInstanceList = ExamResult.createCriteria().list(offset: offset, max: max) {
            order('id','desc')
            if (idLongbox.value) {
                eq('id', idLongbox.value)
            }
        }
        paging.totalSize = examResultInstanceList.totalCount
        listModel.clear()
        listModel.addAll(examResultInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def examResultInstance = ExamResult.get(id)
        row << {
                a(href: g.createLink(controller:"examResult",action:'edit',id:id), label: examResultInstance.id)
                label(value: examResultInstance.examDate)
                label(value: examResultInstance.mark)
                label(value: examResultInstance.percentMark)
                label(value: examResultInstance.contributionMark)
                label(value: examResultInstance.tutor)
                hlayout{
                    toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "examResult", action: 'edit', id: id))
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        ExamResult.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    }
}