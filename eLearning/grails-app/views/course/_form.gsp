
<z:rows>

    <z:row>
        <z:label value="${message(code:'course.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${courseInstance?.name}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'course.startDate.label',default:'Start Date')}"/>
        <z:datebox name="startDate" value="${courseInstance?.startDate}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'course.endDate.label',default:'End Date')}"/>
        <z:datebox name="endDate" value="${courseInstance?.endDate}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'course.region.label',default:'Region')}"/>
        <zkui:select name="region" from="${com.cland.elearning.Region.list().sort(false){it.name}}" value="${courseInstance?.region}" valueMessagePrefix="course.region"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'course.status.label',default:'Status')}"/>
        <zkui:select name="status" from="${courseInstance.constraints.status.inList}" value="${courseInstance?.status}" valueMessagePrefix="course.status"  />
    </z:row>
<z:row>
        <z:label value="${message(code:'course.comments.label',default:'Comments')}"/>
        <z:textbox name="comments" rows="4" value="${courseInstance?.comments}" />
    </z:row>
</z:rows>