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
                    <h6 class="layout padding-left manage-head-con">审批记录管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="approval">
            <#assign field="entityType">
            <#assign value='${approval.entityType!""}'>
            <#assign isAdd=false>
            <#assign isProject=false>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        实体名称
                    </div>
                    <div class="th w15">
                        实体类型
                    </div>
                    <div class="th w15">
                        审批人
                    </div>
                    <div class="th w10">
                        审批结果
                    </div>
                    <div class="th w15">
                        审批时间
                    </div>
                    <div class="th w30">
                        备注
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            <#if vo['entityName']?length gt 16>
                            ${vo['entityName']?substring(0,16)?html}
                            <#else>
                            ${vo['entityName']?html}
                            </#if>
                        </div>
                        <div class="td w15">
                        ${vo['entityType']?html}
                        </div>
                        <div class="td w15">
                        ${vo['userName']?html}
                        </div>
                        <div class="td w10">
                        ${vo['result']?html}
                        </div>
                        <div class="td w15">
                        ${vo['approval'].date?string('yyyy-MM-dd HH:mm:ss')}
                        </div>
                        <div class="td w30">
                        ${vo['approval'].remark!"无"}
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