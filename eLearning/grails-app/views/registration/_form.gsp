
<z:rows>

    <z:row>
        <z:label value="${message(code:'registration.person.label',default:'Person')}"/>
        <zkui:select name="person.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${registrationInstance?.person?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'registration.course.label',default:'Course')}"/>
        <zkui:select name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${registrationInstance?.course?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'registration.regDate.label',default:'Reg Date')}"/>
        <z:datebox name="regDate" value="${registrationInstance?.regDate}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'registration.regType.label',default:'Reg Type')}"/>
        <zkui:select name="regType.id" from="${com.cland.elearning.Role.list()}" optionKey="id" value="${registrationInstance?.regType?.id}"  />
    </z:row>

</z:rows>