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
                    <h6 class="layout padding-left manage-head-con">教材管理
                        <span class="fr text-small text-normal padding-top">更新时间：${nowDate?string('yyyy-MM-dd')}</span>
                    </h6>
                </div>
            </div>

            <#if msg??>
                <div class="header_msg" align="center">
                    <p class="msg_p">${msg?html!"暂无信息"}</p>
                </div>
            </#if>

            <#assign type="teachingMaterial">
            <#assign field="name">
            <#assign value='${teachingMaterial.name!""}'>
            <#assign isAdd=true>
            <#assign project=teachingMaterial>
            <#assign isProject=true>

            <#--列表头信息-->
            <#include "../headerInfo.ftl" parse=true>

            <#--数据列表-->
            <div class="authority-content">
            <div class="list-content show">
            <div class="offcial-table tr-border margin-big-top clearfix">
                <div class="tr-th clearfix">
                    <div class="th w10">
                        教材名称
                    </div>
                    <div class="th w10">
                        作者
                    </div>
                    <div class="th w10">
                        教材总字数
                    </div>
                    <div class="th w10">
                        其他作者
                    </div>
                    <div class="th w10">
                        类型
                    </div>
                    <div class="th w10">
                        出版单位
                    </div>
                    <div class="th w10">
                        出版地
                    </div>
                    <div class="th w10">
                        书号
                    </div>
                    <div class="th w10">
                        出版时间
                    </div>
                    <div class="th w10">
                        操作
                    </div>
                </div>
            <#if vos?size gt 0>
                <#list vos as vo>
                    <div class="tr clearfix border-bottom-none">
                        <div class="td w10">
                            <#if vo['teachingMaterial'].name?length gt 11>
                            ${vo['teachingMaterial'].name?substring(0,11)?html}
                            <#else>
                            ${vo['teachingMaterial'].name?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['author']?html}
                        </div>
                        <div class="td w10">
                        ${vo['teachingMaterial'].wordNumber?html}
                        </div>
                        <div class="td w10">
                            <#if vo['teachingMaterial'].moreAuthor?length gt 11>
                            ${vo['teachingMaterial'].moreAuthor?substring(0,11)?html}
                            <#else>
                            ${vo['teachingMaterial'].moreAuthor?html}
                            </#if>
                        </div>
                        <div class="td w10">
                        ${vo['teachingMaterial'].type?html}
                        </div>
                        <div class="td w10">
                        ${vo['teachingMaterial'].publishUnit?html}
                        </div>
                        <div class="td w10">
                        ${vo['teachingMaterial'].publishAddress?html}
                        </div>
                        <div class="td w10">
                        ${vo['teachingMaterial'].isbn?html}
                        </div>
                        <div class="td w10">
                        ${vo['teachingMaterial'].publishDate?html}
                        </div>
                        <div class="td w10">
                            <#if localRole.detail!='manager'&&teachingMaterial.status!=4>
                                <a href="#" onclick="del_site('/teachingMaterial/delete/${vo['teachingMaterial'].id}')"
                                   class="button-word2 btn_ajax_confirm">删除</a>
                            </#if>
                            <#if localRole.detail!='teacher'>
                            <a href="#" onclick="modify_site('/teachingMaterial/forEdit?teachingMaterialId=${vo['teachingMaterial'].id}')"
                               class="button-word2 btn_ajax_confirm">查看</a>
                            </#if>
                            <#if teachingMaterial.status==0>
                                <#if localRole.detail=='manager'||localRole.detail=='smanager'>
                                <a href="#" onclick="modify_site('/teachingMaterial/approve/${vo['teachingMaterial'].id}?status=1')"
                                   class="button-word2 btn_ajax_confirm">通过</a>
                                <a href="#" onclick="modify_site('/teachingMaterial/approve/${vo['teachingMaterial'].id}?status=2')"
                                   class="button-word2 btn_ajax_confirm">驳回</a>
                                </#if>
                            </#if>
                            <#if teachingMaterial.status==4>
                                <a href="#" onclick="modify_site('/teachingMaterial/approve/${vo['teachingMaterial'].id}?status=0')"
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