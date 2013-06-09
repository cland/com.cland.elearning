<html>
<head>
    
    <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.course.CreateComposer">

 <z:longbox name="id" value="${courseInstance.id}" visible="false"/>
    <z:longbox name="version" value="${courseInstance.version}" visible="false"/>
    
    <z:window title="Module List" border="normal" width="300px">
    <z:vlayout>
        <g:each var="i" in="${com.cland.elearning.Module.list()}">
        <z:checkbox name="modlist" value="${i.id }" id="${ i}" label=" ${i }"/>     
        </g:each>
        </z:vlayout>
        </z:window>
    <z:hlayout>
        <z:button id="addModuleButton" label="${message(code: 'default.button.add.label', default: 'Add')}"/>   
         
    </z:hlayout>
</z:window>
</body>
</html>