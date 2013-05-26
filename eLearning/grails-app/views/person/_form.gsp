
<z:rows>

    <z:row>
        <z:label value="${message(code:'person.username.label',default:'Username')}"/>
        <z:textbox name="username" value="${personInstance?.username}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.password.label',default:'Password')}"/>
        <z:textbox name="password" value="${personInstance?.password}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.firstName.label',default:'First Name')}"/>
        <z:textbox name="firstName" value="${personInstance?.firstName}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.lastName.label',default:'Last Name')}"/>
        <z:textbox name="lastName" value="${personInstance?.lastName}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.idNo.label',default:'Id No')}"/>
        <z:textbox name="idNo" value="${personInstance?.idNo}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.dateOfBirth.label',default:'Date Of Birth')}"/>
        <z:datebox name="dateOfBirth" value="${personInstance?.dateOfBirth}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.gender.label',default:'Gender')}"/>
        <zkui:select name="gender" from="${personInstance.constraints.gender.inList}" value="${personInstance?.gender}" valueMessagePrefix="person.gender"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.address.label',default:'Address')}"/>
        <z:textbox name="address" value="${personInstance?.address}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.city.label',default:'City')}"/>
        <z:textbox name="city" value="${personInstance?.city}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.region.label',default:'Region')}"/>
        <zkui:select name="region" from="${personInstance.constraints.region.inList}" value="${personInstance?.region}" valueMessagePrefix="person.region"  />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.contactNo.label',default:'Contact No')}"/>
        <z:textbox name="contactNo" value="${personInstance?.contactNo}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.email.label',default:'Email')}"/>
        <z:textbox name="email" value="${personInstance?.email}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.accountExpired.label',default:'Account Expired')}"/>
        <z:checkbox name="accountExpired" checked="${personInstance?.accountExpired}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.accountLocked.label',default:'Account Locked')}"/>
        <z:checkbox name="accountLocked" checked="${personInstance?.accountLocked}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.enabled.label',default:'Enabled')}"/>
        <z:checkbox name="enabled" checked="${personInstance?.enabled}"/>
    </z:row>

    <z:row>
        <z:label value="${message(code:'person.passwordExpired.label',default:'Password Expired')}"/>
        <z:checkbox name="passwordExpired" checked="${personInstance?.passwordExpired}"/>
    </z:row>

</z:rows>