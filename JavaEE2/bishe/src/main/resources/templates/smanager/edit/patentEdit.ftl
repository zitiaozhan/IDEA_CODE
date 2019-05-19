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
                    <h6 class="layout padding-left manage-head-con"
                        onclick="modify_site('/patent')">
                        专利管理
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
                                <input type="hidden" name="status" value="${patent.status}"/>
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
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   placeholder="专利作者编号" name="author"
                                                   required="required" value="${patent.author}"
                                                   disabled="disabled">
                                        <#else>
                                            <input type="hidden" name="author" value="${patent.author}">
                                            <input type="text" class="form-control redisabled"
                                                   disabled="disabled" placeholder="专利作者"
                                                   value="${authorName}">
                                        </#if>
                                    <#else>
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   name="author" placeholder="专利作者编号"
                                                   value="${localUser.number}">
                                        <#else>
                                            <input type="hidden" name="author" value="${localUser.number}"/>
                                            <input type="text" class="form-control"
                                                   disabled="disabled" placeholder="专利作者"
                                                   value="${localUser.name}">
                                        </#if>
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
                                        <textarea class="form-textarea more-author" name="moreAuthor"
                                                  cols="70" rows="7" placeholder="更多人员"
                                                  onmouseout="validateMoreAuthor('参加人员')"
                                                  disabled="disabled">${patent.moreAuthor}</textarea>
                                    <#else>
                                        <textarea class="form-textarea more-author" name="moreAuthor"
                                                  onmouseout="validateMoreAuthor('参加人员')"
                                                  cols="70" rows="7" placeholder="更多人员"></textarea>
                                    </#if>（填入作者编号，使用英文逗号隔开）
                                    <p class="warn_div" style="color: red;"></p>
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
                                    <#if (patent.status==0&&localRole.detail=='teacher')||(localRole.detail=='smanager')>
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