<%@ page import="com.cland.elearning.Event" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>

    <r:require module="calendar" />
</head>
<body>
	<div class="bread-crump">
		<span class="r-arrow"></span>
		<g:link controller="event" action="index">Calendar</g:link>
		<span class="r-arrow"></span>
		<span class="current-crump">
			New Entry
		</span>
	</div>
<div id="create-event" class="content scaffold-create" role="main">

<h1><g:message code="default.create.label" args="[entityName]" /></h1>

<g:if test="${flash.message}">
    <div class="alert-message block-message info">${flash.message}</div>
</g:if>

<g:hasErrors bean="${eventInstance}">
    <ul class="errors" role="alert">
        <g:eachError bean="${eventInstance}" var="error">
            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message
                    error="${error}"/></li>
        </g:eachError>
    </ul>
</g:hasErrors>

<g:form action="save" class="main" method="post" >

    <fieldset class="form">
        <g:render template="form" model="model" />
    </fieldset>
<sec:ifAnyGranted roles="ADMIN,TUTOR">
    <fieldset class="buttons">
        <g:submitButton name="create" class="save" value="Save"/>
    </fieldset>
</sec:ifAnyGranted>
</g:form>

</div>
</body>
</html>