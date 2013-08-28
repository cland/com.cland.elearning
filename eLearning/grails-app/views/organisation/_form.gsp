
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
        <z:label value="${message(code:'organisation.phyAddress.label',default:'Phy Address')}"/>
        <z:textbox rows="2" name="phyAddress" value="${organisationInstance?.phyAddress}" />
    </z:row>
  <z:row>
        <z:label value="${message(code:'organisation.phyPostCode.label',default:'Phy Post Code')}"/>
        <z:textbox name="phyPostCode" value="${organisationInstance?.phyPostCode}" />
    </z:row>
   <z:row>
        <z:label value="${message(code:'organisation.postalAddress.label',default:'Postal Address')}"/>
        <z:textbox rows="2" name="postalAddress" value="${organisationInstance?.postalAddress}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.postalPostCode.label',default:'Postal Post Code')}"/>
        <z:textbox name="postalPostCode" value="${organisationInstance?.postalPostCode}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'organisation.region.label',default:'Region')}"/>
       <zkui:select name="region.id" optionKey="id"
			from="${com.cland.elearning.Region.list().sort(false){it.name}}"
			value="${organisationInstance?.region?.id}" />
    </z:row>
    <z:row>
        <z:label value="${message(code:'organisation.country.label',default:'Country')}"/>
        <zkui:select name="country.id" optionKey="id"
			from="${com.cland.elearning.Country.list().sort(false){it.name}}"
			value="${organisationInstance?.country?.id}"/>
    </z:row>
    <z:row>
        <z:label value="${message(code:'organisation.comments.label',default:'Comments')}"/>
        <z:textbox rows="2" name="comments" value="${organisationInstance?.comments}" />
    </z:row>
  

 



</z:rows>