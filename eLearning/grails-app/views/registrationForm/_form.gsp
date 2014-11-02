<%@ page import="com.cland.elearning.RegistrationForm" %>



<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'firstName', 'error')} required">
	<label for="firstName">
		<g:message code="registrationForm.firstName.label" default="First Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="firstName" required="" value="${registrationFormInstance?.firstName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'lastName', 'error')} required">
	<label for="lastName">
		<g:message code="registrationForm.lastName.label" default="Last Name" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="lastName" required="" value="${registrationFormInstance?.lastName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'middleName', 'error')} ">
	<label for="middleName">
		<g:message code="registrationForm.middleName.label" default="Middle Name" />
		
	</label>
	<g:textField name="middleName" value="${registrationFormInstance?.middleName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'knownAsName', 'error')} ">
	<label for="knownAsName">
		<g:message code="registrationForm.knownAsName.label" default="Known As Name" />
		
	</label>
	<g:textField name="knownAsName" value="${registrationFormInstance?.knownAsName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'homeLanguage', 'error')} ">
	<label for="homeLanguage">
		<g:message code="registrationForm.homeLanguage.label" default="Home Language" />
		
	</label>
	<g:textField name="homeLanguage" value="${registrationFormInstance?.homeLanguage}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'salutation', 'error')} ">
	<label for="salutation">
		<g:message code="registrationForm.salutation.label" default="Salutation" />
		
	</label>
	<g:select name="salutation" from="${registrationFormInstance.constraints.salutation.inList}" value="${registrationFormInstance?.salutation}" valueMessagePrefix="registrationForm.salutation" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'idNo', 'error')} ">
	<label for="idNo">
		<g:message code="registrationForm.idNo.label" default="Id No" />
		
	</label>
	<g:textField name="idNo" value="${registrationFormInstance?.idNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'dateOfBirth', 'error')} ">
	<label for="dateOfBirth">
		<g:message code="registrationForm.dateOfBirth.label" default="Date Of Birth" />
		
	</label>
	<g:datePicker name="dateOfBirth" precision="day"  value="${registrationFormInstance?.dateOfBirth}" default="none" noSelection="['': '']" />
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'gender', 'error')} ">
	<label for="gender">
		<g:message code="registrationForm.gender.label" default="Gender" />
		
	</label>
	<g:select name="gender" from="${registrationFormInstance.constraints.gender.inList}" value="${registrationFormInstance?.gender}" valueMessagePrefix="registrationForm.gender" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'race', 'error')} ">
	<label for="race">
		<g:message code="registrationForm.race.label" default="Race" />
		
	</label>
	<g:select id="race" name="race.id" from="${com.cland.elearning.Race.list()}" optionKey="id" value="${registrationFormInstance?.race?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'maritalStatus', 'error')} ">
	<label for="maritalStatus">
		<g:message code="registrationForm.maritalStatus.label" default="Marital Status" />
		
	</label>
	<g:select name="maritalStatus" from="${registrationFormInstance.constraints.maritalStatus.inList}" value="${registrationFormInstance?.maritalStatus}" valueMessagePrefix="registrationForm.maritalStatus" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'disabilityYN', 'error')} ">
	<label for="disabilityYN">
		<g:message code="registrationForm.disabilityYN.label" default="Disability YN" />
		
	</label>
	<g:select name="disabilityYN" from="${registrationFormInstance.constraints.disabilityYN.inList}" value="${registrationFormInstance?.disabilityYN}" valueMessagePrefix="registrationForm.disabilityYN" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'disabilityList', 'error')} ">
	<label for="disabilityList">
		<g:message code="registrationForm.disabilityList.label" default="Disability List" />
		
	</label>
	<g:textField name="disabilityList" value="${registrationFormInstance?.disabilityList}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'communicationMode', 'error')} ">
	<label for="communicationMode">
		<g:message code="registrationForm.communicationMode.label" default="Communication Mode" />
		
	</label>
	<g:select name="communicationMode" from="${registrationFormInstance.constraints.communicationMode.inList}" value="${registrationFormInstance?.communicationMode}" valueMessagePrefix="registrationForm.communicationMode" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'schoolQualification', 'error')} ">
	<label for="schoolQualification">
		<g:message code="registrationForm.schoolQualification.label" default="School Qualification" />
		
	</label>
	<g:textField name="schoolQualification" value="${registrationFormInstance?.schoolQualification}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'tertiaryQualification', 'error')} ">
	<label for="tertiaryQualification">
		<g:message code="registrationForm.tertiaryQualification.label" default="Tertiary Qualification" />
		
	</label>
	<g:textField name="tertiaryQualification" value="${registrationFormInstance?.tertiaryQualification}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'address', 'error')} ">
	<label for="address">
		<g:message code="registrationForm.address.label" default="Address" />
		
	</label>
	<g:textField name="address" value="${registrationFormInstance?.address}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'postalAddress', 'error')} ">
	<label for="postalAddress">
		<g:message code="registrationForm.postalAddress.label" default="Postal Address" />
		
	</label>
	<g:textField name="postalAddress" value="${registrationFormInstance?.postalAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'postalCode', 'error')} ">
	<label for="postalCode">
		<g:message code="registrationForm.postalCode.label" default="Postal Code" />
		
	</label>
	<g:textField name="postalCode" value="${registrationFormInstance?.postalCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'contactNoHome', 'error')} ">
	<label for="contactNoHome">
		<g:message code="registrationForm.contactNoHome.label" default="Contact No Home" />
		
	</label>
	<g:textField name="contactNoHome" value="${registrationFormInstance?.contactNoHome}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'city', 'error')} ">
	<label for="city">
		<g:message code="registrationForm.city.label" default="City" />
		
	</label>
	<g:textField name="city" value="${registrationFormInstance?.city}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'region', 'error')} ">
	<label for="region">
		<g:message code="registrationForm.region.label" default="Region" />
		
	</label>
	<g:select id="region" name="region.id" from="${com.cland.elearning.Region.list()}" optionKey="id" value="${registrationFormInstance?.region?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'country', 'error')} ">
	<label for="country">
		<g:message code="registrationForm.country.label" default="Country" />
		
	</label>
	<g:select id="country" name="country.id" from="${com.cland.elearning.Country.list()}" optionKey="id" value="${registrationFormInstance?.country?.id}" class="many-to-one" noSelection="['null': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'contactNo', 'error')} ">
	<label for="contactNo">
		<g:message code="registrationForm.contactNo.label" default="Contact No" />
		
	</label>
	<g:textField name="contactNo" value="${registrationFormInstance?.contactNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'email', 'error')} ">
	<label for="email">
		<g:message code="registrationForm.email.label" default="Email" />
		
	</label>
	<g:field type="email" name="email" value="${registrationFormInstance?.email}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'numOfYears', 'error')} required">
	<label for="numOfYears">
		<g:message code="registrationForm.numOfYears.label" default="Num Of Years" />
		<span class="required-indicator">*</span>
	</label>
	<g:field name="numOfYears" type="number" min="0" value="${registrationFormInstance.numOfYears}" required=""/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'department', 'error')} ">
	<label for="department">
		<g:message code="registrationForm.department.label" default="Department" />
		
	</label>
	<g:textField name="department" value="${registrationFormInstance?.department}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'jobTitle', 'error')} ">
	<label for="jobTitle">
		<g:message code="registrationForm.jobTitle.label" default="Job Title" />
		
	</label>
	<g:textField name="jobTitle" value="${registrationFormInstance?.jobTitle}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'studentNo', 'error')} ">
	<label for="studentNo">
		<g:message code="registrationForm.studentNo.label" default="Student No" />
		
	</label>
	<g:textField name="studentNo" value="${registrationFormInstance?.studentNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'cellNo', 'error')} ">
	<label for="cellNo">
		<g:message code="registrationForm.cellNo.label" default="Cell No" />
		
	</label>
	<g:textField name="cellNo" value="${registrationFormInstance?.cellNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'examType', 'error')} ">
	<label for="examType">
		<g:message code="registrationForm.examType.label" default="Exam Type" />
		
	</label>
	<g:select name="examType" from="${registrationFormInstance.constraints.examType.inList}" value="${registrationFormInstance?.examType}" valueMessagePrefix="registrationForm.examType" noSelection="['': '']"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyCity', 'error')} ">
	<label for="companyCity">
		<g:message code="registrationForm.companyCity.label" default="Company City" />
		
	</label>
	<g:textField name="companyCity" value="${registrationFormInstance?.companyCity}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyComments', 'error')} ">
	<label for="companyComments">
		<g:message code="registrationForm.companyComments.label" default="Company Comments" />
		
	</label>
	<g:textField name="companyComments" value="${registrationFormInstance?.companyComments}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyContactPerson', 'error')} ">
	<label for="companyContactPerson">
		<g:message code="registrationForm.companyContactPerson.label" default="Company Contact Person" />
		
	</label>
	<g:textField name="companyContactPerson" value="${registrationFormInstance?.companyContactPerson}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyCountry', 'error')} required">
	<label for="companyCountry">
		<g:message code="registrationForm.companyCountry.label" default="Company Country" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="companyCountry" name="companyCountry.id" from="${com.cland.elearning.Country.list()}" optionKey="id" required="" value="${registrationFormInstance?.companyCountry?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyEmail', 'error')} ">
	<label for="companyEmail">
		<g:message code="registrationForm.companyEmail.label" default="Company Email" />
		
	</label>
	<g:textField name="companyEmail" value="${registrationFormInstance?.companyEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyName', 'error')} ">
	<label for="companyName">
		<g:message code="registrationForm.companyName.label" default="Company Name" />
		
	</label>
	<g:textField name="companyName" value="${registrationFormInstance?.companyName}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyPhoneNo', 'error')} ">
	<label for="companyPhoneNo">
		<g:message code="registrationForm.companyPhoneNo.label" default="Company Phone No" />
		
	</label>
	<g:textField name="companyPhoneNo" value="${registrationFormInstance?.companyPhoneNo}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyPhyAddress', 'error')} ">
	<label for="companyPhyAddress">
		<g:message code="registrationForm.companyPhyAddress.label" default="Company Phy Address" />
		
	</label>
	<g:textField name="companyPhyAddress" value="${registrationFormInstance?.companyPhyAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyPhyPostCode', 'error')} ">
	<label for="companyPhyPostCode">
		<g:message code="registrationForm.companyPhyPostCode.label" default="Company Phy Post Code" />
		
	</label>
	<g:textField name="companyPhyPostCode" value="${registrationFormInstance?.companyPhyPostCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyPostalAddress', 'error')} ">
	<label for="companyPostalAddress">
		<g:message code="registrationForm.companyPostalAddress.label" default="Company Postal Address" />
		
	</label>
	<g:textField name="companyPostalAddress" value="${registrationFormInstance?.companyPostalAddress}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyPostalPostCode', 'error')} ">
	<label for="companyPostalPostCode">
		<g:message code="registrationForm.companyPostalPostCode.label" default="Company Postal Post Code" />
		
	</label>
	<g:textField name="companyPostalPostCode" value="${registrationFormInstance?.companyPostalPostCode}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'companyRegion', 'error')} required">
	<label for="companyRegion">
		<g:message code="registrationForm.companyRegion.label" default="Company Region" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="companyRegion" name="companyRegion.id" from="${com.cland.elearning.Region.list()}" optionKey="id" required="" value="${registrationFormInstance?.companyRegion?.id}" class="many-to-one"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'decoModules', 'error')} ">
	<label for="decoModules">
		<g:message code="registrationForm.decoModules.label" default="Deco Modules" />
		
	</label>
	<g:select name="decoModules" from="${com.cland.elearning.Module.list()}" multiple="multiple" optionKey="id" size="5" value="${registrationFormInstance?.decoModules*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'decoModulesCompleted', 'error')} ">
	<label for="decoModulesCompleted">
		<g:message code="registrationForm.decoModulesCompleted.label" default="Deco Modules Completed" />
		
	</label>
	<g:textField name="decoModulesCompleted" value="${registrationFormInstance?.decoModulesCompleted}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'hrManager', 'error')} ">
	<label for="hrManager">
		<g:message code="registrationForm.hrManager.label" default="Hr Manager" />
		
	</label>
	<g:textField name="hrManager" value="${registrationFormInstance?.hrManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'hrManagerEmail', 'error')} ">
	<label for="hrManagerEmail">
		<g:message code="registrationForm.hrManagerEmail.label" default="Hr Manager Email" />
		
	</label>
	<g:textField name="hrManagerEmail" value="${registrationFormInstance?.hrManagerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'isMember', 'error')} ">
	<label for="isMember">
		<g:message code="registrationForm.isMember.label" default="Is Member" />
		
	</label>
	<g:textField name="isMember" value="${registrationFormInstance?.isMember}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'lineManager', 'error')} ">
	<label for="lineManager">
		<g:message code="registrationForm.lineManager.label" default="Line Manager" />
		
	</label>
	<g:textField name="lineManager" value="${registrationFormInstance?.lineManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'lineManagerEmail', 'error')} ">
	<label for="lineManagerEmail">
		<g:message code="registrationForm.lineManagerEmail.label" default="Line Manager Email" />
		
	</label>
	<g:textField name="lineManagerEmail" value="${registrationFormInstance?.lineManagerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'paintModules', 'error')} ">
	<label for="paintModules">
		<g:message code="registrationForm.paintModules.label" default="Paint Modules" />
		
	</label>
	<g:select name="paintModules" from="${com.cland.elearning.Module.list()}" multiple="multiple" optionKey="id" size="5" value="${registrationFormInstance?.paintModules*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'paintModulesCompleted', 'error')} ">
	<label for="paintModulesCompleted">
		<g:message code="registrationForm.paintModulesCompleted.label" default="Paint Modules Completed" />
		
	</label>
	<g:textField name="paintModulesCompleted" value="${registrationFormInstance?.paintModulesCompleted}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'rawModules', 'error')} ">
	<label for="rawModules">
		<g:message code="registrationForm.rawModules.label" default="Raw Modules" />
		
	</label>
	<g:select name="rawModules" from="${com.cland.elearning.Module.list()}" multiple="multiple" optionKey="id" size="5" value="${registrationFormInstance?.rawModules*.id}" class="many-to-many"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'rawModulesCompleted', 'error')} ">
	<label for="rawModulesCompleted">
		<g:message code="registrationForm.rawModulesCompleted.label" default="Raw Modules Completed" />
		
	</label>
	<g:textField name="rawModulesCompleted" value="${registrationFormInstance?.rawModulesCompleted}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'seniorManageEmail', 'error')} ">
	<label for="seniorManageEmail">
		<g:message code="registrationForm.seniorManageEmail.label" default="Senior Manage Email" />
		
	</label>
	<g:textField name="seniorManageEmail" value="${registrationFormInstance?.seniorManageEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'seniorManager', 'error')} ">
	<label for="seniorManager">
		<g:message code="registrationForm.seniorManager.label" default="Senior Manager" />
		
	</label>
	<g:textField name="seniorManager" value="${registrationFormInstance?.seniorManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'trainingManager', 'error')} ">
	<label for="trainingManager">
		<g:message code="registrationForm.trainingManager.label" default="Training Manager" />
		
	</label>
	<g:textField name="trainingManager" value="${registrationFormInstance?.trainingManager}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'trainingManagerEmail', 'error')} ">
	<label for="trainingManagerEmail">
		<g:message code="registrationForm.trainingManagerEmail.label" default="Training Manager Email" />
		
	</label>
	<g:textField name="trainingManagerEmail" value="${registrationFormInstance?.trainingManagerEmail}"/>
</div>

<div class="fieldcontain ${hasErrors(bean: registrationFormInstance, field: 'vatNumber', 'error')} ">
	<label for="vatNumber">
		<g:message code="registrationForm.vatNumber.label" default="Vat Number" />
		
	</label>
	<g:textField name="vatNumber" value="${registrationFormInstance?.vatNumber}"/>
</div>

