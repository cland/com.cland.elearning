<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"	value="${message(code: 'course.label', default: 'Course')}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir:'css',file:'ui.jqgrid.css')}" />
<link rel="stylesheet"
	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
<g:javascript library="jquerymin" />
<g:javascript library="jqueryuilatest" />
<g:javascript library="jquerygridlocale" />
<g:javascript library="jquerygrid" />
<script type="text/javascript">
var cland_params = {
		thisId : ${params.id},
		modules_list_url : "../jq_list_modules",
		learners_list_url : "../jq_list_learners",
		tutors_list_url	: "../jq_list_tutors",
		events_list_url	: "../jq_list_events",
			
		submod_types :"Assignment:Assignment;Computer Marked Asessment:CMA;Practical Attendance Exercises:PAE;Tutor Marked Assessment:TMA",
		states : "active:active;inactive:inactive",
		operands : "Divide:Divide;Multiply:Multiply;Subtract:Subtract;Add:Add"				
	}
</script>
<style>
.ui-jqgrid .ui-jqgrid-htable th div {
	height: auto;
	overflow: hidden;
	padding-right: 4px;
	padding-top: 2px;
	position: relative;
	vertical-align: text-top;
	white-space: normal !important;
}
</style>
</head>

<body>
	<z:window style="">
		<z:window style="padding:5px">
			<div class="bread-crump">
				<span class="r-arrow"></span>
				<g:link controller="course" action="list">Courses</g:link>
				<span class="r-arrow"></span> <span class="current-crump">
					Course: ${courseInstance.name }
				</span>
			</div>
			<g:if test="${flash.message}">
				<z:window mode="popup" border="normal" style="margin-bottom:5px">
					<z:hlayout>
						<z:image src="/images/skin/information.png" />
						<z:div>
							${flash.message}
						</z:div>
					</z:hlayout>
				</z:window>
			</g:if>
		</z:window>
	</z:window>
	<fieldset>
		<legend>
			<b>&raquo;</b> Course
		</legend>
		<h1>
			<g:fieldValue bean="${courseInstance}" field="name" />
		</h1>
		<div class="content">
			Status:
			${courseInstance.status}.
			${courseInstance.startDate.format('dd MMM yyyy')}
			-
			${courseInstance.endDate.format('dd MMM yyyy')}
		</div>
	</fieldset>

	<!-- The tabs -->
	<div id="tabs">
		<ul>
			<li><g:link controller="course" action="jq_list_modules" params="${params}">Modules</g:link></li>
			<li><g:link controller="course" action="jq_list_learners" params="${params}">Learners</g:link></li>
			<li><g:link controller="course" action="jq_list_tutors" params="${params}">Tutors</g:link></li>
			<li><g:link controller="course" action="jq_list_events" params="${params}">Events</g:link></li>
		</ul>
		
	</div>
	<!--  End tabs -->

	<script type="text/javascript">
		// <![CDATA[
		/* when the page has finished loading.. execute the follow */
		$(document).ready(function() {			
							$("#tabs")
									.tabs(
											{
												beforeLoad : function(event, ui) {
													ui.jqXHR.error(function() {
														ui.panel
														.html("Couldn't load this tab. We'll try to fix this as soon as possible. ");
													});
												}
											});

							//field set functions
							$("legend").click(
									function() {
										$(this).children("b").toggleClass(
												"collapsed");
										$(this).nextAll("div.content")
												.slideToggle(500);
									});
							$("fieldset.topleft1 legend").children("b")
									.addClass("collapsed");
							$("fieldset.topleft1 legend")
									.nextAll("div.content").hide();
							$("fieldset.topleft2 legend").children("b")
									.addClass("collapsed");
							$("fieldset.topleft2 legend")
									.nextAll("div.content").hide();
						});
		//]]>
	</script>
</body>
</html>