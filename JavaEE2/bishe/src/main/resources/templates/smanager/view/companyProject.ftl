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
                    <h6 class="layout padding-left manage-head-con">横向项目管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="companyProject">
            <#assign field="name">
            <#assign value='${companyProject.name!""}'>
            <#assign isAdd=true>
            <#assign project=companyProject>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        项目名称
                    </div>
                    <div class="th w10">
                        负责人员
                    </div>
                    <div class="th w15">
                        起止时间
                    </div>
                    <div class="th w15">
                        负责单位
                    </div>
                    <div class="th w15">
                        项目来源
                    </div>
                    <div class="th w15">
                        参加人员
                    </div>
                    <div class="th w15">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            <#if vo['companyProject'].name?length gt 16>
                                ${vo['companyProject'].name?substring(0,16)?html}
                            <#else>
                            ${vo['companyProject'].name?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['chargePerson']?html}
                        </div>
                        <div class="td w15">
                        ${vo['companyProject'].projectDate?html}
                        </div>
                        <div class="td w15">
                        ${vo['companyProject'].chargeGroup?html}
                        </div>
                        <div class="td w15">
                        ${vo['companyProject'].projectSource?html}
                        </div>
                        <div class="td w15">
                            <#if vo['companyProject'].participant?length gt 16>
                            ${vo['companyProject'].participant?substring(0,16)?html}
                            <#else>
                            ${vo['companyProject'].participant?html}
                            </#if>
                        </div>
                        <div class="td w15">
                            <#if companyProject.status!=4&&((localRole.detail=='teacher'&&companyProject.status==0)||(localRole.detail=='smanager'))>
                                <a href="#" onclick="del_site('/companyProject/delete/${vo['companyProject'].id}')"
                                   class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <a href="#" onclick="modify_site('/companyProject/forEdit?companyProjectId=${vo['companyProject'].id}')"
                               class="button-word2 btn_ajax_confirm">查看</a>
                            <#if vo['companyProject'].status==0&&vo['companyProject'].projectStatus=='已结题'>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                    <a href="#" onclick="confirm_redirect('通过','/companyProject/approve/${vo['companyProject'].id}?status=1')"
                                       class="button-word2 btn_ajax_confirm">通过</a>
                                    <a href="#" onclick="confuse_option('/companyProject/approve/${vo['companyProject'].id}?status=2')"
                                       class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if vo['companyProject'].status==4>
                                <#if localRole.detail=='teacher'&&vo['companyProject'].createdId!=localUser.id>
                                <#else>
                                    <a href="#" onclick="confirm_redirect('恢复','/companyProject/approve/${vo['companyProject'].id}?status=0')"
                                       class="button-word2 btn_ajax_confirm">恢复</a>
                                </#if>
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