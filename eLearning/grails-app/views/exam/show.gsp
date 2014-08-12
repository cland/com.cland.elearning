
<%@ page import="com.cland.elearning.Exam" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'exam.label', default: 'Exam')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-exam" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="show-exam" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list exam">
			
				<g:if test="${examInstance?.testNumber}">
				<li class="fieldcontain">
					<span id="testNumber-label" class="property-label"><g:message code="exam.testNumber.label" default="Test Number" /></span>
					
						<span class="property-value" aria-labelledby="testNumber-label"><g:fieldValue bean="${examInstance}" field="testNumber"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${examInstance?.factor}">
				<li class="fieldcontain">
					<span id="factor-label" class="property-label"><g:message code="exam.factor.label" default="Factor" /></span>
					
						<span class="property-value" aria-labelledby="factor-label"><g:fieldValue bean="${examInstance}" field="factor"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${examInstance?.factorOperand}">
				<li class="fieldcontain">
					<span id="factorOperand-label" class="property-label"><g:message code="exam.factorOperand.label" default="Factor Operand" /></span>
					
						<span class="property-value" aria-labelledby="factorOperand-label"><g:fieldValue bean="${examInstance}" field="factorOperand"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${examInstance?.status}">
				<li class="fieldcontain">
					<span id="status-label" class="property-label"><g:message code="exam.status.label" default="Status" /></span>
					
						<span class="property-value" aria-labelledby="status-label"><g:fieldValue bean="${examInstance}" field="status"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${examInstance?.maxMark}">
				<li class="fieldcontain">
					<span id="maxMark-label" class="property-label"><g:message code="exam.maxMark.label" default="Max Mark" /></span>
					
						<span class="property-value" aria-labelledby="maxMark-label"><g:fieldValue bean="${examInstance}" field="maxMark"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${examInstance?.submodule}">
				<li class="fieldcontain">
					<span id="submodule-label" class="property-label"><g:message code="exam.submodule.label" default="Submodule" /></span>
					
						<span class="property-value" aria-labelledby="submodule-label"><g:link controller="subModule" action="show" id="${examInstance?.submodule?.id}">${examInstance?.submodule?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${examInstance?.weight}">
				<li class="fieldcontain">
					<span id="weight-label" class="property-label"><g:message code="exam.weight.label" default="Weight" /></span>
					
						<span class="property-value" aria-labelledby="weight-label"><g:fieldValue bean="${examInstance}" field="weight"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${examInstance?.id}" />
					<g:link class="edit" action="edit" id="${examInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
