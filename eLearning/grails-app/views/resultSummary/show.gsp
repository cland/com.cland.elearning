<%@page import="com.cland.elearning.LearningMode"%>
<g:set var="cma_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == LearningMode.CMA.toString()}}"/>
<g:set var="pax_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == LearningMode.PAX.toString()}}"/>
<g:set var="tma_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == LearningMode.TMA.toString()}}"/>
<g:set var="ass_results" value="${resultSummaryInstance?.results?.findAll{it?.subModule?.type == LearningMode.ASS.toString()}}"/>
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
<g:javascript library="jquerygridlocale" />
<g:javascript library="jquerygrid" />
<style>
.buttons .printview {
	background-image: url('${fam.icon(name: 'printer')}');
	background-position: 0.7em center;
	background-repeat: no-repeat;
	text-indent: 25px;
}
.results-table {
	font-size:9pt;
}
tr.total-row {background:#ccc;border-top:solid 1px #000; font-weight:bold}
.ui-jqgrid .ui-jqgrid-htable th div {
	height: auto;
	overflow: hidden;
	padding-right: 4px;
	padding-top: 2px;
	position: relative;
	vertical-align: text-top;
	white-space: normal !important;
}
.ui-jqgrid .ui-jqgrid-htable th {
    height: 32px;
    padding: 0 2px;
}
.ui-jqgrid .ui-jqgrid-view {
    font-size: 12px; 
}
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
</script>
</head>

<body>
	<z:window style="padding:5px">
		<div class="bread-crump">
			<span class="r-arrow"></span>
			<g:link controller="course" action="show" id="${resultSummaryInstance.register.course.id }">Course: ${resultSummaryInstance.register.course.name }</g:link>
			<span class="r-arrow"></span>
			<g:link controller="registration" action="show" id="${resultSummaryInstance.register.id }">Course Results</g:link>
			<span class="r-arrow"></span>			
			<span class="current-crump">
				Module Results for: <g:link controller="person" action="show" id="${resultSummaryInstance.register.learner.id }">${resultSummaryInstance.register.learner.toString() }</g:link>
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

	<fieldset>
		<legend>
			<b>&raquo;</b> Module Results
		</legend>
		<h1>
			&nbsp;${resultSummaryInstance.register.learner.toString()}
		</h1>
		<div class="content">
			<div style="font-size:10pt;line-height: 1.8em;">
			<table class="dataTable">
				<tr>
					<td><b>Module:</b></td><td>${resultSummaryInstance.module.name}</td>
					<td><b>Result:</b></td><td>${resultSummaryInstance.result} </td>
					<td><b>Date Started:</b> </td><td>${resultSummaryInstance?.startDate?.format("dd-MMM-yyyy")}</td>
				</tr>
				<tr>
					<td><b>Course:</b></td><td>${resultSummaryInstance.register.course.name} (${resultSummaryInstance.register.course.code})</td>
					<td><b>Status:</b> </td>
					<td>${resultSummaryInstance.status}					
						<g:if test="${resultSummaryInstance?.isExpired()}">
							<br/><span style="background:red;padding: 2px;color:yellow;">Module overdue! It's been ${resultSummaryInstance.getCurrentDuration() } ${resultSummaryInstance?.module?.durationUnit } since module was started.</span>
						</g:if>
						<g:if test="${resultSummaryInstance?.isCertExpired()}">
							<br/><span style="background:red;padding: 2px;color:yellow;">Module certificate has expired! It's been ${resultSummaryInstance.getCurrentCertDuration() } ${resultSummaryInstance?.module?.validUnit } since module was complete and certificate issued.</span>
						</g:if>
					</td>
					<td><b>Date Completed:</b> </td><td>${resultSummaryInstance?.endDate?.format("dd-MMM-yyyy")}</td>
				</tr>
				<tr>
					<td><b>Tutor:</b></td><td> ${resultSummaryInstance.tutor.toString()}</td>
					<td><b>Payment Status:</b></td><td>${resultSummaryInstance?.paymentStatus}</td>
					<td><b>Certificate No.:</b> </td>
					<td>
						<span id="certno">${resultSummaryInstance?.certificate?.certno}</span><span id="certno-msg"></span>
						<sec:ifAnyGranted roles="ADMIN,TUTOR">
							<g:if test="${ resultSummaryInstance?.certificate?.certno == null & (resultSummaryInstance?.result == 'Pass' | resultSummaryInstance?.result == 'Exempt')}">	
								<span id="certno-link">			
									<g:remoteLink action="generate" controller="certificate" params="${['resultSummary.id':resultSummaryInstance?.id ]}"
										onSuccess='onCertSuccess(data)'
										onLoading='onCertLoading()'
										onFailure='onCertFailure(data)'>
										Generate Certificate Number
									</g:remoteLink>
								</span>
							</g:if>
						</sec:ifAnyGranted>
					</td>
				</tr>
				</table>
			</div>
			<div style="margin-right:-20px;border:solid 1px #ccc;border-top:none;">
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
						<td style="border-right:double 1px #000;">
							<g:render template="result_table" bean="${ass_results }" var="results" model="[type:'ASS']"></g:render>
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
		</div>
	</fieldset>
		<sec:ifAnyGranted roles="ADMIN,TUTOR">
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${resultSummaryInstance?.id}" />								
					<g:link class="edit" style="float:right" action="edit" id="${resultSummaryInstance?.id}"><g:message code="default.button.edit.label" default="Edit Results" /></g:link>
					<g:link class="printview" style="float:right" action="print" id="${resultSummaryInstance?.id}"><g:message code="default.button.printview.label" default="Print Version" /></g:link>
				</fieldset>
			</g:form>
			</sec:ifAnyGranted>
<div id="message"></div>
	<div id="myGrid" style="padding: 5px;">
		<!-- table tag will hold our grid -->
		<table id="examresults_list" class="scroll jqTable"></table>
		<!-- pager will hold our paginator -->
		<div id="examresults_list_pager" class="scroll"
			style="text-align: center;"></div>
	</div>

	<script type="text/javascript">// <![CDATA[
  /* when the page has finished loading.. execute the follow */
  $(document).ready(function () {
	  //field set functions
	  $("legend").click(function(){		 
		$(this).children("b").toggleClass("collapsed");
		$(this).nextAll("div.content").slideToggle(500);
	});
		$("fieldset.topleft1 legend").children("b").addClass("collapsed");
		$("fieldset.topleft1 legend").nextAll("div.content").hide();
		$("fieldset.topleft2 legend").children("b").addClass("collapsed");
		$("fieldset.topleft2 legend").nextAll("div.content").hide();
	// set on click events for non toolbar buttons
      $("#btnAdd").click(function(){
    	  //addGridRow("<row_id>", "<grid_id>", "New Record")        
      });

      $("#btnEdit").click(function(){
         var gr = $("#" + cland_params.maingrid_list_url).jqGrid('getGridParam','selrow');
         if( gr != null )
           $("#" + cland_params.maingrid_list_url).jqGrid('editGridRow',gr,
           {closeAfterEdit:true,
            afterSubmit:afterSubmitEvent
           });
         else
           alert("Please Select Row");
      });

      $("#btnDelete").click(function(){
          deleteRow(null); //delete the selected row         
      });

      //initialize the grid
      centerForm = function ($form) {
                    $form.closest('div.ui-jqdialog').position({
                        my: "center",
                        of: grid.closest('div.ui-jqgrid')
                    });
                };
                
    jQuery("#" + cland_params.maingrid_id).jqGrid({
      url:cland_params.maingrid_list_url,
      editurl:cland_params.maingrid_edit_url,
      autowidth: true,
      height:"100%",
      datatype: "json",
      colNames:['Type','Exam No.','Mark','Max Mark','% Mark','% Contribution','id','Actions'],
      colModel:[
		{name:'submodule', editable:false,editrules:{required:true}},
		{name:'examname', editable:false,editrules:{required:true}},
        {name:'mark', editable:true,editrules:{required:true}},
        {name:'maxMark', editable:true,editrules:{required:true}},
        {name:'percentMark', editable:false},     
        {name:'contributionMark', editable:false},   
        {name:'id',hidden:true},
        {name:'act',index:'act', width:200,sortable:false,search:false}
        //{name:'resultSumid',index:'resultSumid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
     ],
     rowNum:20,
     rowList:[10,20,30,40,50,100],
     multiselect: false,
    pager: jQuery('#' + cland_params.maingrid_id_pager),
    viewrecords: true,
    footerrow : true,
    gridview: true,
    afterSubmit:afterSubmitEvent,
   // postData:{modid:cland_params.thisid},
    cellEdit:false,
    cellsubmit: 'remote',
   	cellurl:cland_params.maingrid_edit_url,  
    subGrid :false,
    gridComplete: function(){ 
        var ids = jQuery("#" + cland_params.maingrid_id).jqGrid('getDataIDs'); 
        
        for(var i=0;i < ids.length;i++)
            { 
            	var cl = ids[i]; 
	            be = "<input class='edit' style='height:22px;width:42px;' type='button' value='Edit' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').editRow('"+cl+"');\" />"; 
	            se = "<input class='edit' style='height:22px;width:42px;' type='button' value='Save' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').saveRow('"+cl+"',afterSubmitEvent);\" />"; 
	            ce = "<input class='edit' style='height:22px;width:44px;' type='button' value='Cancel' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').restoreRow('"+cl+"');clearSelection();\" />"; 
	            de = "<input class='edit' style='height:22px;width:44px;' type='button' value='Delete' onclick=\"deleteRow('"+cl+"');\" />";
	            
	            jQuery("#" + cland_params.maingrid_id).jqGrid('setRowData',ids[i],{act:be+se+ce+de}); //be+se+ce+de forall actions 
            }
        if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();

        var markTotal=  $(this).jqGrid('getCol', 'mark', false, 'sum');     
        var contributionTotal=  $(this).jqGrid('getCol', 'contributionMark', false, 'sum');  
        var maxMarkTotal=  $(this).jqGrid('getCol', 'maxMark', false, 'sum');        
        var pmark = (markTotal/maxMarkTotal)*100
        $(this).jqGrid('footerData', 'set', {submodule:'Total:',maxMark: maxMarkTotal,mark: markTotal, percentMark: pmark.toFixed(1) + "%",contributionMark:contributionTotal.toFixed(1) + "%"});
        
    } 
    }).navGrid('#' + cland_params.maingrid_id_pager,
            {add:false,edit:false,del:false,search:false,refresh:true}, // which buttons to show?
            {closeAfterEdit:true, afterSubmit:afterSubmitEvent,savekey:[true,13],afterShowForm: centerForm},  // edit options
            {addCaption:'New Record',afterSubmit:afterSubmitEvent,savekey:[true,13],closeAfterEdit:false},  // add options            
           {afterShowForm: centerForm}          // delete options
        );
    //$("#" + cland_params.maingrid_id).jqGrid('filterToolbar',{autosearch:true});
   
  });  
// ]]>
  function afterSubmitEvent(response, postdata) {	
	  $("#" + cland_params.maingrid_id).trigger('reloadGrid')
      var success = true;
  	  var display = $('#message'); 
      var json = eval('(' + response.responseText + ')');
      var message = json.message;

      if(json.state == 'FAIL') {
          success = false;
          display.addClass("errors")
      }else{
    	  display.addClass("message")
          }      
        display.html(message);
        display.show().fadeOut(10000);            
      var new_id = json.id
      return success; //[success,message,new_id];
  }
  function deleteGridRow(id,grid_id){
	  grid = $("#" + grid_id)
	  if (id!= null) grid.jqGrid('setSelection',id);
	  var gr = grid.jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
      
      if( gr != null && gr != "" )
        grid.jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent});
      else
        alert("Please Select Row to delete!");
	  }
  function deleteRow(id){	  
	  if (id!= null) jQuery('#' + cland_params.maingrid_list_url).jqGrid('setSelection',id);
	  var gr = $("#submodule_list").jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
      
      if( gr != null && gr != "" )
        $("#submodule_list").jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent});
      else
        alert("Please Select Row to delete!");
  }
  function addRow(row_id, subgrid_id, caption){
	 grid = $("#" + subgrid_id)
	 //grid.setGridParam({ postData: { id: row_id} });
	 grid.jqGrid("editGridRow",
              "new",
              {addCaption:caption, afterSubmit:afterSubmitEvent,savekey:[true,13]}
      );
	}
  function clearSelection(){jQuery('#' + cland_params.maingrid_id).jqGrid('resetSelection'); }

  function onCertSuccess(data){
	  $("#certno-msg").html("")
	  var _certno = (data.certno?data.certno:"--");
	  $("#certno").html(_certno)
	 }
  function onCertFailure(){
	  $("#certno-link").show()
	  $("#certno-msg").html("Error: Failed to generate number!")
	  }
  function onCertLoading(){
	  $("#certno-link").hide()
	  $("#certno-msg").html("Generting number...")
	  }
</script>

</body>
</html>