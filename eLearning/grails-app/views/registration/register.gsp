<html>
<head>

    <g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
    <title><g:appTitle title=""><g:message code="default.create.label" args="[entityName]" /></g:appTitle></title>
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.registration.CreateComposer">
<z:textbox name="src" value="${params.src}" visible="false"/>
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
    <z:grid>
        <z:columns sizable="true">
            <z:column label="${message(code:'name',default:'Name')}" width="100px"/>
            <z:column label="${message(code:'value',default:'Value')}"/>
        </z:columns>
        <tmpl:form/>
    </z:grid>
    <z:hlayout>
        <z:button id="saveDialogButton" label="${message(code: 'default.button.register.label', default: 'Register')}"/>        
    </z:hlayout>
</z:window>
</body>
</html>