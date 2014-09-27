
<%@ page import="com.cland.elearning.Certificate" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'certificate.label', default: 'Certificate')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-certificate" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		
		<div id="list-certificate" class="content scaffold-list" role="main">
			<h1>CERTIFICATES PANEL</h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<div class="filter-div">
				
				<g:formRemote id="searchform" name="searchform"  url="[controller:'certificate',action:'search']" 
				after="afterSubmit"
				onSuccess="onSuccess(data)"
				onFailure="onFailure(data)"
				onComplete="onComplete">
					<fieldset class="form">
						<table>
						<tr><td>Select Module:</td><td>
							<g:select name="module.id" from="${com.cland.elearning.Module.list()}" optionKey="id" value="${subModuleInstance?.module?.id}"  />
						</td></tr>
						<tr><td>Start Date:</td><td></td></tr>
						<tr><td>End Date:</td><td></td></tr>
						<tr><td colspan=2>
							<g:checkBox name="certificate_available" value="yes"/><span class="checkbox-text">With Certificate</span><br/>
							<g:checkBox name="certificate_available" value="no"/><span class="checkbox-text">Without Certificate</span><br/>
						</td></tr>
					</table>
					<g:submitButton name="create" class="save" value="Search" />
					</fieldset>

				</g:formRemote>	
			</div>
			
			<div id="resultsDiv" style="display:none;border:solid 1px #000;background:#fff;padding:5px;margin-bottom:20px;">
			
			<g:formRemote id="certform" name="certform"  url="[controller:'certificate',action:'generateList']" 				
				onSuccess="onCertSuccess(data)"
				onFailure="onCertFailure(data)">
				<fieldset class="buttons">
					<g:submitButton name="create" class="save" value="Generate Certificates" />
				</fieldset>
				<div style="padding:10px;">
					<span class="action-link-label">Select: </span><span class="action-link" onclick="setAllCheckbox('.report-checkbox','true')"> All</span> | 
					<span class="action-link" onclick="setAllCheckbox('.report-checkbox','false')">None</span> 
				</div>
				<table class='dataTable'>
				<thead>
					<tr><th></th><th>Learner</th><th>Student No.</th><th>Module</th><th>Certificate No.</th><th>Result</th><th>Status</th><th>Payment Status</th><th>Date Completed</th></tr>
				</thead>
				
				<tbody id="resultbody">
				</tbody>
				</table>
			</g:formRemote>
			</div>
			
		</div>
<script>
		function afterSubmit(){

		}
		function onSuccess(data){
			var resultDiv = $("#resultsDiv");
			var resultbody = $("#resultbody");

			var rows = "";
			$.each(data,function(index,item){
				var _certno = (item.certificate.certno?item.certificate.certno:"--");
				rows = rows + "<tr><td><input class='report-checkbox' type='checkbox' name='result' value='" + item.id + "'/></td><td>" + item.person_name + "</td><td>" + item.person_studentno + "</td><td>" 
						+ item.module.name + "</td><td id='cert-" + item.id + "' style='text-align:center;'>" + _certno + "</td><td style='text-align:center;'>" + item.result + "</td><td style='text-align:center;'>" + item.status + "</td><td style='text-align:center;'>" + item.payment_status + "</td><td>" + item.end_date + "</td></tr>";
			});
			resultbody.html( rows);
			if(data.length == 0){				
				resultbody.html("<div style='text-align:center;padding:5px'><b>No data found!</b></div>");
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
		function onCertSuccess(data){
			$.each(data,function(index,item){
				//console.log(item.result.id + " - " + item.result.certno)
				$("#cert-" + item.result.id).html(item.result.certno)
			});
		}
		function onCertFailure(data){
			alert("Failed to generate certificates")			
		}
			$(document).ready(function() {		
				                
			});  
		</script>		
	</body>
</html>
