
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

</z:rows>