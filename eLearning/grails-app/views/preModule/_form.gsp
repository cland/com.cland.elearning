<%@ page import="com.cland.elearning.*" %>


<div class="fieldcontain ${hasErrors(bean: preModuleInstance, field: 'prerequisites', 'error')} ">
	<label for="prerequisites">
		<g:message code="preModule.prerequisites.label" default="Prerequisites" />
		
	</label>
	<g:select name="prerequisites" from="${Course.get(params.course.id)?.modules}" multiple="multiple" optionKey="id" size="5" value="${preModuleInstance?.prerequisites*.id}" class="many-to-many"/>
</div>
<div class="fieldcontain ${hasErrors(bean: preModuleInstance, field: 'current', 'error')} required">
	<label for="current">
		<g:message code="preModule.module.label" default="For Module" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="current" name="current.id" from="${Course.get(params.course.id)?.modules}" optionKey="id" required="" value="${preModuleInstance?.current?.id}" class="many-to-one"/>
</div>
<div <g:if test="${params.containsKey("course.id")}">style='display:none'</g:if> class="fieldcontain ${hasErrors(bean: preModuleInstance, field: 'course', 'error')} required">
	<label for="course">
		<g:message code="preModule.course.label" default="Course" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="course" name="course.id" from="${com.cland.elearning.Course.list()}" optionKey="id" required="" value="${preModuleInstance?.course?.id}" class="many-to-one"/>
</div>





