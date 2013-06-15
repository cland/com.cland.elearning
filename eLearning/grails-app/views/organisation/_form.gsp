
<z:rows>

    <z:row>
        <z:label value="${message(code:'organisation.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${organisationInstance?.name}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.phoneNo.label',default:'Phone No')}"/>
        <z:textbox name="phoneNo" value="${organisationInstance?.phoneNo}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.email.label',default:'Email')}"/>
        <z:textbox name="email" value="${organisationInstance?.email}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.address.label',default:'Address')}"/>
        <z:textbox name="address" value="${organisationInstance?.address}" />
    </z:row>

</z:rows>