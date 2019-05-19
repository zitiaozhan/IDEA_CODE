<#include "../header.ftl" parse=true>
<#setting number_format="#">
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
                        onclick="modify_site('/governmentProject')">
                        纵向项目管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">纵向项目编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/governmentProject/edit" method="post">
                            <#if governmentProject??>
                                <input type="hidden" name="id" value="${governmentProject.id}"/>
                                <input type="hidden" name="status" value="${governmentProject.status}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">项目名称</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="项目名称"
                                               value="${governmentProject.name}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="项目名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">负责人员</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   placeholder="负责人员编号" name="chargePerson"
                                                   required="required" value="${governmentProject.chargePerson}"
                                                   disabled="disabled">
                                        <#else>
                                            <input type="hidden" name="chargePerson" value="${governmentProject.chargePerson}"/>
                                            <input type="text" class="form-control redisabled"
                                                   disabled="disabled" placeholder="负责人员"
                                                   value="${chargePersonName}">
                                        </#if>
                                    <#else>
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   name="chargePerson" placeholder="负责人员编号"
                                                   value="${localUser.number}">
                                        <#else>
                                            <input type="hidden" name="chargePerson" value="${localUser.number}"/>
                                            <input type="text" class="form-control"
                                                   disabled="disabled" placeholder="负责人员"
                                                   value="${localUser.name}">
                                        </#if>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">立项编号</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="projectId" placeholder="立项编号"
                                               value="${governmentProject.projectId}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="projectId" placeholder="立项编号"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">科技代码</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="techCode" placeholder="科技代码"
                                               value="${governmentProject.techCode}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="techCode" placeholder="科技代码"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">财务代码</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="finaCode" placeholder="财务代码"
                                               value="${governmentProject.finaCode}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="finaCode" placeholder="财务代码"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">负责单位</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="chargeGroup" placeholder="负责单位"
                                               value="${governmentProject.chargeGroup}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="chargeGroup" placeholder="负责单位"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">研究周期</label>
                                <div class="col-sm-11">
                                    <#if governmentProject??>
                                        <input type="date" class="form-control"
                                               name="projectDate" required="required"
                                               disabled="disabled" value="${governmentProject.projectDate}">
                                        至
                                        <input type="date" class="form-control"
                                               name="helpContent"
                                               disabled="disabled" value="${governmentProject.helpContent!""}">
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
                                <label class="">项目来源</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="projectSource" placeholder="项目来源"
                                               value="${governmentProject.projectSource}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="projectSource" placeholder="项目来源"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">拨款时间</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="date" class="form-control"
                                               name="arrivalDate" placeholder="拨款时间"
                                               value="${governmentProject.arrivalDate}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="arrivalDate" placeholder="拨款时间">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">参加人员</label>
                                <div class="col-sm-11">
                                    <#if governmentProject??>
                                        <textarea class="form-textarea more-author" name="participant"
                                                  cols="70" rows="7" onmouseout="validateMoreAuthor('参加人员')"
                                                  disabled="disabled" placeholder="参加人员">${governmentProject.participant!""}</textarea>
                                    <#else>
                                        <textarea class="form-textarea more-author" name="participant"
                                                  onmouseout="validateMoreAuthor('参加人员')"
                                                  cols="70" rows="7" placeholder="参加人员"></textarea>
                                    </#if>（填入作者编号，使用英文逗号隔开）
                                    <p class="warn_div" style="color: red;"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">项目类型</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <select class="form-control" name="projectType"
                                                disabled="disabled" required="required">
                                            <#list optionDatas['2']['projectType'] as item>
                                                <#if governmentProject.projectType==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="projectType"
                                                required="required">
                                            <#list optionDatas['2']['projectType'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">合同经费</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="number" class="form-control" min="5000"
                                               name="contractMoney" placeholder="合同经费"
                                               value="${governmentProject.contractMoney}"
                                               disabled="disabled" required="required">
                                    <#else>
                                        <input type="number" class="form-control"
                                               name="contractMoney" placeholder="合同经费"
                                               value="" required="required">
                                    </#if>
                                </div>
                                <label class="">实到经费</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="number" class="form-control" min="5000"
                                               name="arrivalMoney" placeholder="实到经费"
                                               value="${governmentProject.arrivalMoney}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="number" class="form-control"
                                               name="arrivalMoney" placeholder="实到经费">
                                    </#if>
                                </div>
                                (元)
                            </div>

                            <div class="form-group">
                                <label class="">经费来源</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="text" class="form-control"
                                               name="moneySource" placeholder="经费来源"
                                               value="${governmentProject.moneySource}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="moneySource" placeholder="经费来源">
                                    </#if>
                                </div>
                                <label class="">结题时间</label>
                                <div class="col-sm-3">
                                    <#if governmentProject??>
                                        <input type="date" class="form-control"
                                               name="completeDate" placeholder="结题时间"
                                               value="${governmentProject.completeDate}"
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
                                    <#if governmentProject??>
                                        <select class="form-control" name="projectStatus"
                                                disabled="disabled" required="required">
                                            <#list optionDatas['2']['projectStatus'] as item>
                                                <#if governmentProject.projectStatus==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="projectStatus"
                                                required="required">
                                            <#list optionDatas['2']['projectStatus'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">备注内容</label>
                                <div class="col-sm-11">
                                    <#if governmentProject??>
                                        <textarea class="form-textarea" name="remark"
                                                  cols="70" rows="7" placeholder="备注内容"
                                                  disabled="disabled">${governmentProject.remark}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="remark"
                                                  cols="70" rows="7" placeholder="备注内容"></textarea>
                                    </#if>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if governmentProject??>
                                    <#if (governmentProject.status==0&&localRole.detail=='teacher')||(localRole.detail=='smanager')>
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