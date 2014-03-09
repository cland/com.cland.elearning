<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'organisation.label', default: 'Organisation')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.organisation.ListComposer">
            <z:hlayout>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:label value="${message(code:'organisation.Name',default:'Name')}"/>
                <z:textbox id="keywordBoxName" class="search-box"/>
                <z:space/>
                <z:label value="${message(code:'organisation.vatNumber',default:'VAT Number')}"/>
                <z:textbox id="keywordBoxVat" class="search-box"/>
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
                    
                    <z:column label="${message(code: 'organisation.name.label', default: 'Name')}"/>
                    <z:column label="${message(code: 'organisation.vatnumber.label', default: 'VAT Number')}"/>
                    <z:column label="${message(code: 'organisation.phoneNo.label', default: 'Phone No')}"/>
                    <z:column label="${message(code: 'organisation.email.label', default: 'Email')}"/>
                    <z:column label="${message(code: 'organisation.phyAddress.label', default: 'Phy Address')}"/>
                    <z:column label="${message(code: 'organisation.isMember.label', default: 'Is Member')}"/>
                    <z:column width="150px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="15"/>
        </z:window>
    </body>
</html>