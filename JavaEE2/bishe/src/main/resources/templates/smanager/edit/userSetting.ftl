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
                    <h6 class="layout padding-left manage-head-con">用户设置
                    </h6>
                </div>
            </div>

        <#if msg??>
            <div class="header_msg" align="center">
                <p class="msg_p">${msg?html!"未知信息"}</p>
            </div>
        </#if>

        <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">完善信息</p>
                    </div>
                <#if localUser??>
                    <div class="form_div">
                        <form class="data_form" action="/user/setting" method="post">
                            <input type="hidden" name="id" value="${localUser.id}"/>
                            <div class="form-group">
                                <label class="">教职工编号</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           name="number" placeholder="教职工编号"
                                           value="${localUser.number!""}">
                                </div>
                                <label class="">姓名</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           disabled="disabled" value="${localUser.name}">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">职称</label>
                                <div class="col-sm-3">
                                    <select class="form-control" name="profession">
                                        <#list optionDatas['10']['profession'] as item>
                                            <#if localUser.profession==item>
                                                <option selected>${item!"出错"}</option>
                                            <#else>
                                                <option>${item!"出错"}</option>
                                            </#if>
                                        </#list>
                                    </select>
                                </div>
                                <label class="">电话</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           name="phone" placeholder="电话"
                                           value="${localUser.phone!""}"
                                           id="phone" onmouseover="validatePhone()">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">邮件</label>
                                <div class="col-sm-11">
                                    <input type="email" class="form-control_long"
                                           placeholder="邮件"
                                           disabled="disabled" value="${localUser.mail}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">角色</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           disabled="disabled" value="${localRole.name}">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">注册时间</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           disabled="disabled"
                                           value="${localUser.regDate?string("yyyy-MM-dd HH:mm:ss")}">
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <input class="content-submit" type="submit" value="保存修改">
                            </div>
                        </form>
                    </div>

                    <div class="form_div">
                        <form class="data_form" action="/user/setting" method="post">
                            <input type="hidden" name="id" value="${localUser.id}"/>
                            <div class="form-group">
                                <label class="">原密码</label>
                                <div class="col-sm-3">
                                    <input type="password" class="form-control"
                                           name="oldPassword" placeholder="原密码" minlength="6"
                                           required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">新密码</label>
                                <div class="col-sm-3">
                                    <input type="password" class="form-control"
                                           name="password" placeholder="新密码"
                                           id="pwd" minlength="6"
                                           required="required">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">确认新密码</label>
                                <div class="col-sm-3">
                                    <input type="password" class="form-control"
                                           name="password" placeholder="确认新密码"
                                           id="repwd" onmouseover="validate()" minlength="6"
                                           required="required">
                                </div>
                                <div class="col-sm-3">
                                    <p class="warn_div" style="color: red;"> </p>
                                </div>
                            </div>

                            <div class="content-form-submit" align="center">
                                <input class="content-submit register-submit" type="submit" value="修改密码">
                            </div>
                        </form>
                    </div>
                </#if>
                </div>
            </div>
        </div>
    </div>

</div>
</div>
</div>
<#include "../footer.ftl" parse=true>