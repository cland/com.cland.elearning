
<%@ page import="com.cland.elearning.Certificate" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'certificate.label', default: 'Certificate')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
		<link rel="stylesheet"	href="${resource(dir:'css',file:'ui.jqgrid.css')}" />
		<link rel="stylesheet"	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
		<g:javascript library="jquery11" />
		<g:javascript library="jqueryuilatest" />
		<g:javascript library="jqueryfiledownload" />		
<%--		<g:javascript library="multidatepicker" />--%>
		<style type="text/css">
		.buttons .export,.buttons .search,.search{
			background-position: 0.7em center;
			background-repeat: no-repeat;
			text-indent: 25px;
		}
		.buttons .search,.search{
			background-image: url('${fam.icon(name: 'magnifier')}');
		}
		.buttons .export {
			background-image: url('${fam.icon(name: 'page_excel')}');			
		}		
		</style>
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
						<table class="dataTable">
						<tr><td>Select Module:<span class="required-indicator">*</span></td><td>
							<g:select name="module.id" from="${com.cland.elearning.Module.list()}" optionKey="id" value="${subModuleInstance?.module?.id}"  required="" noSelection="['':'-select module-']"/>
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
								<g:textField name="learnerId" value=""/>
							</td>
						</tr>
					</table>
					<g:submitButton name="search" class="search" value="Search" />
					</fieldset>

				</g:formRemote>	
			</div>
			
			<div id="resultsDiv" style="display:none;border:solid 1px #000;background:#fff;padding:5px;margin-bottom:20px;">
			
			<g:formRemote class="certificateListForm" id="certform" name="certform"  url="[controller:'certificate',action:'generateList']" 	
				before="beforeSubmitCertForm()" 	
				onSuccess="onCertSuccess(data)"
				onFailure="onCertFailure(data)">
				<g:hiddenField name="button" id="clicked-button" value="" />
				<fieldset class="buttons">
					<g:submitButton id="generate_btn" name="generate" class="edit" onclick="setClickedButton('generate');" value="Generate Certificates" />
					<g:submitButton id="export_btn" name="export" class="export" onclick="exportCertificates('export');return false" value="Export Certificates" />
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
	function setClickedButton(button){
			$("#clicked-button").prop("value",button);
			return true;
		}
	function exportCertificates(button){
		$("#clicked-button").prop("value",button);
		var _form = $("#certform");
        $.fileDownload(_form.prop('action'), {
            preparingMessageHtml: "We are preparing your report, please wait...",
            failMessageHtml: "There was a problem generating your report, please try again.",
            httpMethod: "POST",            
            data: _form.serialize()
        }).done(function(){
            console.log("Success...");
          	 $(".ui-dialog-content").html("<span>Done!</span>");
           });
        return false; //this is critical to stop the click event which will trigger a normal file download!
	}
		function afterSubmit(){

		}
		function onSuccess(data){
			var resultDiv = $("#resultsDiv");
			var resultbody = $("#resultbody");

			var rows = "";
			$.each(data,function(index,item){
				var _certno = (item.certificate.certno?item.certificate.certno:"--");
				var _payment_status = (item.payment_status?item.payment_status:"--");
				rows = rows + "<tr><td><input class='report-checkbox' type='checkbox' name='result' value='" + item.id + "'/></td><td>" + item.person_name + "</td><td>" + item.person_studentno + "</td><td>" 
						+ item.module.name + "</td><td id='cert-" + item.id + "' style='text-align:center;'>" + _certno + "</td><td style='text-align:center;'>" + item.result + "</td><td style='text-align:center;'>" + item.status + "</td><td style='text-align:center;'>" + _payment_status + "</td><td>" + item.end_date + "</td></tr>";
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
		function beforeSubmitCertForm(){
			//var c = confirm('sure?')
			//return c;
		}
			$(document).ready(function() {	
				$("#export_btn").on("click", "form.certificateListForm", function() {
					$("#clicked-button").prop("value",'export');
			        $.fileDownload($(this).attr('href'), {
			            preparingMessageHtml: "We are preparing your report, please wait...",
			            failMessageHtml: "There was a problem generating your report, please try again."
			        });
			        return false; //this is critical to stop the click event which will trigger a normal file download!
			    });
				//cland_datepickers.init_datepicker_single_standard("#start-date","dd-M-yy","-5y","+0","+0");
				//cland_datepickers.init_datepicker_single_standard("#enddate1","dd-M-yy","-4y","+0","+0");
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
