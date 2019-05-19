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
    <script type="text/javascript" src="../script/jquery.cookie.js"></script>
    <script type="text/javascript" src="../script/other_script.js"></script>
</head>
<body class="login-bg" style="height: 767px">

		<!--登录-->
		<div class="main">
			<div class="login-dom">
				<div class="logo text-center">
					<img src="../images/logo.png" width="180" height="180">
				</div>
				<!--注册-->
				<div class="text-big text-center logo-color">注册成为教师，科研让人类进步</div>
				<div class=" text-center margin-small-top logo-color text-small">论文 | 专利 | 教材 | ... </div>
				<form class="register-form" name="register" action="/back/register" method="post" autocomplete="off">
					<div class="reg-wrap border" style="height: 162px">
						<div class="reg-number border-bottom">
                            <input type="number" class="fl padding-big-left reg-phone" autofocus
                                   name="number" placeholder="请输入编号" minlength="3" maxlength="15"
								   nullmsg="popup('请填写编号')" required="required"/>
						</div>
						<div class="reg-number border-bottom">
                            <input type="password" class="fl padding-big-left reg-phone"
                                   id="pwd" name="password" placeholder="请输入密码" minlength="6"
								   nullmsg="popup('请填写密码')" required="required"/>
						</div>
						<div class="reg-number border-bottom">
                            <input type="password" class="fl padding-big-left reg-phone"
                                   id="repwd" placeholder="重复输入密码" minlength="6"
								   nullmsg="popup('请重复填写密码')" required="required"
								   onmouseover="validate()"/>
						</div>
						<div class="reg-number border-bottom">
							<input type="email" class="fl padding-big-left reg-phone"
                                   name="mail" value="${mail!" "}" readonly="readonly"
								   placeholder="请输入邮箱" datatype="m"
								   nullmsg="popup('请填写邮箱')" required="required"/>
						</div>
					</div>
					<#if msg??>
                    <div class="text-big text-center" style="color: red;margin-top: 30px">
						${msg!"注册异常"}
					</div>
					</#if>
                    <div class="warn_div text-big text-center" style="color: red;margin-top: 30px">
					</div>
					<div class="margin-large-top padding-big-top">
						<input type="submit" id="register-submit" class="btn text-big" value="立即注册">
					</div>
				</form>
				<div class="forget">
                    <a href="#" onclick="modify_site_tip('需要验证邮箱才能找回密码','/backJumpTo?path=mailInput&param1=repassword')" class="forget-pwd text-small fl">忘记登录密码？</a>
					<a href="/toLogin" class="forget-new text-small fr" id="forger-login">已有账号，立即登录</a>
				</div>
			</div>

            <div class="footer text-center  text-small">
                Copyright 2019 西安石油大学 版权所有
                <span class="footer text-center text-small ie">|</span>
            </div>

            <div class="popupDom">
                <div class="popup  text-default">
                </div>
            </div>
        </div>
</body>

</html>