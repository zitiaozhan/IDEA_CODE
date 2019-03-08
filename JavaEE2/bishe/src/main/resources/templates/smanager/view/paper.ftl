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
                    <h6 class="layout padding-left manage-head-con">论文管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="paper">
            <#assign field="name">
            <#assign value='${paper.name!""}'>
            <#assign isAdd=true>
            <#assign project=paper>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        论文名称
                    </div>
                    <div class="th w15">
                        第一作者
                    </div>
                    <div class="th w15">
                        其他作者
                    </div>
                    <div class="th w15">
                        第一署名单位
                    </div>
                    <div class="th w10">
                        期刊名称
                    </div>
                    <div class="th w15">
                        发表日期
                    </div>
                    <div class="th w15">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            <#if vo['paper'].name?length gt 16>
                            ${vo['paper'].name?substring(0,16)?html}
                            <#else>
                            ${vo['paper'].name?html}
                            </#if>
                        </div>
                        <div class="td w15">
                        ${vo['firstAuthor']?html}
                        </div>
                        <div class="td w15">
                            <#if vo['paper'].moreAuthor?length gt 16>
                            ${vo['paper'].moreAuthor?substring(0,16)?html}
                            <#else>
                            ${vo['paper'].moreAuthor?html}
                            </#if>
                        </div>
                        <div class="td w15">
                        ${vo['paper'].firstSignatureUnit?html}
                        </div>
                        <div class="td w10">
                        ${vo['paper'].periodicalName?html}
                        </div>
                        <div class="td w15">
                        ${vo['paper'].publishDate?html}
                        </div>
                        <div class="td w5">
                            <#if localRole.detail!='manager'&&paper.status!=4>
                            <a href="#" onclick="del_site('/paper/delete/${vo['paper'].id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <a href="#" onclick="modify_site('/paper/forEdit?paperId=${vo['paper'].id}')"
                               class="button-word2 btn_ajax_confirm">查看</a>
                            <#if paper.status==0>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                <a href="#" onclick="modify_site('/paper/approve/${vo['paper'].id}?status=1')"
                                   class="button-word2 btn_ajax_confirm">通过</a>
                                <a href="#" onclick="modify_site('/paper/approve/${vo['paper'].id}?status=2')"
                                   class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if paper.status==4>
                                <a href="#" onclick="modify_site('/paper/approve/${vo['paper'].id}?status=0')"
                                   class="button-word2 btn_ajax_confirm">恢复</a>
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