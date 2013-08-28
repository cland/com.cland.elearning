<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
    <title><g:appTitle title=""><g:message code="default.create.label" args="[entityName]" /></g:appTitle></title>
    <link rel="stylesheet"
	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
<g:javascript library="jquerymin" />
<g:javascript library="jqueryuilatest" />
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.person.CreateComposer">   
        <tmpl:formTabs/>
    
    <z:hlayout>
        <z:button id="saveButton" label="${message(code: 'default.button.create.label', default: 'Create')}"/>
        <z:button href="${createLink(action:'list')}" label="${message(code: 'default.list.label', args:[entityName])}"/>
    </z:hlayout>
</z:window>
  <script>
  $(document).ready(function() {
	 // $("#add_org").live("click",function(){
	//	  addOrganisation();
	//	});
  });
  function after(res){
	  alert (res)
	}
	function before(){
		alert("before submit")
	}
	function loadOrganisations(){

	}
  function addOrganisation(){

	  	 var $dialog = $('<div></div>')
    
                 .load('../organisation/dialogcreate')
                 .dialog({
                     autoOpen: false,
                     width:450,
                     beforeClose: function(event,ui){
                     	
                     },
                     close: function(event){     
                     	loadOrganisations()
                     },
                     title: 'New Organisation'                         
                 });
                     
        $dialog.dialog('open');
	  }
  </script>
</body>
</html>