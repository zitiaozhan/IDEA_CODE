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
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">横向项目编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/companyProject/edit" method="post">
                            <#if companyProject??>
                                <input type="hidden" name="id" value="${companyProject.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">项目名称</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="项目名称"
                                               required="required" value="${companyProject.name}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="项目名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">负责人员</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="hidden" name="chargePerson" value="${localUser.id}"/>
                                        <input type="text" class="form-control redisabled"
                                               placeholder="负责人员"
                                               required="required" value="${chargePersonName}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="hidden" name="chargePerson" value="${localUser.id}"/>
                                        <input type="text" class="form-control"
                                               disabled="disabled" placeholder="负责人员"
                                               value="${localUser.id}">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">起止时间</label>
                                <div class="col-sm-11">
                                    <#if companyProject??>
                                        <input type="date" class="form-control"
                                               name="projectDate" required="required"
                                               disabled="disabled" value="${companyProject.projectDate}">
                                        至
                                        <input type="date" class="form-control"
                                               name="helpContent"
                                               disabled="disabled" value="${companyProject.helpContent!""}">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="projectDate" required="required"
                                               value="">
                                        至
                                        <input type="date" class="form-control"
                                               name="helpContent"
                                               value="">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">负责单位</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="text" class="form-control"
                                               name="chargeGroup" placeholder="负责单位"
                                               required="required" value="${companyProject.chargeGroup}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="chargeGroup" placeholder="负责单位"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">项目来源</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="text" class="form-control"
                                               name="projectSource" placeholder="项目来源"
                                               required="required" value="${companyProject.projectSource}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="projectSource" placeholder="项目来源"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">参加人员</label>
                                <div class="col-sm-11">
                                    <#if companyProject??>
                                        <textarea class="form-textarea" name="participant"
                                                  cols="70" rows="7"
                                                  disabled="disabled">${companyProject.participant}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="participant"
                                                  cols="70" rows="7">参加人员</textarea>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">项目类型</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <select class="form-control" name="projectType"
                                                disabled="disabled" required="required">
                                            <#list optionDatas['1']['projectType'] as item>
                                                <#if companyProject.projectType==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="projectType"
                                                required="required">
                                            <#list optionDatas['1']['projectType'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">合同经费</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="text" class="form-control"
                                               name="contractMoney" placeholder="合同经费"
                                               required="required" value="${companyProject.contractMoney}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="contractMoney" placeholder="合同经费"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">到款经费</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="text" class="form-control"
                                               name="arrivalMoney" placeholder="到款经费"
                                               value="${companyProject.arrivalMoney}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="arrivalMoney" placeholder="到款经费">
                                    </#if>
                                </div>
                                (万元)
                            </div>

                            <div class="form-group">
                                <label class="">拨款时间</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="date" class="form-control"
                                               name="arrivalDate" placeholder="拨款时间"
                                               value="${companyProject.arrivalDate!""}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="arrivalDate" placeholder="拨款时间">
                                    </#if>
                                </div>
                                <label class="">结题时间</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <input type="date" class="form-control"
                                               name="completeDate" placeholder="结题时间"
                                               value="${companyProject.completeDate!""}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="completeDate" placeholder="结题时间">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">项目状态</label>
                                <div class="col-sm-3">
                                    <#if companyProject??>
                                        <select class="form-control" name="projectStatus"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['1']['projectStatus'] as item>
                                                <#if companyProject.projectStatus==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="projectStatus"
                                                required="required">
                                            <#list optionDatas['1']['projectStatus'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if companyProject??>
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