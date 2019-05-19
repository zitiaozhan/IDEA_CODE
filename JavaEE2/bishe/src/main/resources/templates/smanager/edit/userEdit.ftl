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
        location.href = "/toLogin";
    </script>
</#if>

<#--页面内容-->
    <div class="view-product">
        <div class="authority">
            <div class="authority-head">
                <div class="manage-head">
                    <h6 class="layout padding-left manage-head-con"
                        onclick="modify_site('/user')">
                        帐号管理
                    </h6>
                </div>
            </div>
        <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">账户编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/user/edit" method="post">
                        <#if user??>
                            <input type="hidden" name="id" value="${user.id}"/>
                        </#if>
                            <div class="form-group">
                                <label class="">教职工编号</label>
                                <div class="col-sm-3">
                                <#if user??>
                                    <input type="text" class="form-control"
                                           name="number" placeholder="教职工编号"
                                           required="required" value="${user.number!""}">
                                <#else>
                                    <input type="text" class="form-control"
                                           name="number" placeholder="教职工编号"
                                           required="required">
                                </#if>
                                </div>
                                <label class="">姓名</label>
                                <div class="col-sm-3">
                                <#if user??>
                                    <input type="text" class="form-control"
                                           name="name" placeholder="姓名"
                                           required="required" value="${user.name}">
                                <#else>
                                    <input type="text" class="form-control"
                                           name="name" placeholder="姓名"
                                           required="required">
                                </#if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">设置密码</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           name="password" placeholder="密码"
                                           required="required" minlength="6">
                                </div>
                                <label class="">角色</label>
                                <div class="col-sm-3">
                                    <select class="form-control" name="roleId"
                                            required="required">
                                    <#list roleList as role>
                                        <#if user??>
                                            <#if user.roleId==role.id>
                                                <option value="${role.id}"
                                                        selected="selected">${role.name?html}</option>
                                            </#if>
                                        <#else>
                                            <option value="${role.id}">${role.name?html}</option>
                                        </#if>
                                    </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">职称</label>
                                <div class="col-sm-3">
                                    <select class="form-control" name="profession">
                                    <#list optionDatas['10']['profession'] as item>
                                        <#if user??&&user.profession??&&user.profession==item>
                                            <option selected>${item!"出错"}</option>
                                        <#else>
                                            <option>${item!"出错"}</option>
                                        </#if>
                                    </#list>
                                    </select>
                                </div>
                                <label class="">电话</label>
                                <div class="col-sm-3">
                                <#if user??>
                                    <input type="text" class="form-control"
                                           name="phone" placeholder="电话"
                                           value="${user.phone!""}" id="phone"
                                           onmouseout="validatePhone()">
                                <#else>
                                    <input type="text" class="form-control"
                                           name="phone" placeholder="电话"
                                           id="phone" onmouseout="validatePhone()">
                                </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">邮件</label>
                                <div class="col-sm-11">
                                <#if user??>
                                    <input type="email" class="form-control_long"
                                           name="mail" placeholder="邮件"
                                           required="required" value="${user.mail}">
                                <#else>
                                    <input type="email" class="form-control_long"
                                           name="mail" placeholder="邮件"
                                           required="required">
                                </#if>
                                </div>
                            </div>
                            <div class="col-sm-3">
                                <p class="warn_div" style="color: red;"></p>
                            </div>

                            <div class="content-form-submit" align="center">
                                <input class="content-submit" type="submit" value="提交">
                            </div>
                        </form>
                    </div>
                </div>
            </div>

        <#--批量注册-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">账户批量注册</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/user/batchReg" method="post">
                            <div class="form-group">
                                <label class="">教职工编号从</label>
                                <div class="col-sm-3">
                                    <input type="number" class="form-control" minlength="3" min="100" maxlength="15"
                                           name="numberFrom" placeholder="教职工编号"
                                           required="required">
                                </div>
                                <label class="">教职工编号至</label>
                                <div class="col-sm-3">
                                    <input type="number" class="form-control"
                                           name="numberTo" placeholder="教职工编号"
                                           required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">设置密码</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           name="password" placeholder="密码"
                                           required="required" minlength="6">
                                </div>
                                <label class="">角色</label>
                                <div class="col-sm-3">
                                    <select class="form-control" name="roleId"
                                            required="required">
                                    <#list roleList as role>
                                        <option value="${role.id}">${role.name?html}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">职称</label>
                                <div class="col-sm-3">
                                    <select class="form-control" name="profession">
                                    <#list optionDatas['10']['profession'] as item>
                                        <option>${item!"出错"}</option>
                                    </#list>
                                    </select>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <input class="content-submit-2" type="submit" value="提交">
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