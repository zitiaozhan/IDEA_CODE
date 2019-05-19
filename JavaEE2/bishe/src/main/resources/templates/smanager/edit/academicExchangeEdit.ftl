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
                    <h6 class="layout padding-left manage-head-con" onclick="modify_site('/academicExchange')">
                        学术交流活动管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">学术交流活动编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/academicExchange/edit" method="post">
                            <#if academicExchange??>
                                <input type="hidden" name="id" value="${academicExchange.id}"/>
                                <input type="hidden" name="status" value="${academicExchange.status}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">活动名称</label>
                                <div class="col-sm-3">
                                    <#if academicExchange??>
                                        <input type="text" class="form-control"
                                               name="meetingName" placeholder="活动名称"
                                               required="required" value="${academicExchange.meetingName}">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="meetingName" placeholder="活动名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">活动时间</label>
                                <div class="col-sm-3">
                                    <#if academicExchange??>
                                        <input type="date" class="form-control"
                                               name="meetingDate" placeholder="活动时间"
                                               required="required" value="${academicExchange.meetingDate}">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="meetingDate" placeholder="活动时间"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">活动地点</label>
                                <div class="col-sm-11">
                                    <#if academicExchange??>
                                        <input type="text" class="form-control_long"
                                               name="meetingAddress" placeholder="活动地点"
                                               required="required" value="${academicExchange.meetingAddress}">
                                    <#else>
                                        <input type="text" class="form-control_long"
                                               name="meetingAddress" placeholder="活动地点"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">主办学院</label>
                                <div class="col-sm-11">
                                    <#if academicExchange??>
                                        <input type="text" class="form-control_long"
                                               name="college" placeholder="主办学院"
                                               required="required" value="${academicExchange.college}">
                                    <#else>
                                        <input type="text" class="form-control_long"
                                               name="college" placeholder="主办学院"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">参加人员</label>
                                <div class="col-sm-11">
                                    <#if academicExchange??>
                                        <textarea class="form-textarea more-author" name="mainParticipant"
                                                  placeholder="参加人员"
                                                  onmouseout="validateMoreAuthor('参加人员')"
                                                  cols="70" rows="7">${academicExchange.mainParticipant}</textarea>
                                    <#else>
                                        <textarea class="form-textarea more-author" name="mainParticipant"
                                                  onmouseout="validateMoreAuthor('参加人员')"
                                                  cols="70" rows="7" placeholder="参加人员">${localUser.number}</textarea>
                                    </#if>
                                </div>（填入作者编号，使用英文逗号隔开）
                                <p class="warn_div" style="color: red;"></p>
                            </div>

                            <div class="form-group">
                                <label class="">备注信息</label>
                                <div class="col-sm-11">
                                    <#if academicExchange??>
                                        <textarea class="form-textarea" name="remark"
                                                  placeholder="备注信息"
                                                  cols="70" rows="7">${academicExchange.remark}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="remark"
                                                  cols="70" rows="7" placeholder="备注信息"></textarea>
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