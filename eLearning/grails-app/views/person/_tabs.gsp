<!-- The tabs -->
<div id="tabs" style="display: none;">
	<ul>
		<li><a href="#tab-person">Personal Details</a></li>
		<li><a href="#tab-employee">Employee Details</a></li>
		<li id='tab_learner_head'><a href="#tab-learner">Courses & Results</a></li>
		<li><a href="#tab-attachments">Supporting Documents</a></li>
	</ul>
	<div id="tab-person">
		<z:hlayout>
			<z:panel width="435px">
				<z:panelchildren>
					<z:grid>
						<z:rows>
							<z:row>
								<z:label value="Known as: " />
								<z:label value="${personInstance?.knownAsName }" />
							</z:row>
							<z:row>
								<z:label value="Id Number: " />
								<z:label value="${personInstance.idNo }" />
							</z:row>
							<z:row>
								<z:label value="Gender: " />
								<z:label value="${personInstance.gender }" />
							</z:row>
							<z:row>
								<z:label value="Race: " />
								<z:label value="${personInstance?.race?.name }" />
							</z:row>
							<z:row>
								<z:label value="Marital status: " />
								<z:label value="${personInstance.maritalStatus }" />
							</z:row>
						</z:rows>
					</z:grid>
				</z:panelchildren>
			</z:panel>

			<z:panel width="435px">
				<z:panelchildren>
					<z:grid>
						<z:rows>
							<z:row>
								<z:label value="Date of birth: " />
								<z:label
									value="${personInstance.dateOfBirth.format("dd MMM yyyy") }" />
							</z:row>
							<z:row>
								<z:label value="Home Language: " />
								<z:label
									value="${personInstance.homeLanguage}" />
							</z:row>
							<z:row>
								<z:label value="School Qualification: " />
								<z:label
									value="${personInstance.schoolQualification }" />
							</z:row>
							<z:row>
								<z:label value="Tertiary Qualification: " />
								<z:label
									value="${personInstance.tertiaryQualification }" />
							</z:row>
						</z:rows>
					</z:grid>
				</z:panelchildren>
			</z:panel>
		</z:hlayout>
	</div>
	<div id="tab-employee">
		<z:hlayout>
			<z:panel width="435px">
				<z:panelchildren>
					<z:grid>
						<z:rows>
							<z:row>
								<z:label value="Employee name: " />
								<z:label value="${personInstance?.company?.name }" />
							</z:row>
							<z:row>
								<z:label value="Location: " />
								<z:label value="${personInstance?.company?.region?.name }, ${personInstance?.company?.country?.name }" />
							</z:row>
							<z:row>
								<z:label value="Physical code: " />
								<z:label value="${personInstance?.company?.phyAddress }" />
							</z:row>
							<z:row>
								<z:label value="Physical Code: " />
								<z:label value="${personInstance?.company?.phyPostCode }" />
							</z:row>
							<z:row>
								<z:label value="Job Title: " />
								<z:label value="${personInstance?.jobTitle }" />
							</z:row>
							<z:row>
								<z:label value="Department: " />
								<z:label value="${personInstance?.department }" />
							</z:row>
							<z:row>
								<z:label value="Number of years: " />
								<z:label value="${personInstance?.numOfYears }" />
							</z:row>
						</z:rows>
					</z:grid>
				</z:panelchildren>
			</z:panel>

			<z:panel width="435px">
				<z:panelchildren>
					<z:grid>
						<z:rows>
							<z:row>
								<z:label value="Contact No.: " />
								<z:label value="${personInstance?.company?.phoneNo }" />
							</z:row>
							
							<z:row>
								<z:label value="Contact Person: " />
								<z:label value="${personInstance?.company?.contactPerson }" />
							</z:row>
							<z:row>
								<z:label value="Postal address: " />
								<z:label value="${personInstance?.company?.postalAddress }" />
							</z:row>
							<z:row>
								<z:label value="Postal code: " />
								<z:label value="${personInstance?.company?.postalPostCode }" />
							</z:row>
						</z:rows>
					</z:grid>
				</z:panelchildren>
			</z:panel>
		</z:hlayout>
	</div>
	<div id="tab-learner">
		<div id="course_grid" style="padding: 5px;">
			<table id="course_list" class="scroll jqTable"></table>
			<!-- pager will hold our paginator -->
			<div id="course_list_pager" class="scroll"
				style="text-align: center;"></div>
		</div>
	</div>
	<div id="tab-attachments">
		
	</div>
</div>
<!--  End tabs -->