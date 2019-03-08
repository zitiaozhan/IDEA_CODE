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
                    <h6 class="layout padding-left manage-head-con">字符串管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="strings">
            <#assign field="entityField">
            <#assign value='${strings.entityField!""}'>
            <#assign isAdd=true>
            <#assign isProject=false>
            <#assign stringsType=true>
            <#assign stringsTypeList=infoTypeList>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w25">
                        实体类型
                    </div>
                    <div class="th w25">
                        实体字段
                    </div>
                    <div class="th w25">
                        内容
                    </div>
                    <div class="th w25">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w25">
                        ${vo['entityType']?html}
                        </div>
                        <div class="td w25">
                        ${vo['strings'].entityField?html}
                        </div>
                        <div class="td w25">
                        ${vo['strings'].content!""}
                        </div>
                        <div class="td w25">
                            <#if localRole.detail=='smanager'>
                            <a href="#" onclick="del_site('/strings/delete/${vo['strings'].id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            <a href="#" onclick="modify_site('/strings/forEdit?stringsId=${vo['strings'].id}')"
                               class="button-word2 btn_ajax_confirm">编辑</a>
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