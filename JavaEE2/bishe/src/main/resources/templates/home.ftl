<!DOCTYPE html>
<html lang="zxx">

<head>

    <title>Home</title>
    <!--meta tags -->
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content=""
    />
    <script>
        addEventListener("load", function () {
            setTimeout(hideURLbar, 0);
        }, false);

        function hideURLbar() {
            window.scrollTo(0, 1);
        }
    </script>
    <!--//meta tags ends here-->
    <!-- bootstrap style sheet -->
    <link href="../home/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- fontawesome -->
    <link href="../home/css/fontawesome-all.min.css" rel="stylesheet" type="text/css" media="all">
    <!-- Popup css (for Video Popup) -->
    <link href="../home/css/popuo-box.css" rel="stylesheet" type="text/css" media="all" />

    <link rel="stylesheet " href="../home/css/chocolat.css " type="text/css" media="all" />

    <link href="../home/css/easy-responsive-tabs.css" rel='stylesheet' type='text/css' />
    <!--stylesheets-->
    <link href="../home/css/style.css" rel='stylesheet' type='text/css' media="all">

    <link rel="stylesheet" type="text/css" href="../../style/identify.css" />
    <link rel="stylesheet" type="text/css" href="../../style/layout.css" />
    <link rel="stylesheet" type="text/css" href="../../style/style.css" />
    <link rel="stylesheet" type="text/css" href="../../style/control_index.css" />

    <script type="text/javascript" src="../../script/jquery-1.7.2.min.js"></script>
    <script type="text/javascript" src="../../script/echarts.js"></script>
    <script type="text/javascript" src="../../script/other_script.js"></script>
</head>

