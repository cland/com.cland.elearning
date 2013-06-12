
<z:rows>
<z:row>
        <z:label value="${message(code:'resultSummary.learner.label',default:'Learner')}"/>
        <zkui:select name="learner.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${resultSummaryInstance?.learner?.id}"  />
    </z:row>
     <z:row>
        <z:label value="${message(code:'resultSummary.course.label',default:'Course')}"/>
        <zkui:select name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${resultSummaryInstance?.course?.id}"  />
    </z:row>
      <z:row>
        <z:label value="${message(code:'resultSummary.module.label',default:'Module')}"/>
        <zkui:select name="module.id" from="${com.cland.elearning.Module.list()}" optionKey="id" value="${resultSummaryInstance?.module?.id}"  />
    </z:row>
    <z:row>
        <z:label value="${message(code:'resultSummary.status.label',default:'Status')}"/>
        <zkui:select name="status" from="${resultSummaryInstance.constraints.status.inList}" value="${resultSummaryInstance?.status}" valueMessagePrefix="resultSummary.status"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'resultSummary.result.label',default:'Result')}"/>
        <zkui:select name="result" from="${resultSummaryInstance.constraints.result.inList}" value="${resultSummaryInstance?.result}" valueMessagePrefix="resultSummary.result"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'resultSummary.certNumber.label',default:'Cert Number')}"/>
        <z:textbox name="certNumber" value="${resultSummaryInstance?.certNumber}" />
    </z:row>

  

    

   

</z:rows>