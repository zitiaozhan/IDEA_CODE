<#include "../header.ftl" parse=true>

<link rel="stylesheet" type="text/css" href="../../style/conversation.css">

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
            <#--聊天对话-->
            <div class="conversationDetail" align="left">
                <div class="chatTop">
                    <div class="back">
                        <a href="/message" class="back_a">
                            返回
                        </a>
                    </div>
                    <p class="chatTitle">
                        与<strong class="name W_fb">${anotherUser.name!" -- "}</strong>对话中
                    </p>
                </div>

                <div class="chatDetail">
                    <#if messageList?size gt 0>
                        <#list messageList as message>
                            <#if message.fromId==localUser.id>
                                <div class="msg_bubble_list bubble_r">
                                    <div class="bubble_mod bubble_r clearfix">
                                        <div class="bubble_user bubble_r">
                                            <p class="page">${localUser.name}</p>
                                        </div>
                                        <div class="bubble_box bubble_r">
                                            <div class="bubble_cont">
                                                <div class="bubble_arrow_right">
                                                    <div class="W_arrow_bor W_arrow_bor_r"><i></i><em></em></div>
                                                </div>
                                                <div class="bubble_main clearfix">
                                                    <div class="cont">
                                                        <p class="page">
                                                        ${message.content!"未知错误"}
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="main_bottom bubble_l">
                                            ${message.createdDate}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            <#else>
                                <div class="msg_bubble_list bubble_l">
                                    <div class="bubble_mod clearfix">
                                        <div class="bubble_user">
                                            <p class="page">${anotherUser.name}</p>
                                        </div>
                                        <div class="bubble_box">
                                            <div class="bubble_cont">
                                                <div class="bubble_arrow_left">
                                                    <div class="W_arrow_bor W_arrow_bor_l"><i></i><em></em></div>
                                                </div>
                                                <div class="bubble_main clearfix">
                                                    <div class="cont">
                                                        <p class="page">
                                                        ${message.content!"未知错误"}
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="main_bottom bubble_r">
                                            ${message.createdDate}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </#if>
                        </#list>
                    </#if>
                </div>

                <div class="chatBottom">
                    <div class="chatBottom_form_div">
                        <#if anotherUser??>
                            <form class="chatBottom_data_form" action="/message/show" method="post">
                                <input type="hidden" name="conversationId" value="${conversationId}">
                                <input type="hidden" name="toId" value="${anotherUser.id}">
                                <div class="chatBottom_col-sm-11">
                                <textarea class="chatBottom_form-textarea"
                                          name="content" cols="70" rows="3"
                                          placeholder="发言版" required></textarea>
                                </div>
                                <div class="chatBottom_content-form-submit" align="center">
                                    <input class="chatBottom_content-submit" type="submit" value="提交">
                                </div>
                            </form>
                        </#if>
                    </div>
                </div>
            </div>

        </div>
    </div>

</div>
</div>
</div>
<#include "../footer.ftl" parse=true>