<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<title><g:layoutTitle default="eLearning"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link rel="shortcut icon" href="${resource(dir: 'images', file: 'favicon.ico')}" type="image/x-icon">
		<link rel="apple-touch-icon" href="${resource(dir: 'images', file: 'apple-touch-icon.png')}">
		<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir: 'images', file: 'apple-touch-icon-retina.png')}">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'main.css')}" type="text/css">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'print.css')}" type="text/css" media="print">
		<link rel="stylesheet" href="${resource(dir: 'css', file: 'mobile.css')}" type="text/css">
			<link rel="stylesheet" href="${resource(dir: 'css', file: 'fieldset.css')}" type="text/css">
		<g:layoutHead/>
		<r:layoutResources />
		<z:resources/>
		<style>
		/* Custom rules */
		.ui-jqgrid .ui-jqgrid-htable th div {
	height: auto;
	overflow: hidden;
	padding-right: 4px;
	padding-top: 2px;
	position: relative;
	vertical-align: text-top;
	white-space: normal !important;
}
.ui-jqgrid .ui-jqgrid-htable th {
    height: 32px;
    padding: 0 2px;
}
.ui-jqgrid .ui-jqgrid-view {
    font-size: 12px; 
}
		
.subgrid-data .ui-state-default { background:#768074;border:1px solid #ccc;}
		</style>
	</head>
	<body>
		<div id="grailsLogo" role="banner">
		<a href="/eLearning"><img src="${resource(dir: 'images', file: 'sapma_logo64.png')}" alt="Cland"/></a>
		<div class="float-right">
				<div id="current-user"> <label><span class="r-arrow"></span></label>
				<sec:ifLoggedIn>
					<span class="current-user-label"> Logged in as: </span>
					<span class="current-user-value"><sec:loggedInUserInfo field="username" /></span>						
				</sec:ifLoggedIn> 
				<sec:ifNotLoggedIn>Anonymous</sec:ifNotLoggedIn>
			</div>
		</div>
		</div>
		<g:render template="/layouts/navigator" />
		<g:layoutBody/>
		<g:render template="/layouts/footer" />
		<div id="spinner" class="spinner" style="display:none;"><g:message code="spinner.alt" default="Loading&hellip;"/></div>
		<g:javascript library="application"/>
		<r:layoutResources />
	</body>
</html>
