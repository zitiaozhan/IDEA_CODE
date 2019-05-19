<div class="view-product background-color">
    <div class="padding-big background-color">
        <div class="account-info clearfix">
            <div class="text-box-main min-width-300 fl" style="height: 230px;">
                <dl>
                    <dt class="padding-big-left lists-border-list clearfix">
                    <div class="title fl padding-big-top padding-big-bottom">
                        <p>HI,${localUser.name!"用户"}</p>
                        <#--账户安全级别-->
                        <p class="margin-small-top clearfix">
                        <#if localUser.phone??&&localUser.mail??>
                            <span class="fl">账户安全级别：<em class="text-green-deep">高</em></span>
                        <#elseif (localUser.phone??&&!localUser.mail??)||(!localUser.phone??&&localUser.mail??)>
                            <span class="fl">账户安全级别：<em class="text-yellow-deep">中</em></span>
                        <#else>
                            <span class="fl">账户安全级别：<em class="text-red-deep">低</em></span>
                        </#if>
                        </p>
                    </div>
                    <span class="fr icon-head">
                        <img src="../../images/noavatar_middle.gif" alt="账户头像">
                    </span>
                    </dt>
                <#--手机与邮箱绑定情况-->
                    <dd class="padding-big clearfix">
                        <p class="w50 fl">
                            <i class="fl icon icon-mobile"></i>
                        <#if localUser.phone??&&localUser.phone?length gt 3>
                            <span class="fl margin-left">手机：已绑定</span>
                        <#else>
                            <span class="fl margin-left">手机：未绑定</span>
                        </#if>
                        </p>
                        <p class="w50 fl">
                            <i class="fl icon icon-email"></i>
                        <#if localUser.mail??&&localUser.mail?length gt 3>
                            <span class="fl margin-left">邮箱：已绑定</span>
                        <#else>
                            <span class="fl margin-left">邮箱：未绑定</span>
                        </#if>
                        </p>
                    </dd>
                </dl>
            </div>
            <#--最近发布 或 最近审批-->
            <div class="text-box-main min-width-360 fl padding-big" style="height: 230px;overflow-y: auto;"
                 align="center">
                <div style="background-color: white;width: 360px;height: 20px;">
                    <h2 class="h6 margin-big-bottom">最近发布 展示最近10条</h2>
                </div>
                <ul class="lists">
                    <li class="lists-border-list">
                        <div style="padding: 5px 0;background-color: gray;color: white;">
                            <p class="w10" style="display: inline-block;">序号</p>
                            <p class="w40" style="display: inline-block;">项目名称</p>
                            <p class="w20" style="display: inline-block;">项目类型</p>
                            <p class="w20" style="display: inline-block;">发布日期</p>
                        </div>
                    </li>
                <#list recentlyReleased as item>
                    <li class="lists-border-list">
                        <div style="padding: 5px 0">
                            <p class="w10" style="display: inline-block">${item_index+1}</p>
                            <#if item.entityName?length gt 10>
                                <p class="w40" style="display: inline-block">${item.entityName?substring(0,10)}.</p>
                            <#else>
                                <p class="w40" style="display: inline-block">${item.entityName}</p>
                            </#if>
                            <p class="w20" style="display: inline-block">${item.entityType}</p>
                            <p class="w20" style="display: inline-block">${item.date}</p>
                        </div>
                    </li>
                </#list>
                </ul>
            </div>
            <#--公告-->
            <div class="text-box-main min-width-300 fl padding-big margin-right-none"
                 style="height: 230px;overflow-y: auto;" align="center">
                <div style="background-color: white;width: 300px;height: 20px;">
                    <h2 class="h6 margin-big-bottom">公告 ${bulletins?size!0}条公告</h2>
                </div>
                <ul class="lists">
                    <li class="lists-border-list">
                        <div style="padding: 5px 0;background-color: gray;color: white;">
                            <p class="w70" style="display: inline-block;">公告</p>
                            <p class="w20" style="display: inline-block;">发布日期</p>
                        </div>
                    </li>
                <#list bulletins as item>
                    <li class="lists-border-list">
                        <div style="padding: 5px 0">
                            <p class="w70" style="display: inline-block">${item.content}</p>
                            <p class="w20" style="display: inline-block">${item.createdDate}</p>
                        </div>
                    </li>
                </#list>
                </ul>
            </div>
        </div>

        <!--科技分来源-->
        <div class="account-info margin-big-top clearfix" align="center">
            <div class="text-box-main padding-big fl" style="width: 1130px;overflow-y: auto;">
                <div style="background-color: white;width: 1080px;height: 25px;">
                    <h2 class="h6 margin-big-bottom">科技分来源 总分数：${satScore} </h2>
                </div>
                <ul class="lists">
                    <li class="lists-border-list">
                        <div style="padding: 5px 0;background-color: gray;color: white;">
                            <p class="w25" style="display: inline-block;">项目名称</p>
                            <p class="w15" style="display: inline-block;">项目类型</p>
                            <p class="w10" style="display: inline-block;">我的科技分</p>
                            <p class="w15" style="display: inline-block;">项目科技分</p>
                            <p class="w15" style="display: inline-block;">科技分占比</p>
                            <p class="w15" style="display: inline-block;">审核日期</p>
                        </div>
                    </li>
                <#list scoreSource as item>
                    <li class="lists-border-list">
                        <div style="padding: 5px 0">
                            <p class="w25" style="display: inline-block">${item.entityName}</p>
                            <p class="w15" style="display: inline-block">${item.entityType}</p>
                            <p class="w10" style="display: inline-block">${item.myScore}</p>
                            <p class="w15" style="display: inline-block">${item.projectScore}</p>
                            <p class="w15" style="display: inline-block">${item.scale}%</p>
                            <p class="w15" style="display: inline-block">${item.date}</p>
                        </div>
                    </li>
                </#list>
                </ul>
            </div>
        </div>
        <div class="account-info margin-big-top clearfix">
            <#--柱状图-->
            <div class="text-box-main padding-big fl" style="width: 558px;height: 650px">
                <div id="teacher-bar" style="width=507px;height:600px;"></div>
            </div>
            <#--饼状图-->
            <div class="text-box-main padding-big fl margin-right-none" style="width: 559px;height: 650px">
                <div id="teacher-pie" style="width=507px;height:600px;"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //查询
    function loadAuthorScore() {
        //柱状图
        loadBarChart('/show/data/1','teacher-bar');
        //饼状图
        loadPieChart('/show/data/2','teacher-pie');
    }
    //载入图表
    loadAuthorScore();
</script>