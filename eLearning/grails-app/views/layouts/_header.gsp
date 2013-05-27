<div id="main-header" role="banner">

	<div id="header">
	<g:render template="/layouts/siteSearch" />
		<p>
			<a class="header-main" href="${resource(dir:'')}"> eLearning </a>
		</p>
		<p class="header-sub">When's your next course?</p>
		<div id="loginHeader">
			<g:loginControl />
		</div>
	</div>
		<div id="grailsLogo">
		<a href="/eLearning/"> <img
			src="${resource(dir: 'images', file: 'cland_logo.png')}"
			alt="Grails" /></a>
	</div>
</div>
