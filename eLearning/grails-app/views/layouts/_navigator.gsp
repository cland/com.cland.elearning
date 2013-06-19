<div class="nav" role="navigation">
	<ul>
		<sec:ifLoggedIn>
		<li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label" /></a></li>
		<li><g:link controller="module" action="list">Modules</g:link></li>		
		<li><g:link controller="course" action="list">Courses</g:link></li>
		<li><g:link controller="person" action="list">People</g:link></li>						
		<sec:ifAnyGranted roles="ADMIN">
			<li><g:link controller="registration" action="list">Registrations</g:link></li>		
			<li><g:link controller="resultSummary" action="list">Results</g:link></li>			
			<li><g:link controller="organisation" action="list">Organisations</g:link></li>
			<li><g:link controller="admin" action="index">Technical</g:link></li>
		</sec:ifAnyGranted>
	
			<li><g:link controller="logout" action="index">Logout</g:link></li>
		</sec:ifLoggedIn>
		<sec:ifNotLoggedIn>
			<li><g:link controller="login" action="index">Login</g:link></li>
		</sec:ifNotLoggedIn>
	</ul>
</div>
