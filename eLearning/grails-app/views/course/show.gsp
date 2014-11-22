<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName"	value="${message(code: 'course.label', default: 'Course')}" />
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
//<![CDATA[
var cland_params = {
		active_tab : function(){ if (${params.tab==null}) return 0; else return ${params.tab};},
		canEdit :${org.codehaus.groovy.grails.plugins.springsecurity.SpringSecurityUtils.ifAnyGranted("ADMIN,TUTOR")},
		thisId : ${params.id},
		modules_list_url : "../jq_list_modules?courseid=" + ${params.id},
		modules_edit_url : "../jq_remove_module?courseid=" + ${params.id}, //to fix
		module_maingrid_id		: "module_list",
		module_maingrid_id_pager : "module_list_pager",
		
		submodule_subgrid_id	: "submodule_list",
		submodule_list_url :  "../jq_list_exam",

		learners_list_url : "../jq_list_learners?courseid=" + ${params.id},
		learners_edit_url : "../jq_remove_learner?courseid=" + ${params.id}, //to fix
		learner_maingrid_id		: "learner_list",
		learner_maingrid_id_pager : "learner_list_pager",
		
		learner_subgrid_id	: "learner_result_list",
		learner_subgrid_list_url :  "../jq_list_exam",
		
		tutors_list_url	: "../jq_list_tutors",
		events_list_url	: "../jq_list_events",		
			
		submod_types :"Assignment:Assignment;Computer Marked Asessment:CMA;Practical Attendance Exercises:PAE;Tutor Marked Assessment:TMA",
		states : "active:active;inactive:inactive",
		operands : "Divide:Divide;Multiply:Multiply;Subtract:Subtract;Add:Add"				
	}
//]]>
</script>

</head>

<body>

	<z:window style="">
		<z:window style="padding:5px">
			<div class="bread-crump">
				<span class="r-arrow"></span>
				<g:link controller="course" action="list">Courses</g:link>
				<span class="r-arrow"></span> <span class="current-crump">
					Course: ${courseInstance.name } (${courseInstance.code })
				</span>
			</div>
			<div id="message"></div>
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
	</z:window>
	<fieldset>
		<legend>
			<b>&raquo;</b> Course
		</legend>
		<h1>
			&nbsp;<g:fieldValue bean="${courseInstance}" field="name" /> (Code: ${courseInstance.code })
		</h1>
		<div class="content">
			Status:
			${courseInstance.status}

		</div>
	</fieldset>

	<!-- The tabs -->
	<div id="tabs" style="display:none">
		<ul>
			<li><a href="#tab-module-list">Modules</a></li>
			<li><a href="#tab-learner-list">Learners</a></li>
			<li><a href="#tab-events-list">Events</a></li>
	
		</ul>
		<div id="tab-module-list">
			<div id="module_grid" style="padding: 5px;">
				<table id="module_list" class="scroll jqTable"></table>
				<!-- pager will hold our paginator -->
				<div id="module_list_pager" class="scroll"
					style="text-align: center;"></div>
			</div>
		</div>
		
		<div id="tab-learner-list">
			<div id="learner_grid" style="padding: 5px;">
				<table id="learner_list" class="scroll jqTable"></table>
				<!-- pager will hold our paginator -->
				<div id="learner_list_pager" class="scroll"
					style="text-align: center;"></div>
			</div>
		</div>
		<div id="tab-events-list">
			<g:link controller="event" action="create" class="create" params="${['course.id':courseInstance?.id] }">New Event</g:link>
			<br/>
			<table class="dataTable">
				<tr>
					<th>Title</th>
					<th>When</th>
					<th>Location</th>
					<th>Region</th>
				</tr>				
			
			<g:each in="${ courseInstance?.events?.sort{it.startTime}}" var="eventInstance" status="i">
			<tr>
				<td> <g:link controller="event" action="show" id="${eventInstance?.id }">${eventInstance?.title }</g:link></td>
				<td> <g:formatDate date="${eventInstance?.startTime }" format="E, MMM d, hh:mma"/> - <g:formatDate date="${eventInstance?.startTime }" format="E, MMM d, hh:mma"/></td>
				<td> ${eventInstance?.location }</td>
				<td> ${eventInstance?.region }</td>
			</tr>
			</g:each>
			</table>
		</div>
	</div>
	<!--  End tabs -->
	
<r:script>

