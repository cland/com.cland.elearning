
<z:hbox>
	<z:panel width="100%" height="" title="Login Details"
		collapsible="true">
		<z:panelchildren>
			<z:grid>
				<z:columns sizable="true">
					<z:column label="${message(code:'name',default:'Name')}"
						width="100px" />
					<z:column label="${message(code:'value',default:'Value')}" />
				</z:columns>
				<z:rows>
					<z:row>
						<z:label
							value="${message(code:'person.username.label',default:'Username')}" />
						<z:textbox name="username" value="${personInstance?.username}" />
					</z:row>
					<g:if test="${params.action=='create'}">
						<z:row>
							<z:label
								value="${message(code:'person.password.label',default:'Password')}" />
							<z:textbox name="password" type="password"
								value="${personInstance?.password}" />
						</z:row>
					</g:if>
					<z:row>
						<z:label
							value="${message(code:'person.enabled.label',default:'Enabled')}" />
						<z:checkbox name="enabled" checked="${personInstance?.enabled}" />
					</z:row>
					<z:row>
						<z:label
							value="${message(code:'person.accountExpired.label',default:'Account Expired')}" />
						<z:checkbox name="accountExpired"
							checked="${personInstance?.accountExpired}" />
					</z:row>
				<z:row>
						<z:label
							value="${message(code:'person.passwordExpired.label',default:'Password Expired')}" />
						<z:checkbox name="passwordExpired"
							checked="${personInstance?.passwordExpired}" />
					</z:row>				
					<z:row>
						<z:label
							value="${message(code:'person.accountLocked.label',default:'Account Locked')}" />
						<z:checkbox name="accountLocked"
							checked="${personInstance?.accountLocked}" />
					</z:row>
					<tmpl:editRoles/>														
				</z:rows>				
			</z:grid>
			
		</z:panelchildren>
	</z:panel>
	
	
	<z:panel width="435px" height="" title="Contact Details"
		border="normal" collapsible="true">
		<z:panelchildren>
			<z:grid>
				<z:columns sizable="true">
					<z:column label="${message(code:'name',default:'Name')}"
						width="100px" />
					<z:column label="${message(code:'value',default:'Value')}" />
				</z:columns>
				<z:rows>
	<z:row>
		<z:label
			value="${message(code:'person.address.label',default:'Address')}" />
		<z:textbox name="address" value="${personInstance?.address}" />
	</z:row>

	<z:row>
		<z:label value="${message(code:'person.city.label',default:'City')}" />
		<z:textbox name="city" value="${personInstance?.city}" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.region.label',default:'Region')}" />
		<zkui:select name="region"
			from="${com.cland.elearning.Region.list().sort(false){it.name}}"
			value="${personInstance?.region}" valueMessagePrefix="person.region" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.country.label',default:'Country')}" />
		<zkui:select name="Country"
			from="${com.cland.elearning.Country.list().sort(false){it.name}}"
			value="${personInstance?.country}"
			valueMessagePrefix="person.country" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.contactNo.label',default:'Contact No')}" />
		<z:textbox name="contactNo" value="${personInstance?.contactNo}" />
	</z:row>

	<z:row>
		<z:label value="${message(code:'person.email.label',default:'Email')}" />
		<z:textbox name="email" value="${personInstance?.email}" />
	</z:row>			
		<z:row>
		<z:label
			value="${message(code:'person.communicationMode.label',default:'Communication Mode')}" />
		<zkui:select name="communicationMode"
			from="${personInstance.constraints.communicationMode.inList}"
			value="${personInstance?.communicationMode}"
			valueMessagePrefix="person.communicationMode" />
	</z:row>
				</z:rows>
			</z:grid>
		</z:panelchildren>
	</z:panel>
</z:hbox>

