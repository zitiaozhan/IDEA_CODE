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
    <style>
        .gt_ajax_tip .ready{right: -56px;}
    </style>
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
					创建账号，驰骋科研
				</p>
				<p class=" text-center margin-small-top logo-color text-small">
                    论文 | 专利 | 教材 | ...
				</p>
				<form class="register-form" action="index.html" method="post" autocomplete="off">
					<div class="num-box ">
						<input type="text" placeholder="请输入邮箱" autofocus="true"
                               id="num-name" name="mail" datatype="m"
                               nullmsg="请填写正确的邮箱" required="required">
					</div>
					<div class="slider-box">
						<div id="captcha" style="margin-left: 12px;">
						</div>
					</div>
					<input type="submit" class="btn text-center login-btn" value="发送验证码">
					<div class="forget">
						<a href="/toRegister" class="forget-pwd text-small fl">创建一个新账号</a>
						<a href="/toLogin" class="forget-new text-small fr" id="forger-login">已有账号，立即登录</a>
					</div>
				</form>
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
<script type="text/javascript" src="Js/Validform_v5.3.2_min.js"></script>
<script type="text/javascript">

    function popup_msg(msg){
        $(".popup").html(""+msg+"");
        $(".popupDom").animate({
            "top": "0px"
        }, 400);
        setTimeout(function() {
            $(".popupDom").animate({
                "top": "-40px"
            }, 400);
        }, 2000);

    }
    /*动画（注册）*/
    $(document).ready(function(e) {
        /*极验*/
        var geetest_status = false;

        var captchaObj = new Geetest({
            gt: "",
            challenge: "",
            product: "float" // 产品形式
        }); // 实例化，config为配置参数

        captchaObj.appendTo("#captcha"); // 绑定到id为captcha的元素上

        captchaObj.onSuccess(function () {
            geetest_status=true;
        });
        /*调用验证*/
        $('.register-form').Validform({
            ajaxPost:true,
            tiptype:function(msg){
                if(msg)popup_msg(''+msg+'');
            },
            beforeSubmit:function(curform){
                if(geetest_status == false){
                    popup_msg('请拖动滑块,完成验证');
                    return false;
                }
                return true;
            },
            callback:function(data){
                popup_msg(''+data.info+'');
                if(data.status == 1){
                    window.location.href=""+data.url+""
                }
            }
        });
    });
</script>
</html>