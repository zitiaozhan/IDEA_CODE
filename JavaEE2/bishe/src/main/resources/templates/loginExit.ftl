<!doctype html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
        <title>西安石油大学 - 教师科研项目</title>
		<link href="../style/layout.css" rel="stylesheet" type="text/css">
		<link href="../style/login.css" rel="stylesheet" type="text/css">
		<script type="text/javascript" src="../script/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="../script/js.js"></script>
	</head>

	<body class="login-bg">
		<!--登录-->
		<div class="main login-dom">
			<div class="successDom">
				<div class="four-top">
					<div class="logo1 text-center ">
						<a href="/index">
						<img src="../images/logo.png" width="180px" height="180px">
					</div>
					</a>
					<div class="logo-color margin-big-top text-big text-center">
						${msg!"未知错误"}
					</div>
					<div class="logo-color margin-big-top text-center text-small">
						<a class="text-white" id="href" href="/toLogin"> 页面正在跳转，请稍等 <b id="wait">2</a></b>
					</div>
				</div>
			</div>
			<div class="footer text-center text-small ">
                Copyright 2019 西安石油大学 版权所有
				<span class="margin-left margin-right">|</span>
			</div>
			<div class="popupDom">
				<div class="popup text-default">
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
		$(".successDom").fadeIn("fast", function() {
			$(".successDom").animate({
				"top": "50%"
			}, 500);
		})
	</script>
	<script type="text/javascript">
		(function() {
			var wait = document.getElementById('wait'),
				href = document.getElementById('href').href;
			var interval = setInterval(function() {
				var time = --wait.innerHTML;
				if (time <= 0) {
					location.href = href;
					clearInterval(interval);
				};
			}, 1000);
		})();
	</script>

</html>