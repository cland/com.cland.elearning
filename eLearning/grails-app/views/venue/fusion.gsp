<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'venue.label', default: 'Venue')}" />
<title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
	<z:window style="padding:5px"
		apply="com.cland.elearning.venue.FusionComposer">

		<z:hlayout>
			<z:toolbarbutton href="${createLink(action:'create')}"
				image="/images/skin/database_add.png"
				label="${message(code:'default.new.label',args:[entityName])}" />
			<z:space />
			<z:label value="${message(code:'venue.id',default:'Search name: ')}" />
			<z:textbox id="keywordBox" />
			<z:space />
			<z:button style="display:none" id="searchButton"
				label="${message(code:'search')}" />
		</z:hlayout>
		<g:if test="${flash.message}">
			<z:window mode="popup" border="normal">
				<z:hlayout>
					<z:image src="/images/skin/information.png" />
					<z:div>
						${flash.message}
					</z:div>
				</z:hlayout>
			</z:window>
		</g:if>
		<z:grid id="grid"
			emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
			<z:columns sizable="true">
				<z:column label="${message(code: 'venue.id.label', default: 'Id')}" />
				<z:column
					label="${message(code: 'venue.name.label', default: 'Name')}" />
				<z:column width="150px" />
			</z:columns>
		</z:grid>
		<z:paging autohide="true" id="paging" pageSize="15" />

		<z:longbox id="idBox" name="id" value="${venueInstance.id}"
			visible="false" />
		<z:longbox id="versionBox" name="version"
			value="${venueInstance.version}" visible="false" />
		<z:grid>
			<z:columns sizable="true">
				<z:column label="${message(code:'name',default:'Venue')}"
					width="100px" />
				<z:column label="${message(code:'value',default:'Value')}" />
			</z:columns>
			<z:rows>
				<z:row>
					<z:label value="${message(code:'venue.name.label',default:'Name')}" />
					<z:textbox id="nameBox" name="name" maxlength="50"
						value="${venueInstance?.name}" />
				</z:row>
			</z:rows>
		</z:grid>
		<z:hlayout>
			<z:button id="createButton"
				label="${message(code: 'default.button.create.label', default: 'Create')}" />
			<z:button id="updateButton"
				label="${message(code: 'default.button.create.update', default: 'Update')}"
				visible="false" />
				<z:button id="cancelButton"
				label="${message(code: 'default.button.create.cancel', default: 'Cancel')}"
				visible="false" />
		</z:hlayout>
	</z:window>
</body>
</html>