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
                        onclick="modify_site('/softwareWork')">
                        软件著作管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">软件著作编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/softwareWork/edit" method="post">
                            <#if softwareWork??>
                                <input type="hidden" name="id" value="${softwareWork.id}"/>
                                <input type="hidden" name="status" value="${softwareWork.status}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">软件名称</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="软件名称"
                                               value="${softwareWork.name}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="软件名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">著作权人</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="text" class="form-control"
                                               name="copyrightOwner" placeholder="著作权人"
                                               value="${softwareWork.copyrightOwner}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="copyrightOwner" placeholder="著作权人">
                                    </#if>
                                </div>（或单位）
                            </div>

                            <div class="form-group">
                                <label class="">完成日期</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="date" class="form-control"
                                               name="completeDate" placeholder="完成日期"
                                               value="${softwareWork.completeDate}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="completeDate" placeholder="完成日期"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">首次发表</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="date" class="form-control"
                                               name="firstPublishDate" placeholder="首次发表"
                                               value="${softwareWork.firstPublishDate}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="firstPublishDate" placeholder="首次发表"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">取得方式</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <select class="form-control" name="ownWay"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['6']['ownWay'] as item>
                                                <#if softwareWork.ownWay==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="ownWay"
                                                required="required">
                                            <#list optionDatas['6']['ownWay'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                                <label class="">权利范围</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <select class="form-control" name="rightRange"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['6']['rightRange'] as item>
                                                <#if softwareWork.rightRange==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="rightRange"
                                                required="required">
                                            <#list optionDatas['6']['rightRange'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">登记编号</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="text" class="form-control"
                                               name="publishNumber" placeholder="登记编号"
                                               value="${softwareWork.publishNumber}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="publishNumber" placeholder="登记编号"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">证书编号</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="text" class="form-control"
                                               name="certificateId" placeholder="证书编号"
                                               value="${softwareWork.certificateId}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="certificateId" placeholder="证书编号"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">发证时间</label>
                                <div class="col-sm-3">
                                    <#if softwareWork??>
                                        <input type="date" class="form-control"
                                               name="awardDate" placeholder="发证时间"
                                               value="${softwareWork.awardDate}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="awardDate" placeholder="发证时间"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">开发人员</label>
                                <div class="col-sm-11">
                                    <#if softwareWork??>
                                        <textarea class="form-textarea more-author" name="author"
                                                  cols="70" rows="7" placeholder="开发人员"
                                                  disabled="disabled"
                                                  onmouseout="validateMoreAuthor('开发人员')"
                                                  required="required">${softwareWork.author}</textarea>
                                    <#else>
                                        <textarea class="form-textarea more-author" name="author"
                                                  cols="70" rows="7"
                                                  onmouseout="validateMoreAuthor('开发人员')"
                                                  required="required" placeholder="开发人员">${localUser.number}</textarea>
                                    </#if>
                                </div>（填入作者编号，使用英文逗号隔开）
                                <p class="warn_div" style="color: red;"></p>
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if softwareWork??>
                                    <#if (softwareWork.status==0&&localRole.detail=='teacher')||(localRole.detail=='smanager')>
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