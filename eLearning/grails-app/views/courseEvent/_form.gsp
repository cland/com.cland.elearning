
<z:rows>

    <z:row>
        <z:label value="${message(code:'courseEvent.course.label',default:'Course')}"/>
        <zkui:select name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" value="${courseEventInstance?.course?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.subModule.label',default:'Sub Module')}"/>
        <zkui:select name="subModule.id" from="${com.cland.elearning.SubModule.list()}" optionKey="id" value="${courseEventInstance?.subModule?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.exam.label',default:'Exam')}"/>
        <zkui:select name="exam.id" from="${com.cland.elearning.Exam.list()}" optionKey="id" value="${courseEventInstance?.exam?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.region.label',default:'Region')}"/>
        <zkui:select name="region" from="${courseEventInstance.constraints.region.inList}" value="${courseEventInstance?.region}" valueMessagePrefix="courseEvent.region"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.eventDate.label',default:'Event Date')}"/>
        <z:datebox name="eventDate" value="${courseEventInstance?.eventDate}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.tutor.label',default:'Tutor')}"/>
        <zkui:select name="tutor.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${courseEventInstance?.tutor?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.counsellor.label',default:'Counsellor')}"/>
        <zkui:select name="counsellor.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${courseEventInstance?.counsellor?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'courseEvent.venue.label',default:'Venue')}"/>
        <zkui:select name="venue.id" from="${com.cland.elearning.Venue.list()}" optionKey="id" value="${courseEventInstance?.venue?.id}"  />
    </z:row>

</z:rows>