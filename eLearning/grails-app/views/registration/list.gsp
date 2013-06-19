<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
        <title><g:appTitle title=""><g:message code="default.list.label" args="[entityName]" /></g:appTitle></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.registration.ListComposer">
            <z:hlayout>
                <z:toolbarbutton style="display:none" href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:label value="${message(code:'registration.learner',default:'Lastname: ')}"/>
                <z:textbox id="keywordBox"/>
                <z:space/>
                
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
                    <z:column label="${message(code: 'registration.learner.label', default: 'Learner')}"/>
                    <z:column label="${message(code: 'registration.course.label', default: 'Course')}"/>
                    <z:column label="${message(code: 'registration.tutor.label', default: 'Tutor')}"/>                    
                    <z:column label="${message(code: 'registration.regDate.label', default: 'Reg Date')}"/>
                    <z:column label="${message(code: 'registration.dateCreated.label', default: 'Date Created')}"/>
                    <z:column width="150px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="15"/>
        </z:window>
    </body>
</html>