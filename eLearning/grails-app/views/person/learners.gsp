<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'learner.label', default: 'Learner')}" />
        <title><g:appTitle title=""><g:message code="default.list.label" args="[entityName]" /></g:appTitle></title>
        <g:javascript library="jquerymin" />
       
    </head>
    <body>
    
        <z:window style="padding:5px" apply="com.cland.elearning.person.LearnersComposer">
        	<z:vlayout>
        		
        		<z:hlayout>
        			<h2 style="padding: 2px 10px 1px 5px;"> Learners </h2>
        			<z:toolbarbutton href="${createLink(action:'create')}" image="/images/skin/database_add.png" label="${message(code:'default.new.label',args:[entityName])}"/>
	                <z:space/>
	                <sec:ifAnyGranted roles="ADMIN,TUTOR">
	              		<z:toolbarbutton id="export_learners" href="${createLink(controller:'person', action:'jq_export_learners', params:[save: '1'])}" image="${fam.icon(name: 'page_excel')}" label="${message(code:'default.export.label',args:['All Learners'])}"/>
	              		<z:toolbarbutton href="${createLink(controller:'resultSummary', action:'jq_export_results_flat', params:[save: '1',rtype:'2'])}" image="${fam.icon(name: 'page_excel')}" label="${message(code:'default.export.label',args:['All Results'])}"/>
		                <z:toolbarbutton href="${createLink(controller:'resultSummary', action:'jq_export_results_flat', params:[save: '1',rtype:'1'])}" image="${fam.icon(name: 'page_excel')}" label="${message(code:'default.export.label',args:['All Test Scores'])}"/>
              		</sec:ifAnyGranted>
        		</z:hlayout>
        		<z:hlayout style="border:solid 1px rgba(62, 59, 59, 1);padding:5px;">
        			<z:vlayout >
        				<z:hlayout>
        					<z:label value="${message(code:'person.lastName',default:'Learner Lastname')}"/>
			                <z:textbox id="keywordBoxLastname" class="search-box"/>
			                <z:space/>
			               <z:label value="${message(code:'person.tutor',default:'Tutor Lastname')}"/>
			                <z:textbox id="keywordBoxTutor" class="search-box"/>
			                <z:space/>
			                <z:label value="${message(code:'person.studentNo',default:'Student No.')}"/>
			                <z:textbox id="keywordBoxId" class="search-box"/>
        				</z:hlayout>
        				<z:hlayout>
        					<z:label value="${message(code:'person.company',default:'Company')}"/>
			                <z:textbox id="keywordBoxCompany" class="search-box"/>
			                <z:space/>
			                 <z:label style="font-weight:bold" value="Date Range:"/>
			               <z:label value="${message(code:'person.startdate',default:'From Date')}"/>
			                <z:datebox id="keywordBoxStartDate" class="search-box" format="dd-MM-yyyy" constraint="no future: now or never"/>
			                <z:space/>
			                <z:label value="${message(code:'person.enddate',default:'To Date')}"/>
			                <z:datebox id="keywordBoxEndDate" class="search-box" format="dd-MM-yyyy" constraint="no future: now or never"/>
			                <z:space/>
			                <z:radiogroup id="dateRangeType" name="dateRangeType">
			                	<z:radio checked="true" value="dateCompleted" label="Final Date" style="padding:2px;"/>
			                	<z:radio value="dateRegistered" label="Date Registered" style="padding:2px;" />
			                </z:radiogroup>
        				</z:hlayout>
        			</z:vlayout>
        			<z:hlayout style="vertical-align:middle;margin-top:10px;">  
        				<z:button id="searchButton" label="${message(code:'Search')}" style="font-weight:bold;padding:5px;"/>
	                	<z:button id="clearButton" label="${message(code:'Clear')}" style="font-weight:bold;padding:5px;"/>
	                </z:hlayout>

        		</z:hlayout>
        		<z:hlayout>
        		<z:button id="exportLearnerButton" label="${message(code:'Export Learners')}" style="font-weight:bold;padding:5px;"/>
        		</z:hlayout>
        	</z:vlayout>
           
           
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
                    <z:column width="90px" label="${message(code: 'person.studentno.label', default: 'Student No')}"/>
                    <z:column width="250px" label="${message(code: 'person.courses.label', default: 'Courses')}"/>                    
                    <z:column width="170px"/>
                </z:columns>
            </z:grid>
            <z:paging autohide="false" id="paging" pageSize="50"/>
        </z:window>
         <script type="text/javascript">
         function exportLeaners(){
             	alert("fuction export...")
             }
        $(document).ready(function () {
            $("#test1").click(function(){
                	alert("Clicked!");
                });
        	$(document).on("click","#test1",function(){
				alert("Exporting....");
            	});
        });
        </script>
    </body>
</html>