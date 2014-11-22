<%@ page import="com.cland.elearning.RegistrationForm" %>



<fieldset><legend>Personal Details</legend>
<table class="dataTable">
	<tr>
		<td><label>First name:</label></td>
		<td><g:textField name="firstName" required="" value="${personInstance?.firstName}"/></td>
		<td><label>Surname:</label></td>
		<td><g:textField name="lastName" required="" value="${personInstance?.lastName}"/></td>		
	</tr>
	<tr>
		<td><label>Known as:</label></td>
		<td><g:textField name="knownAsName" value="${personInstance?.knownAsName}"/></td>
		<td><label>Middle name:</label></td>
		<td><g:textField name="middleName" value="${personInstance?.middleName}"/></td>		
	</tr>	
	<tr>
		<td><label>Salutation:</label></td>
		<td><g:select name="salutation" from="${personInstance.constraints.salutation.inList}" value="${personInstance?.salutation}" valueMessagePrefix="registrationForm.salutation" noSelection="['': '']"/></td>
		<td><label>Home language:</label></td>
		<td><g:textField name="homeLanguage" value="${personInstance?.homeLanguage}"/></td>		
	</tr>
	<tr>
		<td><label>ID Number:</label></td>
		<td><g:textField name="idNo" value="${personInstance?.idNo}"/></td>
		<td><label>Date of birth:</label></td>
		<td><g:datePicker name="dateOfBirth" precision="day"  value="${personInstance?.dateOfBirth}" default="none" noSelection="['': '']" /></td>		
	</tr>
	<tr>
		<td><label>Gender:</label></td>
		<td><g:select name="gender" from="${personInstance.constraints.gender.inList}" value="${personInstance?.gender}" valueMessagePrefix="registrationForm.gender" noSelection="['': '']"/>></td>
		<td><label>Race:</label></td>
		<td><g:select id="race" name="race.id" from="${com.cland.elearning.Race.list()}" optionKey="id" value="${personInstance?.race?.id}" class="many-to-one" noSelection="['null': '']"/></td>		
	</tr>
	<tr>
		<td><label>Marital status:</label></td>
		<td><g:select name="maritalStatus" from="${personInstance.constraints.maritalStatus.inList}" value="${personInstance?.maritalStatus}" valueMessagePrefix="registrationForm.maritalStatus" noSelection="['': '']"/></td>
		<td><label>Disability Yes/No:</label></td>
		<td><g:select name="disabilityYN" from="${personInstance.constraints.disabilityYN.inList}" value="${personInstance?.disabilityYN}" valueMessagePrefix="registrationForm.disabilityYN" noSelection="['': '']"/></td>		
	</tr>	
	<tr>
		<td><label>Disability List:</label></td>
		<td><g:textField name="disabilityList" value="${personInstance?.disabilityList}"/></td>
		<td><label>Communication mode:</label></td>
		<td><g:select name="communicationMode" from="${personInstance.constraints.communicationMode.inList}" value="${personInstance?.communicationMode}" valueMessagePrefix="registrationForm.communicationMode" noSelection="['': '']"/></td>		
	</tr>
	<tr>
		<td><label>School qualifications:</label></td>
		<td><g:textField name="schoolQualification" value="${personInstance?.schoolQualification}"/></td>
		<td><label>Tertiary qualifications:</label></td>
		<td><g:textField name="tertiaryQualification" value="${personInstance?.tertiaryQualification}"/></td>		
	</tr>	
	<tr>
		<td><label>Physical address:</label></td>
		<td><g:textField name="address" value="${personInstance?.address}"/></td>
		<td><label>Postal address:</label></td>
		<td><g:textField name="postalAddress" value="${personInstance?.postalAddress}"/></td>		
	</tr>
	<tr>
		<td><label>Home contact number:</label></td>
		<td><g:textField name="contactNoHome" value="${personInstance?.contactNoHome}"/></td>
		<td><label>Postal code:</label></td>
		<td><g:textField name="postalCode" value="${personInstance?.postalCode}"/></td>		
	</tr>
	<tr>
		<td><label>Cell number:</label></td>
		<td><g:textField name="cellNo" value="${personInstance?.cellNo}"/></td>
		<td><label>Country:</label></td>
		<td><g:select id="country" name="country.id" from="${com.cland.elearning.Country.list()}" optionKey="id" value="${personInstance?.country?.id}" class="many-to-one" noSelection="['null': '']"/></td>
				
	</tr>	
	<tr>
		<td><label>Contact number:</label></td>
		<td><g:textField name="contactNo" value="${personInstance?.contactNo}"/></td>
		<td><label>Email:</label></td>
		<td><g:field type="email" name="email" value="${personInstance?.email}"/></td>		
	</tr>		
	<tr>
		<td><label>Region:</label></td>
		<td><g:select id="region" name="region.id" from="${com.cland.elearning.Region.list()}" optionKey="id" value="${personInstance?.region?.id}" class="many-to-one" noSelection="['null': '']"/></td>
		<td><label>City:</label></td>
		<td><g:textField name="city" value="${personInstance?.city}"/></td>		
	</tr>
	
