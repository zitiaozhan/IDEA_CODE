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
                    <h6 class="layout padding-left manage-head-con">获奖管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="prize">
            <#assign field="name">
            <#assign value='${prize.name!""}'>
            <#assign isAdd=true>
            <#assign project=prize>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        获奖名称
                    </div>
                    <div class="th w10">
                        获奖人
                    </div>
                    <div class="th w20">
                        参赛队员
                    </div>
                    <div class="th w10">
                        证书号
                    </div>
                    <div class="th w10">
                        获奖等级
                    </div>
                    <div class="th w15">
                        颁奖单位
                    </div>
                    <div class="th w10">
                        获奖时间
                    </div>
                    <div class="th w10">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            <#if vo['prize'].name?length gt 16>
                            ${vo['prize'].name?substring(0,16)?html}
                            <#else>
                            ${vo['prize'].name?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['author']?html}
                        </div>
                        <div class="td w20">
                            <#if vo['prize'].guidanceTeacher?length gt 21>
                            ${vo['prize'].guidanceTeacher?substring(0,21)?html}
                            <#else>
                            ${vo['prize'].guidanceTeacher?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['prize'].certificateId?html}
                        </div>
                        <div class="td w10">
                        ${vo['prize'].prizeLevel?html}
                        </div>
                        <div class="td w15">
                        ${vo['prize'].awardUnit?html}
                        </div>
                        <div class="td w10">
                        ${vo['prize'].prizeDate?html}
                        </div>
                        <div class="td w10">
                            <#if localRole.detail!='manager'&&prize.status!=4>
                            <a href="#" onclick="del_site('/prize/delete/${vo['prize'].id}')"
                               class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <#if localRole.detail!='teacher'>
                            <a href="#" onclick="modify_site('/prize/forEdit?prizeId=${vo['prize'].id}')"
                               class="button-word2 btn_ajax_confirm">查看</a>
                            </#if>
                            <#if prize.status==0>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                <a href="#" onclick="modify_site('/prize/approve/${vo['prize'].id}?status=1')"
                                   class="button-word2 btn_ajax_confirm">通过</a>
                                <a href="#" onclick="modify_site('/prize/approve/${vo['prize'].id}?status=2')"
                                   class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if prize.status==4>
                                <a href="#" onclick="modify_site('/prize/approve/${vo['prize'].id}?status=0')"
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