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
            <div class="text-box-main min-width-360 fl padding-big" style="height: 230px;overflow-y: auto;" align="center">
                <div style="background-color: white;width: 360px;height: 20px;">
                    <h2 class="h6 margin-big-bottom">最近审批 展示最近10条</h2>
                </div>
                <ul class="lists">
                    <li class="lists-border-list">
                        <div style="padding: 5px 0;background-color: gray;color: white;">
                            <p class="w40" style="display: inline-block;">项目名称</p>
                            <p class="w15" style="display: inline-block;">项目类型</p>
                            <p class="w15" style="display: inline-block;">审批结果</p>
                            <p class="w20" style="display: inline-block;">审批日期</p>
                        </div>
                    </li>
                <#list approvalList as item>
                    <li class="lists-border-list">
                        <div style="padding: 5px 0">
                            <#if item.entityName?length gt 10>
                                <p class="w40" style="display: inline-block">${item.entityName?substring(0,10)}.</p>
                            <#else>
                                <p class="w40" style="display: inline-block">${item.entityName}</p>
                            </#if>
                            <p class="w15" style="display: inline-block">${item.entityType}</p>
                            <#if item.approvalResult?length gt 6>
                                <p class="w15" style="display: inline-block">${item.approvalResult?substring(0,6)}.</p>
                            <#else>
                                <p class="w15" style="display: inline-block">${item.approvalResult}</p>
                            </#if>
                            <p class="w20" style="display: inline-block">${item.date}</p>
                        </div>
                    </li>
                </#list>
                </ul>
            </div>
            <#--公告-->
            <div class="text-box-main min-width-300 fl padding-big margin-right-none" style="height: 230px;overflow-y: auto;" align="center">
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

        <!--排行榜-->
        <div class="account-info margin-big-top clearfix" align="center">
            <#--分数排行榜-->
            <div class="text-box-main padding-big fl" style="width: 555px;overflow-y: auto;">
                <div style="background-color: white;width: 400px;height: 20px;">
                    <h2 class="h6 margin-big-bottom" style="color: gold;font-weight: bold">分数排行榜 TOP10</h2>
                </div>
                <ul class="lists">
                    <li class="lists-border-list">
                        <div style="padding: 5px 0;background-color: gray;color: white;">
                            <p class="w10" style="display: inline-block;">排名</p>
                            <p class="w25" style="display: inline-block;">用户编号</p>
                            <p class="w25" style="display: inline-block;">用户姓名</p>
                            <p class="w15" style="display: inline-block;">科技分</p>
                        </div>
                    </li>
                <#list scoreTop as item>
                    <li class="lists-border-list">
                        <#if item_index==0>
                            <div style="padding: 5px 0;background-color: gold;color: snow;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.satScore} 分</p>
                            </div>
                        <#elseif item_index==1>
                            <div style="padding: 5px 0;background-color: #FF6745;color: snow;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.satScore} 分</p>
                            </div>
                        <#elseif item_index==2>
                            <div style="padding: 5px 0;background-color: #00BC9B;color: snow;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.satScore} 分</p>
                            </div>
                        <#else>
                            <div style="padding: 5px 0;background-color: #A4B2BA;color: white;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.satScore} 分</p>
                            </div>
                        </#if>
                    </li>
                </#list>
                </ul>
            </div>

            <#--数量排行榜-->
            <div class="text-box-main padding-big fl" style="width: 550px;overflow-y: auto;">
                <div style="background-color: white;width: 400px;height: 20px;">
                    <h2 class="h6 margin-big-bottom" style="color: gold;font-weight: bold">数量排行榜 TOP10</h2>
                </div>
                <ul class="lists">
                    <li class="lists-border-list">
                        <div style="padding: 5px 0;background-color: gray;color: white;">
                            <p class="w10" style="display: inline-block;">排名</p>
                            <p class="w25" style="display: inline-block;">用户编号</p>
                            <p class="w25" style="display: inline-block;">用户姓名</p>
                            <p class="w15" style="display: inline-block;">项目数</p>
                        </div>
                    </li>
                <#list numberTop as item>
                    <li class="lists-border-list">
                        <#if item_index==0>
                            <div style="padding: 5px 0;background-color: gold;color: snow;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.projectNum} 个</p>
                            </div>
                        <#elseif item_index==1>
                            <div style="padding: 5px 0;background-color: #FF6745;color: snow;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.projectNum} 个</p>
                            </div>
                        <#elseif item_index==2>
                            <div style="padding: 5px 0;background-color: #00BC9B;color: snow;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.projectNum} 个</p>
                            </div>
                        <#else>
                            <div style="padding: 5px 0;background-color: #A4B2BA;color: white;">
                                <p class="w10" style="display: inline-block">${item_index+1}</p>
                                <p class="w25" style="display: inline-block">${item.userNumber}</p>
                                <p class="w25" style="display: inline-block">${item.name}</p>
                                <p class="w15" style="display: inline-block">${item.projectNum} 个</p>
                            </div>
                        </#if>
                    </li>
                </#list>
                </ul>
            </div>
        </div>
        <div class="account-info margin-big-top clearfix">
        <#--饼状图-->
            <div class="text-box-main padding-big fl margin-right-none" style="width: 1130px;height: 650px">
                <div id="manager-pie" style="width=1050px;height:600px;"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    //图表
    var echartsPie = echarts.init(document.getElementById('manager-pie'));

    //查询
    function loadManagerApproval() {
        //饼状图
        echartsPie.clear();
        echartsPie.showLoading({text: '正在努力的读取数据中...'});
        $.getJSON('/show/data/2', function (data) {
            if (data.success) {
                echartsPie.setOption(data.data, true);
                echartsPie.hideLoading();
            } else {
                alert('提示', data.msg);
            }
        });
    }
    //载入图表
    loadManagerApproval();
</script>