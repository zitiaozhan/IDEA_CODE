<!DOCTYPE html>
<html lang="zh-CN"
      class="is-AppPromotionBarVisible cssanimations csstransforms csstransitions flexbox no-touchevents no-mobile">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="renderer" content="webkit">
    <meta name="description" content="一个真实的网络问答社区，帮助你寻找答案，分享知识。">
    <meta name="viewport" content="user-scalable=no, width=device-width, initial-scale=1.0, maximum-scale=1.0">
    <title>无涯 - 与世界分享你的知识、经验和见解</title>
    <link rel="dns-prefetch" href="">
    <link rel="stylesheet" href="../styles/login.css">

</head>
<body class="zhi  no-auth">
<div class="index-main">
    <div class="index-main-body">
        <div class="index-header">
            <h1 class="logo hide-text"><img src="../images/res/nk.png" alt=""></h1>
            <h2 class="subtitle">
                ${msg!"与世界分享你的知识、经验和见解"}
            </h2>
        </div>
        <div class="desk-front sign-flow clearfix sign-flow-simple">
            <div class="view view-signin" data-za-module="SignInForm" style="display: block;">
                <form action="/reg" id="regloginform" method="post">
                    <input type="hidden" name="_xsrf" value="21aa1c8d254df2899b23ab9afbd62a53">
                    <div class="group-inputs">
                        <div class="email input-wrapper">
                            <input type="text" name="userName" aria-label="用户名" placeholder="用户名" required="required">
                        </div>
                        <div class="input-wrapper">
                            <input id="pwd" type="password" name="password" aria-label="密码" placeholder="密码" required="required">
                        </div>
                    </div>
                    <input type="hidden" name="next" value="${next!"2f696e646578"}"/>
                    <div class="button-wrapper command clearfix">
                        <button name="login" class="sign-button submit" type="submit" onclick="form=document.getElementById('regloginform');form.action='/login'">登录</button>
                        <button name="reg" class="sign-button submit" type="submit" onclick="form=document.getElementById('regloginform');form.action='/reg'">注册</button>
                    </div>
                    <div class="signin-misc-wrapper clearfix">
                        <label class="remember-me">
                            <input type="checkbox" name="rememberme" checked="" value="true"> 记住我
                        </label>
                        <a class="unable-login" href="#">无法登录?</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>