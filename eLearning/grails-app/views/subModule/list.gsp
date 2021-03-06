<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'subModule.label', default: 'SubModule')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.submodule.ListComposer">
            <z:hlayout>
            	<h2 style="padding: 2px 10px 1px 5px;"> Mode of Learning </h2>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:label value="${message(code:'subModule.id',default:'Id')}"/>
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
                    <z:column label="${message(code: 'subModule.id.label', default: 'Id')}"/>
                    <z:column label="${message(code: 'subModule.type.label', default: 'Type')}"/>
                    <z:column label="${message(code: 'subModule.description.label', default: 'Description')}"/>
                    <z:column label="${message(code: 'subModule.module.label', default: 'Module')}"/>
                    <z:column label="${message(code: 'subModule.name.label', default: 'Name')}"/>
                    <z:column width="150px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="15"/>
        </z:window>
    </body>
</html>