
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
        <z:label value="${message(code:'organisation.contactPerson.label',default:'Contact Person')}"/>
        <z:textbox name="contactPerson" value="${organisationInstance?.contactPerson}" />
    </z:row>
    
 <z:row>
        <z:label value="${message(code:'organisation.isMember.label',default:'Is Member')}"/>
        <zkui:select name="isMember" from="${organisationInstance.constraints.isMember.inList}" value="${organisationInstance?.isMember}" valueMessagePrefix="organisation.isMember" noSelection="['': '']" />
    </z:row>
    
    <z:row>
        <z:label value="${message(code:'organisation.phyAddress.label',default:'Physical Address')}"/>
        <z:textbox name="phyAddress" value="${organisationInstance?.phyAddress}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.phyPostCode.label',default:'Physical Post Code')}"/>
        <z:textbox name="phyPostCode" value="${organisationInstance?.phyPostCode}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.postalAddress.label',default:'Postal Address')}"/>
        <z:textbox name="postalAddress" value="${organisationInstance?.postalAddress}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.postalPostCode.label',default:'Postal Post Code')}"/>
        <z:textbox name="postalPostCode" value="${organisationInstance?.postalPostCode}" />
    </z:row>

</z:rows>