
<z:rows>

    <z:row>
        <z:label value="${message(code:'courseResult.course.label',default:'Course')}"/>
        <zkui:select name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${courseResultInstance?.course?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.subModule.label',default:'Sub Module')}"/>
        <zkui:select name="subModule.id" from="${com.cland.elearning.SubModule.list()}" optionKey="id" value="${courseResultInstance?.subModule?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.exam.label',default:'Exam')}"/>
        <zkui:select name="exam.id" from="${com.cland.elearning.Exam.list()}" optionKey="id" value="${courseResultInstance?.exam?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.region.label',default:'Region')}"/>
        <zkui:select name="region" from="${courseResultInstance.constraints.region.inList}" value="${courseResultInstance?.region}" valueMessagePrefix="courseResult.region"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.resultDate.label',default:'Result Date')}"/>
        <z:datebox name="resultDate" value="${courseResultInstance?.resultDate}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.tutor.label',default:'Tutor')}"/>
        <zkui:select name="tutor.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${courseResultInstance?.tutor?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.counsellor.label',default:'Counsellor')}"/>
        <zkui:select name="counsellor.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${courseResultInstance?.counsellor?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.venue.label',default:'Venue')}"/>
        <zkui:select name="venue.id" from="${com.cland.elearning.Venue.list()}" optionKey="id" value="${courseResultInstance?.venue?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.mark.label',default:'Mark')}"/>
        <z:textbox name="mark" value="${fieldValue(bean: courseResultInstance, field: 'mark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.percentMark.label',default:'Percent Mark')}"/>
        <z:textbox name="percentMark" value="${fieldValue(bean: courseResultInstance, field: 'percentMark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.contributionMark.label',default:'Contribution Mark')}"/>
        <z:textbox name="contributionMark" value="${fieldValue(bean: courseResultInstance, field: 'contributionMark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseResult.learner.label',default:'Learner')}"/>
        <zkui:select name="learner.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${courseResultInstance?.learner?.id}"  />
    </z:row>

</z:rows>