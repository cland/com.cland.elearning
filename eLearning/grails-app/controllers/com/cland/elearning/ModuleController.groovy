package com.cland.elearning
import grails.converters.JSON
class ModuleController {

	def index = {
		redirect(action: "list", params: params)
	}

	def list = {}

	def create = {
		def moduleInstance = new Module()
		moduleInstance.properties = params
		return [moduleInstance: moduleInstance]
	}

	def edit = {
		def moduleInstance = Module.get(params.id)
		if (!moduleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [moduleInstance: moduleInstance]
		}
	}
	def show = {		
		redirect(action: "grid", params: params)
	}
	def grid = {
		//println("grid: ${params}")
		def moduleInstance = Module.get(params.id  as Long)
		if (!moduleInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'module.label', default: 'Module'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [moduleInstance: moduleInstance]
		}
	}

	def jq_list_submodule = {
		println("jq_submodule_list: ${params}")
		def sortIndex = params.sidx ?: 'name'
		def sortOrder  = params.sord ?: 'asc'

		def maxRows = Integer.valueOf(params.rows)
		def currentPage = Integer.valueOf(params.page) ?: 1

		def rowOffset = currentPage == 1 ? 0 : (currentPage - 1) * maxRows

		def moduleInstance = Module.get(params.modid)
		def submodules = moduleInstance.submodules //SubModule.createCriteria().list(max:maxRows, offset:rowOffset) {
		
		def totalRows = submodules.size() //totalCount
		def numberOfPages = Math.ceil(totalRows / maxRows)

		def jsonCells = submodules.collect {
			[cell: [it.name,
					it.type,
					it.description,
				], id: it.id]
		}
		def jsonData= [rows: jsonCells,page:currentPage,records:totalRows,total:numberOfPages]
		render jsonData as JSON
	} //end def

	def jq_edit_submodule = {
		//println("jq_edit_submodule: ${params}")
		def submodule = null
		def message = ""
		def state = "FAIL"
		def id

		// determine our action
		switch (params.oper) {
			case 'add':
			// add instruction sent
			params.put("module.id", params.modid);
				submodule = new SubModule(params)
				if (! submodule.hasErrors() && submodule.save()) {
					message = "Sub-Module  ${submodule.toString()} Added"
					id = submodule.id
					state = "OK"
				} else {
					message = "Could Not Save Sub-Module"
					println(submodule.errors)
				}
				break;
			case 'del':

				def idlist = params.list("id")

				idlist = JSON.parse(idlist.toString())

				for(int i=0;i<idlist.size();i++){
					submodule = SubModule.get(idlist[i] as Long)
					//submodule = SubModule.get(params.id)
					if (submodule) {
						// delete record
						submodule.delete()
						message = "${message} ${i+1}) Sub-Module  ${submodule.toString()} Deleted. "
						state = "OK"
					}else{
						message = "${message} ${i+1}) failed to delete record with id ${idlist[i]}. "
						state = "fail"

					}
				}//end for loop
				break;
			default:
			// edit action
			// first retrieve the  by its ID
				submodule = SubModule.get(params.id)
				if (submodule) {
					// set the properties according to passed in parameters
					submodule.properties = params
					if (! submodule.hasErrors() && submodule.save()) {
						message = "Sub-Module  ${submodule.toString()} Updated"
						id = submodule.id
						state = "OK"
					} else {
						message = "Could Not Update Record"
					}
				}
				break;
		}//end switch statement

		def response = [message:message,state:state,id:id]

		render response as JSON
	} //end jq_edit_submodule

	def jq_list_exam = {
		//println("jq_list_exam: ${params}")
		def exams = Exam.createCriteria().list() {
			eq "submodule.id", params.id as long
			order('testNumber','asc')
		}

		def jsonCells = exams.collect {
			[cell: [it.testNumber,
					it.maxMark,
					it.weight,
					it.factor,
					it.factorOperand,
					it.status
				], id: it.id]
		}
		def jsonData= [rows: jsonCells]
		render jsonData as JSON
	} //end list
	def jq_edit_exam = {
		//println("jq_edit_exam: ${params}")
		def exam = null
		def message = ""
		def state = "FAIL"
		def id

		// determine our action
		switch (params.oper) {
			case 'add':
			// add instruction sent
				params.put("submodule.id", params.subid);

				exam = new Exam(params)
				if (! exam.hasErrors() && exam.save()) {
					message = "Exam  ${exam.toString()}  Added"
					id = exam.id
					state = "OK"
				} else {
					message = "Could Not Add Record"
					println(exam.errors)
				}

				break;
			case 'del':
				def idlist = params.list("id")

				idlist = JSON.parse(idlist.toString())

				for(int i=0;i<idlist.size();i++){
					exam = Exam.get(idlist[i] as Long)
					if (exam) {
						// delete
						exam.delete()
						message = "${message} ${i+1}) Exam ${exam.toString()} Deleted. "
						state = "OK"
					}else{
						message = "${message} ${i+1}) failed to delete record with id ${idlist[i]}. "
						state = "fail"
					}
				}//end for loop
				break;
			default:
			// edit action
			// first retrieve the record by its ID
				exam = Exam.get(params.id)
				if (exam) {
					// set the properties according to passed in parameters
					exam.properties = params
					if (! exam.hasErrors() && exam.save()) {
						message = "exam  ${exam.toString()} Updated"
						id = exam.id
						state = "OK"
					} else {
						message = "Could Not Update Record"
					}
				}
				break;
		}//end switch statement

		def response = [message:message,state:state,id:id]

		render response as JSON
	} //end edit
} //end class
