
<%@ page import="com.cland.elearning.RegistrationForm" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'registrationForm.label', default: 'RegistrationForm')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-registrationForm" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
				<li><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></li>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="show-registrationForm" class="content scaffold-show" role="main">
			<h1><g:message code="default.show.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
			</g:if>
			<ol class="property-list registrationForm">
			
				<g:if test="${registrationFormInstance?.firstName}">
				<li class="fieldcontain">
					<span id="firstName-label" class="property-label"><g:message code="registrationForm.firstName.label" default="First Name" /></span>
					
						<span class="property-value" aria-labelledby="firstName-label"><g:fieldValue bean="${registrationFormInstance}" field="firstName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.lastName}">
				<li class="fieldcontain">
					<span id="lastName-label" class="property-label"><g:message code="registrationForm.lastName.label" default="Last Name" /></span>
					
						<span class="property-value" aria-labelledby="lastName-label"><g:fieldValue bean="${registrationFormInstance}" field="lastName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.middleName}">
				<li class="fieldcontain">
					<span id="middleName-label" class="property-label"><g:message code="registrationForm.middleName.label" default="Middle Name" /></span>
					
						<span class="property-value" aria-labelledby="middleName-label"><g:fieldValue bean="${registrationFormInstance}" field="middleName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.knownAsName}">
				<li class="fieldcontain">
					<span id="knownAsName-label" class="property-label"><g:message code="registrationForm.knownAsName.label" default="Known As Name" /></span>
					
						<span class="property-value" aria-labelledby="knownAsName-label"><g:fieldValue bean="${registrationFormInstance}" field="knownAsName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.homeLanguage}">
				<li class="fieldcontain">
					<span id="homeLanguage-label" class="property-label"><g:message code="registrationForm.homeLanguage.label" default="Home Language" /></span>
					
						<span class="property-value" aria-labelledby="homeLanguage-label"><g:fieldValue bean="${registrationFormInstance}" field="homeLanguage"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.salutation}">
				<li class="fieldcontain">
					<span id="salutation-label" class="property-label"><g:message code="registrationForm.salutation.label" default="Salutation" /></span>
					
						<span class="property-value" aria-labelledby="salutation-label"><g:fieldValue bean="${registrationFormInstance}" field="salutation"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.idNo}">
				<li class="fieldcontain">
					<span id="idNo-label" class="property-label"><g:message code="registrationForm.idNo.label" default="Id No" /></span>
					
						<span class="property-value" aria-labelledby="idNo-label"><g:fieldValue bean="${registrationFormInstance}" field="idNo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.dateOfBirth}">
				<li class="fieldcontain">
					<span id="dateOfBirth-label" class="property-label"><g:message code="registrationForm.dateOfBirth.label" default="Date Of Birth" /></span>
					
						<span class="property-value" aria-labelledby="dateOfBirth-label"><g:formatDate date="${registrationFormInstance?.dateOfBirth}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.gender}">
				<li class="fieldcontain">
					<span id="gender-label" class="property-label"><g:message code="registrationForm.gender.label" default="Gender" /></span>
					
						<span class="property-value" aria-labelledby="gender-label"><g:fieldValue bean="${registrationFormInstance}" field="gender"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.race}">
				<li class="fieldcontain">
					<span id="race-label" class="property-label"><g:message code="registrationForm.race.label" default="Race" /></span>
					
						<span class="property-value" aria-labelledby="race-label"><g:link controller="race" action="show" id="${registrationFormInstance?.race?.id}">${registrationFormInstance?.race?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.maritalStatus}">
				<li class="fieldcontain">
					<span id="maritalStatus-label" class="property-label"><g:message code="registrationForm.maritalStatus.label" default="Marital Status" /></span>
					
						<span class="property-value" aria-labelledby="maritalStatus-label"><g:fieldValue bean="${registrationFormInstance}" field="maritalStatus"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.disabilityYN}">
				<li class="fieldcontain">
					<span id="disabilityYN-label" class="property-label"><g:message code="registrationForm.disabilityYN.label" default="Disability YN" /></span>
					
						<span class="property-value" aria-labelledby="disabilityYN-label"><g:fieldValue bean="${registrationFormInstance}" field="disabilityYN"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.disabilityList}">
				<li class="fieldcontain">
					<span id="disabilityList-label" class="property-label"><g:message code="registrationForm.disabilityList.label" default="Disability List" /></span>
					
						<span class="property-value" aria-labelledby="disabilityList-label"><g:fieldValue bean="${registrationFormInstance}" field="disabilityList"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.communicationMode}">
				<li class="fieldcontain">
					<span id="communicationMode-label" class="property-label"><g:message code="registrationForm.communicationMode.label" default="Communication Mode" /></span>
					
						<span class="property-value" aria-labelledby="communicationMode-label"><g:fieldValue bean="${registrationFormInstance}" field="communicationMode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.schoolQualification}">
				<li class="fieldcontain">
					<span id="schoolQualification-label" class="property-label"><g:message code="registrationForm.schoolQualification.label" default="School Qualification" /></span>
					
						<span class="property-value" aria-labelledby="schoolQualification-label"><g:fieldValue bean="${registrationFormInstance}" field="schoolQualification"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.tertiaryQualification}">
				<li class="fieldcontain">
					<span id="tertiaryQualification-label" class="property-label"><g:message code="registrationForm.tertiaryQualification.label" default="Tertiary Qualification" /></span>
					
						<span class="property-value" aria-labelledby="tertiaryQualification-label"><g:fieldValue bean="${registrationFormInstance}" field="tertiaryQualification"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.address}">
				<li class="fieldcontain">
					<span id="address-label" class="property-label"><g:message code="registrationForm.address.label" default="Address" /></span>
					
						<span class="property-value" aria-labelledby="address-label"><g:fieldValue bean="${registrationFormInstance}" field="address"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.postalAddress}">
				<li class="fieldcontain">
					<span id="postalAddress-label" class="property-label"><g:message code="registrationForm.postalAddress.label" default="Postal Address" /></span>
					
						<span class="property-value" aria-labelledby="postalAddress-label"><g:fieldValue bean="${registrationFormInstance}" field="postalAddress"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.postalCode}">
				<li class="fieldcontain">
					<span id="postalCode-label" class="property-label"><g:message code="registrationForm.postalCode.label" default="Postal Code" /></span>
					
						<span class="property-value" aria-labelledby="postalCode-label"><g:fieldValue bean="${registrationFormInstance}" field="postalCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.contactNoHome}">
				<li class="fieldcontain">
					<span id="contactNoHome-label" class="property-label"><g:message code="registrationForm.contactNoHome.label" default="Contact No Home" /></span>
					
						<span class="property-value" aria-labelledby="contactNoHome-label"><g:fieldValue bean="${registrationFormInstance}" field="contactNoHome"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.city}">
				<li class="fieldcontain">
					<span id="city-label" class="property-label"><g:message code="registrationForm.city.label" default="City" /></span>
					
						<span class="property-value" aria-labelledby="city-label"><g:fieldValue bean="${registrationFormInstance}" field="city"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.region}">
				<li class="fieldcontain">
					<span id="region-label" class="property-label"><g:message code="registrationForm.region.label" default="Region" /></span>
					
						<span class="property-value" aria-labelledby="region-label"><g:link controller="region" action="show" id="${registrationFormInstance?.region?.id}">${registrationFormInstance?.region?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.country}">
				<li class="fieldcontain">
					<span id="country-label" class="property-label"><g:message code="registrationForm.country.label" default="Country" /></span>
					
						<span class="property-value" aria-labelledby="country-label"><g:link controller="country" action="show" id="${registrationFormInstance?.country?.id}">${registrationFormInstance?.country?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.contactNo}">
				<li class="fieldcontain">
					<span id="contactNo-label" class="property-label"><g:message code="registrationForm.contactNo.label" default="Contact No" /></span>
					
						<span class="property-value" aria-labelledby="contactNo-label"><g:fieldValue bean="${registrationFormInstance}" field="contactNo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.email}">
				<li class="fieldcontain">
					<span id="email-label" class="property-label"><g:message code="registrationForm.email.label" default="Email" /></span>
					
						<span class="property-value" aria-labelledby="email-label"><g:fieldValue bean="${registrationFormInstance}" field="email"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.numOfYears}">
				<li class="fieldcontain">
					<span id="numOfYears-label" class="property-label"><g:message code="registrationForm.numOfYears.label" default="Num Of Years" /></span>
					
						<span class="property-value" aria-labelledby="numOfYears-label"><g:fieldValue bean="${registrationFormInstance}" field="numOfYears"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.department}">
				<li class="fieldcontain">
					<span id="department-label" class="property-label"><g:message code="registrationForm.department.label" default="Department" /></span>
					
						<span class="property-value" aria-labelledby="department-label"><g:fieldValue bean="${registrationFormInstance}" field="department"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.jobTitle}">
				<li class="fieldcontain">
					<span id="jobTitle-label" class="property-label"><g:message code="registrationForm.jobTitle.label" default="Job Title" /></span>
					
						<span class="property-value" aria-labelledby="jobTitle-label"><g:fieldValue bean="${registrationFormInstance}" field="jobTitle"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.dateCreated}">
				<li class="fieldcontain">
					<span id="dateCreated-label" class="property-label"><g:message code="registrationForm.dateCreated.label" default="Date Created" /></span>
					
						<span class="property-value" aria-labelledby="dateCreated-label"><g:formatDate date="${registrationFormInstance?.dateCreated}" /></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.studentNo}">
				<li class="fieldcontain">
					<span id="studentNo-label" class="property-label"><g:message code="registrationForm.studentNo.label" default="Student No" /></span>
					
						<span class="property-value" aria-labelledby="studentNo-label"><g:fieldValue bean="${registrationFormInstance}" field="studentNo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.cellNo}">
				<li class="fieldcontain">
					<span id="cellNo-label" class="property-label"><g:message code="registrationForm.cellNo.label" default="Cell No" /></span>
					
						<span class="property-value" aria-labelledby="cellNo-label"><g:fieldValue bean="${registrationFormInstance}" field="cellNo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.examType}">
				<li class="fieldcontain">
					<span id="examType-label" class="property-label"><g:message code="registrationForm.examType.label" default="Exam Type" /></span>
					
						<span class="property-value" aria-labelledby="examType-label"><g:fieldValue bean="${registrationFormInstance}" field="examType"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyCity}">
				<li class="fieldcontain">
					<span id="companyCity-label" class="property-label"><g:message code="registrationForm.companyCity.label" default="Company City" /></span>
					
						<span class="property-value" aria-labelledby="companyCity-label"><g:fieldValue bean="${registrationFormInstance}" field="companyCity"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyComments}">
				<li class="fieldcontain">
					<span id="companyComments-label" class="property-label"><g:message code="registrationForm.companyComments.label" default="Company Comments" /></span>
					
						<span class="property-value" aria-labelledby="companyComments-label"><g:fieldValue bean="${registrationFormInstance}" field="companyComments"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyContactPerson}">
				<li class="fieldcontain">
					<span id="companyContactPerson-label" class="property-label"><g:message code="registrationForm.companyContactPerson.label" default="Company Contact Person" /></span>
					
						<span class="property-value" aria-labelledby="companyContactPerson-label"><g:fieldValue bean="${registrationFormInstance}" field="companyContactPerson"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyCountry}">
				<li class="fieldcontain">
					<span id="companyCountry-label" class="property-label"><g:message code="registrationForm.companyCountry.label" default="Company Country" /></span>
					
						<span class="property-value" aria-labelledby="companyCountry-label"><g:link controller="country" action="show" id="${registrationFormInstance?.companyCountry?.id}">${registrationFormInstance?.companyCountry?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyEmail}">
				<li class="fieldcontain">
					<span id="companyEmail-label" class="property-label"><g:message code="registrationForm.companyEmail.label" default="Company Email" /></span>
					
						<span class="property-value" aria-labelledby="companyEmail-label"><g:fieldValue bean="${registrationFormInstance}" field="companyEmail"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyName}">
				<li class="fieldcontain">
					<span id="companyName-label" class="property-label"><g:message code="registrationForm.companyName.label" default="Company Name" /></span>
					
						<span class="property-value" aria-labelledby="companyName-label"><g:fieldValue bean="${registrationFormInstance}" field="companyName"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyPhoneNo}">
				<li class="fieldcontain">
					<span id="companyPhoneNo-label" class="property-label"><g:message code="registrationForm.companyPhoneNo.label" default="Company Phone No" /></span>
					
						<span class="property-value" aria-labelledby="companyPhoneNo-label"><g:fieldValue bean="${registrationFormInstance}" field="companyPhoneNo"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyPhyAddress}">
				<li class="fieldcontain">
					<span id="companyPhyAddress-label" class="property-label"><g:message code="registrationForm.companyPhyAddress.label" default="Company Phy Address" /></span>
					
						<span class="property-value" aria-labelledby="companyPhyAddress-label"><g:fieldValue bean="${registrationFormInstance}" field="companyPhyAddress"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyPhyPostCode}">
				<li class="fieldcontain">
					<span id="companyPhyPostCode-label" class="property-label"><g:message code="registrationForm.companyPhyPostCode.label" default="Company Phy Post Code" /></span>
					
						<span class="property-value" aria-labelledby="companyPhyPostCode-label"><g:fieldValue bean="${registrationFormInstance}" field="companyPhyPostCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyPostalAddress}">
				<li class="fieldcontain">
					<span id="companyPostalAddress-label" class="property-label"><g:message code="registrationForm.companyPostalAddress.label" default="Company Postal Address" /></span>
					
						<span class="property-value" aria-labelledby="companyPostalAddress-label"><g:fieldValue bean="${registrationFormInstance}" field="companyPostalAddress"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyPostalPostCode}">
				<li class="fieldcontain">
					<span id="companyPostalPostCode-label" class="property-label"><g:message code="registrationForm.companyPostalPostCode.label" default="Company Postal Post Code" /></span>
					
						<span class="property-value" aria-labelledby="companyPostalPostCode-label"><g:fieldValue bean="${registrationFormInstance}" field="companyPostalPostCode"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.companyRegion}">
				<li class="fieldcontain">
					<span id="companyRegion-label" class="property-label"><g:message code="registrationForm.companyRegion.label" default="Company Region" /></span>
					
						<span class="property-value" aria-labelledby="companyRegion-label"><g:link controller="region" action="show" id="${registrationFormInstance?.companyRegion?.id}">${registrationFormInstance?.companyRegion?.encodeAsHTML()}</g:link></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.decoModules}">
				<li class="fieldcontain">
					<span id="decoModules-label" class="property-label"><g:message code="registrationForm.decoModules.label" default="Deco Modules" /></span>
					
						<g:each in="${registrationFormInstance.decoModules}" var="d">
						<span class="property-value" aria-labelledby="decoModules-label"><g:link controller="module" action="show" id="${d.id}">${d?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.decoModulesCompleted}">
				<li class="fieldcontain">
					<span id="decoModulesCompleted-label" class="property-label"><g:message code="registrationForm.decoModulesCompleted.label" default="Deco Modules Completed" /></span>
					
						<span class="property-value" aria-labelledby="decoModulesCompleted-label"><g:fieldValue bean="${registrationFormInstance}" field="decoModulesCompleted"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.hrManager}">
				<li class="fieldcontain">
					<span id="hrManager-label" class="property-label"><g:message code="registrationForm.hrManager.label" default="Hr Manager" /></span>
					
						<span class="property-value" aria-labelledby="hrManager-label"><g:fieldValue bean="${registrationFormInstance}" field="hrManager"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.hrManagerEmail}">
				<li class="fieldcontain">
					<span id="hrManagerEmail-label" class="property-label"><g:message code="registrationForm.hrManagerEmail.label" default="Hr Manager Email" /></span>
					
						<span class="property-value" aria-labelledby="hrManagerEmail-label"><g:fieldValue bean="${registrationFormInstance}" field="hrManagerEmail"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.isMember}">
				<li class="fieldcontain">
					<span id="isMember-label" class="property-label"><g:message code="registrationForm.isMember.label" default="Is Member" /></span>
					
						<span class="property-value" aria-labelledby="isMember-label"><g:fieldValue bean="${registrationFormInstance}" field="isMember"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.lineManager}">
				<li class="fieldcontain">
					<span id="lineManager-label" class="property-label"><g:message code="registrationForm.lineManager.label" default="Line Manager" /></span>
					
						<span class="property-value" aria-labelledby="lineManager-label"><g:fieldValue bean="${registrationFormInstance}" field="lineManager"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.lineManagerEmail}">
				<li class="fieldcontain">
					<span id="lineManagerEmail-label" class="property-label"><g:message code="registrationForm.lineManagerEmail.label" default="Line Manager Email" /></span>
					
						<span class="property-value" aria-labelledby="lineManagerEmail-label"><g:fieldValue bean="${registrationFormInstance}" field="lineManagerEmail"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.paintModules}">
				<li class="fieldcontain">
					<span id="paintModules-label" class="property-label"><g:message code="registrationForm.paintModules.label" default="Paint Modules" /></span>
					
						<g:each in="${registrationFormInstance.paintModules}" var="p">
						<span class="property-value" aria-labelledby="paintModules-label"><g:link controller="module" action="show" id="${p.id}">${p?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.paintModulesCompleted}">
				<li class="fieldcontain">
					<span id="paintModulesCompleted-label" class="property-label"><g:message code="registrationForm.paintModulesCompleted.label" default="Paint Modules Completed" /></span>
					
						<span class="property-value" aria-labelledby="paintModulesCompleted-label"><g:fieldValue bean="${registrationFormInstance}" field="paintModulesCompleted"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.rawModules}">
				<li class="fieldcontain">
					<span id="rawModules-label" class="property-label"><g:message code="registrationForm.rawModules.label" default="Raw Modules" /></span>
					
						<g:each in="${registrationFormInstance.rawModules}" var="r">
						<span class="property-value" aria-labelledby="rawModules-label"><g:link controller="module" action="show" id="${r.id}">${r?.encodeAsHTML()}</g:link></span>
						</g:each>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.rawModulesCompleted}">
				<li class="fieldcontain">
					<span id="rawModulesCompleted-label" class="property-label"><g:message code="registrationForm.rawModulesCompleted.label" default="Raw Modules Completed" /></span>
					
						<span class="property-value" aria-labelledby="rawModulesCompleted-label"><g:fieldValue bean="${registrationFormInstance}" field="rawModulesCompleted"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.seniorManageEmail}">
				<li class="fieldcontain">
					<span id="seniorManageEmail-label" class="property-label"><g:message code="registrationForm.seniorManageEmail.label" default="Senior Manage Email" /></span>
					
						<span class="property-value" aria-labelledby="seniorManageEmail-label"><g:fieldValue bean="${registrationFormInstance}" field="seniorManageEmail"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.seniorManager}">
				<li class="fieldcontain">
					<span id="seniorManager-label" class="property-label"><g:message code="registrationForm.seniorManager.label" default="Senior Manager" /></span>
					
						<span class="property-value" aria-labelledby="seniorManager-label"><g:fieldValue bean="${registrationFormInstance}" field="seniorManager"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.trainingManager}">
				<li class="fieldcontain">
					<span id="trainingManager-label" class="property-label"><g:message code="registrationForm.trainingManager.label" default="Training Manager" /></span>
					
						<span class="property-value" aria-labelledby="trainingManager-label"><g:fieldValue bean="${registrationFormInstance}" field="trainingManager"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.trainingManagerEmail}">
				<li class="fieldcontain">
					<span id="trainingManagerEmail-label" class="property-label"><g:message code="registrationForm.trainingManagerEmail.label" default="Training Manager Email" /></span>
					
						<span class="property-value" aria-labelledby="trainingManagerEmail-label"><g:fieldValue bean="${registrationFormInstance}" field="trainingManagerEmail"/></span>
					
				</li>
				</g:if>
			
				<g:if test="${registrationFormInstance?.vatNumber}">
				<li class="fieldcontain">
					<span id="vatNumber-label" class="property-label"><g:message code="registrationForm.vatNumber.label" default="Vat Number" /></span>
					
						<span class="property-value" aria-labelledby="vatNumber-label"><g:fieldValue bean="${registrationFormInstance}" field="vatNumber"/></span>
					
				</li>
				</g:if>
			
			</ol>
			<g:form>
				<fieldset class="buttons">
					<g:hiddenField name="id" value="${registrationFormInstance?.id}" />
					<g:link class="edit" action="edit" id="${registrationFormInstance?.id}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
					<g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" />
				</fieldset>
			</g:form>
		</div>
	</body>
</html>
