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
                    <h6 class="layout padding-left manage-head-con">学术讲座管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">学术讲座编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/lecture/edit" method="post">
                            <#if lecture??>
                                <input type="hidden" name="id" value="${lecture.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">报告名称</label>
                                <div class="col-sm-3">
                                    <#if lecture??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="报告名称"
                                               required="required" value="${lecture.name}">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="报告名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">报告人员</label>
                                <div class="col-sm-3">
                                    <#if lecture??>
                                        <input type="hidden" name="rapporteur" value="${lecture.rapporteur}">
                                        <input type="text" class="form-control"
                                               placeholder="报告人员"
                                               disabled="disabled" required="required"
                                               value="${rapporteurName}">
                                    <#else>
                                        <input type="hidden" name="rapporteur" value="${localUser.id}">
                                        <input type="text" class="form-control"
                                               value="${localUser.name}" placeholder="报告人员"
                                               disabled="disabled" required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">报告时间</label>
                                <div class="col-sm-3">
                                    <#if lecture??>
                                        <input type="date" class="form-control"
                                               name="date" placeholder="报告时间"
                                               required="required" value="${lecture.date}">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="date" placeholder="报告时间"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">报告人单位</label>
                                <div class="col-sm-3">
                                    <#if lecture??>
                                        <input type="text" class="form-control"
                                               name="rapporteurUnit" placeholder="报告人单位"
                                               required="required" value="${lecture.rapporteurUnit}">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="rapporteurUnit" placeholder="报告人单位"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">报告地点</label>
                                <div class="col-sm-3">
                                    <#if lecture??>
                                        <input type="text" class="form-control"
                                               name="address" placeholder="报告地点"
                                               required="required" value="${lecture.address}">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="address" placeholder="报告地点"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">主办单位</label>
                                <div class="col-sm-3">
                                    <#if lecture??>
                                        <input type="text" class="form-control"
                                               name="holdUnit" placeholder="主办单位"
                                               required="required" value="${lecture.holdUnit}">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="holdUnit" placeholder="主办单位"
                                               required="required">
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