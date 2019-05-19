<div class="header_content">
    <div class="add_headerDiv">
        <#if isAdd&&localRole.detail!='manager'>
            <a class="add_entity" href="/${type}/forEdit">新增</a>
        </#if>
    </div>
    <#--新增公告-->
    <#if isMessage??>
        <div class="add_headerDiv">
            <#if isMessage&&localRole.detail=='smanager'>
                <a class="add_entity" href="/backJumpTo?path=smanager/edit/bulletinEdit">新增公告</a>
            </#if>
        </div>
    </#if>

    <div class="form_headerDiv">
        <form action="/${type}" method="post">
            <input class="form_input" name="${field}" type="text"
                   value="<#if value??>${value}</#if>" placeholder="<#if placeholder??>${placeholder}<#else>请输入</#if>"/>
            <#if stringsType?exists&&stringsType>
                <select class="form-control" name="entityType">
                    <#list stringsTypeList as item>
                        <option value="${item.value}">${item.desc!"加载出错..."}</option>
                    </#list>
                </select>
            </#if>
            <#if isProject>
                <select class="form_input" name="status">
                    <option value="0" <#if project.status==0>selected</#if>>申报提交</option>
                    <option value="1" <#if project.status==1>selected</#if>>申报成功</option>
                    <option value="2" <#if project.status==2>selected</#if>>申报驳回</option>
                    <option value="4" <#if project.status==4>selected</#if>>已删除</option>
                </select>
            </#if>
            <input class="form_submit" type="submit" value="搜索"/>
        </form>
    </div>
</div>