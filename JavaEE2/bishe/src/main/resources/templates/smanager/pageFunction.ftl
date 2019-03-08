<div class="show-page padding-big-right">
    <div class="page">
        <div class="page">
            <ul class="offcial-page margin-top margin-big-right">
                <li>共<em class="margin-small-left margin-small-right">${pageInfo.total}</em>条数据</li>
                <li>每页显示<em class="margin-small-left margin-small-right">${pageInfo.pageSize}</em>条</li>
            <#if pageInfo.hasPreviousPage>
                <li><a class="next"
                       href="/${type}?page=${pageInfo.prePage}">上一页</a></li>
            <#else>
                <li><a class="next disable">上一页</a></li>
            </#if>

                <li></li>

            <#if pageInfo.hasNextPage>
                <li><a class="next"
                       href="/${type}?page=${pageInfo.nextPage}">下一页</a></li>
            <#else>
                <li><a class="next disable">下一页</a></li>
            </#if>
                <li>第<em class="margin-small-left margin-small-right">${pageInfo.pageNum}</em>页</li>
                <li><span class="fl">共<em class="margin-small-left margin-small-right">${pageInfo.pages}</em>页</span></li>
            </ul>
        </div>
    </div>
</div>