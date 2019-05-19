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
</head>
<body class="login-bg">

<!--登录-->
<div class="main">
    <div class="login-dom">
        <div class="logo text-center">
            <img src="../images/logo.png" width="180" height="180">
        </div>
        <!--注册-->
        <div class="text-big text-center logo-color">注册成为教师，科研让人类进步</div>
        <div class=" text-center margin-small-top logo-color text-small">论文 | 专利 | 教材 | ... </div>

        <#if validateCode??>
            <div class="text-big text-center" style="margin-top: 30px">注册或找回密码信息已发送成功，请注意查收</div>
        <#else>
            <#if msg??>
                <div class="text-big text-center" style="color: red;margin-top: 30px">${msg!"异常！"}</div>
            <#else>
                <#if param1??>
                <form class="register-form" name="register" action="/back/reg" method="post" autocomplete="off">
                <#else>
                <form class="register-form" name="register" action="/back/repassword" method="post" autocomplete="off">
                </#if>
                    <div class="reg-wrap border" style="height: 42px">
                        <div class="reg-number border-bottom">
                            <input type="email" class="fl padding-big-left reg-phone"
                                   name="mail" placeholder="请输入邮箱" datatype="m"
                                   required="required"/>
                        </div>
                    </div>
                    <div class="margin-large-top padding-big-top">
                        <input type="submit" class="btn text-big" value="发送验证信息">
                    </div>
                </form>
            </#if>
        </#if>

        <div class="forget">
            <a href="/toRepassword" class="forget-pwd text-small fl">忘记登录密码？</a>
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