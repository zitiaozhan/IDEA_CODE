<#assign bodyclass="page-search">
<#include "header.ftl" parse=true>
<link rel="stylesheet" href="../styles/result.css">
<link rel="stylesheet" href="../styles/detail.css">
<div class="zg-wrap zu-main clearfix" role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <ul class="list contents navigable">
                <#list vos!! as vo>
                <li class="item clearfix">
                    <div class="title">
                        <a target="_blank" href="/user/${vo.user.id}" class="js-title-link">${vo.question.title}</a>
                    </div>
                    <div class="content">

                        <ul class="answers">
                            <li class="answer-item clearfix">
                                <div class="entry answer">
                                    <div class="entry-left hidden-phone">
                                        <a class="zm-item-vote-count hidden-expanded js-expand js-vote-count" data-bind-votecount="">${vo.followCount}</a>
                                    </div>
                                    <div class="entry-body">
                                        <div class="entry-meta">
                                            <strong class="author-line"><a class="author" href="/user/${vo.user.id}">${vo.user.name}</a>，${vo.question.createdDate?string('yyyy-MM-dd HH:mm:ss')}</strong>
                                        </div>
                                        <div class="entry-content js-collapse-body">
                                            <div class="summary hidden-expanded" style="">
                                            ${vo.question.content}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                </li>
                </#list>
            </ul>
        </div>
    </div>
</div>
<#include "js.ftl" parse=true>
<#include "footer.ftl" parse=true>