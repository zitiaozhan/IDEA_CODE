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
                    <h6 class="layout padding-left manage-head-con">学术交流管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="academicExchange">
            <#assign field="meetingName">
            <#assign value='${academicExchange.meetingName!""}'>
            <#assign isAdd=true>
            <#assign project=academicExchange>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w15">
                        会议名称
                    </div>
                    <div class="th w15">
                        会议时间
                    </div>
                    <div class="th w15">
                        会议地点
                    </div>
                    <div class="th w15">
                        参加人员
                    </div>
                    <div class="th w10">
                        举办学院
                    </div>
                    <div class="th w15">
                        备注
                    </div>
                    <div class="th w15">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w15">
                            <#if vo['academicExchange'].meetingName?length gt 16>
                            ${vo['academicExchange'].meetingName?substring(0,16)?html}
                            <#else>
                            ${vo['academicExchange'].meetingName?html}
                            </#if>
                        </div>
                        <div class="td w15">
                        ${vo['academicExchange'].meetingDate?html}
                        </div>
                        <div class="td w15">
                        ${vo['academicExchange'].meetingAddress?html}
                        </div>
                        <div class="td w15">
                            <#if vo['academicExchange'].mainParticipant?length gt 16>
                            ${vo['academicExchange'].mainParticipant?substring(0,16)?html}
                            <#else>
                            ${vo['academicExchange'].mainParticipant?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['academicExchange'].college?html}
                        </div>
                        <div class="td w15">
                            <#if vo['academicExchange'].remark?length gt 16>
                            ${vo['academicExchange'].remark?substring(0,16)?html}
                            <#else>
                            ${vo['academicExchange'].remark?html}
                            </#if>
                        </div>
                        <div class="td w5">
                            <#if localRole.detail!='manager'&&academicExchange.status!=4>
                                <a href="#" onclick="del_site('/academicExchange/delete/${vo['academicExchange'].id}')"
                                   class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <#if localRole.detail!='teacher'>
                                <a href="#" onclick="modify_site('/academicExchange/forEdit?academicExchangeId=${vo['academicExchange'].id}')"
                                   class="button-word2 btn_ajax_confirm">修改</a>
                            </#if>

                            <#if academicExchange.status==0>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                <a href="#" onclick="modify_site('/academicExchange/approve/${vo['academicExchange'].id}?status=1')"
                                   class="button-word2 btn_ajax_confirm">通过</a>
                                <a href="#" onclick="modify_site('/academicExchange/approve/${vo['academicExchange'].id}?status=2')"
                                   class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if academicExchange.status==4>
                                <a href="#" onclick="modify_site('/academicExchange/approve/${vo['academicExchange'].id}?status=0')"
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