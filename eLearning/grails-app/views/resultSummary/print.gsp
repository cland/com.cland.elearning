
<g:set var="cma_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == 'Computer Marked Assessment'}}"/>
<g:set var="pax_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == 'Practical Attendance Exercises'}}"/>
<g:set var="tma_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == 'Tutor Marked Assessment'}}"/>
<g:set var="ass_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == 'Assignment'}}"/>
<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'resultSummary.name', default: resultSummaryInstance.toString())}" />
<title><g:message code="default.show.label" args="[entityName]" /></title>
<link rel="stylesheet"
	href="${resource(dir:'css',file:'ui.jqgrid.css')}" />
<link rel="stylesheet"
	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
<g:javascript library="jquerymin" />
<g:javascript library="jqueryuilatest" />

<style>
.print {
	background-image: url('${fam.icon(name: 'printer')}');
	background-position: 0.7em center;
	background-repeat: no-repeat;
	text-indent: 25px;
}
@media print {
	body {
		background: none;
		color: #000;
		margin: 0 auto;
		max-width: 1200px;
	}
	td {
	    line-height: 1.5em;
	    padding: 0.5em 0.6em;
	    vertical-align: top;
	}
	td {border-bottom:solid 1px #ccc;}
}
td {border-bottom:solid 1px #ccc;}
table.results_table tbody tr td.td-last  {}
table.results_table tbody tr.total-row {border-top:solid 2px #000;border-bottom:solid 2px #000;}

</style>
<script type="text/javascript">
var cland_params = {
		active_tab : function(){ if (${params.tab==null}) return 0; else return ${params.tab};},
		canEdit :${org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")},
		thisId : ${params.id},
		maingrid_list_url : "../jq_list_results?resultSumId=" + ${params.id},
		maingrid_edit_url : "../jq_edit_results?resultSumId=" + ${params.id},
		maingrid_id		: "examresults_list",
		maingrid_id_pager : "examresults_list_pager",
			
		subgrid_list_url :  "../jq_list_exam",
		subgrid_edit_url :  "../jq_edit_exam",
		submod_types :"Assignment:Assignment;Computer Marked Asessment:CMA;Practical Attendance Exercises:PAE;Tutor Marked Assessment:TMA",
		states : "Not Started:Not Started;In Progress:In Progress;Completed:Completed;Exempt:Exempt",
		operands : "Divide:Divide;Multiply:Multiply;Subtract:Subtract;Add:Add"
	}

function printPage() {
	    window.print();
	}

</script>
</head>

<body >
	<div id="container" style="background:#fff;" >
	<div style="float:right;"><button class="no-print print" onclick="printPage()" style="font-size:10pt;padding:5px;"><b>Print this page</b></button></div>
	<table style="" class="info-table">
		<tr><td colspan="4">
			<div style="text-align:center;">
				<b>${resultSummaryInstance.register.course.name} (${resultSummaryInstance.register.course.code})</b><br>
				${resultSummaryInstance.module.name} <br/>
				${resultSummaryInstance.register.learner.toString()}
			</div>
		</td></tr>
		<tr><td><b>Status:</b> </td><td>${resultSummaryInstance.status}</td><td><b>Tutor:</b></td><td>${resultSummaryInstance.tutor.toString()}</td></tr>
		<tr><td><b>Date Started:</b> </td><td> ${resultSummaryInstance?.startDate?.format("dd-MMM-yyyy")}</td><td><b>Payment Status:</b></td><td>${resultSummaryInstance?.paymentStatus}</td></tr>
		<tr><td><b>Date Completed:</b> </td><td>${resultSummaryInstance?.endDate?.format("dd-MMM-yyyy")}</td><td><b>Certificate No:</b></td><td>${resultSummaryInstance?.certificate?.certno}</td></tr>
	</table>
	
	<table style="" class="results-table">
		<tr>
			<td style="border-right:double 1px #000;">
				<g:render template="result_table" bean="${cma_results }" var="results" model="[type:'CMA']"></g:render>
			</td>
			<td style="border-right:double 1px #000;">
				<g:render template="result_table" bean="${pax_results }" var="results" model="[type:'PAX']"></g:render>
			</td>
			<td style="border-right:double 1px #000;">
				<g:render template="result_table" bean="${tma_results }" var="results" model="[type:'TMA']"></g:render>
			</td>
			<td>
		
		<table>
			<tr><th colspan="2">GRAND TOTALS</th></tr>
			<tr><td>MARK:</td><td>${resultSummaryInstance.totalMark()}</td></tr>
			<tr><td>OUT OF:</td><td>${resultSummaryInstance.totalMaxMark()}</td></tr>
			<tr><td>PERCENT:</td><td> <b>${String.format( '%.1f', resultSummaryInstance.totalPercentMark())}</b>%</td></tr>
		</table>
		</td>
		</tr>
	</table>
	</div>
</body>
</html>