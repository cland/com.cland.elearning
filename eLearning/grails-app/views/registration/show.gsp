<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
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
		thisId : ${params.id},
		maingrid_list_url : "../jq_list_results?regId=" + ${params.id},
		maingrid_edit_url : "../jq_edit_results?regId=" + ${params.id},
		maingrid_id		: "results_summary_list",
		maingrid_id_pager : "results_summary_list_pager",
			
		subgrid_list_url :  "../jq_list_exam",
		subgrid_edit_url :  "../jq_edit_exam",
		submod_types :"Assignment:Assignment;Computer Marked Asessment:CMA;Practical Attendance Exercises:PAE;Tutor Marked Assessment:TMA",
		states : "Not Started:Not Started;In Progress:In Progress;Completed:Completed",		
		result_types: "Pass:Pass;Fail:Fail;None:None",
		operands : "Divide:Divide;Multiply:Multiply;Subtract:Subtract;Add:Add"
	}
</script>
</head>

<body>
	<z:window style="padding:5px">
		<div class="bread-crump">
			<span class="r-arrow"></span>
			<g:link controller="course" action="list">Course</g:link>			
			<span class="r-arrow"></span>			
			<span class="current-crump">
				Register and results for: ${registrationInstance.learner.toString() }
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
			<b>&raquo;</b> Course Register
		</legend>
		<h1>
			&nbsp;${registrationInstance.learner.toString()}
		</h1>
		<div class="content">
			<b>Course:</b> ${registrationInstance.course.name} (${registrationInstance.course.code})<br/>
			<b>Tutor:</b> ${registrationInstance.tutor.toString()}<br/>
			<b>Registration date:</b> ${registrationInstance.regDate.format("dd MMM yyyy")}
		</div>
	</fieldset>
<div id="message"></div>
	<div id="myGrid" style="padding: 5px;">
		<!-- table tag will hold our grid -->
		<table id="results_summary_list" class="scroll jqTable"></table>
		<!-- pager will hold our paginator -->
		<div id="results_summary_list_pager" class="scroll"
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
      colNames:['Module','Result','Status','Cert Number','id','Actions'],
      colModel:[
		{name:'module', editable:false},	
		{name:'result',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.result_types}},
        {name:'status',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.states}},
        {name:'certNumber', editable:true},        
        {name:'id',hidden:true},
        {name:'act',index:'act', width:180,sortable:false,search:false}
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
	            be = "<input style='height:22px;width:42px;' type='button' value='Edit' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').editRow('"+cl+"');\" />"; 
	            se = "<input style='height:22px;width:42px;' type='button' value='Save' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').saveRow('"+cl+"',afterSubmitEvent);\" />"; 
	            ce = "<input style='height:22px;width:44px;' type='button' value='Cancel' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').restoreRow('"+cl+"');clearSelection();\" />"; 
	            
	            jQuery("#" + cland_params.maingrid_id).jqGrid('setRowData',ids[i],{act:be+se+ce}); //be+se+ce+de forall actions 
            }
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
</script>

</body>
</html>