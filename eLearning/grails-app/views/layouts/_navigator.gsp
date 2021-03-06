<div class="nav" role="navigation">
	<ul>
		<sec:ifLoggedIn>
			<li><a class="home" href="${createLink(uri: '/')}"><g:message
						code="default.home.label" /></a></li>
			<li><g:link controller="course" action="list">Courses</g:link></li>

			<li><g:link controller="person" action="show"
					id="${sec.loggedInUserInfo(field:'id')}">My Profile
		<sec:ifAnyGranted roles="LEARNER,TUTOR">
				and Courses
		</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ADMIN,TUTOR">				
				<li><g:link controller="module" action="list">Modules</g:link></li>
<%--				<li><g:link controller="resultSummary" action="list">Registered Learners</g:link></li>--%>
				<li><g:link controller="person" action="learners">Learners</g:link></li>	
				<li><g:link controller="person" action="tutors">Tutors</g:link></li>											
			</sec:ifAnyGranted>
			<sec:ifAnyGranted roles="ADMIN">
				<li><g:link controller="certificate" action="list">Certificates</g:link></li>	
				<li><g:link controller="resultSummary" action="export">Export-Panel</g:link></li>
			</sec:ifAnyGranted>
		</g:link></li>
		<li><g:link controller="event" action="index">Calendar</g:link></li>	
		<li><g:link controller="logout" action="index">Logout</g:link></li>
		</sec:ifLoggedIn>

		<sec:ifNotLoggedIn>
			<li><g:link controller="login" action="index">&nbsp;</g:link></li>
		</sec:ifNotLoggedIn>
	</ul>
</div>
