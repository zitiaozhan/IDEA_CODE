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
        location.href="/toLogin";
    </script>
</#if>

    <#--页面内容-->
    <div class="view-product">
        <div class="authority">
            <div class="authority-head">
                <div class="manage-head">
                    <h6 class="layout padding-left manage-head-con">学术讲座管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="lecture">
            <#assign field="name">
            <#assign value='${lecture.name!""}'>
            <#assign isAdd=true>
            <#assign project=lecture>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        报告名称
                    </div>
                    <div class="th w10">
                        报告人
                    </div>
                    <div class="th w15">
                        报告人单位
                    </div>
                    <div class="th w15">
                        时间
                    </div>
                    <div class="th w15">
                        地点
                    </div>
                    <div class="th w15">
                        主办单位
                    </div>
                    <div class="th w15">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            <#if vo['lecture'].name?length gt 16>
                            ${vo['lecture'].name?substring(0,16)?html}
                            <#else>
                            ${vo['lecture'].name?html}
                            </#if>
                        </div>
                        <div class="td w15">
                        ${vo['rapporteur']?html}
                        </div>
                        <div class="td w10">
                        ${vo['lecture'].rapporteurUnit?html}
                        </div>
                        <div class="td w15">
                        ${vo['lecture'].date?html}
                        </div>
                        <div class="td w15">
                        ${vo['lecture'].address?html}
                        </div>
                        <div class="td w15">
                        ${vo['lecture'].holdUnit?html}
                        </div>
                        <div class="td w15">
                            <#if lecture.status!=4&&((localRole.detail=='teacher'&&lecture.status==0)||(localRole.detail=='smanager'))>
                            <a href="#" onclick="del_site('/lecture/delete/${vo['lecture'].id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <#if (lecture.status==0&&localRole.detail=='teacher')||(localRole.detail=='smanager')>
                                <a href="#" onclick="modify_site('/lecture/forEdit?lectureId=${vo['lecture'].id}')"
                                   class="button-word2 btn_ajax_confirm">编辑</a>
                            </#if>

                            <#if lecture.status==0>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                <a href="#" onclick="confirm_redirect('通过','/lecture/approve/${vo['lecture'].id}?status=1')"
                                   class="button-word2 btn_ajax_confirm">通过</a>
                                <a href="#" onclick="confuse_option('/lecture/approve/${vo['lecture'].id}?status=2')"
                                   class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if lecture.status==4>
                                <a href="#" onclick="confirm_redirect('恢复','/lecture/approve/${vo['lecture'].id}?status=0')"
                                   class="button-word2 btn_ajax_confirm">恢复</a>
                            </#if>
                        </div>
                    </div>
                </#list>
            <#else>
            </div>
                <div class="tr non-info show border-bottom-none">
                    <span>未查询到符合条件的记录</span>
                </div>
            </div>
            </#if>

            </div>
        </div>
        <#--分页功能区-->
        <#include "../pageFunction.ftl" parse=true>
    </div>

</div>
</div>
</div>
<#include "../footer.ftl" parse=true>