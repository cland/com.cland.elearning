
<z:rows>

     <z:row>
        <z:label value="${message(code:'resultSummary.register.course.label',default:'Course')}"/>
        <zkui:select name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${resultSummaryInstance?.register?.course?.id}"  />
    </z:row>
      <z:row>
        <z:label value="${message(code:'resultSummary.module.label',default:'Module')}"/>
        <zkui:select name="module.id" from="${com.cland.elearning.Module.list()}" optionKey="id" value="${resultSummaryInstance?.module?.id}"  />
    </z:row>
    
    <z:row>
        <z:label value="${message(code:'resultSummary.register.learner.label',default:'Learner')}"/>
        <zkui:select name="register.id" from="${com.cland.elearning.Registration.list()?.sort{it.learner.firstName}}" optionKey="id" value="${resultSummaryInstance?.register?.id}"  />
    </z:row>
    
    <z:row>
        <z:label value="${message(code:'resultSummary.status.label',default:'Status')}"/>
        <zkui:select name="status" from="${resultSummaryInstance.constraints.status.inList}" value="${resultSummaryInstance?.status}" valueMessagePrefix="resultSummary.status"  />
    </z:row>
	<z:row>
		<z:label
			value="${message(code:'resultSummary.startdate.label',default:'Start Date [yyy/mm/dd]')}" />
		<z:datebox name="startDate"
			value="${resultSummaryInstance?.startDate}" format="yyyy/MM/dd" constraint="no future: now or never" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'resultSummary.enddate.label',default:'Completed Date [yyy/mm/dd]')}" />
		<z:datebox name="endDate"
			value="${resultSummaryInstance?.endDate}" format="yyyy/MM/dd" constraint="no future: now or never" />
	</z:row>
    <z:row>
        <z:label value="${message(code:'resultSummary.result.label',default:'Result')}"/>
        <zkui:select name="result" from="${resultSummaryInstance.constraints.result.inList}" value="${resultSummaryInstance?.result}" valueMessagePrefix="resultSummary.result"  />
    </z:row>
	<z:row>
        <z:label value="${message(code:'resultSummary.paymentStatus.label',default:'Payment Status')}"/>
        <zkui:select name="paymentStatus" from="${resultSummaryInstance.constraints.paymentStatus.inList}" value="${resultSummaryInstance?.paymentStatus}" valueMessagePrefix="resultSummary.paymentStatus"  />
    </z:row>
    <z:row>
        <z:label value="${message(code:'resultSummary.certNumber.label',default:'Cert Number')}"/>
        <z:textbox name="certNumber" value="${resultSummaryInstance?.certNumber}" />
    </z:row>
	<z:row>
        <z:label value="${message(code:'resultSummary.tutor.label',default:'Tutor')}"/>
        <zkui:select name="tutor.id" from="${tutorList}" optionKey="id" value="${resultSummaryInstance?.tutor?.id}"  />
    </z:row>
   
</z:rows>