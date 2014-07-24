<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'resultSummary.label', default: 'ResultSummary')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <z:window style="padding:5px" apply="com.cland.elearning.resultsummary.ListComposer">
        
            <z:hlayout>
                <z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
                <z:space/>
                <z:vlayout>
                
                <z:hlayout>
                 <z:label value="${message(code:'resultSummary.register.learner',default:'Learner lastname:')}"/>
                <z:textbox id="keywordBox" />
                <z:space/><z:space/>
                <z:label value="${message(code:'resultSummary.tutor',default:'Tutor lastname:')}"/>
                <z:textbox id="keywordBoxTutor" />
                <z:space/>
                </z:hlayout>
                
<%--                <z:hlayout>--%>
<%--	            	<z:label value="${message(code:'resultSummary.status.label',default:'Status')}"/>--%>
<%--	                <zkui:select id="keywordBoxStatus" from="['All','Not Started','In Progress','Completed','Exempt']" value="All" valueMessagePrefix="resultSummary.status"  />--%>
<%--	               	<z:space/>--%>
<%--	               	<z:label value="${message(code:'resultSummary.module.label',default:'Module')}"/>--%>
<%--	        		<zkui:select id="keywordBoxModule" from="${com.cland.elearning.Module.list()}" optionKey="id" value=""  />--%>
<%--            	</z:hlayout>--%>
                </z:vlayout>
               <sec:ifAnyGranted roles="ADMIN,TUTOR">
                	<z:toolbarbutton href="${createLink(action:'jq_export_results_flat', params:[save: '1'])}" image="${fam.icon(name: 'page_excel')}" label="${message(code:'default.export.label',args:['Results'])}"/>
                </sec:ifAnyGranted>
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
                    <z:column label="${message(code: 'resultSummary.register.learner.label', default: 'Learner')}"/>
                    <z:column label="${message(code: 'resultSummary.register.course.label', default: 'Course')}"/>
                    <z:column label="${message(code: 'resultSummary.module.label', default: 'Module')}"/>
                    <z:column label="${message(code: 'resultSummary.status.label', default: 'Status')}"/>
                    <z:column label="${message(code: 'resultSummary.result.label', default: 'Result')}"/>
                     <z:column label="${message(code: 'resultSummary.tutor.label', default: 'Tutor')}"/>
                    <z:column label="${message(code: 'resultSummary.certNumber.label', default: 'Cert Number')}"/>
                                        
                    <z:column width="230px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="true" id="paging" pageSize="15"/>
        </z:window>
    </body>
</html>