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
                        onclick="modify_site('/teachingMaterial')">
                        教材管理
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
                                <input type="hidden" name="status" value="${teachingMaterial.status}"/>
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
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   placeholder="教材作者编号" name="author"
                                                   required="required" value="${teachingMaterial.author}"
                                                   disabled="disabled">
                                        <#else>
                                            <input type="hidden" name="author" value="${teachingMaterial.author}">
                                            <input type="text" class="form-control redisabled"
                                                   disabled="disabled" placeholder="教材作者"
                                                   value="${authorName}">
                                        </#if>
                                    <#else>
                                        <#if localRole.detail=='smanager'>
                                            <input type="text" class="form-control"
                                                   name="author" placeholder="教材作者编号"
                                                   value="${localUser.number}">
                                        <#else>
                                            <input type="hidden" name="author" value="${localUser.number}"/>
                                            <input type="text" class="form-control"
                                                   disabled="disabled" placeholder="教材作者"
                                                   value="${localUser.name}">
                                        </#if>
                                    </#if>
                                </div>
                                <label class="">教材字数</label>
                                <div class="col-sm-3">
                                    <#if teachingMaterial??>
                                        <input type="number" class="form-control"
                                               name="wordNumber" placeholder="教材字数"
                                               required="required" min="0"
                                               value="${teachingMaterial.wordNumber}"
                                               disabled="disabled">
                                    <#else>
                                        <input type="number" class="form-control"
                                               name="wordNumber" placeholder="教材字数"
                                               min="0" required="required">
                                    </#if>
                                </div>万字
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
                                        <textarea class="form-textarea more-author" name="moreAuthor"
                                                  cols="70" rows="7" placeholder="其他作者排序"
                                                  onmouseout="validateMoreAuthor('其他作者排序')"
                                                  disabled="disabled">${teachingMaterial.moreAuthor}</textarea>
                                    <#else>
                                        <textarea class="form-textarea more-author" name="moreAuthor"
                                                  onmouseout="validateMoreAuthor('其他作者排序')"
                                                  cols="70" rows="7" placeholder="其他作者排序"></textarea>
                                    </#if>
                                </div>（填入作者编号，使用英文逗号隔开）
                                <p class="warn_div" style="color: red;"></p>
                            </div>

                            <div class="content-form-submit" align="center">
                                <#if teachingMaterial??>
                                    <#if (teachingMaterial.status==0&&localRole.detail=='teacher')||(localRole.detail=='smanager')>
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