
<%@ page import="com.cland.elearning.RegistrationForm" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'registrationForm.label', default: 'RegistrationForm')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-registrationForm" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-registrationForm" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="firstName" title="${message(code: 'registrationForm.firstName.label', default: 'First Name')}" />
					
						<g:sortableColumn property="lastName" title="${message(code: 'registrationForm.lastName.label', default: 'Last Name')}" />
					
						<g:sortableColumn property="middleName" title="${message(code: 'registrationForm.middleName.label', default: 'Middle Name')}" />
					
						<g:sortableColumn property="knownAsName" title="${message(code: 'registrationForm.knownAsName.label', default: 'Known As Name')}" />
					
						<g:sortableColumn property="homeLanguage" title="${message(code: 'registrationForm.homeLanguage.label', default: 'Home Language')}" />
					
						<g:sortableColumn property="salutation" title="${message(code: 'registrationForm.salutation.label', default: 'Salutation')}" />
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${registrationFormInstanceList}" status="i" var="registrationFormInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${registrationFormInstance.id}">${fieldValue(bean: registrationFormInstance, field: "firstName")}</g:link></td>
					
						<td>${fieldValue(bean: registrationFormInstance, field: "lastName")}</td>
					
						<td>${fieldValue(bean: registrationFormInstance, field: "middleName")}</td>
					
						<td>${fieldValue(bean: registrationFormInstance, field: "knownAsName")}</td>
					
						<td>${fieldValue(bean: registrationFormInstance, field: "homeLanguage")}</td>
					
						<td>${fieldValue(bean: registrationFormInstance, field: "salutation")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${registrationFormInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
