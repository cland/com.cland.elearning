
<z:longbox id="companyIdBox" name="company.id" visible="false"  value="${personInstance?.company?.id}" />
<z:tabbox>
	<z:tabs>
		<z:tab label="Personal Details" />
		<z:tab label="Login Details" />
		<z:tab label="Contact Details" />
		<z:tab label="Employee Details" />
		<z:tab label="Other Details" />
		<z:tab label="Supporting Documents" />
	</z:tabs>
	<z:tabpanels>
		<z:tabpanel>
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
									<z:textbox name="firstName"
										value="${personInstance?.firstName}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.middleName.label',default:'Middle Name')}" />
									<z:textbox name="middleName"
										value="${personInstance?.middleName}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.lastName.label',default:'Last Name')}" />
									<z:textbox name="lastName" value="${personInstance?.lastName}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.knownAsName.label',default:'Known as')}" />
									<z:textbox name="knownAsName"
										value="${personInstance?.knownAsName}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.idNo.label',default:'Id No')}" />
									<z:textbox name="idNo" value="${personInstance?.idNo}" />
								</z:row>
								<sec:ifAnyGranted roles="ADMIN">
								<z:row>
									<z:label
										value="${message(code:'person.studentno.label',default:'Student No')}" />
									<z:textbox name="studentNo" value="${personInstance?.studentNo}" />
								</z:row>
								</sec:ifAnyGranted>
								<z:row>
									<z:label
										value="${message(code:'person.dateOfBirth.label',default:'Date Of Birth')}" />
									<z:datebox name="dateOfBirth"
										value="${personInstance?.dateOfBirth}" constraint="no future" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.gender.label',default:'Gender')}" />
									<zkui:select name="gender"
										from="${personInstance.constraints.gender.inList}"
										value="${personInstance?.gender}"
										valueMessagePrefix="person.gender" />
								</z:row>

								<z:row>
									<z:label
										value="${message(code:'person.homeLanguage.label',default:'Home Language')}" />
									<z:textbox name="homeLanguage"
										value="${personInstance?.homeLanguage}" />
								</z:row>


								<z:row>
									<z:label
										value="${message(code:'person.race.label',default:'Race')}" />
									<zkui:select name="race.id" optionKey="id"
										from="${com.cland.elearning.Race.list().sort(false){it.name}}"
										value="${personInstance?.race?.id}" />
								</z:row>

								<z:row>
									<z:label
										value="${message(code:'person.maritalStatus.label',default:'Marital Status')}" />
									<zkui:select name="maritalStatus"
										from="${personInstance.constraints.maritalStatus.inList}"
										value="${personInstance?.maritalStatus}"
										valueMessagePrefix="person.maritalStatus" />
								</z:row>
							</z:rows>
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
									<z:textbox style="height:60px" rows="4" name="tertiaryQualification"
										value="${personInstance?.tertiaryQualification}" />
								</z:row>
							</z:rows>
						</z:grid>
					</z:panelchildren>
				</z:panel>
			</z:hbox>
		</z:tabpanel>
		<z:tabpanel>
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
								
									<z:row>
										<z:label
											value="${message(code:'person.password.label',default:'Password')}" />
										<z:textbox name="password" type="password"
											value="${personInstance?.password}" />
									</z:row>
								
								<z:row>
									<z:label
										value="${message(code:'person.enabled.label',default:'Enabled')}" />
									<z:checkbox name="enabled" checked="${personInstance?.enabled}" />
								</z:row>
								<tmpl:editRoles />
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

							</z:rows>
						</z:grid>

					</z:panelchildren>
				</z:panel>
			</z:hbox>
		</z:tabpanel>
		<z:tabpanel>
			<z:hbox>
				<z:panel width="100%" height="" title="Contact Details"
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
										value="${message(code:'person.address.label',default:'Physical Address')}" />
									<z:textbox style="height:60px" rows="2" name="address"
										value="${personInstance?.address}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.postalAddress.label',default:'Postal Address')}" />
									<z:textbox style="height:60px" rows="2" name="postalAddress"
										value="${personInstance?.postalAddress}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.postalCode.label',default:'Postal code')}" />
									<z:textbox name="postalCode"
										value="${personInstance?.postalCode}" />
								</z:row>


								<z:row>
									<z:label
										value="${message(code:'person.region.label',default:'Region')}" />
									<zkui:select name="region.id" optionKey="id"
										from="${com.cland.elearning.Region.list().sort(false){it.name}}"
										value="${personInstance?.region?.id}" />
								</z:row>

								<z:row>
									<z:label
										value="${message(code:'person.country.label',default:'Country')}" />
									<zkui:select name="country.id" optionKey="id"
										from="${com.cland.elearning.Country.list().sort(false){it.name}}"
										value="${personInstance?.country?.id}" />
								</z:row>

								<z:row>
									<z:label
										value="${message(code:'person.contactNo.label',default:'Cell No')}" />
									<z:textbox name="contactNo"
										value="${personInstance?.contactNo}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.contactNoHome.label',default:'Home No')}" />
									<z:textbox name="contactNo"
										value="${personInstance?.contactNoHome}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.email.label',default:'Email')}" />
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
		</z:tabpanel>
		<z:tabpanel>
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
									
										
				<z:bandbox name="companyBandbox" width="400px" id="companyBandbox"  autodrop="true" readonly="true" onClick="companySearch.focus();" value="${personInstance?.company?.name}">
	        <z:bandpopup>
	            <z:vbox>
	                <z:hbox>
	                    <z:label value="Search"/>
	                    <z:textbox id="companySearch"/>
	                    <z:space />
						<z:toolbarbutton id="clearCompanyListBoxSearch" image="${fam.icon(name: 'drink_empty')}" label="${message(code:'default.button.clear.label',default:"Clear")}"/>
						<z:toolbarbutton id="newCompany" client_onClick="addOrganisation();return false;" label="${message(code:'default.button.add.label',default:"New Company")}"/>
	                </z:hbox>
	                <z:listbox id="companyListBox" width="650px" vflex="min">
	                </z:listbox>
	                <z:paging id="companyPaging" pageSize="5"/>
	            </z:vbox>
	        </z:bandpopup>
	    </z:bandbox>
										
																
								</z:row>
								
								<z:row>
									<z:label
										value="${message(code:'person.jobTitle.label',default:'Job title')}" />
									<z:textbox name="jobTitle"
										value="${personInstance?.jobTitle}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.department.label',default:'Department')}" />
									<z:textbox name="department"
										value="${personInstance?.department}" />
								</z:row>
								<z:row>
									<z:label
										value="${message(code:'person.numOfYears.label',default:'Number of years')}" />
									<z:textbox name="numOfYears"
										value="${personInstance?.numOfYears}" />
								</z:row>
							</z:rows>
						</z:grid>
					</z:panelchildren>
				</z:panel>
			</z:hbox>
		</z:tabpanel>
		<z:tabpanel>
			<z:hbox>
				<z:panel width="100%" height="" title="Disabilities" border="normal"
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
										value="${message(code:'person.disabilityYN.label',default:'Disability Yes/No?')}" />
									<zkui:select name="disabilityYN"
										from="${personInstance.constraints.disabilityYN.inList}"
										value="${personInstance?.disabilityYN}"
										valueMessagePrefix="person.disabilityYN"
										noSelection="['': '']" />
								</z:row>

								<z:row>
									<z:label
										value="${message(code:'person.disabilityList.label',default:'Disability List')}" />
									<z:textbox style="height:60px" rows="4" name="disabilityList"
										value="${personInstance?.disabilityList}" />
								</z:row>
							</z:rows>
						</z:grid>
					</z:panelchildren>
				</z:panel>
			</z:hbox>
		</z:tabpanel>

		<z:tabpanel visible="false">
			<z:hbox>
				<z:panel width="100%" height="" title="Supporting Documents"
					border="normal" collapsible="true">
					<z:panelchildren>
			Not yet available
		</z:panelchildren>
				</z:panel>
			</z:hbox>
		</z:tabpanel>
	</z:tabpanels>


</z:tabbox>