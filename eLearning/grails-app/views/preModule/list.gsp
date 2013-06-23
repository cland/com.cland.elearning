
<%@ page import="com.cland.elearning.PreModule" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'preModule.label', default: 'PreModule')}" />
		<title><g:appTitle title=""><g:message code="default.list.label" args="[entityName]" /></g:appTitle></title>
	</head>
	<body>
		<a href="#list-preModule" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-preModule" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<th><g:message code="preModule.course.label" default="Course" /></th>
					
						<th><g:message code="preModule.current.label" default="Current" /></th>
						<th><g:message code="preModule.prerequisites.label" default="Pre-Requisites" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${preModuleInstanceList}" status="i" var="preModuleInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${preModuleInstance.id}">${fieldValue(bean: preModuleInstance, field: "course")}</g:link></td>
					
						<td>${fieldValue(bean: preModuleInstance, field: "current")}</td>
						<td>
						<g:each in="${preModuleInstance?.prerequisites}" var="moduleInstance">
						&raquo; ${fieldValue(bean: moduleInstance, field: "name")}
						</g:each>
						</td>
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${preModuleInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
