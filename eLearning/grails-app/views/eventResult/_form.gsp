
<z:rows>

    <z:row>
        <z:label value="${message(code:'eventResult.mark.label',default:'Mark')}"/>
        <z:textbox name="mark" value="${fieldValue(bean: eventResultInstance, field: 'mark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'eventResult.percentMark.label',default:'Percent Mark')}"/>
        <z:textbox name="percentMark" value="${fieldValue(bean: eventResultInstance, field: 'percentMark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'eventResult.contributionMark.label',default:'Contribution Mark')}"/>
        <z:textbox name="contributionMark" value="${fieldValue(bean: eventResultInstance, field: 'contributionMark')}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'eventResult.courseEvent.label',default:'Course Event')}"/>
        <zkui:select name="courseEvent.id" from="${com.cland.elearning.CourseEvent.list()}" optionKey="id" value="${eventResultInstance?.courseEvent?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'eventResult.learner.label',default:'Learner')}"/>
        <zkui:select name="learner.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${eventResultInstance?.learner?.id}"  />
    </z:row>

</z:rows>