</table>
</fieldset>

<fieldset><legend>Employment Details</legend>
<table class="dataTable">
	<tr>
		<td><label>Company name:</label></td>
		<td><g:textField name="companyName" value="${personInstance?.companyName}"/></td>
		<td><label>Company contact person:</label></td>
		<td><g:textField name="companyContactPerson" value="${personInstance?.companyContactPerson}"/></td>		
	</tr>
	<tr>
		<td><label>Contact number:</label></td>
		<td><g:textField name="companyPhoneNo" value="${personInstance?.companyPhoneNo}"/></td>
		<td><label>Email:</label></td>
		<td><g:textField name="companyEmail" value="${personInstance?.companyEmail}"/></td>		
	</tr>
	<tr>
		<td><label>Job Title:</label></td>
		<td><g:textField name="jobTitle" value="${personInstance?.jobTitle}"/></td>
		<td><label>Department:</label></td>
		<td><g:textField name="department" value="${personInstance?.department}"/></td>		
	</tr>
	<tr>
		<td><label>Number of years:</label></td>
		<td><g:field name="numOfYears" type="number" min="0" value="${personInstance.numOfYears}" required=""/></td>
		<td><label>:</label></td>
		<td></td>		
	</tr>
	<tr>
		<td><label>Physical address:</label></td>
		<td><g:textField name="companyPhyAddress" value="${personInstance?.companyPhyAddress}"/></td>
		<td><label>Postal address:</label></td>
		<td><g:textField name="companyPhyPostCode" value="${personInstance?.companyPhyPostCode}"/></td>		
	</tr>	
	<tr>
		<td><label>Known as:</label></td>
		<td></td>
		<td><label>Middle name:</label></td>
		<td></td>		
	</tr>
	
</table>
</fieldset>

<fieldset><legend>Modules</legend>
<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'decoModules', 'error')} ">
	<label for="decoModules">
		<g:message code="registrationForm.decoModules.label" default="Deco Modules" />
		
	</label>
	<g:select name="decoModules" from="${com.cland.elearning.Module.list()}" multiple="multiple" optionKey="id" size="5" value="${personInstance?.decoModules*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'decoModulesCompleted', 'error')} ">
	<label for="decoModulesCompleted">
		<g:message code="registrationForm.decoModulesCompleted.label" default="Deco Modules Completed" />
		
	</label>
	<g:textField name="decoModulesCompleted" value="${personInstance?.decoModulesCompleted}"/>
</div>
</fieldset>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'studentNo', 'error')} ">
	<label for="studentNo">
		<g:message code="registrationForm.studentNo.label" default="Student No" />
		
	</label>
	<g:textField name="studentNo" value="${personInstance?.studentNo}"/>
</div>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'examType', 'error')} ">
	<label for="examType">
		<g:message code="registrationForm.examType.label" default="Exam Type" />
		
	</label>
	<g:select name="examType" from="${personInstance.constraints.examType.inList}" value="${personInstance?.examType}" valueMessagePrefix="registrationForm.examType" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'companyCity', 'error')} ">
	<label for="companyCity">
		<g:message code="registrationForm.companyCity.label" default="Company City" />
		
	</label>
	<g:textField name="companyCity" value="${personInstance?.companyCity}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'companyComments', 'error')} ">
	<label for="companyComments">
		<g:message code="registrationForm.companyComments.label" default="Company Comments" />
		
	</label>
	<g:textField name="companyComments" value="${personInstance?.companyComments}"/>
</div>


<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'companyCountry', 'error')} required">
	<label for="companyCountry">
		<g:message code="registrationForm.companyCountry.label" default="Company Country" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="companyCountry" name="companyCountry.id" from="${com.cland.elearning.Country.list()}" optionKey="id" required="" value="${personInstance?.companyCountry?.id}" class="many-to-one"/>
</div>





