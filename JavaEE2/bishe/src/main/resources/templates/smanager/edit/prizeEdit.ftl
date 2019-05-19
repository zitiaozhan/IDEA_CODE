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
                        onclick="modify_site('/prize')">
                        获奖信息管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">获奖信息编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/prize/edit" method="post">
                            <#if prize??>
                                <input type="hidden" name="id" value="${prize.id}"/>
                                <input type="hidden" name="status" value="${prize.status}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">项目名称</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="项目名称"
                                               required="required" value="${prize.name}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="项目名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">获奖人员</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   placeholder="获奖人员编号" name="author"
                                                   required="required" value="${prize.author}"
                                                   disabled="disabled">
                                        <#else>
                                            <input type="hidden" name="author" value="${prize.author}">
                                            <input type="text" class="form-control redisabled"
                                                   disabled="disabled" placeholder="获奖人员"
                                                   value="${authorName}">
                                        </#if>
                                    <#else>
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   name="author" placeholder="获奖人员编号"
                                                   value="${localUser.number}">
                                        <#else>
                                            <input type="hidden" name="author" value="${localUser.number}"/>
                                            <input type="text" class="form-control"
                                                   disabled="disabled" placeholder="获奖人员"
                                                   value="${localUser.name}">
                                        </#if>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">证书编号</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <input type="text" class="form-control"
                                               name="certificateId" placeholder="证书编号"
                                               required="required" value="${prize.certificateId}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="certificateId" placeholder="证书编号"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">颁奖单位</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <input type="text" class="form-control"
                                               name="awardUnit" placeholder="颁奖单位"
                                               required="required" value="${prize.awardUnit}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="awardUnit" placeholder="颁奖单位"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">其它参与者</label>
                                <div class="col-sm-11">
                                    <#if prize??>
                                        <textarea class="form-textarea more-author" name="guidanceTeacher"
                                                  cols="70" rows="7" placeholder="其它参与者"
                                                  onmouseout="validateMoreAuthor('其它参与者')"
                                                  disabled="disabled">${prize.guidanceTeacher}</textarea>
                                    <#else>
                                        <textarea class="form-textarea more-author" name="guidanceTeacher"
                                                  onmouseout="validateMoreAuthor('其它参与者')"
                                                  cols="70" rows="7" placeholder="其它参与者"></textarea>
                                    </#if>（填入作者编号，使用英文逗号隔开）
                                    <p class="warn_div" style="color: red;"></p>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">获奖类型</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <select class="form-control"
                                                name="prizeType" required="required"
                                                disabled="disabled">
                                            <#list optionDatas['3']['prizeType'] as item>
                                                <#if prize.prizeType==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control"
                                                name="prizeType" required="required">
                                            <#list optionDatas['3']['prizeType'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">获奖等级</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <select class="form-control"
                                                name="prizeLevel" required="required"
                                                disabled="disabled">
                                            <#list optionDatas['3']['prizeLevel'] as item>
                                                <#if prize.prizeLevel==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control"
                                                name="prizeLevel" required="required">
                                            <#list optionDatas['3']['prizeLevel'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">获奖时间</label>
                                <div class="col-sm-3">
                                    <#if prize??>
                                        <input type="date" class="form-control"
                                               name="prizeDate" placeholder="获奖时间"
                                               required="required" value="${prize.prizeDate}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="prizeDate" placeholder="获奖时间"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if prize??>
                                    <#if (prize.status==0&&localRole.detail=='teacher')||(localRole.detail=='smanager')>
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