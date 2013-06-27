<html>
<head>
    
    <g:set var="entityName" value="${message(code: 'course.label', default: 'Course')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
</head>

<body>
<z:window style="padding:5px" apply="com.cland.elearning.course.CreateComposer">

 <z:longbox name="id" value="${courseInstance.id}" visible="false"/>
    <z:longbox name="version" value="${courseInstance.version}" visible="false"/>
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
    <z:window title="Module List" border="normal" width="300px">
    <z:vlayout>
        <g:each var="i" in="${com.cland.elearning.Module.list()}">
        <z:checkbox name="modlist_${i.id }" checked ="false" id="${i.id }" label="${i }"  />     
        </g:each>
        </z:vlayout>
        </z:window>
    <z:hlayout>
        <z:button id="addModuleButton" label="${message(code: 'default.button.add.label', default: 'Add')}"/>   
         
    </z:hlayout>
    
</z:window>
</body>
</html>