<%@ page import="com.cland.elearning.Organisation" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="dialogmain">
		<g:set var="entityName" value="${message(code: 'organisation.label', default: 'Organisation')}" />
		<title><g:appTitle title="">New Organisation</g:appTitle></title>
	</head>
	<body>

		<div id="create-organisation" class="content scaffold-create" role="main">			
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="message" id="resultsDiv"></div>
			<g:hasErrors bean="${organisationInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${organisationInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>>
				<g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			<g:formRemote id="orgForm" name="Org" url="[controller:'organisation',action:'dialogsave']" update="resultsDiv" 
			onSuccess="processResult(data)">
			<g:hiddenField name="id" value="${organisationInstance?.id}" />
				<g:hiddenField name="version" value="${organisationInstance?.version}" />
				<fieldset class="form">
<div class="fieldcontain ${hasErrors(bean: organisationInstance, field: 'name', 'error')} required">
	<label for="Name">
		<g:message code="organisation.name.label" default="Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="name" required="" value="${organisationInstance?.name}"/>
</div>
<div class="fieldcontain ${hasErrors(bean: organisationInstance, field: 'vatNumber', 'error')} required">
	<label for="vatNumber">
		<g:message code="organisation.vatnumber.label" default="VAT Number" />
		
	</label>
	<g:textField name="vatNumber" required="" value="${organisationInstance?.vatNumber}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: organisationInstance, field: 'phoneNo', 'error')} required">
	<label for="phoneNo">
		<g:message code="organisation.phoneNo.label" default="Phone number" />
		<span class="required-indicator"></span>
	</label>
	<g:textField name="phoneNo" required="" value="${organisationInstance?.phoneNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: organisationInstance, field: 'isMember', 'error')} required">
	<label for="phoneNo">
		<g:message code="organisation.isMember.label" default="Is Member?" />
		<span class="required-indicator"></span>
	</label>
	<g:select name="isMember" from="${organisationInstance.constraints.isMember.inList}" value="${organisationInstance?.isMember}" valueMessagePrefix="organisation.gender" noSelection="['': '']"/>
	
</div>


</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.submit.label', default: 'Submit')}" />
					
				</fieldset>
			</g:formRemote>
		</div>
		 <script>
		
		function processResult(data){
			$("#resultsDiv").html(data.message)
			$("#orgForm").hide()
		}
		 </script>
	</body>
</html>
