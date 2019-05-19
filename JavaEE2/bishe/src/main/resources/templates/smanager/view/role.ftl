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
                    <h6 class="layout padding-left manage-head-con">角色管理
                        <span class="fr text-small text-normal padding-top">更新时间：${vo['nowDate']?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="role">
            <#assign field="name">
            <#assign value='${role.name!""}'>
            <#assign isAdd=true>
            <#assign isProject=false>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w20">
                        名称
                    </div>
                    <div class="th w65">
                        权限详情
                    </div>
                    <div class="th w15">
                        操作
                    </div>
                </div>
            <#if vo['roleList']?size gt 0>
                <#list vo['roleList'] as role>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w20">
                        ${role.name?html}
                        </div>
                        <div class="td w65">
                        ${role.detail!"无"?html}
                        </div>
                        <div class="td w15">
                            <#--<a href="#" onclick="del_site('/role/delete/${role.id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            <a href="#" onclick="modify_site('/role/forEdit?roleId=${role.id}')"
                               class="button-word2 btn_ajax_confirm">编辑</a>-->
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