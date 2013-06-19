<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'organisation.label', default: 'Organisation')}" />
    <title><g:message code="default.edit.label" args="[entityName]" /></title>
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.organisation.EditComposer">
    <z:longbox name="id" value="${organisationInstance.id}" visible="false"/>
    <z:longbox name="version" value="${organisationInstance.version}" visible="false"/>
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
    <z:grid>
        <z:columns sizable="true">
            <z:column label="${message(code:'name',default:'Name')}" width="100px"/>
            <z:column label="${message(code:'value',default:'Value')}"/>
        </z:columns>
        <tmpl:form/>
    </z:grid>
    <z:hlayout>
        <z:button id="saveButton" label="${message(code: 'default.button.update.label', default: 'Update')}"/>
        <z:button href="${createLink(action:'list')}" label="${message(code: 'default.list.label', args:[entityName])}"/>
    </z:hlayout>
</z:window>
<script type="text/javascript">
  /* when the page has finished loading.. execute the follow */
  $(document).ready(function () {
	  $( "body" ).css("height","1160px")
  });
</script>
</body>
</html>