<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'submodule.label', default: submoduleInstance.toString())}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
</head>

<body>

	<z:window style="padding:5px"
		apply="com.cland.elearning.submodule.ShowComposer">
<div class="bread-crump">
<span class="r-arrow"></span> 
	<a href="${createLink(mapping: 'module',controller:'module',params:[id:submoduleInstance.module.id,headline:submoduleInstance.module.name.replaceAll(/ /,'-')])}">Module: ${submoduleInstance.module.name }</a>
<span class="r-arrow"></span><span class="current-crump"> Sub-Module: ${submoduleInstance.name }</span>
</div>
		<g:if test="${flash.message}">
			<z:window mode="popup" border="normal" style="margin-bottom:5px">
				<z:hlayout>
					<z:image src="/images/skin/information.png" />
					<z:div>
						${flash.message}
					</z:div>
				</z:hlayout>
			</z:window>
		</g:if>
		<z:grid>
			
			<z:rows>

				<z:row>
					<z:label
						value="${message(code:'submodule.name.label',default:'Name')}" />
					<g:fieldValue bean="${submoduleInstance}" field="name" />
				</z:row>
				<z:row>
					<z:label
						value="${message(code:'submodule.description.label',default:'Description')}" />
					${submoduleInstance?.description}
				</z:row>
			</z:rows>
		</z:grid>
		<z:hlayout>
			<z:button id="addExamButton"
				label="${message(code: 'default.button.add.label', default: 'Add Exam')}" />
			<z:button href="${createLink(controller:'module',action:'show',params:[id:submoduleInstance.module.id])}"
				label="${message(code: 'default.show.label', args:['Module'])}" />
		</z:hlayout>
		<z:space />

		<z:hbox>
			<z:panel width="100%" title="Tests and Exams" collapsible="false">
				<z:panelchildren>
					<z:grid id="examGrid"
						emptyMessage="${message(code:'emptyMessage',default:'No Record - Administrators Only')}">
						<z:columns sizable="true">
							<z:column 
								label="${message(code: 'exams.testNumber.label', default: 'Number')}" />
							<z:column
								label="${message(code: 'exams.maxMark.label', default: 'Max Mark')}" />
							<z:column width="80px"
								label="${message(code: 'exams.weight.label', default: '% Weight')}" />
								<z:column width="80px"
								label="${message(code: 'exams.status.label', default: 'Status')}" />
							<z:column width="80px"
								label="${message(code: 'exams.factor.label', default: 'Factor')}" />
							<z:column width="100px"
								label="${message(code: 'exams.factorOperand.label', default: 'Factor Operand')}" />								
							<z:column width="150px"/>
						</z:columns>
					</z:grid>
					<z:paging autohide="true" id="paging" pageSize="15" />
				</z:panelchildren>
			</z:panel>
		</z:hbox>
	</z:window>
</body>
</html>