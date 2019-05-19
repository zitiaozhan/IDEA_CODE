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

		<div class="main ">
			<!--登录-->
			<div class="login-dom login-max">
				<div class="logo text-center">
					<a href="#">
					<img src="../images/logo.png" width="180px" height="180px">
					</a>
				</div>
				<div class="login container " id="login">
					<p class="text-big text-center logo-color">
                        科研让人类进步
					</p>
					<p class=" text-center margin-small-top logo-color text-small">
                        论文 | 专利 | 教材 | ...
					</p>
					<form class="login-form" action="/back/login" method="post" autocomplete="off">
						<div class="login-box border text-small" id="box">
							<div class="name border-bottom">
								<input type="number" placeholder="编号" id="username"  minlength="3" maxlength="15"
									   name="number" datatype="*" nullmsg="popup('请填写编号')"
									   required="required">
							</div>
							<div class="pwd">
								<input type="password" placeholder="密码" datatype="*" id="password"
									   name="password" nullmsg="popup('请填写密码')"
                                       minlength="6" required="required">
							</div>
						</div>
						<#if msg??>
							<div class="text-big text-center" style="color: red;margin-top: 20px">
								${msg!"登录异常"}
							</div>
						</#if>
						<input type="submit" class="btn text-center login-btn" value="立即登录">
					</form>
					<div class="forget">
						<a href="#" onclick="modify_site_tip('需要验证邮箱才能找回密码','/backJumpTo?path=mailInput&param1=repassword')" class="forget-pwd text-small fl">忘记登录密码？</a>
						<a href="/toRegister" class="forget-new text-small fr" id="forget-new">创建一个新账号</a>
					</div>
				</div>
			</div>

            <div class="footer text-center  text-small">
                Copyright 2019 西安石油大学 版权所有
                <span class="footer text-center text-small ie">|</span>
            </div>
            <div class="popupDom">
                <div class="popup text-default">
                </div>
            </div>
        </div>
</body>
<script type="text/javascript" src="../script/Validform_v5.3.2_min.js"></script>

</html>