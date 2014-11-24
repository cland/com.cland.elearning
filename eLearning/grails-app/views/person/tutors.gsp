<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
        <title><g:appTitle title=""><g:message code="default.list.label" args="[entityName]" /></g:appTitle></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.person.TutorsComposer">
            <z:hlayout>
            <h2 style="padding: 2px 10px 1px 5px;"> Tutors </h2>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:label value="${message(code:'person.firstName',default:'Firstname')}"/>
                <z:textbox id="keywordBox" class="search-box"/>
                <z:space/>
               
                <z:label value="${message(code:'person.lastName',default:'Lastname')}"/>
                <z:textbox id="keywordBoxLastname" class="search-box"/>
                <z:space/>
                               
                <z:button id="searchButton" label="${message(code:'search')}"/>
                 <z:button id="clearButton" label="${message(code:'clear')}"/>
<%--                <sec:ifAnyGranted roles="ADMIN,TUTOR">--%>
<%--              		<z:toolbarbutton href="${createLink(action:'jq_export_learners', params:[save: '1'])}" image="${fam.icon(name: 'page_excel')}" label="${message(code:'default.export.label',args:['All'])}"/>--%>
<%--              	</sec:ifAnyGranted>--%>
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
                    <z:column label="${message(code: 'person.firstname.label', default: 'First Name')}"/>
                    <z:column label="${message(code: 'person.lastname.label', default: 'Last Name')}"/>
                    <z:column label="${message(code: 'person.username.label', default: 'Username')}"/>                   
                    <z:column label="${message(code: 'person.role.label', default: 'Roles')}"/>                    
                    <z:column width="170px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="10"/>
        </z:window>
    </body>
</html>