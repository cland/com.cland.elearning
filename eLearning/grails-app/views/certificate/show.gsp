
<%@ page import="com.cland.elearning.Certificate" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'certificate.label', default: 'Certificate')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-certificate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-certificate" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list certificate">
			
				<g:if test="${certificateInstance?.certno}">
				<li class="fieldcontain">
					<span id="certno-label" class="property-label"><g:message code="certificate.certno.label" default="Certno" /></span>
					
						<span class="property-value" aria-labelledby="certno-label"><g:fieldValue bean="${certificateInstance}" field="certno"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${certificateInstance?.lastUpdatedBy}">
				<li class="fieldcontain">
					<span id="lastUpdatedBy-label" class="property-label"><g:message code="certificate.lastUpdatedBy.label" default="Last Updated By" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdatedBy-label"><g:fieldValue bean="${certificateInstance}" field="lastUpdatedBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${certificateInstance?.createdBy}">
				<li class="fieldcontain">
					<span id="createdBy-label" class="property-label"><g:message code="certificate.createdBy.label" default="Created By" /></span>
					
						<span class="property-value" aria-labelledby="createdBy-label"><g:fieldValue bean="${certificateInstance}" field="createdBy"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${certificateInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="certificate.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${certificateInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${certificateInstance?.lastUpdated}">
				<li class="fieldcontain">
					<span id="lastUpdated-label" class="property-label"><g:message code="certificate.lastUpdated.label" default="Last Updated" /></span>
					
						<span class="property-value" aria-labelledby="lastUpdated-label"><g:formatDate date="${certificateInstance?.lastUpdated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${certificateInstance?.resultSummary}">
				<li class="fieldcontain">
					<span id="resultSummary-label" class="property-label"><g:message code="certificate.resultSummary.label" default="Result Summary" /></span>
					
						<span class="property-value" aria-labelledby="resultSummary-label"><g:link controller="resultSummary" action="show" id="${certificateInstance?.resultSummary?.id}">${certificateInstance?.resultSummary?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${certificateInstance?.id}" />
					<g:link class="edit" action="edit" id="${certificateInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
