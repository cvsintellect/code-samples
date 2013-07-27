<!DOCTYPE html>
<html ng-app="CVsIntellect">
<head>
<title>CVsIntellect :: Code Sample</title>
<script type="text/javascript">
    if (window.location.hash == '#_=_'){
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

<link href="/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="/bootstrap/css/bootstrap-responsive.css" rel="stylesheet">
<link href="/css/application.css"rel="stylesheet" type="text/css"  media="screen">
<link href="/css/angular-animate.css"rel="stylesheet" type="text/css"  media="screen">
</head>

<body>
	<div class="container">
		<div id='edit' class='resume_editor' style="width: 600px;">
			<div ng-view id='ng-view'></div>
		</div>
	</div>
	
	<!-- JS -->
 	<script src="/js/lib/angular.1.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/angular-resource.1.1.4.min.js" type="text/javascript"></script>
	<script src="/js/lib/ui-bootstrap-tpls-0.5.0-SNAPSHOT.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-1.9.1.min.js" type="text/javascript"></script>
	<script src="/js/lib/jquery-ui-1.9.1.custom.js" type="text/javascript"></script>
	<script src="/bootstrap/js/bootstrap.js" type="text/javascript"></script>
	<script src="/js/lib/jquery.html5-placeholder-shim.js" type="text/javascript"></script>
	<script src="/js/lib/angular-placeholder-shim.js" type="text/javascript"></script>

	<script src="/ckeditor/ckeditor.js" type="text/javascript"></script>

	<script src="/js/application/app.js" type="text/javascript"></script>
	<script src="/js/application/services.js" type="text/javascript"></script>
	<script src="/js/application/user-controller.js" type="text/javascript"></script>
	<script src="/js/application/entry-controller.js" type="text/javascript"></script>
</body>
</html>