/* when the page has finished loading.. execute the follow */
$(document).ready(function() {		
	
	
$("#tabs").tabs(
									{
									active:cland_params.active_tab(),
									create: function (event,ui){	
										//executed after is created								
										$('#tabs').show()
									},
									show: function(event,ui){
										//on every tabs clicked
									},
									beforeLoad : function(event, ui) {
											ui.jqXHR.error(function() {
												ui.panel
												.html("Couldn't load this tab. We'll try to fix this as soon as possible. ");
											});
										}
									});
					//field set functions
					$("legend").click(
							function() {
								$(this).children("b").toggleClass("collapsed");
								$(this).nextAll("div.content").slideToggle(500);
							});
					$("fieldset.topleft1 legend").children("b").addClass("collapsed");
					$("fieldset.topleft1 legend").nextAll("div.content").hide();
					$("fieldset.topleft2 legend").children("b").addClass("collapsed");
					$("fieldset.topleft2 legend").nextAll("div.content").hide();
					
					  centerForm = function ($form) {
		                    $form.closest('div.ui-jqdialog').position({
		                        my: "center"
		                    });
		                };

		              //initialize the modulegrid
					    jQuery("#" + cland_params.module_maingrid_id).jqGrid({
					      url:cland_params.modules_list_url,
					      editurl:cland_params.modules_edit_url,
					      autowidth: true,
					      height:"100%",
					      datatype: "json",					      
					      colNames:['Name','Description','Pre-Requisites','PreId','id','<input class="edit" type="button" style="display:none;" name="Add_Module" onClick="addModuleRow(\''+cland_params.thisId+'\',\''+cland_params.module_maingrid_id+'\');" id="module_add" value="Add Module"/>'],
					      colModel:[
					        {name:'name', editable:false},						        
					        {name:'description', editable:false},
					        {name:'prevalues', editable:false},   
							{name:'preid', editable:false, hidden:true},     
					        {name:'id',hidden:true},
					        {name:'act',index:'act', width:100,sortable:false,search:false}
					       // {name:'modid',index:'modid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
					     ],
					     rowNum:100,
					     rowList:[10,20,30,40,50,100],
					     multiselect: false,
					    pager: jQuery('#' + cland_params.module_maingrid_id_pager),
					    viewrecords: true,
					    gridview: true,
					   	//postData:{id:cland_params.thisId},
					    cellEdit:false,
					    cellsubmit: 'remote',
					   	cellurl:cland_params.module_maingrid_edit_url,
					   subGridRowExpanded: function(subgrid_id,row_id){
						   //subgrid_id: id of the div tag created within table data
						   //id of this element is combination of the "sg_" + id of the row
						   return;
						   
						   var subgrid_table_id, pager_id;
						   subgrid_table_id = subgrid_id + "_t";
						   pager_id = "p_" + subgrid_table_id;						  
						   
						   $("#"+submodule_subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
						   jQuery("#"+subgrid_table_id).jqGrid({
							   url:cland_params.submodule_subgrid_list_url,
							   editurl:cland_params.subgrid_edit_url,
							   datatype:"json",
							   colNames:['Exam Number','Max Mark','Weight','Factor','Factor Operand','Status','id',' <input class="edit"type="button" name="Add_Exam" onClick="addRow(\''+row_id+'\',\''+subgrid_table_id+'\');" id="exam_add" value="Add Exam"/>','Sub Id'],
							   colModel:[ {name:'testNumber', editable:true,width:100,editrules:{required:true,integer:true},align:'left'},
							              {name:'maxMark', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false},
							              {name:'weight', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false},
							              {name:'factor', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false},
							              {name:'factorOperand',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.operands}},
							              {name:'status',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.states}},
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
							   gridComplete: function(){
								   thisgrid = jQuery("#" + subgrid_table_id);
								   var subids = thisgrid.jqGrid('getDataIDs');
								   for(var i=0;i<subids.length;i++){
									   	var _id =subids[i];
									   	de = "<input class='edit' style='height:22px;' type='button' value='Delete' onclick=\"deleteGridRow('"+_id+"','"+subgrid_table_id+"');\" />";		            
							            thisgrid.jqGrid('setRowData',_id,{subact: de}); //be+se+ce+de forall actions
									}
									if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();
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
					    subGrid : false,
					    gridComplete: function(){ 
					    	var grid = jQuery("#" + cland_params.module_maingrid_id)
					        var ids = grid.jqGrid('getDataIDs'); 
					        
					        for(var i=0;i < ids.length;i++)
					            { 
					            	var cl = ids[i]; 	
					            	var rowdata = grid.jqGrid('getRowData',cl); 					            
						            rm = "<input class='edit' style='height:22px;width:82px;' type='button' value='Remove' onclick=\"removeGridRow('"+cl+"','"+cland_params.module_maingrid_id+"');\" />";
						            pre = "<input class='edit' style='height:22px;width:92px;' type='button' value='Pre-Requisites' onclick=\"addPreModule('"+${params.id}+"','"+cl+"','"+rowdata.preid+"','"+cland_params.module_maingrid_id+"');\" />";
						            
						            jQuery("#" + cland_params.module_maingrid_id).jqGrid('setRowData',ids[i],{act:pre+" " + rm}); //be+se+ce+de forall actions 
					            }
					         if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();   
					    } 
					    }).navGrid('#' + cland_params.module_maingrid_id_pager,
					            {add:false,edit:false,del:false,search:false,refresh:true}, // which buttons to show?
					            {closeAfterEdit:true, afterSubmit:afterSubmitEvent,savekey:[true,13],afterShowForm: centerForm},  // edit options
					            {addCaption:'New Record',afterSubmit:afterSubmitEvent,savekey:[true,13],closeAfterEdit:false},  // add options            
					           {afterShowForm: centerForm}          // delete options
					        );
					   // $("#" + cland_params.module_maingrid_id).jqGrid('filterToolbar',{autosearch:true});

    //initialize the learnergrid
					    jQuery("#" + cland_params.learner_maingrid_id).jqGrid({
					      url:cland_params.learners_list_url,
					      editurl:cland_params.learners_edit_url,
					      autowidth: true,
					      height:"100%",
					      datatype: "json",
					      colNames:['Name','Surname','Registration Date','id','<input class="edit" type="button" name="Add_Learner" onClick="addLearnerRow(\''+cland_params.thisId+'\',\''+cland_params.learner_maingrid_id+'\');" id="learner_add" value="Register Learner"/>'],
					      colModel:[
					        {name:'firstName', editable:false},						        
					        {name:'lastName', editable:false},
					        {name:'regDate', editable:false},					               
					        {name:'id',hidden:true},
					        {name:'act',index:'act', width:162,sortable:false,search:false}
					       // {name:'modid',index:'modid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
					     ],
					     rowNum:20,
					     rowList:[10,20,30,40,50,100],
					     multiselect: false,
					    pager: jQuery('#' + cland_params.learner_maingrid_id_pager),
					    viewrecords: true,
					    gridview: true,
					  // 	postData:{id:cland_params.thisId},
					    cellEdit:false,
					    cellsubmit: 'remote',
					   	cellurl:cland_params.learner_maingrid_edit_url,
					   subGridRowExpanded: function(subgrid_id,row_id){
						   //subgrid_id: id of the div tag created within table data
						   //id of this element is combination of the "sg_" + id of the row
						   return;
						   
						   var subgrid_table_id, pager_id;
						   subgrid_table_id = subgrid_id + "_t";
						   pager_id = "p_" + subgrid_table_id;
						   console.log(subgrid_table_id + " - " + pager_id);
						   
						   $("#"+learner_subgrid_id).html("<table id='" + subgrid_table_id + "' class='scroll'></table><div id='"+pager_id+"' class='scroll'></div>");
						   jQuery("#"+subgrid_table_id).jqGrid({
							   url:cland_params.learner_subgrid_list_url,
							   editurl:cland_params.learner_subgrid_edit_url,
							   datatype:"json",
							   colNames:['Exam Number','Max Mark','Weight','Factor','Factor Operand','Status','id',' <input class="edit" type="button" name="Add_Exam" onClick="addRow(\''+row_id+'\',\''+subgrid_table_id+'\');" id="exam_add" value="Add Exam"/>','Sub Id'],
							   colModel:[ {name:'testNumber', editable:true,width:100,editrules:{required:true,integer:true},align:'left'},
							              {name:'maxMark', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false},
							              {name:'weight', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false},
							              {name:'factor', editable:true,width:100,editrules:{required:true,integer:true},align:'right',sortable:false},
							              {name:'factorOperand',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.operands}},
							              {name:'status',editable:true,width:60,editrules:{required:true},edittype:"select",formatter:'select', editoptions:{value:cland_params.states}},
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
							   gridComplete: function(){
								   thisgrid = jQuery("#" + subgrid_table_id);
								   var subids = thisgrid.jqGrid('getDataIDs');
								   for(var i=0;i<subids.length;i++){
									   	var _id =subids[i];
									   		            
									   	de = "<input class='edit' style='height:22px;' type='button' value='Delete' onclick=\"deleteGridRow('"+_id+"','"+subgrid_table_id+"');\" />";
							            thisgrid.jqGrid('setRowData',_id,{subact: de}); //be+se+ce+de forall actions
									}
									if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();
								},  
							   cellEdit:true,
							    cellsubmit: 'remote',
							   	cellurl:cland_params.learner_subgrid_edit_url,
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
					    subGrid : false,
					    gridComplete: function(){ 
					    	var grid = jQuery("#" + cland_params.learner_maingrid_id)
					        var ids = grid.jqGrid('getDataIDs'); 
					        
					        for(var i=0;i < ids.length;i++)
					            { 
					            	var cl = ids[i]; 						          	
						            rs = "<input class='edit' style='height:22px;width:80px;' type='button' value='Modules' onclick=\"viewResults('"+cl+"','"+cland_params.learner_maingrid_id+"');\" />";
						            rm = "<input class='edit' style='height:22px;width:80px;' type='button' value='Remove' onclick=\"removeGridRow('"+cl+"','"+cland_params.learner_maingrid_id+"');\" />";
						            jQuery("#" + cland_params.learner_maingrid_id).jqGrid('setRowData',ids[i],{act:rs+rm}); //be+se+ce+de forall actions 
					            }
					            if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();
					    } 
					    }).navGrid('#' + cland_params.learner_maingrid_id_pager,
					            {add:false,edit:false,del:false,search:false,refresh:true}, // which buttons to show?
					            {closeAfterEdit:true, afterSubmit:afterSubmitEvent,savekey:[true,13],afterShowForm: centerForm},  // edit options
					            {addCaption:'New Record',afterSubmit:afterSubmitEvent,savekey:[true,13],closeAfterEdit:false},  // add options            
					           {afterShowForm: centerForm}          // delete options
					        );
					    $("#" + cland_params.learner_maingrid_id).jqGrid('filterToolbar',{autosearch:true});  
					   
		                
});  //end method ready(...)

/** helper functions **/
 
  function afterSubmitEvent(response, postdata) {  
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
      return [success,message,new_id];
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
			  if (id!= null) jQuery('#submodule_list').jqGrid('setSelection',id);
			  var gr = $("#submodule_list").jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
		      
		      if( gr != null && gr != "" )
		        $("#submodule_list").jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent,height:300,width:700});
		      else
		        alert("Please Select Row to delete!");
		  }
		  function removeGridRow(id, grid_id){
		  
			 if (id!= null) jQuery('#' + grid_id).jqGrid('setSelection',id);
			  var gr = $("#" + grid_id).jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
		      
		      if( gr != null && gr != "" )
		        $("#" + grid_id).jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent,height:300,width:700});
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
		  function addModuleRow(course_id,grid_id){		  	 
		  	 var $dialog = $('<div></div>')
           
                        .load('../addmodule?id=' + course_id)
                        .dialog({
                            autoOpen: false,
                            width:350,
                            beforeClose: function(event,ui){
                            	
                            },
                            close: function(event){
                            	$("#" + grid_id).trigger('reloadGrid')
                            },
                            title: 'Add Module to Course'                         
                        });
                            
                        $dialog.dialog('open');
                        
		  }
		 function addPreModule(course_id,mod_id,pre_id,grid_id){
		
		  	 var $dialog = $('<div></div>')
           
                        .load('../../preModule/dialogcreate/'+pre_id +'?course.id=' + course_id + '&current.id=' +mod_id)
                        .dialog({
                            autoOpen: false,
                            width:550,
                            beforeClose: function(event,ui){
                            	
                            },
                            close: function(event){
                            	$("#" + grid_id).trigger('reloadGrid')
                            },
                            title: 'Add Module Pre-requisites'                         
                        });
                            
                        $dialog.dialog('open');
                        
		  }
		  function viewResults(id,grid_id){
		  	document.location.href= "../../registration/show/" + id
		  	
		  }
		  function addLearnerRow(course_id, grid_id){
		  	 var $dialog = $('<div></div>')
           
                        .load('../../registration/register?course.id=' + course_id)
                        .dialog({
                            autoOpen: false,
                            width:350,
                            beforeClose: function(event,ui){
                            	
                            },
                            close: function(event){                                                     
                            	$("#" + grid_id).trigger('reloadGrid')
                            },
                            title: 'Register Learner'                         
                        });
                            
                        $dialog.dialog('open');
		  }
		  function clearSelection(grid_id){jQuery('#' + grid_id).jqGrid('resetSelection'); }



</r:script>
</body>
</html>
