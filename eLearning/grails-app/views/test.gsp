<html>
<head>
<meta name="layout" content="main" />
<title>Test</title>
<link href='http://fonts.googleapis.com/css?family=Droid+Serif' rel='stylesheet' type='text/css'>

<script src="/eLearning/static/js/jquery-1.9.1.js" type="text/javascript" ></script>
<script type="text/javascript">
$(document).ready(function() {
	$("legend").click(function(){
		$(this).children("b").toggleClass("collapsed");
		$(this).nextAll("div.content").slideToggle(500);
	});
		$("fieldset.topleft1 legend").children("b").addClass("collapsed");
		$("fieldset.topleft1 legend").nextAll("div.content").hide();
		$("fieldset.topleft2 legend").children("b").addClass("collapsed");
		$("fieldset.topleft2 legend").nextAll("div.content").hide();
});
</script>


</head>
<body>

	<fieldset>
<legend><b>&raquo;</b> Module </legend>
<h1> Module 01</h1>
<div class="content">
<ul>
<li><a href="http://www.developersnippets.com" title="DeveloperSnippets.com">DeveloperSnippets.com</a></li>
<li><a href="http://www.WittySparks.com" title="WittySparks.com">WittySparks.com</a></li>
<li><a href="http://videos.wittysparks.com" title="Videos.WittySparks.com">Videos.WittySparks.com</a></li>
<li><a href="http://www.WittySparks.com" title="Flair.WittySparks.com">Flair.WittySparks.com</a></li>
<li><a href="http://www.TechVideoBytes.com" title="TechVideoBytes.com">TechVideoBytes.com</a></li>
<li><a href="http://www.IndianWebShowcase.com" title="IndianWebShowcase.com">IndianWebShowcase.com</a></li>
<li><a href="http://www.snehaH.com" title="snehaH.com">snehaH.com</a></li>
<li><a href="http://www.FullOfEntertainment.com" title="FullOfEntertainment.com">FullOfEntertainment.com</a></li>
</ul>
</div>
</fieldset>
</body>
</html>