<%@ page import="com.cland.elearning.Certificate" %>



<div class="fieldcontain ${hasErrors(bean: certificateInstance, field: 'certno', 'error')} ">
	<label for="certno">
		<g:message code="certificate.certno.label" default="Certno" />
		
	</label>
	<g:textField name="certno" value="${certificateInstance?.certno}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: certificateInstance, field: 'lastUpdatedBy', 'error')} required">
	<label for="lastUpdatedBy">
		<g:message code="certificate.lastUpdatedBy.label" default="Last Updated By" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="lastUpdatedBy" type="number" value="${certificateInstance.lastUpdatedBy}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: certificateInstance, field: 'createdBy', 'error')} required">
	<label for="createdBy">
		<g:message code="certificate.createdBy.label" default="Created By" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="createdBy" type="number" value="${certificateInstance.createdBy}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: certificateInstance, field: 'resultSummary', 'error')} required">
	<label for="resultSummary">
		<g:message code="certificate.resultSummary.label" default="Result Summary" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="resultSummary" name="resultSummary.id" from="${com.cland.elearning.ResultSummary.list()}" optionKey="id" required="" value="${certificateInstance?.resultSummary?.id}" class="many-to-one"/>
</div>

