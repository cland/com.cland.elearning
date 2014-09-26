
<%@ page import="com.cland.elearning.Certificate" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'certificate.label', default: 'Certificate')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-certificate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-certificate" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
				<thead>
					<tr>
					
						<g:sortableColumn property="certno" title="${message(code: 'certificate.certno.label', default: 'Certno')}" />
					
						<g:sortableColumn property="lastUpdatedBy" title="${message(code: 'certificate.lastUpdatedBy.label', default: 'Last Updated By')}" />
					
						<g:sortableColumn property="createdBy" title="${message(code: 'certificate.createdBy.label', default: 'Created By')}" />
					
						<g:sortableColumn property="dateCreated" title="${message(code: 'certificate.dateCreated.label', default: 'Date Created')}" />
					
						<g:sortableColumn property="lastUpdated" title="${message(code: 'certificate.lastUpdated.label', default: 'Last Updated')}" />
					
						<th><g:message code="certificate.resultSummary.label" default="Result Summary" /></th>
					
					</tr>
				</thead>
				<tbody>
				<g:each in="${certificateInstanceList}" status="i" var="certificateInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
					
						<td><g:link action="show" id="${certificateInstance.id}">${fieldValue(bean: certificateInstance, field: "certno")}</g:link></td>
					
						<td>${fieldValue(bean: certificateInstance, field: "lastUpdatedBy")}</td>
					
						<td>${fieldValue(bean: certificateInstance, field: "createdBy")}</td>
					
						<td><g:formatDate date="${certificateInstance.dateCreated}" /></td>
					
						<td><g:formatDate date="${certificateInstance.lastUpdated}" /></td>
					
						<td>${fieldValue(bean: certificateInstance, field: "resultSummary")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${certificateInstanceTotal}" />
			</div>
		</div>
	</body>
</html>