<z:hbox>
	<z:panel width="100%" height="" title="Person Details"
		collapsible="true">
		<z:panelchildren>
			<z:grid>
				<z:columns sizable="true">
					<z:column label="${message(code:'name',default:'Name')}"
						width="100px" />
					<z:column label="${message(code:'value',default:'Value')}" />
				</z:columns>
				<z:rows>
	<z:row>
		<z:label
			value="${message(code:'person.salutation.label',default:'Salutation')}" />
		<zkui:select name="salutation"
			from="${personInstance.constraints.salutation.inList}"
			value="${personInstance?.salutation}"
			valueMessagePrefix="person.salutation" />
	</z:row>
				
	<z:row>
		<z:label
			value="${message(code:'person.firstName.label',default:'First Name')}" />
		<z:textbox name="firstName" value="${personInstance?.firstName}" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'person.middleName.label',default:'Middle Name')}" />
		<z:textbox name="middleName" value="${personInstance?.middleName}" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'person.lastName.label',default:'Last Name')}" />
		<z:textbox name="lastName" value="${personInstance?.lastName}" />
	</z:row>

	<z:row>
		<z:label value="${message(code:'person.idNo.label',default:'Id No')}" />
		<z:textbox name="idNo" value="${personInstance?.idNo}" />
	</z:row>
	
	<z:row>
		<z:label
			value="${message(code:'person.dateOfBirth.label',default:'Date Of Birth')}" />
		<z:datebox name="dateOfBirth" value="${personInstance?.dateOfBirth}" />
	</z:row>
	<z:row>
		<z:label
			value="${message(code:'person.gender.label',default:'Gender')}" />
		<zkui:select name="gender"
			from="${personInstance.constraints.gender.inList}"
			value="${personInstance?.gender}" valueMessagePrefix="person.gender" />
	</z:row>
	
	<z:row>
		<z:label
			value="${message(code:'person.homeLanguage.label',default:'Home Language')}" />
		<z:textbox name="homeLanguage" value="${personInstance?.homeLanguage}" />
	</z:row>


	<z:row>
		<z:label value="${message(code:'person.race.label',default:'Race')}" />
		<zkui:select name="race"
			from="${com.cland.elearning.Race.list().sort(false){it.name}}"
			value="${personInstance?.race}" valueMessagePrefix="person.race" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.maritalStatus.label',default:'Marital Status')}" />
		<zkui:select name="maritalStatus"
			from="${personInstance.constraints.maritalStatus.inList}"
			value="${personInstance?.maritalStatus}"
			valueMessagePrefix="person.maritalStatus" />
	</z:row>				</z:rows>
			</z:grid>
		</z:panelchildren>
	</z:panel>
	
	
	<z:panel width="435px" height="" title="Education Details"
		border="normal" collapsible="true">
		<z:panelchildren>
			<z:grid>
				<z:columns sizable="true">
					<z:column label="${message(code:'name',default:'Name')}"
						width="100px" />
					<z:column label="${message(code:'value',default:'Value')}" />
				</z:columns>
				
				<z:rows>
					<z:row>
		<z:label
			value="${message(code:'person.schoolQualification.label',default:'School Qualification')}" />
		<z:textbox name="schoolQualification"
			value="${personInstance?.schoolQualification}" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.tertiaryQualification.label',default:'Tertiary Qualification')}" />
		<z:textbox name="tertiaryQualification"
			value="${personInstance?.tertiaryQualification}" />
	</z:row>
				</z:rows>
			</z:grid>
		</z:panelchildren>
	</z:panel>
</z:hbox>

<z:hbox>
	<z:panel width="100%" height="" title="Employment Details"
		collapsible="true">
		<z:panelchildren>
			<z:grid>
				<z:columns sizable="true">
					<z:column label="${message(code:'name',default:'Name')}"
						width="100px" />
					<z:column label="${message(code:'value',default:'Value')}" />
				</z:columns>
				<z:rows>
		<z:row>
		<z:label
			value="${message(code:'person.company.label',default:'Company')}" />
		<zkui:select name="company.id"
			from="${com.cland.elearning.Organisation.list()}" optionKey="id"
			value="${personInstance?.company?.id}" noSelection="['null': '']" />
	</z:row>									
				</z:rows>
			</z:grid>
		</z:panelchildren>
	</z:panel>
		
	<z:panel width="435px" height="" title="Other Details"
		border="normal" collapsible="true">
		<z:panelchildren>
			<z:grid>
				<z:columns sizable="true">
					<z:column label="${message(code:'name',default:'Name')}"
						width="100px" />
					<z:column label="${message(code:'value',default:'Value')}" />
				</z:columns>
				<z:rows>
	<z:row>
		<z:label
			value="${message(code:'person.disabilityYN.label',default:'Disability YN')}" />
		<zkui:select name="disabilityYN"
			from="${personInstance.constraints.disabilityYN.inList}"
			value="${personInstance?.disabilityYN}"
			valueMessagePrefix="person.disabilityYN" noSelection="['': '']" />
	</z:row>

	<z:row>
		<z:label
			value="${message(code:'person.disabilityList.label',default:'Disability List')}" />
		<z:textbox name="disabilityList"
			value="${personInstance?.disabilityList}" />
	</z:row>
				</z:rows>
			</z:grid>
		</z:panelchildren>
	</z:panel>
</z:hbox>