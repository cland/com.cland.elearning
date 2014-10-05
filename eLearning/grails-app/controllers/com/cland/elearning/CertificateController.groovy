package com.cland.elearning

import grails.converters.JSON
import java.text.DateFormat
import java.text.SimpleDateFormat
import org.joda.time.DateTime
import org.springframework.dao.DataIntegrityViolationException
import pl.touk.excel.export.WebXlsxExporter

class CertificateController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [certificateInstanceList: Certificate.list(params), certificateInstanceTotal: Certificate.count()]
    }

    def create() {
        [certificateInstance: new Certificate(params)]
    }

    def save() {
        def certificateInstance = new Certificate(params)
        if (!certificateInstance.save(flush: true)) {
            render(view: "create", model: [certificateInstance: certificateInstance])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'certificate.label', default: 'Certificate'), certificateInstance.id])
        redirect(action: "show", id: certificateInstance.id)
    }

    def show(Long id) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        [certificateInstance: certificateInstance]
    }

    def edit(Long id) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        [certificateInstance: certificateInstance]
    }

    def update(Long id, Long version) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (certificateInstance.version > version) {
                certificateInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'certificate.label', default: 'Certificate')] as Object[],
                          "Another user has updated this Certificate while you were editing")
                render(view: "edit", model: [certificateInstance: certificateInstance])
                return
            }
        }

        certificateInstance.properties = params

        if (!certificateInstance.save(flush: true)) {
            render(view: "edit", model: [certificateInstance: certificateInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'certificate.label', default: 'Certificate'), certificateInstance.id])
        redirect(action: "show", id: certificateInstance.id)
    }

    def delete(Long id) {
        def certificateInstance = Certificate.get(id)
        if (!certificateInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
            return
        }

        try {
            certificateInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'certificate.label', default: 'Certificate'), id])
            redirect(action: "show", id: id)
        }
    }
	
	def search(){
		def startdate = params?.startDate_Completed
		def enddate = params?.endDate_Completed
		def learnerId = params?.learnerId
		
		if(startdate != null & startdate != "") startdate = parseDate(startdate) else startdate = new DateTime().minusMonths(24).toDate()
		if(enddate != null  & startdate != "") enddate = parseDate(enddate) else enddate = (new Date())
		
		long moduleid = params?.module.id?.toLong()
		def resultList = ResultSummary.createCriteria().list(){
			createAlias('module','module')
			createAlias('register','register')
			createAlias('register.learner','learner')
			//createAlias('certificate','cert')
			eq('status',"completed")
			or {
				eq('result',"Pass")
				eq('result',"Exempt")
			}
			if(startdate != null & enddate != null){
				between('endDate', startdate, enddate)
			}
			if(moduleid > 0) {				
				eq('module.id',moduleid)
			}
			if(learnerId){
				like("learner.studentNo",learnerId)
			}
			and {
				order("learner.firstName","asc")
				order("learner.lastName","asc")
				order("endDate","desc")
			}
			
			//if(max > 0) maxResults(max)
			//ilike('hair','black')
		}		
		render resultList*.toCertificateMap() as JSON
	}
	def exportCertificates(params){ 
		//export selected certificates to excel
		List idlist = params?.list("result")
		List results = []
		idlist?.each {id ->
			ResultSummary resultSummary = ResultSummary.get(id )
			if(resultSummary){
				results?.add(['result':resultSummary.toCertificateMap()])
			} //end if resultSummary
		} //end for each
			
		def headers = ['Learner',
			'Student No',
			'Module',
			'Certificate No.',
			'Completion Date',
			'Result',
			'Status',
			'Registration Date',
			'Payment Status',
			'System Id']
		
		def withProperties = ['result.person_name',
			'result.person_studentno',
			'result.module.name',
			'result.certificate.certno',
			'result.end_date',
			'result.result',
			'result.status',
			'result.reg_date',
			'result.payment_status',
			'result.id'
			]
		new WebXlsxExporter().with {
			setResponseHeaders(response)
			sheet('Certificates').with {
				fillHeader(headers)
				add(results, withProperties)
			}			
			save(response.outputStream)
		}
	} //
	
	def generateList(){
		//receives a list of resultSummary ids
		if(params?.button == "export"){
			exportCertificates(params)
			return;
		}
		List idlist = params?.list("result")
		List results = []
		idlist?.each {id ->
			ResultSummary resultSummary = ResultSummary.get(id )
			if(resultSummary){				
					//generating certificate numbers
					if(!resultSummary?.certificate){
						Certificate certInstance = new Certificate(resultSummary:resultSummary).save()
						if(certInstance){
							certInstance.certno = generateCertNo(certInstance?.id)
							certInstance.save()
							if(certInstance?.hasErrors()) println(certInstance?.errors)
							resultSummary?.certificate = certInstance
							resultSummary?.save()
							if(resultSummary?.hasErrors()) resultSummary?.errors
							results?.add(['result':['id':id,'certno':certInstance?.certno]])
						}
					}				
			} //end if resultSummary
		} //end for each
		
		render results as JSON
	} //end function
	def generate(){
		//params: resultSummary.id
		ResultSummary resultSummary = ResultSummary.get(params?.resultSummary?.id)
		Certificate certInstance = new Certificate(params).save()
		if(certInstance){			
			certInstance.certno = generateCertNo(certInstance?.id)
			certInstance.save(flush:true)
			render certInstance?.toMap() as JSON
		}else{
			println (">> " + certInstance?.errors)  //render [error:"Failed to save item"] as JSON		
		}
		List error =['error':'Failed!']
		render error as JSON
	}
	private String generateCertNo(long offset){
		//certno should be yyyymm-10001
		def today = new Date()
		Long n = offset + 10000
		String certNo = g.formatDate(date:today,format:"yyyymm") + "-" + n.toString()
		return  certNo
	}
	private parseDate(date) {
		SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");	
		return (!date) ? new Date() : df.parse(date)
   }
}//end class
