<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'courseEvent.label', default: 'CourseEvent')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.courseevent.ListComposer">
            <z:hlayout>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:label value="${message(code:'courseEvent.id',default:'Id')}"/>
                <z:longbox id="idLongbox"/>
                <z:space/>
                <z:button id="searchButton" label="${message(code:'search')}"/>
            </z:hlayout>
            <g:if test="${flash.message}">
                <z:window mode="popup" border="normal">
                    <z:hlayout>
                        <z:image src="/images/skin/information.png"/>
                        <z:div>
                            ${flash.message}
                        </z:div>
                    </z:hlayout>
                </z:window>
            </g:if>
            <z:grid id="grid" emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
                <z:columns sizable="true">
                    <z:column label="${message(code: 'courseEvent.id.label', default: 'Id')}"/>
                    <z:column label="${message(code: 'courseEvent.course.label', default: 'Course')}"/>
                    <z:column label="${message(code: 'courseEvent.subModule.label', default: 'Sub Module')}"/>
                    <z:column label="${message(code: 'courseEvent.exam.label', default: 'Exam')}"/>
                    <z:column label="${message(code: 'courseEvent.region.label', default: 'Region')}"/>
                    <z:column label="${message(code: 'courseEvent.eventDate.label', default: 'Event Date')}"/>
                    <z:column width="150px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="15"/>
        </z:window>
    </body>
</html>