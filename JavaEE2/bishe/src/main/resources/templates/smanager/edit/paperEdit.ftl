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
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">论文编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/paper/edit" method="post">
                            <#if paper??>
                                <input type="hidden" name="id" value="${paper.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">论文名称</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="论文名称"
                                               value="${paper.name}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="论文名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">第一作者</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <input type="hidden" name="firstAuthor" value="${paper.firstAuthor}">
                                        <input type="text" class="form-control redisabled"
                                               placeholder="第一作者"
                                               value="${firstAuthorName}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="hidden" name="firstAuthor" value="${localUser.id}">
                                        <input type="text" class="form-control"
                                               value="${localUser.name}" placeholder="第一作者"
                                               required="required" disabled>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">期刊名称</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <input type="text" class="form-control"
                                               name="periodicalName" placeholder="期刊名称"
                                               value="${paper.periodicalName}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="periodicalName" placeholder="期刊名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">第一署名单位</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <input type="text" class="form-control"
                                               name="firstSignatureUnit" placeholder="第一署名单位"
                                               value="${paper.firstSignatureUnit}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="firstSignatureUnit" placeholder="第一署名单位"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">发表时间</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <input type="date" class="form-control"
                                               name="publishDate" placeholder="发表时间"
                                               value="${paper.publishDate}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="publishDate" placeholder="发表时间"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">卷期编号</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <input type="text" class="form-control"
                                               name="issueNumber" placeholder="卷期编号"
                                               value="${paper.issueNumber}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="issueNumber" placeholder="卷期编号"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">其他作者</label>
                                <div class="col-sm-11">
                                    <#if paper??>
                                        <textarea class="form-textarea" name="moreAuthor"
                                                  cols="70" rows="7"
                                                  disabled="disabled">${paper.moreAuthor}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="moreAuthor"
                                                  cols="70" rows="7">其他作者</textarea>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">期刊级别</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <select class="form-control" name="level"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['9']['level'] as item>
                                                <#if paper.level==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="level"
                                                required="required">
                                            <#list optionDatas['9']['level'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">发表范围</label>
                                <div class="col-sm-3">
                                    <#if paper??>
                                        <select class="form-control" name="publishRange"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['9']['publishRange'] as item>
                                                <#if paper.publishRange==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="publishRange"
                                                required="required">
                                            <#list optionDatas['9']['publishRange'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">备注信息</label>
                                <div class="col-sm-11">
                                    <#if paper??>
                                        <textarea class="form-textarea" name="remark"
                                                  cols="70" rows="7"
                                                  disabled="disabled">${paper.remark}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="remark"
                                                  cols="70" rows="7">备注信息</textarea>
                                    </#if>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                            <#if paper??>
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