
<z:rows>

    <z:row>
        <z:label value="${message(code:'venue.name.label',default:'Name')}"/>
        <z:textbox name="name" value="${venueInstance?.name}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'venue.address.label',default:'Address')}"/>
        <z:textbox name="address" value="${venueInstance?.address}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'venue.geoLocation.label',default:'Geo Location')}"/>
        <z:textbox name="geoLocation" value="${venueInstance?.geoLocation}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'venue.contactName.label',default:'Contact Name')}"/>
        <z:textbox name="contactName" value="${venueInstance?.contactName}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'venue.contactNumber.label',default:'Contact Number')}"/>
        <z:textbox name="contactNumber" value="${venueInstance?.contactNumber}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'venue.directions.label',default:'Directions')}"/>
        <z:textbox name="directions" value="${venueInstance?.directions}" />
    </z:row>

    <z:row>
        <z:label value="${message(code:'venue.region.label',default:'Region')}"/>
        <zkui:select name="region" from="${com.cland.elearning.Region.list().sort(false){it.name}}" value="${venueInstance?.region}" valueMessagePrefix="venue.region"  />
    </z:row>

</z:rows>