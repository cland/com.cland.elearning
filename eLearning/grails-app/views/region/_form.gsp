
<z:rows>

    <z:row>
        <z:label value="${message(code:'region.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${regionInstance?.name}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'region.country.label',default:'Country')}"/>
        <zkui:select name="country.id" from="${com.cland.elearning.Country.list()}" optionKey="id" value="${regionInstance?.country?.id}"  />
    </z:row>

</z:rows>