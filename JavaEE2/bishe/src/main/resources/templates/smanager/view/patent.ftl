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
                    <h6 class="layout padding-left manage-head-con">专利管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="patent">
            <#assign field="name">
            <#assign value='${patent.name!""}'>
            <#assign isAdd=true>
            <#assign project=patent>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w10">
                        证书号
                    </div>
                    <div class="th w10">
                        名称
                    </div>
                    <div class="th w10">
                        发明人
                    </div>
                    <div class="th w20">
                        更多发明人
                    </div>
                    <div class="th w10">
                        专利号
                    </div>
                    <div class="th w10">
                        申请日
                    </div>
                    <div class="th w10">
                        专利权人
                    </div>
                    <div class="th w10">
                        发证日
                    </div>
                    <div class="th w10">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w10">
                        ${vo['patent'].certificateId?html}
                        </div>
                        <div class="td w10">
                            <#if vo['patent'].name?length gt 11>
                            ${vo['patent'].name?substring(0,11)?html}
                            <#else>
                            ${vo['patent'].name?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['author'].name?html}
                        </div>
                        <div class="td w20">
                            <#if vo['patent'].moreAuthor?length gt 21>
                            ${vo['patent'].moreAuthor?substring(0,21)?html}
                            <#else>
                            ${vo['patent'].moreAuthor?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['patent'].patentNumber?html}
                        </div>
                        <div class="td w10">
                        ${vo['patent'].applyDate?html}
                        </div>
                        <div class="td w10">
                        ${vo['patent'].patentedPerson?html}
                        </div>
                        <div class="td w10">
                        ${vo['patent'].awardDate?html}
                        </div>
                        <div class="td w10">
                            <#if patent.status!=4&&((localRole.detail=='teacher'&&patent.status==0)||(localRole.detail=='smanager'))>
                            <a href="#" onclick="del_site('/patent/delete/${vo['patent'].id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <a href="#" onclick="modify_site('/patent/forEdit?patentId=${vo['patent'].id}')"
                               class="button-word2 btn_ajax_confirm">查看</a>
                            <#if patent.status==0>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                <a href="#" onclick="confirm_redirect('通过','/patent/approve/${vo['patent'].id}?status=1')"
                                   class="button-word2 btn_ajax_confirm">通过</a>
                                <a href="#" onclick="confuse_option('/patent/approve/${vo['patent'].id}?status=2')"
                                   class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if patent.status==4>
                                <a href="#" onclick="confirm_redirect('恢复','/patent/approve/${vo['patent'].id}?status=0')"
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