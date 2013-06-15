
<z:rows>

    <z:row>
        <z:label value="${message(code:'examResult.examDate.label',default:'Exam Date')}"/>
        <z:datebox name="examDate" value="${examResultInstance?.examDate}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.mark.label',default:'Mark')}"/>
        <z:textbox name="mark" value="${fieldValue(bean: examResultInstance, field: 'mark')}" />
    </z:row>


    <z:row>
        <z:label value="${message(code:'examResult.tutor.label',default:'Tutor')}"/>
        <zkui:select name="tutor.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${examResultInstance?.tutor?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.counsellor.label',default:'Counsellor')}"/>
        <zkui:select name="counsellor.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${examResultInstance?.counsellor?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.venue.label',default:'Venue')}"/>
        <zkui:select name="venue.id" from="${com.cland.elearning.Venue.list()}" optionKey="id" value="${examResultInstance?.venue?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.region.label',default:'Region')}"/>
        <zkui:select name="region" from="${Region.list()}" value="${examResultInstance?.region}" valueMessagePrefix="examResult.region"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.subModule.label',default:'Sub Module')}"/>
        <zkui:select name="subModule.id" from="${com.cland.elearning.SubModule.list()}" optionKey="id" value="${examResultInstance?.subModule?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.exam.label',default:'Exam')}"/>
        <zkui:select name="exam.id" from="${com.cland.elearning.Exam.list()}" optionKey="id" value="${examResultInstance?.exam?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'examResult.resultSummary.label',default:'Result Summary')}"/>
        <zkui:select name="resultSummary.id" from="${com.cland.elearning.ResultSummary.list()}" optionKey="id" value="${examResultInstance?.resultSummary?.id}"  />
    </z:row>

</z:rows>