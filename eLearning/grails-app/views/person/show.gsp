<html>
<head>
<meta name="layout" content="main" />
<g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
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
		course_list_url : "../jq_list_courses?personid=" + ${params.id},
		course_edit_url : "../jq_remove_course", 
		course_maingrid_id		: "course_list",
		course_maingrid_id_pager : "course_list_pager",
		
		course_subgrid_id	: "learner_result_list",
		course_subgrid_list_url :  "../jq_list_exam",	
			
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
				
				<span class="r-arrow"></span> <span class="current-crump">
					Person: ${personInstance.toString() }
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
			<b>&raquo;</b> Person
		</legend>
		<h1>
			&nbsp; ${personInstance.toString() } <g:if test="${personInstance?.studentNo != "" }">(Learner ID: ${personInstance.studentNo })</g:if>
		</h1>
		<div class="content">
		<b>Cell No.:</b> ${personInstance.contactNo}<br/>
		<b>Work No.:</b> ${personInstance?.company?.phoneNo}<br/>
		<b>Home No.:</b> ${personInstance?.contactNoHome}<br/>
		
		<b>Email:</b> ${personInstance.email}<br/>
		<b>Roles: </b>
			<g:each var="auth" in="${roleMap }">
			[${auth.authority }] 
			</g:each><br/>
		</div>
	</fieldset>
	<sec:ifAnyGranted roles="ADMIN,TUTOR">
<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${personInstance?.id}" />
					<g:link class="edit" style="float:right" action="edit" id="${personInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>					
				</fieldset>
			</g:form>
			</sec:ifAnyGranted>
	<!-- The tabs -->
	<tmpl:tabs/>
	<!--  End tabs -->
	
<script type="text/javascript">
// when the page has finished loading.. execute the follow

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
  
		                //initialize the coursegrid 
					    jQuery("#" + cland_params.course_maingrid_id).jqGrid({
					      url:cland_params.course_list_url,
					      editurl:cland_params.course_edit_url,
					      autowidth: true,
					      height:"100%",
					      datatype: "json",
					      colNames:['Course','Code','Registration Date','id','<input class="edit" type="button" name="Add_Learner" onClick="addLearnerRow(\''+cland_params.thisId+'\',\''+cland_params.course_maingrid_id+'\');" id="learner_add" value="Register"/>'],
					      colModel:[
					        {name:'course', editable:false},						        
					        {name:'code', editable:false},
					        {name:'regDate', editable:false},					             
					        {name:'id',hidden:true},
					        {name:'act',index:'act', width:162,sortable:false,search:false}
					       // {name:'modid',index:'modid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
					     ],
					     rowNum:20,
					     rowList:[10,20,30,40,50],
					     multiselect: false,
					    pager: jQuery('#' + cland_params.course_maingrid_id_pager),
					    viewrecords: true,
					    gridview: true,
					  // 	postData:{id:cland_params.thisId},
					    cellEdit:false,
					    cellsubmit: 'remote',
					   	cellurl:cland_params.course_maingrid_edit_url,					   
					    subGrid : false,
					    gridComplete: function(){ 
					        var ids = jQuery("#" + cland_params.course_maingrid_id).jqGrid('getDataIDs'); 
					        
					        for(var i=0;i < ids.length;i++)
					            { 
					            	var cl = ids[i]; 
						          
						            rs = "<input class='view' style='height:22px;width:80px;' type='button' value='Results' onclick=\"viewResults('"+cl+"','"+cland_params.course_maingrid_id+"');\" />";
						            rm = "<input class='edit' style='height:22px;width:80px;' type='button' value='Remove' onclick=\"removeGridRow('"+cl+"','"+cland_params.course_maingrid_id+"');\" />";
						            jQuery("#" + cland_params.course_maingrid_id).jqGrid('setRowData',ids[i],{act:rs+rm}); //be+se+ce+de forall actions 
					            }
					        if(cland_params.canEdit) $(".edit").show(); else  $(".edit").hide();
					    } 
					    }).navGrid('#' + cland_params.course_maingrid_id_pager,
					            {add:false,edit:false,del:false,search:false,refresh:true}, // which buttons to show?
					            {closeAfterEdit:true, afterSubmit:afterSubmitEvent,savekey:[true,13],afterShowForm: centerForm},  // edit options
					            {addCaption:'New Record',afterSubmit:afterSubmitEvent,savekey:[true,13],closeAfterEdit:false},  // add options            
					           {afterShowForm: centerForm}          // delete options
					        );
					  //  $("#" + cland_params.course_maingrid_id).jqGrid('filterToolbar',{autosearch:true});  
					   
		                
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
			  grid = $("#" + grid_id);
			  if (id!= null) grid.jqGrid('setSelection',id);
			  var gr = grid.jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'		      
		      if( gr != null && gr != "" )
		        grid.jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent,height:250,width:650});
		      else
		        alert("Please Select Row to delete!");
			
		  }
		  function removeGridRow(id, grid_id){
		  
			 if (id!= null) jQuery('#' + grid_id).jqGrid('setSelection',id);
			  var gr = $("#" + grid_id).jqGrid('getGridParam','selrow'); //if multi use: 'selarrrow'
		      
		      if( gr != null && gr != "" )
		        $("#" + grid_id).jqGrid('delGridRow',gr , {afterSubmit:afterSubmitEvent,height:250,width:650});
		      else
		        alert("Please Select Row to delete!");
		}
		  
		  function viewResults(id,grid_id){
		  	document.location.href= "../../registration/show/" + id
		  	
		  }
		  function addLearnerRow(course_id, grid_id){
		  	 var $dialog = $('<div></div>')
           
                        .load('../../registration/register?src=learner&learner.id=' + course_id)
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


</script>
</body>
</html>
