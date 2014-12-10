<%@page import="com.cland.elearning.LearningMode"%>
<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"
	value="${message(code: 'module.name', default: moduleInstance.toString())}" />
<title><g:appTitle title=""><g:message code="default.show.label" args="[entityName]" /></g:appTitle></title>
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
		maingrid_list_url : "../jq_list_submodule",
		maingrid_edit_url : "../jq_edit_submodule",
		maingrid_id		: "submodule_list",
		maingrid_id_pager : "submodule_list_pager",
			
		subgrid_list_url :  "../jq_list_exam",
		subgrid_edit_url :  "../jq_edit_exam",
		submod_types :"${com.cland.elearning.LearningMode.stringKeyValuePair()}",
		states : "Active:Active;Inactive:Inactive",
		operands : "Divide:Divide;Multiply:Multiply;Subtract:Subtract;Add:Add",		
		thisId : ${params.id}
	}
</script>
</head>

<body>
	<z:window style="padding:5px">
		<div class="bread-crump">
			<span class="r-arrow"></span>
			<g:link controller="module" action="list">Modules</g:link>
			<span class="r-arrow"></span>			
			<span class="current-crump">
				Module: ${moduleInstance.name }
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
			<b>&raquo;</b>Module
		</legend>
		<h1>
			&nbsp;<g:fieldValue bean="${moduleInstance}" field="name" />
		</h1>
		<div class="content" style="width:100%;">
		<div style="float:right;margin-right:20px;width:40%;">
			<table class="dataTable">
			<tr><th></th><th>Max Mark</th><th>Weight</th></tr>
			<tr><td><label>CMA</label></td><td><span id="cma_mark">${moduleInstance.totalMaxMark(LearningMode.CMA.toString())}</span></td><td><span id="cma_cont">${moduleInstance.totalWeight(LearningMode.CMA.toString())}</span></td></tr>
			<tr><td><label>PAX</label></td><td><span id="pax_mark">${moduleInstance.totalMaxMark(LearningMode.PAX.toString())}</span></td><td><span id="pax_cont">${moduleInstance.totalWeight(LearningMode.PAX.toString())}</span></td></tr>
			<tr><td><label>TMA</label></td><td><span id="tma_mark">${moduleInstance.totalMaxMark(LearningMode.TMA.toString())}</span></td><td><span id="tma_cont">${moduleInstance.totalWeight(LearningMode.TMA.toString())}</span></td></tr>
			<tr><td><label>ASS</label></td><td><span id="ass_mark">${moduleInstance.totalMaxMark(LearningMode.ASS.toString())}</span></td><td><span id="ass_cont">${moduleInstance.totalWeight(LearningMode.ASS.toString())}</span></td></tr>
			<tr><td><b>TOTALS</b></td><td><b>${moduleInstance.totalMaxMark()}</b></td><td><b>${(moduleInstance.totalWeight()) * 100} %</b></td></tr>
			</table>
		</div>
			<g:fieldValue bean="${moduleInstance}" field="description" /><br/>
			<b>Duration:</b> ${moduleInstance?.duration} ${moduleInstance?.durationUnit}<br/>
			<b>Certification expires:</b> ${moduleInstance?.valid} ${moduleInstance?.validUnit}
		</div>
		
	</fieldset>
	<sec:ifAnyGranted roles="ADMIN,TUTOR">
		<g:form>
			<fieldset class="buttons">
				<g:hiddenField name="id" value="${moduleInstance?.id}" />
				<g:link class="edit" style="float:right" action="edit" id="${moduleInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>					
			</fieldset>
		</g:form>
	</sec:ifAnyGranted>
	<div id="myGrid" style="padding: 5px;">
		<!-- table tag will hold our grid -->
		<table id="submodule_list" class="scroll jqTable"></table>
		<!-- pager will hold our paginator -->
		<div id="submodule_list_pager" class="scroll"
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
         var gr = $("#submodule_list").jqGrid('getGridParam','selrow');
         if( gr != null )
           $("#submodule_list").jqGrid('editGridRow',gr,
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
                        my: "center"
                    });
                };
                
    jQuery("#" + cland_params.maingrid_id).jqGrid({
      url:cland_params.maingrid_list_url,
      editurl:cland_params.maingrid_edit_url,
      autowidth: true,
      height:"100%",
      datatype: "json",
      colNames:['Sub Module Name','Type','Description','id','Actions',"Module Id"],
      colModel:[
        {name:'name', editable:true,editrules:{required:true}},
        {name:'type',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.submod_types}},
        {name:'description', editable:true,editrules:{required:true}},        
        {name:'id',hidden:true},
        {name:'act',index:'act', width:180,sortable:false,search:false},
        {name:'modid',index:'modid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
     ],
     rowNum:50,
    // rowList:[10,20,30,40,50],
     multiselect: false,
    pager: jQuery('#' + cland_params.maingrid_id_pager),
    viewrecords: true,
    gridview: true,
    postData:{modid:cland_params.thisId},
    cellEdit:false,
    cellsubmit: 'remote',
   	cellurl:cland_params.maingrid_edit_url,
   subGridRowExpanded: function(subgrid_id,row_id){
	   //subgrid_id: id of the div tag created within table data
	   //id of this element is combination of the "sg_" + id of the row
	   var subgrid_table_id, pager_id;
	   subgrid_table_id = subgrid_id + "_t";
	   pager_id = "p_" + subgrid_table_id;
	   console.log(subgrid_table_id + " - " + pager_id);
	   
	   $("#"+subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
	   jQuery("#"+subgrid_table_id).jqGrid({
		   url:cland_params.subgrid_list_url,
		   editurl:cland_params.subgrid_edit_url,
		   datatype:"json",
		   colNames:['Exam Number','Max Mark','Weight','Factor','Factor Operand','Status','id',' <input type="button" name="Add_Exam" onClick="addRow(\''+row_id+'\',\''+subgrid_table_id+'\');" id="exam_add" value="Add Exam"/>','Sub Id'],
		   colModel:[ {name:'testNumber', editable:true,width:100,editrules:{required:true,integer:true},align:'left',formoptions:{rowpos: 1, colpos: 1}},
		              {name:'maxMark', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false,formoptions:{rowpos: 2, colpos: 1}},
		              {name:'weight', editable:true,width:100,editrules:{required:true,double:true},align:'right',sortable:false,formoptions:{rowpos: 1, colpos: 2}},
		              {name:'factor', editable:true,width:100,editrules:{required:true,double:true},align:'right',sortable:false,formoptions:{rowpos: 2, colpos: 2}},
		              {name:'factorOperand',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.operands},formoptions:{rowpos: 3, colpos: 2}},
		              {name:'status',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.states},formoptions:{rowpos: 3, colpos: 1}},
		              {name:'id',hidden:true},
		              {name:'subact',index:'subact', width:90,sortable:false,search:false,align:'center'},
		              {name:'subid',index:'subid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:row_id}}
		              ],		              
		   //rowNum:2,
		   pager:pager_id,
		   sortname:'testNumber',
		   sortorder:'asc',
		   height:"100%",
		   autowidth:true,
		   footerrow : true,
		   gridComplete: function(){
			   thisgrid = jQuery("#" + subgrid_table_id);
			   var subids = thisgrid.jqGrid('getDataIDs');
			   for(var i=0;i<subids.length;i++){
				   	var _id =subids[i];
				   	de = "<input style='height:22px;' type='button' value='Delete' onclick=\"deleteGridRow('"+_id+"','"+subgrid_table_id+"');\" />";		            
		            thisgrid.jqGrid('setRowData',_id,{subact: de}); //be+se+ce+de forall actions
				}
			  
		        var maxMarkTotal=  $(this).jqGrid('getCol', 'maxMark', false, 'sum');		      
		        var weightTotal=  $(this).jqGrid('getCol', 'weight', false, 'sum');
		        weightTotal=(weightTotal*100).toFixed(0)
		        $(this).jqGrid('footerData', 'set', {testNumber:'Total:', weight: weightTotal + "%",maxMark: maxMarkTotal});
		        
		        
			},  
		   cellEdit:true,
		    cellsubmit: 'remote',
		   	cellurl:cland_params.subgrid_edit_url,
		   postData:{id:row_id}           
		   });
	  // jQuery("#"+subgrid_table_id).setGridParam({id:row_id})''
	   jQuery("#"+subgrid_table_id).jqGrid('navGrid'),"#"+pager_id,{edit:false,add:false,del:false}
	   },
	subGridRowColapsed: function(subgrid_id,row_id){
		//This functiona is called before removing the data
		//var subgrid_table_id;
		//subgrid_table_id = subgrid_id+"_t";
		//jQuery("#"+subgrid_table_id).remove();
		},   
    subGrid : true,
    gridComplete: function(){ 
        var ids = jQuery("#" + cland_params.maingrid_id).jqGrid('getDataIDs'); 
        
        for(var i=0;i < ids.length;i++)
            { 
            	var cl = ids[i]; 
	            be = "<input style='height:22px;width:42px;' type='button' value='Edit' onclick=\"jQuery('#submodule_list').editRow('"+cl+"');\" />"; 
	            se = "<input style='height:22px;width:42px;' type='button' value='Save' onclick=\"jQuery('#submodule_list').saveRow('"+cl+"');\" />"; 
	            ce = "<input style='height:22px;width:42px;' type='button' value='Cancel' onclick=\"jQuery('#submodule_list').restoreRow('"+cl+"');clearSelection();\" />"; 
	            de = "<input style='height:22px;width:82px;' type='button' value='Delete' onclick=\"deleteRow('"+cl+"');\" />";
	            
	            jQuery("#" + cland_params.maingrid_id).jqGrid('setRowData',ids[i],{act:be+se+ce+de}); //be+se+ce+de forall actions 
            }
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
      var success = true;
  
      var json = eval('(' + response.responseText + ')');
      var message = json.message;

      if(json.state == 'FAIL') {
          success = false;
      } else {
        $('#message').html(message);
        $('#message').show().fadeOut(10000);
      }
      
      var new_id = json.id
      return [success,message,new_id];
  }
  function deleteGridRow(id,grid_id){
	  grid = $("#" + grid_id)
	  if (id!= null) grid.jqGrid('setSelection',id);
	  var gr = grid.jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
      
      if( gr != null && gr != "" )
        grid.jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent,height:300,width:700});
      else
        alert("Please Select Row to delete!");
	  }
  function deleteRow(id){	  
	  if (id!= null) jQuery('#submodule_list').jqGrid('setSelection',id);
	  var gr = $("#submodule_list").jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
      
      if( gr != null && gr != "" )
        $("#submodule_list").jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent,height:300,width:700});
      else
        alert("Please Select Row to delete!");
  }
  function addRow(row_id, subgrid_id, caption){
	 grid = $("#" + subgrid_id)
	 //grid.setGridParam({ postData: { id: row_id} });
	 grid.jqGrid("editGridRow",
              "new",
              {addCaption:caption, afterSubmit:afterSubmitEvent,savekey:[true,13],height:350,width:750}
      );
	}
  function clearSelection(){jQuery('#submodule_list').jqGrid('resetSelection'); }
</script>

</body>
</html>