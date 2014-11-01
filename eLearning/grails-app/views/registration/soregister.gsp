
<%@ page import="com.cland.elearning.Registration" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'registration.label', default: 'Online Registration')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<link rel="stylesheet"	href="${resource(dir:'css',file:'ui.jqgrid.css')}" />
		<link rel="stylesheet"	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
		<g:javascript library="jquery11" />
		<g:javascript library="jqueryuilatest" />
		<g:javascript library="jqueryfiledownload" />		
<%--		<g:javascript library="multidatepicker" />--%>
	<g:render template="../layouts/icon_style"></g:render>
	<style type="text/css">
	.green-tick {
	background-image: url('${fam.icon(name: 'accept')}');
	background-position: 0.7em center;
	background-repeat: no-repeat;
	text-indent: 25px;
}
		
	</style>
	</head>
	<body>
		<a href="#list-registration" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="list-registration" class="content scaffold-list" role="main">
			<h1>START Online Registration</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="filter-div">
				
				<g:form action="onlineregistration" >
					<fieldset class="form">
						<table class="dataTable">					
						<tr>
							<td>Have you been registered before? Enter student number: (Optional)</td>
							<td>
								<g:textField name="learnerId" value=""/>
							</td>
						</tr>
					</table>
					<g:submitButton name="start-online" class="green-tick" value="Start" />
					</fieldset>

				</g:form>
			</div>

			</div>
			

		<script>
	
		</script>		
	</body>
</html>
