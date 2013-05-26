
<z:rows>

    <z:row>
        <z:label value="${message(code:'subModule.type.label',default:'Type')}"/>
        <zkui:select name="type" from="${subModuleInstance.constraints.type.inList}" value="${subModuleInstance?.type}" valueMessagePrefix="subModule.type"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'subModule.description.label',default:'Description')}"/>
        <z:textbox name="description" value="${subModuleInstance?.description}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'subModule.module.label',default:'Module')}"/>
        <zkui:select name="module.id" from="${com.cland.elearning.Module.list()}" optionKey="id" value="${subModuleInstance?.module?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'subModule.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${subModuleInstance?.name}" />
    </z:row>

</z:rows>