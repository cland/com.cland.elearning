
<z:rows>

    <z:row>
        <z:label value="${message(code:'personRole.person.label',default:'Person')}"/>
        <zkui:select name="person.id" from="${com.cland.elearning.Person.list()}" optionKey="id" value="${personRoleInstance?.person?.id}"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'personRole.role.label',default:'Role')}"/>
        <zkui:select name="role.id" from="${com.cland.elearning.Role.list()}" optionKey="id" value="${personRoleInstance?.role?.id}"  />
    </z:row>

</z:rows>