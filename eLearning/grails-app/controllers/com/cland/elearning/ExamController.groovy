package com.cland.elearning
import pl.touk.excel.export.WebXlsxExporter
class ExamController {

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {}

    def create = {
        def examInstance = new Exam()
        examInstance.properties = params
        return [examInstance: examInstance]
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
