<%@ page import="com.cland.elearning.Exam" %>

<%--<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'submodule', 'error')} required">--%>
<%--	<label for="submodule">--%>
<%--		<g:message code="exam.submodule.label" default="Submodule" />--%>
<%--		<span class="required-indicator">*</span>--%>
<%--	</label>--%>
<%--	<g:select id="submodule" name="submodule.id" from="${com.cland.elearning.SubModule.list()}" optionKey="id" required="" value="${examInstance?.submodule?.id}" class="many-to-one"/>--%>
<%--</div>--%>

<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'testNumber', 'error')} required">
	<label for="testNumber">
		<g:message code="exam.testNumber.label" default="Test Number" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="testNumber" type="number" min="1" value="${examInstance.testNumber}" required=""/>
</div>
<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'maxMark', 'error')} required">
	<label for="maxMark">
		<g:message code="exam.maxMark.label" default="Max Mark" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="maxMark" type="number" value="${examInstance.maxMark}" required=""/>
</div>
<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'weight', 'error')} required">
	<label for="weight">
		<g:message code="exam.weight.label" default="Weight" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="weight" value="${fieldValue(bean: examInstance, field: 'weight')}" required=""/>
</div>
<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'status', 'error')} ">
	<label for="status">
		<g:message code="exam.status.label" default="Status" />
		
	</label>
	<g:select name="status" from="${examInstance.constraints.status.inList}" value="${examInstance?.status}" valueMessagePrefix="exam.status" noSelection="['': '']"/>
</div>
<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'factor', 'error')} required">
	<label for="factor">
		<g:message code="exam.factor.label" default="Factor" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="factor" type="number" value="${examInstance.factor}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: examInstance, field: 'factorOperand', 'error')} ">
	<label for="factorOperand">
		<g:message code="exam.factorOperand.label" default="Factor Operand" />
		
	</label>
	<g:select name="factorOperand" from="${examInstance.constraints.factorOperand.inList}" value="${examInstance?.factorOperand}" valueMessagePrefix="exam.factorOperand" noSelection="['': '']"/>
</div>

