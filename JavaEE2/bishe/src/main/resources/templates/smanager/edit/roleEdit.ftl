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
                        onclick="modify_site('/role')">
                        角色管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">角色编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/role/edit" method="post">
                            <#if governmentProject??>
                                <input type="hidden" name="id" value="${role.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">名称</label>
                                <div class="col-sm-3">
                                    <input type="text" class="form-control"
                                           name="name" placeholder="名称"
                                           required="required">
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">详情</label>
                                <div class="col-sm-11">
                            <textarea class="form-textarea" name="detail" cols="70" rows="7" placeholder="权限详情"></textarea>
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