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
<#if localRole.detail!='smanager'>
    <script>
        location.href="/toLogin";
    </script>
</#if>

    <#--页面内容-->
    <div class="view-product">
        <div class="authority">
            <div class="authority-head">
                <div class="manage-head">
                    <h6 class="layout padding-left manage-head-con">帐号管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="user">
            <#assign field="name">
            <#assign value='${user.name!""}'>
            <#assign isAdd=true>
            <#assign isProject=false>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        名称
                    </div>
                    <div class="th w15">
                        角色
                    </div>
                    <div class="th w15">
                        职称
                    </div>
                    <div class="th w15">
                        电话
                    </div>
                    <div class="th w20">
                        邮件
                    </div>
                    <div class="th w20">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                        ${vo['user'].name?html}
                        </div>
                        <div class="td w15">
                        ${vo['roleName']?html}
                        </div>
                        <div class="td w15">
                        ${vo['user'].profession!"未填写"}
                        </div>
                        <div class="td w15">
                        ${vo['user'].phone!"未填写"}
                        </div>
                        <div class="td w20">
                        ${vo['user'].mail?html}
                        </div>
                        <div class="td w20">
                        <#if localRole.detail=='smanager'>
                            <a href="#" onclick="del_site('/user/delete/${vo['user'].id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            <a href="#" onclick="modify_site('/user/forEdit?userId=${vo['user'].id}')"
                               class="button-word2 btn_ajax_confirm">修改</a>
                        </#if>
                        </div>
                    </div>
                </#list>
            <#else>
            </div>
                <div class="tr non-info show border-bottom-none">
                    <span>没查询到符合条件的记录</span>
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