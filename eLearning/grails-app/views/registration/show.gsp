<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
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
		active_tab : function(){ if (${params.tab==null}) return 0; else return ${params.tab};},
		canEdit :${org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")},
		isAdmin: ${org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils.ifAnyGranted("ADMIN")},
		thisId : ${params.id},
		maingrid_list_url : "../jq_list_results?regId=" + ${params.id},
		maingrid_edit_url : "../jq_edit_results?regId=" + ${params.id},
		maingrid_id		: "results_summary_list",
		maingrid_id_pager : "results_summary_list_pager",
			
		subgrid_list_url :  "../../resultSummary/jq_list_results",
		subgrid_edit_url :  "../../resultSummary/jq_edit_results",
		submod_types :"Assignment:Assignment;Computer Marked Asessment:CMA;Practical Attendance Exercises:PAE;Tutor Marked Assessment:TMA",
		states : "Not Started:Not Started;In Progress:In Progress;Completed:Completed;Exempt:Exempt",	
		tutors : ${tutorList},	
		result_types: "Pass:Pass;Fail:Fail;None:None;Exempt:Exempt",
		
		operands : "Divide:Divide;Multiply:Multiply;Subtract:Subtract;Add:Add"
	}
</script>
</head>

<body>
	<z:window style="padding:5px">
		<div class="bread-crump">
			<span class="r-arrow"></span>
			<g:link controller="course" action="list">Courses</g:link>			
			<span class="r-arrow"></span>
			<g:link controller="course" action="show" id="${registrationInstance.course.id}" params="[tab:1]">${registrationInstance.course.toString() }</g:link>
			<span class="r-arrow"></span>			
			<span class="current-crump">
			<g:link controller="person" action="show" id="${registrationInstance.learner.id}" params="[tab:2]">${registrationInstance.learner.toString() }</g:link>
			<span class="r-arrow"></span>
				Course Register 
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
                        my: "center" //,
                       // of: grid.closest('div.ui-jqgrid')
                    });
                };
                
    jQuery("#" + cland_params.maingrid_id).jqGrid({
      url:cland_params.maingrid_list_url,
      editurl:cland_params.maingrid_edit_url,
      autowidth: true,
      height:"100%",
      datatype: "json",
      colNames:['Module','Result','Status','Tutor','Cert Number','id','Actions'],
      colModel:[
		{name:'module', editable:false},	
		{name:'result',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.result_types}},
        {name:'status',editable:true,width:70,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.states}},
        {name:'tutor.id',editable:cland_params.isAdmin,width:90,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.tutors}},
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
    gridComplete: function(){ 
        var ids = jQuery("#" + cland_params.maingrid_id).jqGrid('getDataIDs'); 
        
        for(var i=0;i < ids.length;i++)
            { 
            	var cl = ids[i]; 
	            be = "<input class='edit' style='height:22px;width:42px;' type='button' value='Edit' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').editRow('"+cl+"');\" />"; 
	            se = "<input class='edit' style='height:22px;width:42px;' type='button' value='Save' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').saveRow('"+cl+"',afterSubmitEvent);\" />"; 
	            ce = "<input class='edit' style='height:22px;width:44px;' type='button' value='Cancel' onclick=\"jQuery('#"+ cland_params.maingrid_id+"').restoreRow('"+cl+"');clearSelection();\" />"; 
	            
	            if(cland_params.canEdit) jQuery("#" + cland_params.maingrid_id).jqGrid('setRowData',ids[i],{act:be+" "+se+" "+ce}); //be+se+ce+de forall actions 
            }
        if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();
    },
    subGrid :true,
    subGridRowExpanded: function(subgrid_id,row_id){
 	   //subgrid_id: id of the div tag created within table data
 	   //id of this element is combination of the "sg_" + id of the row
 	   var subgrid_table_id, pager_id;
 	   subgrid_table_id = subgrid_id + "_t";
 	   pager_id = "p_" + subgrid_table_id;
 	  	   
 	   $("#"+subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
 	   jQuery("#"+subgrid_table_id).jqGrid({
 		   url:cland_params.subgrid_list_url,
 		   editurl:cland_params.subgrid_edit_url,
 		   datatype:"json",
 		  colNames:['Type','Exam No.','Mark','Max Mark','% Mark','% Contribution','id',' <input style="display:none" class="" type="button" name="Add_Result" onClick="addRow(\''+row_id+'\',\''+subgrid_table_id+'\');" id="result_add" value="Add Result"/>','Sub Id'],
 	      colModel:[
		 			{name:'submodule', editable:false,editrules:{required:true}},
		 			{name:'examname', editable:false,editrules:{required:true}},
		 	        {name:'mark', editable:true,editrules:{required:true}},
		 	        {name:'maxMark', editable:false},
		 	        {name:'percentMark', editable:false},     
		 	        {name:'tutor', editable:false},   
		 	        {name:'id',hidden:true},		 	        
		 	       	{name:'subact',index:'subact', width:150,sortable:false,search:false,align:'center'},
		            {name:'resultSumId',index:'resultSumId',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:row_id}}
 		              ],		              
 		   //rowNum:2,
 		   //pager:pager_id,
 		   sortname:'testNumber',
 		   sortorder:'asc',
 		   height:"100%",
 		   autowidth:true,
 		   gridComplete: function(){
 			   thisgrid = jQuery("#" + subgrid_table_id);
 			   var subids = thisgrid.jqGrid('getDataIDs');
 			   for(var i=0;i<subids.length;i++){
 				   	var _id =subids[i];
 				    de = "<input class='edit' style='height:22px;' type='button' value='Add Result' onclick=\"deleteGridRow('"+_id+"','"+subgrid_table_id+"');\" />";
 				   	be = "<input class='edit' style='height:22px;width:42px;' type='button' value='Edit' onclick=\"jQuery('#"+ subgrid_table_id+"').editRow('"+_id+"');\" />"; 
 		            se = "<input class='edit' style='height:22px;width:42px;' type='button' value='Save' onclick=\"jQuery('#"+ subgrid_table_id+"').saveRow('"+_id+"',afterSubmitEvent,null,{'grid_id':'" +subgrid_table_id+"'});\" />"; 
 		            ce = "<input class='edit' style='height:22px;width:44px;' type='button' value='Cancel' onclick=\"jQuery('#"+ subgrid_table_id+"').restoreRow('"+_id+"');clearSelection();\" />";  		            
 		             		            		            
 		            thisgrid.jqGrid('setRowData',_id,{subact: be+se+ce}); //be+se+ce+de forall actions
 				}
 			  if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();
 			},  
 		   cellEdit:false,
 		    cellsubmit: 'remote',
 		   	cellurl:cland_params.subgrid_edit_url,
 		   postData:{resultSumId:row_id}           
 		   });
 	  
 	  // jQuery("#"+subgrid_table_id).setGridParam({id:row_id})''
 	   jQuery("#"+subgrid_table_id).jqGrid('navGrid'),"#"+pager_id,{edit:false,add:false,del:false,refresh:true}
 	   },
 	subGridRowColapsed: function(subgrid_id,row_id){
 		//This functiona is called before removing the data
 		//var subgrid_table_id;
 		//subgrid_table_id = subgrid_id+"_t";
 		//jQuery("#"+subgrid_table_id).remove();
 		}, 
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
      var success = true;
  	  var display = $('#message'); 
      var json = eval('(' + response.responseText + ')');
      var message = json.message;

      if(json.state == 'FAIL') {
          success = false;
          //display.removeClass("message")
          display.addClass("message")
          display.addClass("errors")
          
      }else{
          display.removeClass("errors")
    	  display.addClass("message")
          }      
        display.html(message);
        display.show().fadeOut(10000);            
      var new_id = json.id

      //reload the grid
      var grid_id = json.grid_id;
      if(!grid_id) grid_id = cland_params.maingrid_id;

      $("#" + grid_id).trigger('reloadGrid')
      return success; //[success,message,new_id];
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
	  if (id!= null) jQuery('#' + cland_params.maingrid_list_url).jqGrid('setSelection',id);
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
  function clearSelection(){jQuery('#' + cland_params.maingrid_id).jqGrid('resetSelection'); }
</script>

</body>
</html>