<!DOCTYPE html>
<html ng-app="CVsIntellect">
<head>
<title>CVsIntellect :: Code Sample</title>
<script type="text/javascript">
	if (window.location.hash == '#_=_') {
		window.location.hash = '';
	}
</script>

<!--[if IE]>
	<style type="text/css">
		#ng-view #pagesetup{
			padding:60px;
		}
	</style>
<![endif]-->

<!-- CSS -->
<link href='http://fonts.googleapis.com/css?family=Maven+Pro:400,500,700' rel='stylesheet' type='text/css'>

<link href="/static/lib/bootstrap/css/bootstrap.min.css" rel="stylesheet">
<link href="/static/lib/bootstrap/css/bootstrap-theme.min.css" rel="stylesheet">
<link href="/static/css/application.css" rel="stylesheet" type="text/css" media="screen">
<link href="/static/css/angular-animate.css" rel="stylesheet" type="text/css" media="screen">
</head>

<body>
	<a href="/user/latextopdf">LaTeX To PDF</a>

	<div class="container">
		<div id='edit' class='resume_editor' style="width: 600px;">
			<div ng-view id='ng-view'></div>
		</div>
	</div>

	<!-- JS -->
	<script src="/static/js/lib/angular.1.1.4.min.js" type="text/javascript"></script>
	<script src="/static/js/lib/angular-resource.1.1.4.min.js" type="text/javascript"></script>
	<script src="/static/js/lib/ui-bootstrap-tpls-0.5.0-SNAPSHOT.min.js" type="text/javascript"></script>
	<script src="/static/js/lib/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="/static/js/lib/jquery-ui-1.9.1.custom.js" type="text/javascript"></script>
	<script src="/static/js/lib/jquery.html5-placeholder-shim.js" type="text/javascript"></script>
	<script src="/static/js/lib/angular-placeholder-shim.js" type="text/javascript"></script>

	<script src="/static/lib/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
	<script src="/static/lib/ckeditor/ckeditor.js" type="text/javascript"></script>

	<script src="/static/js/application/app.js" type="text/javascript"></script>
	<script src="/static/js/application/services.js" type="text/javascript"></script>
	<script src="/static/js/application/user-controller.js" type="text/javascript"></script>
	<script src="/static/js/application/entry-controller.js" type="text/javascript"></script>
</body>
</html>