<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'companyPostalAddress', 'error')} ">
	<label for="companyPostalAddress">
		<g:message code="registrationForm.companyPostalAddress.label" default="Company Postal Address" />
		
	</label>
	<g:textField name="companyPostalAddress" value="${personInstance?.companyPostalAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'companyPostalPostCode', 'error')} ">
	<label for="companyPostalPostCode">
		<g:message code="registrationForm.companyPostalPostCode.label" default="Company Postal Post Code" />
		
	</label>
	<g:textField name="companyPostalPostCode" value="${personInstance?.companyPostalPostCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'companyRegion', 'error')} required">
	<label for="companyRegion">
		<g:message code="registrationForm.companyRegion.label" default="Company Region" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="companyRegion" name="companyRegion.id" from="${com.cland.elearning.Region.list()}" optionKey="id" required="" value="${personInstance?.companyRegion?.id}" class="many-to-one"/>
</div>



<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'hrManager', 'error')} ">
	<label for="hrManager">
		<g:message code="registrationForm.hrManager.label" default="Hr Manager" />
		
	</label>
	<g:textField name="hrManager" value="${personInstance?.hrManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'hrManagerEmail', 'error')} ">
	<label for="hrManagerEmail">
		<g:message code="registrationForm.hrManagerEmail.label" default="Hr Manager Email" />
		
	</label>
	<g:textField name="hrManagerEmail" value="${personInstance?.hrManagerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'isMember', 'error')} ">
	<label for="isMember">
		<g:message code="registrationForm.isMember.label" default="Is Member" />
		
	</label>
	<g:textField name="isMember" value="${personInstance?.isMember}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'lineManager', 'error')} ">
	<label for="lineManager">
		<g:message code="registrationForm.lineManager.label" default="Line Manager" />
		
	</label>
	<g:textField name="lineManager" value="${personInstance?.lineManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'lineManagerEmail', 'error')} ">
	<label for="lineManagerEmail">
		<g:message code="registrationForm.lineManagerEmail.label" default="Line Manager Email" />
		
	</label>
	<g:textField name="lineManagerEmail" value="${personInstance?.lineManagerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'paintModules', 'error')} ">
	<label for="paintModules">
		<g:message code="registrationForm.paintModules.label" default="Paint Modules" />
		
	</label>
	<g:select name="paintModules" from="${com.cland.elearning.Module.list()}" multiple="multiple" optionKey="id" size="5" value="${personInstance?.paintModules*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'paintModulesCompleted', 'error')} ">
	<label for="paintModulesCompleted">
		<g:message code="registrationForm.paintModulesCompleted.label" default="Paint Modules Completed" />
		
	</label>
	<g:textField name="paintModulesCompleted" value="${personInstance?.paintModulesCompleted}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'rawModules', 'error')} ">
	<label for="rawModules">
		<g:message code="registrationForm.rawModules.label" default="Raw Modules" />
		
	</label>
	<g:select name="rawModules" from="${com.cland.elearning.Module.list()}" multiple="multiple" optionKey="id" size="5" value="${personInstance?.rawModules*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'rawModulesCompleted', 'error')} ">
	<label for="rawModulesCompleted">
		<g:message code="registrationForm.rawModulesCompleted.label" default="Raw Modules Completed" />
		
	</label>
	<g:textField name="rawModulesCompleted" value="${personInstance?.rawModulesCompleted}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'seniorManageEmail', 'error')} ">
	<label for="seniorManageEmail">
		<g:message code="registrationForm.seniorManageEmail.label" default="Senior Manage Email" />
		
	</label>
	<g:textField name="seniorManageEmail" value="${personInstance?.seniorManageEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'seniorManager', 'error')} ">
	<label for="seniorManager">
		<g:message code="registrationForm.seniorManager.label" default="Senior Manager" />
		
	</label>
	<g:textField name="seniorManager" value="${personInstance?.seniorManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'trainingManager', 'error')} ">
	<label for="trainingManager">
		<g:message code="registrationForm.trainingManager.label" default="Training Manager" />
		
	</label>
	<g:textField name="trainingManager" value="${personInstance?.trainingManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'trainingManagerEmail', 'error')} ">
	<label for="trainingManagerEmail">
		<g:message code="registrationForm.trainingManagerEmail.label" default="Training Manager Email" />
		
	</label>
	<g:textField name="trainingManagerEmail" value="${personInstance?.trainingManagerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: personInstance, field: 'vatNumber', 'error')} ">
	<label for="vatNumber">
		<g:message code="registrationForm.vatNumber.label" default="Vat Number" />
		
	</label>
	<g:textField name="vatNumber" value="${personInstance?.vatNumber}"/>
</div>


