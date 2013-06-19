<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'person.label', default: 'Person')}" />
    <title><g:appTitle title=""><g:message code="default.edit.label" args="[entityName]" /></g:appTitle></title>
    <link rel="stylesheet"	href="${resource(dir:'css/south-street',file:'jquery-ui-1.10.3.custom.min.css')}" />
<g:javascript library="jquerymin" />
<g:javascript library="jqueryuilatest" />
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.person.EditComposer">
    <z:longbox name="id" value="${personInstance.id}" visible="false"/>
    <z:longbox name="version" value="${personInstance.version}" visible="false"/>
    <g:if test="${flash.message}">
        <z:window mode="popup" border="normal" style="margin-bottom:5px">
            <z:hlayout>
                <z:image src="/images/skin/information.png"/>
                <z:div>
                    ${flash.message}
                </z:div>
            </z:hlayout>
        </z:window>
    </g:if>
  
     <tmpl:form/>
  
    <z:hlayout>
        <z:button id="saveButton" label="${message(code: 'default.button.update.label', default: 'Update')}"/>
        <z:button href="${createLink(action:'list')}" label="${message(code: 'default.list.label', args:[entityName])}"/>
    </z:hlayout>
</z:window>
<script type="text/javascript">
  /* when the page has finished loading.. execute the follow */
  $(document).ready(function () {
	  $( "body" ).css("height","1730px")
  });
</script>
</body>
</html>