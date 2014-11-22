<html>
<head>
<meta name="layout" content="main" />
<title>eLearning Home</title>
</head>
<body>

	<z:window style="padding:5px"
		apply="com.cland.elearning.home.HomeComposer">
		<z:space />
		<div>
			<h2>
				Welcome
				<sec:ifLoggedIn>
					<span style="color: green">
					<i><sec:loggedInUserInfo field="username" /></i>
					
					</span>
				</sec:ifLoggedIn>
				to e-Learning
			</h2>
		</div>
		<z:space />
		<z:hbox>
			<z:panel width="100%" minheight="300" title="My Work"
				collapsible="true" sizable="true">
				<z:panelchildren>
					<z:tabbox width="400px" height="100%">
						<z:tabs>
							<z:tab label="Courses" />
							<z:tab label="Results" />
						</z:tabs>
						<z:tabpanels>
							<z:tabpanel>
								<sec:ifLoggedIn>
									<z:grid id="myRegGrid"
										emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
										<z:columns sizable="true">
											<z:column width="150px"
												label="${message(code: 'registration.course.name.label', default: 'Course')}" />
											<z:column
												label="${message(code: 'registration.regDate.label', default: 'Registration Date')}" />
											<z:column
												label="${message(code: 'registration.learner.firstName.label', default: 'Learner')}" />
											<z:column />
										</z:columns>
									</z:grid>
									<z:paging autohide="true" id="paging" pageSize="15" />
								</sec:ifLoggedIn>
								<sec:ifNotLoggedIn>
									<center>
										<span style="color:red">You have not registered for any courses!</span>
									</center>
								</sec:ifNotLoggedIn>
							</z:tabpanel>

							<z:tabpanel>
							<sec:ifLoggedIn>
									<z:grid id="myResultsGrid"
										emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
										<z:columns sizable="true">
										<z:column label="${message(code: 'resultSummary.register.learner.name.label', default: 'Learner')}" />
										<z:column label="${message(code: 'resultSummary.register.course.name.label', default: 'Course')}" />
										<z:column label="${message(code: 'resultSummary.module.name.label', default: 'Module')}" />
										<z:column label="${message(code: 'resultSummary.result.label', default: 'Result')}" /> 
										<z:column width="80px"/>		
											
										</z:columns>
									</z:grid>
									
								</sec:ifLoggedIn>
								<sec:ifNotLoggedIn>
									<center>
										<span style="color:red">No results available!</span>
									</center>
								</sec:ifNotLoggedIn>
							</z:tabpanel>
						</z:tabpanels>
					</z:tabbox>

				</z:panelchildren>

			</z:panel>
			<z:panel width="435px" minheight="300" title="My Course Events"
				border="normal" collapsible="true" sizable="true">
				<z:panelchildren>
				<sec:ifLoggedIn>
					<z:grid id="myEventsGrid"
						emptyMessage="${message(code:'emptyMessage',default:'No Record')}">
						<z:columns sizable="true">
						<z:column label="${message(code: 'event.title.label', default: 'Title')}" />
						<z:column label="${message(code: 'event.when.label', default: 'When')}" />
						<z:column label="${message(code: 'event.location.label', default: 'Location')}" /> 		
						</z:columns>
					</z:grid>
				</sec:ifLoggedIn>
				<sec:ifNotLoggedIn>
					<center>No course events available yet</center>
				</sec:ifNotLoggedIn>	
				</z:panelchildren>
			</z:panel>
		</z:hbox>

	</z:window>
</body>
</html>