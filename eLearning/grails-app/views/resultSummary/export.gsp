
<%@ page import="com.cland.elearning.ResultSummary" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'export.label', default: 'Export Panel')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<link rel="stylesheet"	href="${resource(dir:'css',file:'ui.jqgrid.css')}" />
		<link rel="stylesheet"	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
		<g:javascript library="jquery11" />
		<g:javascript library="jqueryuilatest" />
		<%--<g:javascript library="jqueryfiledownload" /> --%>	
<%--		<g:javascript library="multidatepicker" />--%>
	<g:render template="../layouts/icon_style"></g:render>
	<style type="text/css">
		
	</style>
	</head>
	<body>
		<a href="#list-certificate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="list-certificate" class="content scaffold-list" role="main">
			<h1>EXPORT PANEL</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="filter-div">
				<g:form onSubmit="return false;">
				
					<fieldset class="form">
						<table class="dataTable">
						<tr><td>Select Module:<span class="required-indicator">*</span></td><td>
							<g:select id="moduleid" name="module.id" from="${com.cland.elearning.Module.list()}" optionKey="id" value="${subModuleInstance?.module?.id}"  required="" noSelection="['0':'- Any Modules -']"/>
						</td></tr>
						<tr>
							<td>Date Completed: (Optional)</td>
							<td>
								From: <g:textField name="startDate_Completed" class="datepick_single_future" id="start-date" value=""/> &nbsp;
								To: <g:textField name="endDate_Completed" class="datepick_single_future" id="end-date" value=""/>	
							</td>
						</tr>						
<%--						<tr>--%>
<%--							<td colspan=2>--%>
<%--								<g:checkBox name="certificate_available" value="yes"/><span class="checkbox-text">With Certificate</span>&nbsp;--%>
<%--								<g:checkBox name="certificate_available" value="no"/><span class="checkbox-text">Without Certificate</span><br/>--%>
<%--							</td>--%>
<%--						</tr>--%>
						<tr>
							<td>Learner Student No.: (Optional)</td>
							<td>
								<g:textField id="learnerid" name="learnerId" value=""/>
								
							</td>
						</tr>

						<g:hiddenField id="save-hidden" name="save" value="1"/>
						<g:hiddenField id="rtype-hidden" name="rtype" value="2"/>
					</table>
					<fieldset class="buttons">						
						<g:submitButton id="export_results_btn" name="export_results" class="export" value="Export Results" onclick="exportResults('1','2');"/>
						<g:submitButton id="export_scores_btn" name="export_scores" class="export" value="Export Test Scores" onclick="exportResults('1','1');"/>
						<g:submitButton id="export_learners_btn" name="export_learners" class="export" value="Export Learners" onclick="exportLearners('1','1');"/>
					</fieldset>
					</fieldset>

				</g:form>
			</div>
			
			<div id="resultsDiv" style="display:none;border:solid 1px #000;background:#fff;padding:5px;margin-bottom:20px;">
				<table class='dataTable'>				
					<tbody id="resultbody">
					</tbody>
				</table>

			</div>
			
		</div>
<script>
	function setClickedButton(button){
			$("#clicked-button").prop("value",button);
			return true;
		}
	function exportResults(save,rtype){
		$("#save-hidden").prop("value",save);
		$("#rtype-hidden").prop("value",rtype);
		var url = "${g.createLink(controller:'resultSummary',action:'jq_export_results_flat')}?rtype=" + rtype + "&save=" + save + "&module.id=" + $("#moduleid").val() + "&startDate_Completed=" + $("#start-date").prop("value") + "&endDate_Completed=" + $("#end-date").prop("value") + "&learnerid=" + $("#learnerid").prop("value") 
		console.log(url) 
		document.location.href = url
		return false
	}
	function exportLearners(save,rtype){
		$("#save-hidden").prop("value",save);
		$("#rtype-hidden").prop("value",rtype);
		var url = "${g.createLink(controller:'person',action:'jq_export_learners')}?rtype=" + rtype + "&save=" + save + "&module.id=" + $("#moduleid").val() + "&startDate_Completed=" + $("#start-date").prop("value") + "&endDate_Completed=" + $("#end-date").prop("value") + "&learnerid=" + $("#learnerid").prop("value") 
		console.log(url) 
		document.location.href = url
		return false
	}
		function afterSubmit(){

		}
		function onSuccess(data){
			var resultDiv = $("#resultsDiv");
			var resultbody = $("#resultbody");

			var rows = "<tr><td>Done exporting!!!</td></tr>";
			$.each(data,function(index,item){
				
			});
			resultbody.html( rows);
			if(data.length == 0){				
				resultbody.html("<div style='text-align:center;padding:5px'><b>No data found to export!</b></div>");
			}
			resultDiv.show();
		}
		function onFailure(data){
			alert("Failed to save")
			$("#resultsDiv").html("Failed to save!  " + data.message)
		}
		function onComplete(){
			alert("completed!")
		}

			$(document).ready(function() {	
				
				var today=new Date();
				 $( "#start-date" ).datepicker({
					 dateFormat: "dd-M-yy",
						maxDate:"+0",
						defaultDate: new Date(today.getFullYear(), today.getMonth()-6, today.getDate()),
						onSelect: function(dateText, inst) {
						      // var actualDate = new Date(dateText);
						      // var newDate = new Date(actualDate.getFullYear(), actualDate.getMonth(), actualDate.getDate()+1);
						      //  $('#end-date').datepicker('option', 'minDate', newDate );
						  }
					 });
				 $( "#end-date" ).datepicker({
					 	dateFormat: "dd-M-yy",
						maxDate:"+0",
						defaultDate: new Date(),
						onSelect: function(dateText, inst) {
							//make sure that start-date is less that end-date  
						  }
					 });
			});  
		</script>		
	</body>
</html>
