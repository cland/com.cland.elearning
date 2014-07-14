<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'module.name', default: moduleInstance.toString())}" />
<title><g:appTitle title=""><g:message code="default.show.label" args="[entityName]" /></g:appTitle></title>
</head>

<body>
	<z:window style="padding:5px"
		apply="com.cland.elearning.module.ShowComposer">
<div class="bread-crump">
<span class="r-arrow"></span><span class="current-crump"> Module: ${moduleInstance.name }</span>
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
						value="${message(code:'module.name.label',default:'Name')}" />
					<g:fieldValue bean="${moduleInstance}" field="name" />
				</z:row>
				<z:row>
					<z:label
						value="${message(code:'module.duration.label',default:'Duration')}" />
					<g:fieldValue bean="${moduleInstance}" field="duration" /> 
					<g:fieldValue bean="${moduleInstance}" field="durationUnit" />
				</z:row>	
				<z:row>
					<z:label
						value="${message(code:'module.valid.label',default:'Certification expires')}" />
					<g:fieldValue bean="${moduleInstance}" field="valid" /> 
					<g:fieldValue bean="${moduleInstance}" field="validUnit" />
				</z:row>			
				<z:row>
					<z:label
						value="${message(code:'module.description.label',default:'Description')}" />
					${moduleInstance?.description}
				</z:row>



			</z:rows>
		</z:grid>
		<z:hlayout>
			<z:button id="addSubmoduleButton"
				label="${message(code: 'default.button.add.label', default: 'Add Sub-Module')}" />
			<z:button href="${createLink(action:'list')}"
				label="${message(code: 'default.list.label', args:[entityName])}" />
		</z:hlayout>
		<z:space />

		<z:hbox>
			<z:panel width="100%" title="Sub Modules" collapsible="false">
				<z:panelchildren>
					<z:grid id="submoduleGrid"
						emptyMessage="${message(code:'emptyMessage',default:'No Record - Administrators Only')}">
						<z:columns sizable="true">
							<z:column 
								label="${message(code: 'submodule.name.label', default: 'Name')}" />
							<z:column
								label="${message(code: 'submodule.type.label', default: 'Type')}" />
							<z:column width="80px"
								label="${message(code: 'submodule.examcount.label', default: 'Total Exams')}" />
							<z:column width="180px"/>
						</z:columns>
					</z:grid>
					<z:paging autohide="true" id="paging" pageSize="15" />
				</z:panelchildren>
			</z:panel>
		</z:hbox>
	</z:window>
</body>
</html>