<html>
<head>

    <g:set var="entityName" value="${message(code: 'registration.label', default: 'Registration')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.registration.CreateComposer">
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