<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.person.CreateComposer">   
        <tmpl:form/>
    
    <z:hlayout>
        <z:button id="saveButton" label="${message(code: 'default.button.create.label', default: 'Create')}"/>
        <z:button href="${createLink(action:'list')}" label="${message(code: 'default.list.label', args:[entityName])}"/>
    </z:hlayout>
</z:window>
<script type="text/javascript">
  /* when the page has finished loading.. execute the follow */
  $(document).ready(function () {
	  $( "body" ).css("height","1520px")
  });
</script>
  
</body>
</html>