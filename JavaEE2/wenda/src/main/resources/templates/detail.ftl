<#include "header.ftl" parse=true>
<link rel="stylesheet" href="../styles/detail.css">
<div class="zg-wrap zu-main clearfix with-indention-votebar" itemscope="" itemtype="http://schema.org/Question"
     id="zh-single-question-page" data-urltoken="36301524" role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <meta itemprop="isTopQuestion" content="false">
            <meta itemprop="visitsCount" content="402">
            <div id="zh-question-title" data-editable="true" class="zm-editable-status-normal">
                <h2 class="zm-item-title">

                    <span class="zm-editable-content">${question.title?html}</span>

                </h2>
            </div>
            <div id="zh-question-detail" class="zm-item-rich-text zm-editable-status-normal">
                <div class="zm-editable-content">${question.content?html}</div>
            </div>
            <div class="zm-side-section">
                <div class="zm-side-section-inner" id="zh-question-side-header-wrap">
                <#if followed>
                    <button class="follow-button zg-follow zg-btn-white js-follow-question" data-id="${question.id}"
                            data-status="1">
                        取消关注
                    </button>
                <#else>
                    <button class="follow-button zg-follow zg-btn-green js-follow-question" data-id="${question.id}">
                        关注问题
                    </button>
                </#if>
                    <div class="zh-question-followers-sidebar">
                        <div class="zg-gray-normal">
                            <a href="javascript:void(0);"><strong class="js-user-count">${followUsers?size}</strong></a>人关注该问题
                        </div>
                        <div class="list zu-small-avatar-list zg-clear js-user-list">
                        <#list followUsers as vo>
                            <a class="zm-item-link-avatar js-user-${vo.id}" href="/user/${vo.id}"
                               data-original_title="${vo.name}">
                                <img src="${vo.headUrl}"
                                     class="zm-item-img-avatar"></a>
                        </#list>
                        </div>
                    </div>
                </div>
            </div>
            <div id="zh-question-answer-wrap" data-pagesize="10" class="zh-question-answer-wrapper navigable"
                 data-widget="navigable" data-navigable-options="{&quot;items&quot;: &quot;&gt;.zm-item-answer&quot;}"
                 data-init="{&quot;params&quot;: {&quot;url_token&quot;: 36301524, &quot;pagesize&quot;: 10, &quot;offset&quot;: 0}, &quot;nodename&quot;: &quot;QuestionAnswerListV2&quot;}">

            <#list comments!! as comment>
                <div class="zm-item-answer  zm-item-expanded js-comment">
                    <link itemprop="url" href="">
                    <meta itemprop="answer-id" content="22162611">
                    <meta itemprop="answer-url-token" content="66862039">
                    <a class="zg-anchor-hidden" name="answer-22162611"></a>

                    <div class="zm-votebar goog-scrollfloater js-vote" data-id="${comment['comment'].id}">
                        <#if comment['liked'] gt 0>
                        <button class="up js-like pressed" title="赞同">
                        <#else>
                        <button class="up js-like" title="赞同">
                        </#if>
                        <i class="icon vote-arrow"></i>
                        <span class="count js-voteCount">${comment['likeCount']}</span>
                        <span class="label sr-only">赞同</span>
                    </button>

                    </div>
                    <div class="answer-head">
                        <div class="zm-item-answer-author-info">
                            <a class="zm-item-link-avatar avatar-link" href="/user/${comment['user'].id!}"
                               target="_blank"
                               data-tip="p$t$yingxiaodao">
                                <img src="${comment['user'].headUrl!}"
                                     class="zm-list-avatar avatar"></a>
                            <a class="author-link" target="_blank"
                               href="/user/${comment['user'].id!}">${comment['user'].name!}</a>
                        </div>
                        <div class="zm-item-vote-info" data-votecount="28" data-za-module="VoteInfo">
                                <span class="voters text">
                                        <span class="js-voteCount">${comment['likeCount']!}</span>&nbsp;人赞同</span>
                        </div>
                    </div>
                    <div class="zm-item-rich-text expandable js-collapse-body" data-resourceid="6727688"
                         data-action="/answer/content" data-author-name="营销岛"
                         data-entry-url="/question/36301524/answer/66862039">
                        <div class="zm-editable-content clearfix">
                        ${comment['comment'].content?html!'评论不见啦'}
                        </div>
                    </div>
                    <a class="zg-anchor-hidden ac" name="22162611-comment"></a>
                    <div class="zm-item-meta answer-actions clearfix js-contentActions">
                        <div class="zm-meta-panel">
                            发布于
                        ${comment['comment'].createdDate?string("yyyy年MM月dd日 HH:mm:SS")}

                        </div>
                    </div>
                </div>
            </#list>
            </div>
            <a name="draft"></a>
            <form action="/addComment" method="post" id="commentform">
                <input type="hidden" name="questionId" value="$question.id"/>
                <div id="zh-question-answer-form-wrap" class="zh-question-answer-form-wrap">
                    <div class="zm-editable-editor-wrap" style="">
                        <div class="zm-editable-editor-outer">
                            <div class="zm-editable-editor-field-wrap">
                                <input type="hidden" name="entityType" value="1">
                                <input type="hidden" name="entityId" value="${question.id!0}">
                                <input type="hidden" name="next" value="/question/${question.id!}">
                                <textarea name="content" id="content" class="zm-editable-editor-field-element editable"
                                          style="font-style: italic;width:100%;"></textarea>
                            </div>
                        </div>

                        <div class="zm-command clearfix">
                            <span class=" zg-right">
                                <button type="submit" class="submit-button zg-btn-blue">发布回答</button></span>
                        </div>
                    </div>
                </div>
            </form>

        </div>
    </div>
</div>
<#include "js.ftl" parse=true>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl" parse=true>