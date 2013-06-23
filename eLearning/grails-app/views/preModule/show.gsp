
<%@ page import="com.cland.elearning.PreModule" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'preModule.label', default: 'PreModule')}" />
		<title><g:appTitle title=""><g:message code="default.show.label" args="[entityName]" /></g:appTitle></title>
	</head>
	<body>
		<a href="#show-preModule" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-preModule" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list preModule">
			
				<g:if test="${preModuleInstance?.course}">
				<li class="fieldcontain">
					<span id="course-label" class="property-label"><g:message code="preModule.course.label" default="Course" /></span>
					
						<span class="property-value" aria-labelledby="course-label"><g:link controller="course" action="show" id="${preModuleInstance?.course?.id}">${preModuleInstance?.course?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${preModuleInstance?.current}">
				<li class="fieldcontain">
					<span id="current-label" class="property-label"><g:message code="preModule.current.label" default="Current" /></span>
					
						<span class="property-value" aria-labelledby="current-label"><g:link controller="module" action="show" id="${preModuleInstance?.current?.id}">${preModuleInstance?.current?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${preModuleInstance?.prerequisites}">
				<li class="fieldcontain">
					<span id="prerequisites-label" class="property-label"><g:message code="preModule.prerequisites.label" default="Prerequisites" /></span>
					
						<g:each in="${preModuleInstance.prerequisites}" var="p">
						<span class="property-value" aria-labelledby="prerequisites-label"><g:link controller="module" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${preModuleInstance?.id}" />
					<g:link class="edit" action="edit" id="${preModuleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
