<div class="footer" id="footer">
<a href="/eLearning/">Home</a> |
<sec:ifNotLoggedIn>
<g:link controller="login" action="index" >Login</g:link> |
</sec:ifNotLoggedIn>
<sec:ifLoggedIn>
<g:link controller="course" action="list" >Courses</g:link> |
<g:link controller="module" action="list" >Modules</g:link> |
<g:link controller="subModule" action="list" >Sub-Modules</g:link> |
<g:link controller="exam" action="list" >Exams</g:link> |  
<g:link controller="courseEvent" action="list">Course Events</g:link> |
<g:link controller="courseResult" action="list">Course Results</g:link> |
<g:link controller="venue" action="fusion" >Venues</g:link> |
<g:link controller="organisation" action="list" >Organisations</g:link> |

<g:link controller="logout" action="index" >Logout</g:link> |

<sec:ifAnyGranted roles="ADMIN">
<g:link controller="admin" action="index">System Admin</g:link>
</sec:ifAnyGranted>
</sec:ifLoggedIn>
<br/>
<g:copyright startYear="2013">TI Systems</g:copyright>
</div>