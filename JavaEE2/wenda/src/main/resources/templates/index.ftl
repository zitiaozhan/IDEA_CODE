<#include "header.ftl" parse=true>
<link rel="stylesheet" href="../styles/index.css">
<link rel="stylesheet" href="../styles/detail.css">
<div class="zg-wrap zu-main clearfix " role="main">
    <div class="zu-main-content">
        <div class="zu-main-content-inner">
            <div class="zg-section" id="zh-home-list-title">
                <i class="zg-icon zg-icon-feedlist"></i>最新动态
                <input type="hidden" id="is-topstory">
                <span class="zg-right zm-noti-cleaner-setting" style="list-style:none">
                        <a href="https://nowcoder.com/settings/filter" class="zg-link-gray-normal">
                            <i class="zg-icon zg-icon-settings"></i>设置</a></span>
            </div>
            <div class="zu-main-feed-con navigable" data-feedtype="topstory" id="zh-question-list"
                 data-widget="navigable"
                 data-navigable-options="{&quot;items&quot;:&quot;&gt; .zh-general-list .feed-content&quot;,&quot;offsetTop&quot;:-82}">
                <a href="javascript:;" class="zu-main-feed-fresh-button" id="zh-main-feed-fresh-button"
                   style="display:none"></a>
                <div id="js-home-feed-list" class="zh-general-list topstory clearfix"
                     data-init="{&quot;params&quot;: {}, &quot;nodename&quot;: &quot;TopStory2FeedList&quot;}"
                     data-delayed="true" data-za-module="TopStoryFeedList">

                <#list vos!! as vo>
                    <div class="feed-item folding feed-item-hook feed-item-2
                        " feed-item-a="" data-type="a" id="feed-2" data-za-module="FeedItem" data-za-index="">
                        <meta itemprop="ZReactor" data-id="389034"
                              data-meta="{&quot;source_type&quot;: &quot;promotion_answer&quot;, &quot;voteups&quot;: 4168, &quot;comments&quot;: 69, &quot;source&quot;: []}">
                        <div class="feed-item-inner">
                            <div class="avatar">
                                <a title="${vo['user'].name?html!}" data-tip="p$t$amuro1230" class="zm-item-link-avatar"
                                   target="_blank" href="/user/${vo['user'].id!}">
                                    <img src="${vo['user'].headUrl?html!}" class="zm-item-img-avatar"></a>
                            </div>
                            <div class="feed-main">
                                <div class="feed-content" data-za-module="AnswerItem">
                                    <meta itemprop="answer-id" content="389034">
                                    <meta itemprop="answer-url-token" content="13174385">
                                    <h2 class="feed-title">
                                        <a class="question_link" target="_blank"
                                           href="/question/${vo['question'].id!}">${vo['question'].title?html!}</a></h2>
                                    <div class="feed-question-detail-item">
                                        <div class="question-description-plain zm-editable-content"></div>
                                    </div>
                                    <div class="expandable entry-body">
                                        <div class="zm-item-vote">
                                            <a class="zm-item-vote-count js-expand js-vote-count" href="javascript:;"
                                               data-bind-votecount="">4168</a></div>
                                        <div class="zm-item-answer-author-info">
                                            <a class="author-link" data-tip="p$b$amuro1230" target="_blank"
                                               href="/user/${vo['user'].id!}">${vo['user'].name?html!}</a>
                                            ，${vo['question'].createdDate?string('yyyy年MM月dd日 hh:mm:ss')}</div>
                                        <div class="zm-item-vote-info" data-votecount="4168" data-za-module="VoteInfo">
                                                <span class="voters text">
                                                    <a href="#" class="more text">
                                                        <span class="js-voteCount">4168</span>&nbsp;人赞同</a></span>
                                        </div>
                                        <div class="zm-item-rich-text expandable js-collapse-body"
                                             data-resourceid="123114" data-action="/answer/content"
                                             data-author-name="${vo['user'].name!}"
                                             data-entry-url="/question/19857995/answer/13174385">
                                            <div class="zm-editable-content">${vo['question'].content?html!'内容不见啦！'}</div>
                                        </div>
                                    </div>
                                    <div class="feed-meta">
                                        <div class="zm-item-meta answer-actions clearfix js-contentActions">
                                            <div class="zm-meta-panel">
                                                <#if vo['isOwner']==false>
                                                    <#if vo['followed']>
                                                        <button class="follow-button zg-follow zg-btn-white js-follow-question"
                                                                data-id="${vo['question'].id}"
                                                                data-status="1">
                                                            取消关注
                                                        </button>
                                                    <#else>
                                                        <button class="follow-button zg-follow zg-btn-green js-follow-question"
                                                                data-id="${vo['question'].id}">
                                                            关注问题
                                                        </button>
                                                    </#if>
                                                </#if>
                                                <a href="/question/${vo['question'].id!}" name="addcomment"
                                                   class="meta-item toggle-comment js-toggleCommentBox">
                                                    <i class="z-icon-comment"></i>${vo['question'].commentCount!}
                                                    条评论</a>


                                                <button class="meta-item item-collapse js-collapse">
                                                    <i class="z-icon-fold"></i>收起
                                                </button>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
                </div>
                <a href="javascript:;" id="zh-load-more" data-method="next" class="zg-btn-white zg-r3px zu-button-more"
                   style="">更多</a></div>
        </div>
    </div>
</div>
<#include "js.ftl" parse=true>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl" parse=true>