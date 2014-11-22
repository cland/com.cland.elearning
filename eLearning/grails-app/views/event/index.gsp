<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
  <meta name="layout" content="main">

  <title>Calendar</title>
  <r:require module="calendar" />
</head>
<body>
	<sec:ifAnyGranted roles="ADMIN,TUTOR">	
		<div class="nav nav-level2" role="navigation" style="">
	
		    <ul>
		        <li><g:link action="create" class="create">New Event</g:link></li>
		    </ul>
		</div>
	</sec:ifAnyGranted>
    <div id="calendar"></div>

</body>
</html>