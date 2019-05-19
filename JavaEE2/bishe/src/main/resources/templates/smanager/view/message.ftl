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
                    <h6 class="layout padding-left manage-head-con">消息列表
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="message">
            <#assign field="userName">
            <#assign placeholder="请输入联系人姓名">
            <#assign value='${userName!""}'>
            <#assign isAdd=false>
            <#assign isMessage=true>
            <#assign isProject=false>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        联系人
                    </div>
                    <div class="th w30">
                        内容
                    </div>
                    <div class="th w20">
                        未读消息
                    </div>
                    <div class="th w20">
                        时间
                    </div>
                    <div class="th w15">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            ${vo['anotherName']!"未知错误"}
                        </div>
                        <div class="td w30">
                            <#if vo['message'].content?length gt 31>
                            ${vo['message'].content?substring(0,31)?html}
                            <#else>
                            ${vo['message'].content?html}
                            </#if>
                        </div>
                        <div class="td w20">
                            ${vo['unread']!"未知错误"}
                        </div>
                        <div class="td w20">
                            ${vo['message'].createdDate?string('yyyy年MM月dd日 HH:mm:ss')}
                        </div>
                        <div class="td w15">
                            <a href="#" onclick="modify_site('/message/show?conversationId=${vo['message'].conversationId}')"
                               class="button-word2 btn_ajax_confirm">查看</a>
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