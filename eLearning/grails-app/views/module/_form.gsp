
<z:rows>
 <z:row>
        <z:label value="${message(code:'module.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${moduleInstance?.name}" />
    </z:row>
    <z:row>
        <z:label value="${message(code:'module.description.label',default:'Description')}"/>
        <z:textbox rows="4" name="description" value="${moduleInstance?.description}" />
    </z:row>

   

</z:rows>