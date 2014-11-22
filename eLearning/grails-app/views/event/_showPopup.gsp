<%@ page import="org.joda.time.Instant" %>
<%@ page import="com.cland.elearning.*" %>
%{--Use CourseService--}%
<g:set var="courseService" bean="courseService"/>
<g:set var="courseInstance" value="${courseService?.findCourseForEvent(eventInstance) }"/>

<div class="eventPopup">

<h2>${eventInstance?.title}</h2>
<p class="date">
    <g:formatDate date="${new Instant(occurrenceStart).toDate()}" format="E, MMM d, hh:mma"/>  –
    <g:formatDate date="${new Instant(occurrenceEnd).toDate()}" format="E, MMM d, hh:mma"/>
</p>
<p style="padding-bottom:5px;">
	<label>Course:</label> 
	<g:if test="${courseInstance != null }">
		<g:link controller="course" action="show" id="${courseInstance?.id }">${courseInstance }</g:link>
	</g:if>
	<g:else>
		--
	</g:else>
	
	
	<br/>
	${eventInstance?.description} <br/>
</p>
<p>	
	<label>Region:</label> ${eventInstance?.region } <br/>
	<label>Facilitator: </label> ${eventInstance?.facilitator } <br/>
	<label>Location: </label> ${eventInstance?.location } <br/>
</p>
<br/><hr/>
<p>
    <g:link action="show" id="${eventInstance.id}" params="[occurrenceStart: occurrenceStart, occurrenceEnd: occurrenceEnd]">More details »</g:link>
</p>
</div>