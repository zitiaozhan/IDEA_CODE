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
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">专利编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/patent/edit" method="post">
                            <#if patent??>
                                <input type="hidden" name="id" value="${patent.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">专利名称</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="专利名称"
                                               value="${patent.name}" required="required"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="专利名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">专利编号</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="text" class="form-control"
                                               name="patentNumber" placeholder="专利编号"
                                               value="${patent.patentNumber}" required="required"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="patentNumber" placeholder="专利编号"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">证书编号</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="text" class="form-control"
                                               name="certificateId" placeholder="证书编号"
                                               value="${patent.certificateId}" required="required"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="certificateId" placeholder="证书编号"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">专利作者</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="hidden" name="author" value="${patent.author}">
                                        <input type="text" class="form-control redisabled"
                                               placeholder="专利作者"
                                               value="${authorName}" required="required"
                                               disabled="disabled">
                                    <#else>
                                        <input type="hidden" name="author" value="${localUser.id}">
                                        <input type="text" class="form-control"
                                               value="${localUser.name}" placeholder="专利作者"
                                               required="required" disabled="disabled">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">专利类型</label>
                                <div class="col-sm-3">
                                <#if patent??>
                                    <select class="form-control" name="type"
                                            required="required" disabled="disabled">
                                        <#list optionDatas['5']['type'] as item>
                                            <#if patent.type==item>
                                                <option selected>${item!"出错"}</option>
                                            <#else>
                                                <option>${item!"出错"}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                <#else>
                                    <select class="form-control" name="type" required="required">
                                        <#list optionDatas['5']['type'] as item>
                                            <option>${item!"出错"}</option>
                                        </#list>
                                    </select>
                                </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">更多人员</label>
                                <div class="col-sm-11">
                                    <#if patent??>
                                        <textarea class="form-textarea" name="moreAuthor"
                                                  cols="70" rows="7"
                                                  disabled="disabled">${patent.applyDate}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="moreAuthor"
                                                  cols="70" rows="7">更多人员</textarea>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">申请日期</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="date" class="form-control"
                                               name="applyDate" placeholder="申请日期"
                                               value="${patent.applyDate}" required="required"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="applyDate" placeholder="申请日期"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">发证日期</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="date" class="form-control"
                                               name="awardDate" placeholder="发证日期"
                                               value="${patent.awardDate}" required="required"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="awardDate" placeholder="发证日期"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">专利权人</label>
                                <div class="col-sm-3">
                                    <#if patent??>
                                        <input type="text" class="form-control"
                                               name="patentedPerson" placeholder="专利权人"
                                               value="${patent.patentedPerson}" disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="patentedPerson" placeholder="专利权人">
                                    </#if>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if patent??>
                                    <#if localRole.detail!='teacher'>
                                        <input class="content-submit" id="edit_btn" type="button"
                                               value="编辑">
                                    </#if>
                                <#else>
                                    <input class="content-submit" type="submit" value="提交">
                                </#if>
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