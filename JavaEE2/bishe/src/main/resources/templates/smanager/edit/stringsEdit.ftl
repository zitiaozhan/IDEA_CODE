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
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">常用字符串编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/strings/edit" method="post">
                            <#if strings??>
                                <input type="hidden" name="id" value="${strings.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">实体类型</label>
                                <div class="col-sm-3">
                                    <select class="form-control" name="entityType">
                                    <#list infoTypeList as item>
                                        <#if strings?exists&&strings.entityType==item.id>
                                            <option selected value="${item.id}">${item.entityName!"出错"}</option>
                                        <#else>
                                            <option value="${item.id}">${item.entityName!"出错"}</option>
                                        </#if>
                                    </#list>
                                    </select>
                                </div>
                                <label class="">实体字段</label>
                                <div class="col-sm-3">
                                    <#if strings??>
                                        <input type="text" class="form-control"
                                               name="entityField" placeholder="实体字段"
                                               value="${strings.entityField}">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="entityField" placeholder="实体字段">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">信息内容</label>
                                <div class="col-sm-11">
                                    <#if strings??>
                                        <input type="text" class="form-control_long"
                                               name="content" placeholder="信息内容"
                                               value="${strings.content}">
                                    <#else>
                                        <input type="text" class="form-control_long"
                                               name="content" placeholder="信息内容">
                                    </#if>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <input class="content-submit" type="submit" value="提交">
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
</div>
<#include "../footer.ftl" parse=true>