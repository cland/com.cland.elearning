<html>
<head>
<meta name="layout" content="main" />
<title>Export Results</title>
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
		thisId : function(){ if (${params.id==null}) return -1; else return ${params.id};},
		maingrid_list_url : "jq_export_results_flat?status=Completed",
		maingrid_id		: "examresults_list",
		maingrid_id_pager : "examresults_list_pager"	
	}
</script>
</head>

<body>
	<z:window style="padding:5px">
		<div class="bread-crump">
			<span class="r-arrow"></span>
			
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
			<b>&raquo;</b> Export Results
		</legend>
		<h1>
			&nbsp;results
		</h1>
		<div class="content">
			some content
		</div>
	</fieldset>
		<sec:ifAnyGranted roles="ADMIN,TUTOR">
<g:form>
				<fieldset class="buttons">
										
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
      colNames:['Student No.','First Name.','Last Name','Company','Module','Mode of Learning','Result','Status','Total','Out of','% Mark','Test 1','Test 2','Test 3','Test 4','Test 5','Test 6','Test 7','Test 8','Test 9','Test 10','id'],
      colModel:[
		{name:'student_number', editable:false},
		{name:'firstname', editable:false},
        {name:'lastname', editable:false},
        {name:'company', editable:false},
        {name:'module_name', editable:false}, 
        {name:'submodule', editable:false},    
        {name:'result', editable:false},   
        {name:'status', editable:false},
        {name:'marks.total', editable:false},
        {name:'marks.maxtotal', editable:false},
        {name:'marks.percent', editable:false},
        {name:'marks.test1', editable:false},
        {name:'marks.test2', editable:false},
        {name:'marks.test3', editable:false},
        {name:'marks.test4', editable:false},
        {name:'marks.test5', editable:false},
        {name:'marks.test6', editable:false},
        {name:'marks.test7', editable:false},
        {name:'marks.test8', editable:false},
        {name:'marks.test9', editable:false,hidden:false},
        {name:'marks.test10', editable:false,hidden:false},
        {name:'id',hidden:true}
        //{name:'resultSumid',index:'resultSumid',editable:true, hidden:true,sortable:false,search:false,editoptions:{defaultValue:cland_params.thisId}}
     ],
     rowNum:20,
     rowList:[10,20,30,40,50,100,200],
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