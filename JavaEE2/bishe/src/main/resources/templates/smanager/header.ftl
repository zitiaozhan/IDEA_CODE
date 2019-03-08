<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>控制台</title>
    <link rel="stylesheet" type="text/css" href="../../style/identify.css" />
    <link rel="stylesheet" type="text/css" href="../../style/layout.css" />
    <link href="../../style/login.css" rel="stylesheet" type="text/css">
    <link rel="stylesheet" type="text/css" href="../../style/account.css" />
    <link rel="stylesheet" type="text/css" href="../../style/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/control_index.css" />
    <link rel="stylesheet" type="text/css" href="../../style/other_style.css">

    <script type="text/javascript" src="../../script/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../../script/layer/layer.js"></script>
    <script type="text/javascript" src="../../script/haidao.offcial.general.js"></script>
    <script type="text/javascript" src="../../script/select.js"></script>
    <script type="text/javascript" src="../../script/haidao.validate.js"></script>
    <script type="text/javascript" src="../../script/other_script.js"></script>
</head>

<body>
<div class="view-topbar">
    <div class="topbar-console">
        <div class="tobar-head fl">
            <a href="/index" class="topbar-logo fl">
                <span><img src="../../images/logo.png" width="20" height="20"/></span>
            </a>
            <a href="/index" class="topbar-home-link topbar-btn text-center fl"><span>管理控制台</span></a>
        </div>
    </div>
    <div class="topbar-info">
        <ul class="fr">
            <li class="fl dropdown topbar-notice topbar-btn">
                <a href="/message" class="dropdown-toggle">
                    <span class="icon-notice"></span>
                    <#if unreadSum gt 0>
                        <span class="topbar-num have">${unreadSum!0}</span>
                    <#else>
                        <span class="topbar-num"></span>
                    </#if>
                    <!--have表示有消息，没有消息去掉have-->
                </a>
            </li>
            <li class="fl topbar-info-item strong">
                <div class="dropdown">
                    <a href="#" class="dropdown-toggle topbar-btn">
                        <span class="fl">工单服务</span>
                        <span class="icon-arrow-down"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">我的工单</a></li>
                        <li><a href="#">提交工单</a></li>
                    </ul>
                </div>
            </li>
            <li class="fl topbar-info-item">
                <div class="dropdown">
                    <a href="#" class="topbar-btn">
                        <span class="fl text-normal">帮助与文档</span>
                        <span class="icon-arrow-down"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">模板开发手册</a></li>
                        <li><a href="#">某某数据字典</a></li>
                    </ul>
                </div>
            </li>
            <li class="fl topbar-info-item">
                <div class="dropdown">
                    <a href="#" class="topbar-btn">
                        <span class="fl text-normal">${localUser.name}</span>
                        <span class="icon-arrow-down"></span>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#" onclick="confirm_redirect('确定要退出当前帐号吗？','/back/logout')">退出</a></li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
</div>