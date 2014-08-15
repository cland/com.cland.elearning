<%@ page import="com.cland.elearning.PreModule" %>
<!DOCTYPE html>
<html>
	<head>

    <g:set var="entityName" value="${message(code: 'exam.label', default: 'Exam')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>		
	</head>
	<body>

		<div id="edit-exam" class="content scaffold-create" role="main">	
		<div id="resultsDiv" style="display:none;border:solid 1px #000;background:#fff;padding:5px;">Action completed!</div>		
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<g:hasErrors bean="${examInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${examInstance}" var="error">
				<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
			</g:hasErrors>
			
			<g:formRemote id="formid" name="dialogEdit" update="resultsDiv" url="[controller:'exam',action:'dialogupdate']" 
			after="afterSubmit"
			onSuccess="onSuccess(data)"
			onFailure="onFailure(data)"
			onComplete="onComplete">
			<g:hiddenField name="id" value="${examInstance?.id}" />
				<g:hiddenField name="version" value="${examInstance?.version}" />
				<fieldset class="form">
					<g:render template="formsimple"/>
				</fieldset>
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="${message(code: 'default.button.submit.label', default: 'Submit')}" />
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:formRemote>
		</div>
		<script>
function afterSubmit(){

}
function onSuccess(data){
	$("#resultsDiv").show()
	$("#resultsDiv").html(data.message)
	$("#formid").hide()
}
function onFailure(data){
	alert("Failed to save")
	$("#resultsDiv").html("Failed to save!  " + data.message)
}
function onComplete(){
	alert("completed!")
}
		</script>
	</body>
</html>
