<#include "../header.ftl" parse=true>
<div class="view-body">
<#--个人菜单-->
<#if localRole??>
    <#if localRole.detail=='smanager'>
        <#include "../menu.ftl" parse=true>
    <#else>
        <#include "../../${localRole.detail}/menu.ftl" parse=true>
    </#if>
<#else>
    <script>
        location.href = "/toLogin";
    </script>
</#if>

<#--页面内容-->
    <div class="view-product background-color">
        <div class="authority">
            <div class="authority-head">
                <div class="manage-head">
                    <h6 class="layout padding-left manage-head-con"
                        onclick="modify_site('/user')">
                        数据展示
                    </h6>
                </div>
            </div>
            <div class="padding-big background-color">
            <#--管理员饼状图-->
                <div id="manager-data-pie" class="text-box-main padding-big fl margin-right-none"
                     style="width: 1130px;height: 650px">
                    <div id="manager-pie" style="width=1050px;height:600px;"></div>
                </div>
            <#--教师柱状图-->
                <div id="teacher-data-bar" class="text-box-main padding-big fl" style="width: 558px;height: 650px">
                    <div id="teacher-bar" style="width=507px;height:600px;"></div>
                </div>
            <#--教师饼状图-->
                <div id="teacher-data-pie" class="text-box-main padding-big fl margin-right-none"
                     style="width: 559px;height: 650px">
                    <div id="teacher-pie" style="width=507px;height:600px;"></div>
                </div>
            </div>
        </div>
    </div>

<#if !param1??>
    <script type="text/javascript">
        alert("无数据展示！");
        location.href = "/user";
    </script>
<#else>
    <script type="text/javascript">
        //管理员相关
        var node = document.getElementById('manager-data-pie');
        var managerPie = echarts.init(document.getElementById('manager-pie'));

        //教师相关
        var bar = document.getElementById('teacher-data-bar');
        var pie = document.getElementById('teacher-data-pie');
        var echartsBar = echarts.init(document.getElementById('teacher-bar'));
        var echartsPie = echarts.init(document.getElementById('teacher-pie'));

        //查询管理员数据
        function loadManagerApproval() {
            //饼状图
            managerPie.clear();
            managerPie.showLoading({text: '正在努力的读取数据中...'});
            $.getJSON('/show/data/2?userId=' + ${param1}, function (data) {
                if (data.success) {
                    managerPie.setOption(data.data, true);
                    managerPie.hideLoading();
                    if (data.manager) {
                        //隐藏教师相关数据
                        bar.style.display = 'none';
                        echartsBar.clear();
                        pie.style.display = 'none';
                        echartsPie.clear();
                    }
                } else {
                    node.style.display = 'none';
                    alert(data.msg);
                    managerPie.clear();
                    location.href = "/user";
                }
            });
        }
        //查询教师数据
        function loadAuthorScore() {
            //柱状图
            echartsBar.clear();
            echartsBar.showLoading({text: '正在努力的读取数据中...'});
            $.getJSON('/show/data/1?userId=' + ${param1}, function (data) {
                if (data.success) {
                    //替换不符合的属性
                    var xAxis = data.data.xaxis;
                    delete(data.data.xaxis);
                    data.data.xAxis = xAxis;
                    var yAxis = data.data.yaxis;
                    delete(data.data.yaxis);
                    data.data.yAxis = yAxis;

                    echartsBar.setOption(data.data, true);
                    echartsBar.hideLoading();
                } else {
                    bar.style.display = 'none';
                    echartsBar.clear();
                    location.href = "/user";
                }
            });
            //饼状图
            echartsPie.clear();
            echartsPie.showLoading({text: '正在努力的读取数据中...'});
            $.getJSON('/show/data/2?userId=' + ${param1}, function (data) {
                if (data.success) {
                    echartsPie.setOption(data.data, true);
                    echartsPie.hideLoading();
                    if (!data.manager) {
                        //隐藏管理员相关数据
                        node.style.display = 'none';
                        managerPie.clear();
                    }
                } else {
                    pie.style.display = 'none';
                    echartsPie.clear();
                    location.href = "/user";
                }
            });
        }
        //载入管理员数据
        loadManagerApproval();
        //载入教师数据
        loadAuthorScore();
    </script>
</#if>

<#include "../footer.ftl" parse=true>