<body>
    <!-- 顶部导航栏 -->
    <header>
        <nav class="navbar navbar-expand-lg navbar-light">
            <h1><a class="navbar-brand" href="/home">科研之旅</a></h1>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false"
                aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item active">
                        <a class="nav-link active scroll" href="/home">首页 <span class="sr-only">(current)</span></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link scroll hvr-overline-from-center scroll" href="#about"> 关于我们</a>
                    </li>
					<li class="nav-item">
                        <a class="nav-link scroll hvr-overline-from-center scroll" href="#stats">排行榜</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link scroll hvr-overline-from-center scroll" href="#services">服务</a>
                    </li>
					<li class="nav-item">
                        <a class="nav-link scroll hvr-overline-from-center scroll" href="#gallery">运营数据</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link scroll hvr-overline-from-center scroll" href="#clients">数据分析</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link scroll hvr-overline-from-center scroll" href="#contact">联系我们</a>
                    </li>
                </ul>
            </div>
        </nav>
    </header>
    <!-- /顶部导航栏 -->

    <!-- 顶部开屏图 -->
    <div class="banner">
	<div class="banner-png">
        <div class="banner-text">

            <h2>开启你的科研之旅</h2>
            <p>为科研服务始终是我们的初心.</p>
            <div class="video-button">
                <a href="#small-dialog4" class="play-icon popup-with-zoom-anim ">
			    点击观看介绍
		        </a>
            </div>
        </div>
        <!-- video section -->
        <div id="small-dialog4" class="mfp-hide">
            <iframe src="http://preview.ewang.com/movie/videos/854x48071e0217244394cb1845d3dbd10789ece.mp4"></iframe>
        </div>
        <!-- //video section -->
    </div>
	</div>
    <!-- //顶部开屏图 -->

    <!-- 关于我们 -->
    <div class="about pt-5" id="about">
	<div class="main-about">
        <div class="container">
            <div class="text-h3 text-center">
                <h3>关于我们 </h3>
            </div>
            <div class="row">
                <div class="col-md-6 about-left">
                    <h3>WELCOME</h3>
                    <p>我们因科研项目的管理而生，主要是针对长期工作在科研第一线的工作者们提供一站式解决方案。
                        致力于搭建涵盖大多数科研项目的发布、审核、沟通的跨学科桥梁，促进国家科研工作的蓬勃发展，打造项目发布、项目检查、项目审核、沟通交流、科研数据五大服务的闭环生态圈。
                    目前主要市场为国内的各大高校，未来合作单位将涵盖各大高校、科研机构、医院、企业以及每一位热爱科研的你。
                    随着在此领域的扩展与建模，积累了大量的渠道筛查运营经验，致力成为人工智能与大数据技术在科研场景最好的服务咨询及渠道运营者。</p>
                </div>
                <div class="col-md-6 about-right">
                    <img src="../home/images/ab.jpg" alt="image" />
                </div>
            </div>
        </div>
	</div>

        <!-- about-bottom -->
        <section class="choose pt-5">
            <div class="container">
                <div class="text-h3 text-center">
                    <h3>选择你的科研领域</h3>
                </div>
                <div class="w3-agileits-team-title">
                    <div id="horizontalTab">
                        <div class=" resp-tabs-container">
                            <ul class=" resp-tabs-list">
                                <li class="resp-tab-item">
                                    <h5 class="w3_clr">制造</h5>
                                </li>
                                <li class="resp-tab-item">
                                    <h5 class="w3_clr">建筑</h5>
                                </li>
                                <li class="resp-tab-item">
                                    <h5 class="w3_clr">物理</h5>
                                </li>
                                <li class="resp-tab-item">
                                    <h5 class="w3_clr">化学</h5>
                                </li>
                            </ul>
                            <div class="tab1">
                                <div class="row">
                                    <div class="col-md-6 com-sm-6 w3three_al2">
                                        <div class="test-wel">

                                            <p>制造：把原材料加工成适用的产品制作，或将原材料加工成器物。</p>
                                            <ul>
                                                <li>
                                                    把原材料加工成适用的产品；</li>
                                                <li>
                                                    制侧重于操作制造，对象是一般器物；造侧重于从无到有，对象可以是较大的器物；</li>
                                                <li>
                                                    造成某种气氛或局面；</li>
                                                <li>
                                                    从无到有.</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-md-6 com-sm-6 w3three_al3">
                                        <img src="../home/images/b1.jpg" alt="" class="img-fluid">
                                    </div>
                                </div>
                            </div>

                            <div class="tab2">
                                <div class="row">
                                    <div class="col-md-6 com-sm-6 w3three_al2">
                                        <div class="test-wel">

                                            <p>建筑是建筑物与构筑物的总称，是人们为了满足社会生活需要，
                                                利用所掌握的物质技术手段，并运用一定的科学规律、风水理念和美学法则创造的人工环境。</p>
                                            <ul>
                                                <li>
                                                    建筑设计</li>
                                                <li>
                                                    建筑结构</li>
                                                <li>
                                                    建筑方法</li>
                                                <li>
                                                    建筑技巧</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-md-6 com-sm-6 w3three_al5">
                                        <img src="../home/images/b2.jpg" alt="" class="img-fluid">
                                    </div>
                                </div>
                            </div>
                            <div class="tab3">
                                <div class="row">
                                    <div class="col-md-6 com-sm-6 w3three_al2">
                                        <div class="test-wel">

                                            <p>物理学是研究物质运动最一般规律和物质基本结构的学科。
                                                作为自然科学的带头学科，物理学研究大至宇宙，小至基本粒子等一切物质最基本的运动形式和规律，
                                                因此成为其他各自然科学学科的研究基础。它的理论结构充分地运用数学作为自己的工作语言，
                                                以实验作为检验理论正确性的唯一标准，它是当今最精密的一门自然科学学科。</p>
                                            <ul>
                                                <li>
                                                    牛顿力学</li>
                                                <li>
                                                    电磁学</li>
                                                <li>
                                                    热力学</li>
                                                <li>
                                                    狭义相对论</li>
                                                <li>
                                                    广义相对论</li>
                                                <li>
                                                    量子力学</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-md-6 com-sm-6 w3three_al7">
                                        <img src="../home/images/b3.jpg" alt="" class="img-fluid">
                                    </div>
                                </div>
                            </div>
                            <div class="tab4">
                                <div class="row">
                                    <div class="col-md-6 com-sm-6 w3three_al2">
                                        <div class="test-wel">

                                            <p>
                                                化学是自然科学的一种，在分子、原子层次上研究物质的组成、性质、结构与变化规律；
                                                创造新物质的科学。世界由物质组成，化学则是人类用以认识和改造物质世界的主要方法和手段之一。
                                                它是一门历史悠久而又富有活力的学科，它的成就是社会文明的重要标志，化学中存在着化学变化和物理变化两种变化形式。
                                            </p>
                                            <ul>
                                                <li>
                                                    无机化学</li>
                                                <li>
                                                    有机化学</li>
                                                <li>
                                                    物理化学</li>
                                                <li>
                                                    分析化学</li>
                                                <li>
                                                    高分子化学</li>
                                            </ul>
                                        </div>
                                    </div>
                                    <div class="col-md-6 com-sm-6 w3three_al9">
                                        <img src="../home/images/b4.jpg" alt="" class="img-fluid">
                                    </div>
                                </div>
                            </div>
                        </div>


                    </div>
                </div>

            </div>
        </section>
    </div>
    <!-- //关于我们 -->

    <!-- 排行榜 -->
    <div class="stats_test py-5" id="stats" align="center">
	<div style="width: 90%;margin-bottom: 50px">
        <div class="text-h3 text-center py-3">
            <h3>排行榜</h3>
        </div>
        <div class="stats" style="100%">
            <!--排行榜-->
            <div class="account-info margin-big-top clearfix" align="center">
            <#--分数排行榜-->
                <div class="text-box-main padding-big fl" style="width: 385px;overflow-y: auto;">
                    <div style="background-color: white;width: 300px;height: 20px;">
                        <h2 class="h3 margin-big-bottom" style="color: gold;font-weight: bold">分数排行榜 TOP</h2>
                    </div>
                    <ul class="lists">
                        <li class="lists-border-list">
                            <div style="padding: 5px 0;background-color: gray;color: white;">
                                <p class="w10" style="display: inline-block;color: white;">排名</p>
                                <p class="w30" style="display: inline-block;color: white;">用户编号</p>
                                <p class="w30" style="display: inline-block;color: white;">用户姓名</p>
                                <p class="w15" style="display: inline-block;color: white;">科技分</p>
                            </div>
                        </li>
                    <#list scoreTop as item>
                        <li class="lists-border-list">
                            <#if item_index==0>
                                <div style="padding: 5px 0;background-color: gold;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.satScore} 分</p>
                                </div>
                            <#elseif item_index==1>
                                <div style="padding: 5px 0;background-color: #FF6745;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.satScore} 分</p>
                                </div>
                            <#elseif item_index==2>
                                <div style="padding: 5px 0;background-color: #00BC9B;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.satScore} 分</p>
                                </div>
                            <#else>
                                <div style="padding: 5px 0;background-color: #A4B2BA;color: white;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.satScore} 分</p>
                                </div>
                            </#if>
                        </li>
                    </#list>
                    </ul>
                </div>

            <#--数量排行榜-->
                <div class="text-box-main padding-big fl" style="width: 385px;overflow-y: auto;">
                    <div style="background-color: white;width: 300px;height: 20px;">
                        <h2 class="h3 margin-big-bottom" style="color: gold;font-weight: bold">数量排行榜 TOP</h2>
                    </div>
                    <ul class="lists">
                        <li class="lists-border-list">
                            <div style="padding: 5px 0;background-color: gray;color: white;">
                                <p class="w10" style="display: inline-block;color: white;">排名</p>
                                <p class="w30" style="display: inline-block;color: white;">用户编号</p>
                                <p class="w30" style="display: inline-block;color: white;">用户姓名</p>
                                <p class="w15" style="display: inline-block;color: white;">项目数</p>
                            </div>
                        </li>
                    <#list numberTop as item>
                        <li class="lists-border-list">
                            <#if item_index==0>
                                <div style="padding: 5px 0;background-color: gold;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.projectNum} 个</p>
                                </div>
                            <#elseif item_index==1>
                                <div style="padding: 5px 0;background-color: #FF6745;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.projectNum} 个</p>
                                </div>
                            <#elseif item_index==2>
                                <div style="padding: 5px 0;background-color: #00BC9B;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.projectNum} 个</p>
                                </div>
                            <#else>
                                <div style="padding: 5px 0;background-color: #A4B2BA;color: white;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.userNumber}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.name}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.projectNum} 个</p>
                                </div>
                            </#if>
                        </li>
                    </#list>
                    </ul>
                </div>

            <#--管理员审核数量排行榜-->
                <div class="text-box-main padding-big fl" style="width: 385px;overflow-y: auto;">
                    <div style="background-color: white;width: 300px;height: 20px;">
                        <h2 class="h3 margin-big-bottom" style="color: gold;font-weight: bold">管理员审核数量排行榜 TOP</h2>
                    </div>
                    <ul class="lists">
                        <li class="lists-border-list">
                            <div style="padding: 5px 0;background-color: gray;color: white;">
                                <p class="w10" style="display: inline-block;color: white;">排名</p>
                                <p class="w30" style="display: inline-block;color: white;">管理员编号</p>
                                <p class="w30" style="display: inline-block;color: white;">管理员姓名</p>
                                <p class="w15" style="display: inline-block;color: white;">审核数</p>
                            </div>
                        </li>
                    <#list approvalTop as item>
                        <li class="lists-border-list">
                            <#if item_index==0>
                                <div style="padding: 5px 0;background-color: gold;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityType}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityName}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.myScore?int} 个</p>
                                </div>
                            <#elseif item_index==1>
                                <div style="padding: 5px 0;background-color: #FF6745;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityType}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityName}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.myScore?int} 个</p>
                                </div>
                            <#elseif item_index==2>
                                <div style="padding: 5px 0;background-color: #00BC9B;color: snow;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityType}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityName}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.myScore?int} 个</p>
                                </div>
                            <#else>
                                <div style="padding: 5px 0;background-color: #A4B2BA;color: white;">
                                    <p class="w10" style="display: inline-block;color: white;">${item_index+1}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityType}</p>
                                    <p class="w30" style="display: inline-block;color: white;">${item.entityName}</p>
                                    <p class="w15" style="display: inline-block;color: white;">${item.myScore?int} 个</p>
                                </div>
                            </#if>
                        </li>
                    </#list>
                    </ul>
                </div>

            </div>
        </div>
    </div>
    </div>
    <!-- /排行榜 -->

    <!-- 我们的服务 -->
    <div class="serives py-5" id="services">
        <div class="container py-xl-5 py-lg-3">
            <div class="text-h3 text-center">
                <h3>我们的服务</h3>
            </div>
            <div class="row welcome-bottom">
                <div class="col-md-4 welcome-grid text-center">
                    <h4 class="my-3">项目发布</h4>
                    <p>发布你的科研项目，为科学发展做出贡献.</p>
                </div>
                <div class="col-md-4 welcome-grid text-center">
                    <h4 class="my-3">项目检查</h4>
                    <p>项目的基础检查服务，帮助你更容易的行走在科研之旅.</p>
                </div>
                <div class="col-md-4 welcome-grid text-center">
                    <h4 class="my-3">项目审核</h4>
                    <p>将你的项目发送给你的审核人审理.</p>
                </div>

                <div class="col-md-4 welcome-grid text-center ">
                    <h4 class="my-3">沟通交流</h4>
                    <p>与你的审核人进行沟通协商，与其他领域大牛共同探讨、共同进步.</p>
                </div>
                <div class="col-md-4 welcome-grid text-center">
                    <h4 class="my-3">科研数据</h4>
                    <p>依托大数据技术为你送上最贴心的服务.</p>
                </div>
            </div>
        </div>
    </div>
    <!-- /我们的服务 -->

    <!-- 运营数据 -->
    <div class="stats_test py-5" id="gallery">
        <div class="container">
            <div class="text-h3 text-center py-3">
                <h3>运营数据</h3>
                <p id="operationalOverview"></p>
            </div>
            <div class="stats">
                <div class="row">
                    <div class="col-md-6 stats_left counter_grid">
                        <p class="counter" id="userNum">415</p>
                        <h4>位用户</h4>
                    </div>
                    <div class="col-md-6 stats_left counter_grid1">
                        <p class="counter" id="teacherNum">536</p>
                        <h4>位教师</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 stats_left counter_grid2">
                        <p class="counter" id="scoreSum">356</p>
                        <h4>科技分在平台的帮助下获取</h4>
                    </div>
                    <div class="col-md-6 stats_left counter_grid3">
                        <p class="counter" id="projectNum">1045</p>
                        <h4>个项目在平台的帮助下发布</h4>
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-6 stats_left counter_grid1">
                        <p class="counter" id="approvalProjectNum">356</p>
                        <h4>个项目在平台的帮助下辅助审核</h4>
                    </div>
                    <div class="col-md-6 stats_left counter_grid2">
                        <p class="counter" id="messageNum">1045</p>
                        <h4>次沟通交流在平台的帮助下完成</h4>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        var operationalOverview=document.getElementById('operationalOverview');

        var userNum=document.getElementById('userNum');
        var teacherNum=document.getElementById('teacherNum');
        var scoreSum=document.getElementById('scoreSum');
        var projectNum=document.getElementById('projectNum');
        var approvalProjectNum=document.getElementById('approvalProjectNum');
        var messageNum=document.getElementById('messageNum');

        $.getJSON('/dataShow', function (data) {
            if (data.success) {
                operationalOverview.innerHTML =
                        "平台截止 " + data.data.now + " 共帮助 " + data.data.teacherNum + " 位教师获取了 " + data.data.scoreSum + " 科技分，" +
                        "帮助 " + data.data.teacherNum + " 位教师发布了 " + data.data.projectNum + " 个项目，" +
                        "辅助审核了 " + data.data.approvalProjectNum + " 个项目，" +
                        " " + data.data.userNum + " 位用户通过该平台共沟通交流了 " + data.data.messageNum + " 次。";

                userNum.innerHTML = data.data.userNum;
                teacherNum.innerHTML = data.data.teacherNum;
                scoreSum.innerHTML = data.data.scoreSum;
                projectNum.innerHTML = data.data.projectNum;
                approvalProjectNum.innerHTML = data.data.approvalProjectNum;
                messageNum.innerHTML = data.data.messageNum;
            } else {
                alert(data.msg);
            }
        });
    </script>
    <!-- /运营数据 -->

    <!-- 数据分析-->
    <div class="clients py-5" id="clients">
        <div class="container">
            <div class="text-h3 text-center">
                <h3>数据分析</h3>
            </div>
            <div class="wmuSlider example1 animated wow slideInUp" data-wow-delay=".5s">
                <div class="wmuSliderWrapper">
                    <article style="position: absolute; width: 100%; opacity: 0;">
                        <div class="banner-wrap">
                            <div class="row">
                                <div id="scoreTypeBar" class="col-md-6 client-grids" style="width: 30%;height: 300px;">
                                </div>
                                <div id="scoreTypePie" class="col-md-6 client-grids" style="width: 30%;height: 300px;">
                                </div>
                            </div>
                        </div>
                    </article>
                    <article style="position: absolute; width: 100%; opacity: 0;">
                        <div class="banner-wrap">
                            <div class="row">
                                <div id="projectTypeBar" class="col-md-6 client-grids" style="width: 30%;height: 300px;">
                                </div>
                                <div id="projectTypePie" class="col-md-6 client-grids" style="width: 30%;height: 300px;">
                                </div>
                            </div>
                        </div>
                    </article>
                </div>
            </div>
        </div>
    </div>

    <script type="text/javascript">
        //柱状图
        loadBarChart('/dataStatistics?type=1&category=2','scoreTypeBar');
        loadBarChart('/dataStatistics?type=1&category=1','projectTypeBar');
        //饼状图
        loadPieChart('/dataStatistics?type=2&category=2','scoreTypePie');
        loadPieChart('/dataStatistics?type=2&category=1','projectTypePie');
    </script>
    <!--/数据分析-->

    <!-- 注册跳转 -->
    <div class="subscribe-main section-w3layouts text-center">
        <div class="subscribe-head">
            <div class="text-h3-1 text-center">
                <h3>注册</h3>
            </div>
            <div class="subscribe-form">
                <form action="/back/reg" method="post" class="subscribe_form">
                    <input type="email" name="mail" placeholder="邮箱">
                    <input type="submit" value="注册">
                </form>
                <div class="clearfix"> </div>
            </div>
        </div>
    </div>
    <!-- /注册跳转 -->


    <!-- 联系我们 -->
    <div class="contact py-5" id="contact">
        <div class="container">
            <div class="text-h3 text-center">
                <h3>联系我们</h3>
            </div>
            <div class="row">
                <div class="col-md-6 content-left">
                    <h4>开始联系</h4>
                    <form action="#" method="post">
                        <div class="form-row">
                            <div class="form-group col-md-12">
                                <input type="text" class="form-control" id="inputtext" placeholder="姓名" required="">
                            </div>
                            <div class="form-group col-md-12">
                                <input type="email" class="form-control" id="inputEmail4" placeholder="邮箱" required="">
                            </div>
                            <div class="form-group col-md-12">
                                <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" placeholder="内容" required=""></textarea>
                            </div>
                            <input type="submit" class="form-button" value="提交">
                        </div>
                    </form>
                </div>
                <div class="col-md-6 content-right">
					<h4>地址</h4>

                    <div class="add-info">

                        <p><span class="contact-right-icons"></span> Xi'AN, CHINA.</p>
                    </div>
                    <div class="add-info">

                        <p><span class="contact-right-icons"></span> +(012) 345 6789</p>
                    </div>
                    <div class="add-info">

                        <p><span class="contact-right-icons"></span> <a href="mailto:zitiaozhan@163.com">zitiaozhan@163.com</a></p>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!--/联系我们-->

    <!-- 底部信息与导航 -->
    <footer>
        <div class="w3ls-footer-grids pb-5">
            <div class="container">
                <div class="row">
                    <div class="col-md-4 w3l-footer">
                        <h3 class="mb-sm-3 mb-2">关于我们</h3>
                         <p class="para-w3-agileits">为科研服务始终是我们的初心.</p>

                    </div>
                    <div class="col-md-4 w3l-footer my-md-0 my-4 text-center">
						<h2><a class="navbar-brand" href="/home">科研之旅</a></h2>
                    </div>

                    <div class="col-md-4 w3l-footer">
                        <h3 class="mb-sm-3 mb-2">导航</h3>
                        <div class="nav-w3-l">
                            <ul>
                                <li>
                                    <a href="/home" class="scroll">首页</a>
                                </li>
                                <li class="mt-2">
                                    <a href="#about" class="scroll">关于我们</a>
                                </li>
								<li class="mt-2">
                                    <a href="#stats" class="scroll">排行榜</a>
                                </li>
                                <li class="mt-2">
                                    <a href="#services" class="scroll">服务</a>
                                </li>
								<li class="mt-2">
                                    <a href="#gallery" class="scroll">运营数据</a>
                                </li>
                                <li class="mt-2">
                                    <a href="#clients" class="scroll">数据分析</a>
                                </li>
							</ul>
                        </div>
                    </div>
                </div>
                <div class="row border-top mt-4 pt-lg-4 pt-3 text-lg-left text-center">
                    <p class="col-lg-8 copy-right-grids mt-lg-1">Copyright &copy; 2019.Company All rights reserved.</p>
                </div>
            </div>
        </div>
    </footer>
    <!-- /底部信息与导航 -->

    <!-- js -->
    <script src="../home/js/jquery.min.v3.js"></script>
    <script src="../home/js/bootstrap.min.js"></script>
    <!-- //js -->

    <!-- pop-up(for video popup)-->
    <script src="../home/js/jquery.magnific-popup.js"></script>

    <script>
        $(document).ready(function () {
            $('.popup-with-zoom-anim').magnificPopup({
                type: 'inline',
                fixedContentPos: false,
                fixedBgPos: true,
                overflowY: 'auto',
                closeBtnInside: true,
                preloader: false,
                midClick: true,
                removalDelay: 300,
                mainClass: 'my-mfp-zoom-in'
            });

        });
    </script>
    <!-- //pop-up-box (syllabus section video)-->

    <script src="../home/js/easy-responsive-tabs.js"></script>
    <script>
        $(document).ready(function () {
            $('#horizontalTab').easyResponsiveTabs({
                type: 'default', //Types: default, vertical, accordion           
                width: 'auto', //auto or any width like 600px
                fit: true, // 100% fit in a container
                closed: 'accordion', // Start closed if in accordion view
                activate: function (event) { // Callback function if tab is switched
                    var $tab = $(this);
                    var $info = $('#tabInfo');
                    var $name = $('span', $info);
                    $name.text($tab.text());
                    $info.show();
                }
            });
            $('#verticalTab').easyResponsiveTabs({
                type: 'vertical',
                width: 'auto',
                fit: true
            });
        });
    </script>


    <!-- stats -->
    <script src="../home/js/jquery.waypoints.min.js"></script>
    <script src="../home/js/jquery.countup.js"></script>
    <script>
        $('.counter').countUp();
    </script>
    <!-- //stats -->

    <!-- clients js file-->
    <script src="../home/js/jquery.wmuSlider.js"></script>
    <script>
        $('.example1').wmuSlider();
    </script>
    <!-- //clients js file -->
    <!-- js for portfolio  -->
    <script src="../home/js/jquery.chocolat.js "></script>
    <!--light-box-files -->
    <script>
        $(function () {
            $('.portfolio-grids a').Chocolat();
        });
    </script>
    <!-- /js for portfolio -->


    <!-- start-smooth-scrolling -->
    <script src="../home/js/move-top.js"></script>
    <script src="../home/js/easing.js"></script>
    <script>
        jQuery(document).ready(function ($) {
            $(".scroll").click(function (event) {
                event.preventDefault();

                $('html,body').animate({
                    scrollTop: $(this.hash).offset().top
                }, 1000);
            });
        });
    </script>
    <!-- //end-smooth-scrolling -->
    <!-- smooth-scrolling-of-move-up -->
    <script>
        $(document).ready(function () {

            $().UItoTop({
                easingType: 'easeOutQuart'
            });

        });
    </script>
    <script src="../home/js/SmoothScroll.min.js"></script>
</body>

</html>