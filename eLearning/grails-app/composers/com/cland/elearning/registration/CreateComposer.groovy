package com.cland.elearning.registration

import org.apache.jasper.compiler.Node.ParamsAction;
import org.zkoss.zk.ui.Component
import org.zkoss.zk.ui.event.Event
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zul.*
import org.joda.time.*
import org.joda.time.format.*
import com.cland.elearning.*

class CreateComposer {
    Window self
    def afterCompose = {Component comp ->
        //todo initialize components here
		learnerListBox.setItemRenderer(learnerListBoxRowRenderer as ListitemRenderer)
		learnerListBox.setModel(learnerListBoxModel)
		redrawLearnerListBox()
    }
	ListModelList learnerListBoxModel = new ListModelList()
	Bandbox learnerBandbox
	Listbox learnerListBox
	Longbox learnerIdBox
	Paging learnerPaging
	Textbox learnerSearch
	Toolbarbutton clearLearnerListBoxSearch
	Toolbarbutton newLearner
	String searchLearnerListBoxStr = ""
    void onClick_saveButton(Event e) {
        def registrationInstance = new Registration(self.params)
        if (!registrationInstance.save(flush: true) && registrationInstance.hasErrors()) {
            log.error registrationInstance.errors
            self.renderErrors(bean: registrationInstance)
        } else {
            flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
            redirect(controller: "registration", action: "list")
        }
    }
	
	void onClick_saveDialogButton(Event e) {
		def params = self.params
		//println(params)
	//	for(def key: params.keySet()){
	//		println(key + " - " + params.getAt(key))
	//	}
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy");
		Long active_tab = 1
		def courseId = params.getAt("course.id")
		def learnerId = params.getAt("learner.id")
		def regdate = params?.getAt("regDate")
		def regyear = regdate?.format("yyyy")
		DateTime startyear = formatter.parseDateTime("01/01/" + regyear);
		DateTime endyear = formatter.parseDateTime("31/12/" + regyear);

		def registrationInstance = Registration.withCriteria(uniqueResult:true) {
			createAlias("course","c")
			createAlias("learner","l")
			
			between('regDate',startyear?.toDate(), endyear?.toDate())
			eq('c.id',courseId)
			eq('l.id',learnerId)			
			  
		}
		//def registrationInstance = Registration.findAllWhere("learner.id":learnerId as Long,"course.id":courseId as Long)
		if(registrationInstance){
			flash.message = registrationInstance.learner.toString() + " is already registered!" //g.message(code: 'default.exists.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
			if(params.src =="learner"){
				active_tab = 2
				redirect(controller: "person", action: "show", params:[id:learnerId as Long,tab:active_tab])
			}else{
				redirect(controller: "course", action: "show", params:[id:courseId as Long,tab:active_tab])
			}
			return
		}
		
		//otherwise create a new one
		registrationInstance = new Registration(params)
		if (!registrationInstance.save(flush: true) && registrationInstance.hasErrors()) {
			log.error registrationInstance.errors
			self.renderErrors(bean: registrationInstance)
		} else {
			//if new one has been created successfully, generate the resultsummary and examresult stubs
			createResultStubs(registrationInstance)
			flash.message = g.message(code: 'default.created.message', args: [g.message(code: 'registration.label', default: 'Registration'), registrationInstance.id])
			
			if(params.src =="learner"){
				active_tab = 2
				redirect(controller: "person", action: "show", params:[id:learnerId as Long,tab:active_tab])
			}else{
				redirect(controller: "course", action: "show", params:[id:courseId as Long,tab:active_tab])
			}
			
		}
	} //end
	
	void createResultStubs(Registration registrationInstance){
		def courseInstance = registrationInstance.course
		def modules = courseInstance.modules
		
		def resultSummary = null
		for(Module m : modules){
			
			resultSummary = createResultSummary(m, registrationInstance)
			//create the examresult stubs
			//createExamResults(resultSummary)
			
			registrationInstance.addToResults(resultSummary)
			if(registrationInstance.save(flush:true) && registrationInstance.hasErrors()){
				println("Errors!")
				log.error registrationInstance.errors
			}else{
				//println("added to register for course module ${m.name}!! Size: ${registrationInstance.results.size()} - \n ${resultSummary.toString()}")
				resultSummary.save(flush:true)
				if(resultSummary.hasErrors()){
					println(resultSummary.errors)
				}else{
					//create the examresult stubs
					//createExamResults(resultSummary)
				}
			}
		} //end for all modules
	} //end method
	
	ResultSummary createResultSummary(Module module, Registration register){
		//TODO: use a default none tutor.
		def defaultTutor = Person.findByUsername("default.tutor")
		def resultSummary = new ResultSummary(
				status:"Not Started",
				result:"None",
				module:module,
				tutor:defaultTutor,
				certNumber:""
				)	
		//def module = resultSummary.module
		def submodules = module.submodules
	//	println("Submodules size: " + submodules.size())
		def examResult = null
		def exams = null
		for(SubModule submod : submodules){
	//		println(">> SubModule:" + submod.name)
			exams = submod?.exams		
			for(Exam e : exams){
				if(e.status == 'Active'){
					examResult = new ExamResult(
						examDate:new Date(),
						mark:0,
						percentMark:0.0,
						contributionMark:0.0,
						tutor:defaultTutor,  //fix
						subModule:submod,
						exam:e,
						region:"",
						venue:"",
						testNumber:e.testNumber,maxMark:e.maxMark,weight:e.weight,factor:e.factor,factorOperand:e.factorOperand
						)
				}

				resultSummary.addToResults(examResult)
			} //end for all exam						
		} //end for all subModules
		return resultSummary
	} //end createExamResults
	
	//Begin Learner bandbox
	
		void onSelect_learnerListBox(Event e){
			learnerIdBox.value = learnerListBox.selectedItem.value
			learnerBandbox.value=learnerListBox.selectedItem.label
			learnerBandbox.close()
		}
		
		private learnerListBoxRowRenderer = {Listitem listitem, clientInstanceId, int index ->
			Person clientInstance = Person.read(clientInstanceId)
			listitem << { listcell(label: clientInstance?.toString() + " (" + clientInstance?.studentNo + ")") }
			listitem.setValue(clientInstanceId)
		}
	
		void onPaging_learnerPaging(ForwardEvent fe) {
			def event = fe.origin
			redrawLearnerListBox(event.activePage)
		}
	
		private redrawLearnerListBox(int activePage = 0) {
	
			int offset = activePage * learnerPaging.pageSize
	
			int max = learnerPaging.pageSize
			def clientInstanceList = Person.createCriteria().list(offset: offset, max: max) {
				order('firstName')
				if (!searchLearnerListBoxStr.isEmpty()) {
					or {
						ilike('lastName', "%" + searchLearnerListBoxStr + "%")
						ilike('studentNo',searchLearnerListBoxStr + "%")
					}
				}
				
			}
			learnerPaging.totalSize = clientInstanceList.totalCount
			learnerListBoxModel.clear()
			learnerListBoxModel.addAll(clientInstanceList.id)
		}
	
		void onChanging_learnerSearch(InputEvent e) {
			searchLearnerListBoxStr = e.value
			redrawLearnerListBox()
		}
	
		void onClick_clearLearnerListBoxSearch(Event e){
			learnerSearch.value = ""
			searchLearnerListBoxStr = ""
			redrawLearnerListBox()
		}
		
		void onClick_newLearner(Event e){
			learnerBandbox.close()
		}
} //end class