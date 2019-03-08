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
                    <h6 class="layout padding-left manage-head-con">教材管理
                    </h6>
                </div>
            </div>
            <#--数据编辑-->
            <div align="center">
                <div class="input_content" align="left">
                    <div class="content_header">
                        <p class="text_p">教材编辑</p>
                    </div>
                    <div class="form_div">
                        <form class="data_form" action="/teachingMaterial/edit" method="post">
                            <#if teachingMaterial??>
                                <input type="hidden" name="id" value="${teachingMaterial.id}"/>
                            </#if>
                            <div class="form-group">
                                <label class="">教材名称</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="教材名称"
                                               required="required" value="${teachingMaterial.name}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="name" placeholder="教材名称"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">出版时间</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="date" class="form-control"
                                               name="publishDate" placeholder="出版时间"
                                               required="required" value="${teachingMaterial.publishDate}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="date" class="form-control"
                                               name="publishDate" placeholder="出版时间"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">教材作者</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="hidden" name="author" value="${teachingMaterial.author}">
                                        <input type="text" class="form-control redisabled"
                                               placeholder="教材作者"
                                               required="required" disabled="disabled"
                                               value="${authorName}">
                                    <#else>
                                        <input type="hidden" name="author" value="${localUser.id}">
                                        <input type="text" class="form-control"
                                               value="${localUser.name}" placeholder="教材作者"
                                               required="required" disabled="disabled" >
                                    </#if>
                                </div>
                                <label class="">教材字数</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="number" class="form-control"
                                               name="wordNumber" placeholder="教材字数"
                                               required="required" min="1000"
                                               value="${teachingMaterial.wordNumber}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="number" class="form-control"
                                               name="wordNumber" placeholder="教材字数"
                                               min="1000" required="required">
                                    </#if>
                                </div>字
                            </div>

                            <div class="form-group">
                                <label class="">出版地点</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="text" class="form-control"
                                               name="publishAddress" placeholder="出版地点"
                                               required="required" value="${teachingMaterial.publishAddress}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="publishAddress" placeholder="出版地点"
                                               required="required">
                                    </#if>
                                </div>
                                <label class="">书籍编号</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="text" class="form-control"
                                               name="isbn" placeholder="书籍编号"
                                               required="required" value="${teachingMaterial.isbn}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="isbn" placeholder="书籍编号"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">地域</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <select class="form-control" name="area"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['4']['area'] as item>
                                                <#if teachingMaterial.area==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="area" required="required">
                                            <#list optionDatas['4']['area'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">教材类型</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <select class="form-control" name="type"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['4']['type'] as item>
                                                <#if teachingMaterial.type==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="type" required="required">
                                            <#list optionDatas['4']['type'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="">出版社类型</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <select class="form-control" name="publishLevel"
                                                required="required" disabled="disabled">
                                            <#list optionDatas['4']['publishLevel'] as item>
                                                <#if teachingMaterial.publishLevel==item>
                                                    <option selected>${item!"出错"}</option>
                                                <#else>
                                                    <option>${item!"出错"}</option>
                                                </#if>
                                            </#list>
                                        </select>
                                    <#else>
                                        <select class="form-control" name="publishLevel" required="required">
                                            <#list optionDatas['4']['publishLevel'] as item>
                                                <option>${item!"出错"}</option>
                                            </#list>
                                        </select>
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">出版单位</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="text" class="form-control"
                                               name="publishUnit" placeholder="出版单位"
                                               required="required" value="${teachingMaterial.publishUnit}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="text" class="form-control"
                                               name="publishUnit" placeholder="出版单位"
                                               required="required">
                                    </#if>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="">作者排序</label>
                                <div class="col-sm-11">
                                    <#if teachingMaterial??>
                                        <textarea class="form-textarea" name="moreAuthor"
                                                  cols="70" rows="7"
                                                  disabled="disabled">${teachingMaterial.moreAuthor}</textarea>
                                    <#else>
                                        <textarea class="form-textarea" name="moreAuthor"
                                                  cols="70" rows="7">其他作者排序</textarea>
                                    </#if>
                                </div>（使用中文逗号隔开）
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if teachingMaterial??>
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