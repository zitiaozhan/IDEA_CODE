<#include "header.ftl" parse=true>
<link rel="stylesheet" href="../styles/letter.css">
    <div id="main">
        <div class="container">
            <ul class="letter-list">
            <#list conversations as conversation>
                <li id="conversation-item-10005_622873">
                    <a class="letter-link" href="/msg/detail?conversationId=${conversation.conversation.conversationId}"></a>
                    <div class="letter-info">
                        <span class="l-time">${conversation.conversation.createdDate?string('yyyy年MM月dd日 HH:mm:SS')!}</span>
                        <div class="l-operate-bar">
                            <!--
                            <a href="javascript:void(0);" class="sns-action-del" data-id="">
                            删除
                            </a>
                            -->
                            <a href="/msg/detail?conversationId=${conversation.conversation.conversationId}">
                                共${conversation.conversation.id}条会话
                            </a>
                        </div>
                    </div>
                    <div class="chat-headbox">
                        <#if conversation.unreadCount gt 0&&conversation.unreadCount lt 99>
                            <span class="msg-num">
                            ${conversation.unreadCount}
                        </span>
                        <#elseif conversation.unreadCount gt 98>
                            <span class="msg-num">
                            99+
                        </span>
                        </#if>
                        <a class="list-head" href="/user/${conversation.userId}">
                            <img alt="头像" src="${conversation.headUrl}">
                        </a>
                    </div>
                    <div class="letter-detail">
                        <a title="${conversation.userName}" class="letter-name level-color-1">
                            <p style="color: black">${conversation.userName}</p>
                        </a>
                        <p class="letter-brief">
                            <a href="/msg/detail?conversationId=${conversation.conversation.conversationId}">
                            ${conversation.conversation.content?html!"找不到啦"}
                            </a>
                        </p>
                    </div>
                </li>
            </#list>
            </ul>

        </div>
        <script type="text/javascript">
            $(function(){

                // If really is weixin
                $(document).on('WeixinJSBridgeReady', function() {

                    $('.weixin-qrcode-dropdown').show();

                    var options = {
                        "img_url": "",
                        "link": "http://nowcoder.com/j/wt2rwy",
                        "desc": "",
                        "title": "读《Web 全栈工程师的自我修养》"
                    };

                    WeixinJSBridge.on('menu:share:appmessage', function (argv){
                        WeixinJSBridge.invoke('sendAppMessage', options, function (res) {
                            // _report('send_msg', res.err_msg)
                        });
                    });

                    WeixinJSBridge.on('menu:share:timeline', function (argv) {
                        WeixinJSBridge.invoke('shareTimeline', options, function (res) {
                            // _report('send_msg', res.err_msg)
                        });
                    });

                });

            })
        </script>
    </div>
<#include "js.ftl" parse=true>
<script type="text/javascript" src="/scripts/main/site/detail.js"></script>
<#include "footer.ftl" parse=true>