<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
        <title> <g:appTitle title=""><g:message code="default.list.label" args="[entityName]" /></g:appTitle></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.course.ListComposer">
            <z:hlayout>
            	<h2 style="padding: 2px 10px 1px 5px;"> Courses </h2>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:label value="${message(code:'course.name',default:'Name')}"/>
                <z:textbox id="keywordBox" />
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
                    <z:column label="${message(code: 'course.name.label', default: 'Name')}"/>                    
                    <z:column label="${message(code: 'course.region.label', default: 'Region')}"/>
                    <z:column label="${message(code: 'course.status.label', default: 'Status')}"/>
                    <z:column width="180px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="15"/>
        </z:window>
    </body>
</html>