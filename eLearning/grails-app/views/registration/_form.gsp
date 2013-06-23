
<z:rows>
<z:row>
        <z:label value="${message(code:'registration.course.label',default:'Course')}"/>
        <zkui:select name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${registrationInstance?.course?.id}"  />
    </z:row>
    <z:row>
        <z:label value="${message(code:'registration.learner.label',default:'Learner')}"/>
        <zkui:select name="learner.id" from="${com.cland.elearning.PersonRole.findAllByRole(com.cland.elearning.Role.findByAuthority('LEARNER'))?.person}" optionKey="id" value="${registrationInstance?.learner?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'registration.regDate.label',default:'Reg Date')}"/>
        <z:datebox name="regDate" value="${registrationInstance?.regDate}"/>
    </z:row>

</z:rows>