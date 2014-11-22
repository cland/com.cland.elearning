<%@ page import="com.cland.elearning.Event" %>
<%@ page import="org.joda.time.Instant" %>

<g:set var="courseService" bean="courseService"/>
<g:set var="courseInstance" value="${courseService?.findCourseForEvent(eventInstance) }"/>

<!doctype html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'event.label', default: 'Event')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>

    <r:require module="calendar" />

</head>

<body>
	<div class="bread-crump">
		<span class="r-arrow"></span>
		<g:link controller="event" action="index">Calendar</g:link>
		<span class="r-arrow"></span>
		<span class="current-crump">
			${ eventInstance?.title }
		</span>
	</div>
<a href="#show-event" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                            default="Skip to content&hellip;"/></a>


<div id="show-event" class="content scaffold-show" role="main">
    <h1>${eventInstance?.title}</h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>

    <ol class="property-list event">
        <li class="fieldcontain">
            <span id="when-label" class="property-label">When</span>

            <span class="property-value" aria-labelledby="when-label">
                <g:formatDate date="${new Instant(occurrenceStart).toDate()}" format="E, MMM d, hh:mma"/>  –
                <g:formatDate date="${new Instant(occurrenceEnd).toDate()}" format="E, MMM d, hh:mma"/>
            </span>

        </li>
          <li class="fieldcontain">
              <span id="location-label" class="property-label"><g:message code="event.location.label"
                                                                          default="Location"/></span>

              <span class="property-value" aria-labelledby="location-label"><g:fieldValue bean="${eventInstance}"
                                                                                          field="location"/></span>

          </li>

	<li class="fieldcontain">
	<span class="property-label">Course:</span>
	<span class="property-value">
		<g:if test="${courseInstance != null }">
			<g:link controller="course" action="show" id="${courseInstance?.id }">${courseInstance }</g:link>
		</g:if>
		<g:else>
			--
		</g:else>
	</span>
	</li>
	
        <g:if test="${eventInstance?.description}">
            <li class="fieldcontain">
                <span id="description-label" class="property-label"><g:message code="event.description.label"
                                                                               default="Description"/></span>

                <span class="property-value" aria-labelledby="description-label"><g:fieldValue bean="${eventInstance}"
                                                                                               field="description"/></span>

            </li>
        </g:if>
	<li class="fieldcontain">
		<span class="property-label">Region</span>
		<span class="property-value">${eventInstance?.region }</span>
	</li>
	<li class="fieldcontain">
		<span class="property-label">Facilitator</span>
		<span class="property-value">${eventInstance?.facilitator }</span>
	</li>

    </ol>
    <g:form>
        <fieldset class="buttons">
            <g:hiddenField name="id" value="${eventInstance?.id}"/>

            <g:hiddenField name="occurrenceStart" value="${occurrenceStart}" />
            <g:hiddenField name="occurrenceEnd" value="${occurrenceEnd}" />

		<sec:ifAnyGranted roles="ADMIN,TUTOR">
            <g:actionSubmit class="edit" action="edit"
                            value="${message(code: 'default.button.edit.label', default: 'Edit')}"/>
            <g:actionSubmit class="delete ${eventInstance.isRecurring ? 'recurring' : ''}" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}" />
		</sec:ifAnyGranted>                          
        </fieldset>
    </g:form>

    <g:if test="${eventInstance.isRecurring}">
        <g:render template="deletePopup" model="model" />
    </g:if>

</div>
</body>
</html>
