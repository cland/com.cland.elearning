package com.cland.elearning.resultsummary

import org.zkoss.zk.ui.Component
import org.zkoss.zul.*
import org.zkoss.zk.ui.event.*

import com.cland.elearning.ResultSummary

class ListComposer {
    Grid grid
    ListModelList listModel = new ListModelList()
    Paging paging
         //Longbox idLongbox
	Textbox keywordBox

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
        def resultSummaryInstanceList = ResultSummary.createCriteria().list(offset: offset, max: max) {
            order('learner','asc')
//            if (idLongbox.value) {
//                eq('id', idLongbox.value)
//            }
			if(keywordBox.value){
				ilike('result',"%"+keywordBox.value+"%")
			}
        }
        paging.totalSize = resultSummaryInstanceList.totalCount
        listModel.clear()
        listModel.addAll(resultSummaryInstanceList.id)
    }

    private rowRenderer = {Row row, Object id, int index ->
        def resultSummaryInstance = ResultSummary.get(id)
        row << {
                a(href: g.createLink(controller:"resultSummary",action:'edit',id:id), label: resultSummaryInstance.learner)
				label(value: resultSummaryInstance.course.name)
				label(value: resultSummaryInstance.module)
                label(value: resultSummaryInstance.status)
                label(value: resultSummaryInstance.result)
                label(value: resultSummaryInstance.certNumber)                               
                hlayout{
                    toolbarbutton(label: g.message(code: 'default.button.edit.label', default: 'Edit'),image:'/images/skin/database_edit.png',href:g.createLink(controller: "resultSummary", action: 'edit', id: id))
                    toolbarbutton(label: g.message(code: 'default.button.delete.label', default: 'Delete'), image: "/images/skin/database_delete.png", client_onClick: "if(!confirm('${g.message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}'))event.stop()", onClick: {
                        ResultSummary.get(id).delete(flush: true)
                        listModel.remove(id)
                    })
                }
        }
    }
	
	//** CUSTOM TESTS
	 void onChanging_keywordBox(InputEvent e) {
		 keywordBox.value = e.value
		 redraw()
	 }
} //end class