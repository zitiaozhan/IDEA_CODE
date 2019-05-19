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
                        添加公告
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">公告编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/message/addBulletin" method="post">
                            <div class="form-group">
                                <label class="">公告</label>
                                <div class="col-sm-11">
                                    <textarea class="form-textarea" name="content" cols="70" rows="7" placeholder="公告详情"></textarea>
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