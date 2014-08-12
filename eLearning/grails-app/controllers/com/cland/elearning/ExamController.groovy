package com.cland.elearning
import grails.converters.JSON
import pl.touk.excel.export.WebXlsxExporter
class ExamController {
	static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def examInstance = new Exam()
        examInstance.properties = params
        return [examInstance: examInstance]
    }
	def show(Long id) {
		def examInstance = Exam.get(id)
		if (!examInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'exam.label', default: 'Exam'), id])
			redirect(action: "list")
			return
		}

		[examInstance: examInstance]
	}
	def dialogupdate = {
		Long id = params?.long("id")
		Long version = params?.long("version")
        def examInstance = Exam.get(id)
        if (!examInstance) {
           // flash.message = message(code: 'default.not.found.message', args: [message(code: 'exam.label', default: 'Exam'), id])
            //redirect(action: "dialogedit")
			def response = [message: "Error: Exam not found!", id:id]
			render response as JSON
            return ["failed":true] as JSON
        }

        if (version != null) {
            if (examInstance.version > version) {
                examInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'exam.label', default: 'Exam')] as Object[],
                          "Another user has updated this Exam while you were editing")
                //render(view: "dialogedit", model: [examInstance: examInstance])
				def response = [message: "Error: Another user has updated this Exam while you were editing!", id:examInstance.id]
				render response as JSON
                return
            }
        }

        examInstance.properties = params

        if (!examInstance.save(flush: true)) {
            //render(view: "dialogedit", model: [examInstance: examInstance])
			def response = [message: "Error: Failed to save exam!", id:examInstance.id]
			render response as JSON
            return
        }

       // flash.message = message(code: 'default.updated.message', args: [message(code: 'exam.label', default: 'Exam'), examInstance.toString()])
        //redirect(action: "list", id: examInstance.id)
		def response = [message: examInstance.toString() + " updated!", id:examInstance.id]
		render response as JSON
    }
	
    def edit = {
        def examInstance = Exam.get(params.id)
        if (!examInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'exam.label', default: 'Exam'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [examInstance: examInstance]
        }
    }
	def dialogedit = {
		def examInstance = Exam.get(params.id)
		if (!examInstance) {
			flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'exam.label', default: 'Exam'), params.id])}"
			redirect(action: "list")
		}
		else {
			return [examInstance: examInstance]
		}
	}
	def jq_export_modules = {
		
		String submodule_filter = null
		String module_filter = null
		def examInstanceList = Exam.createCriteria().list() {
			createAlias('submodule','sm')
			createAlias('sm.module','m')
			order('m.name','asc')
			order('sm.name','asc')
			order('testNumber','asc')
			if(module_filter){
				ilike('m.name',""+module_filter+"%")
			}
			if(submodule_filter){
				ilike('sm.name',""+submodule_filter+"%")
			}
		}
		
		def data = examInstanceList.collect({it.toMap()})
		def headers = ['Module', 'Mode of Learning', 'Exam No.', 'Max Mark', 'Weight','Factor','Factor Operand','Status']			
		def withProperties = ['module','type','exam','max_mark','weight','factor','operand','status']
						
		new WebXlsxExporter().with {
			setResponseHeaders(response)
			sheet('Modules and Exams').with {
				fillHeader(headers)
				add(data, withProperties)
			}				
			save(response.outputStream)
		}							
	} //end method jq_export_modules
	
	
} //end class
