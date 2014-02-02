<div class="footer" id="footer">
<a href="/eLearning/">Home</a> |
<sec:ifNotLoggedIn>
<g:link controller="login" action="index" >Login</g:link> |
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
<g:link controller="course" action="list" >Courses</g:link> |
<sec:ifAnyGranted roles="ADMIN,TUTOR">
<g:link controller="module" action="list" >Modules</g:link> |
<g:link controller="subModule" action="list" >Sub-Modules</g:link> |
<g:link controller="exam" action="list" >Exams</g:link> |  
<g:link controller="courseEvent" action="list">Course Events</g:link> |
<g:link controller="resultSummary" action="list">Results</g:link> |
<g:link controller="venue" action="fusion" >Venues</g:link> |
<g:link controller="organisation" action="list" >Organisations</g:link> |
<g:link controller="region" action="list" >Regions</g:link> |
<g:link controller="country" action="list" >Countries</g:link> |
<g:link controller="admin" action="index">Technical</g:link> |
</sec:ifAnyGranted>
<g:link controller="logout" action="index" >Logout</g:link>
</sec:ifLoggedIn>
<br/>
<g:copyright startYear="2013">Tagumi Systems</g:copyright>
</div>