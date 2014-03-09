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
			<g:link controller="registration" action="show" id="${resultSummaryInstance.register.id }">Course Register</g:link>
			<span class="r-arrow"></span>			
			<span class="current-crump">
				Module Result for: <g:link controller="person" action="show" id="${resultSummaryInstance.register.learner.id }">${resultSummaryInstance.register.learner.toString() }</g:link>
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
			<b>&raquo;</b> Module Result
		</legend>
		<h1>
			&nbsp;${resultSummaryInstance.register.learner.toString()}
		</h1>
		<div class="content">
			<b>Course:</b> ${resultSummaryInstance.register.course.name} (${resultSummaryInstance.register.course.code})<br/>
			<b>Module:</b> ${resultSummaryInstance.module.name} <br/>
			<b>Result:</b> ${resultSummaryInstance.result} [ Mark: <b>${resultSummaryInstance.totalMark()}</b> out of <b>${resultSummaryInstance.totalMaxMark()}</b> - PERCENT: <b>${String.format( '%.1f', resultSummaryInstance.totalPercentMark())}</b>% ]<br/>			
			<b>Status:</b> ${resultSummaryInstance.status}
			<g:if test="${resultSummaryInstance?.isExpired()}">
				<span style="background:red;padding: 2px;color:yellow;">Module overdue! It's been ${resultSummaryInstance.getCurrentDuration() } ${resultSummaryInstance?.module?.durationUnit } since module was started.</span>
			</g:if>
			<br/>
			<b>Date Started:</b> ${resultSummaryInstance?.startDate?.format("dd-MMM-yyyy")}<br/>
			<b>Date Completed:</b> ${resultSummaryInstance?.endDate?.format("dd-MMM-yyyy")}<br/>
			<b>Tutor:</b> ${resultSummaryInstance.tutor.toString()}
		</div>
	</fieldset>
		<sec:ifAnyGranted roles="ADMIN,TUTOR">
<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${resultSummaryInstance?.id}" />
					<g:link class="edit" style="float:right" action="edit" id="${resultSummaryInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>					
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
        {name:'maxMark', editable:false},
        {name:'percentMark', editable:false},     
        {name:'tutor', editable:false},   
        {name:'id',hidden:true},
        {name:'act',index:'act', width:200,sortable:false,search:false}
        //{name:'resultSumid',index:'resultSumid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
     ],
     rowNum:10,
     rowList:[10,20,30,40,50],
     multiselect: false,
    pager: jQuery('#' + cland_params.maingrid_id_pager),
    viewrecords: true,
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
    } 
    }).navGrid('#' + cland_params.maingrid_id_pager,
            {add:true,edit:false,del:false,search:false,refresh:true}, // which buttons to show?
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
</script>

</body>
</html>