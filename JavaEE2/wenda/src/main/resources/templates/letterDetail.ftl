<#include "header.ftl" parse=true>
<link rel="stylesheet" href="../styles/letter.css">
    <div id="main">
        <div class="container">
            <ul class="letter-chatlist">
                <#list msg as messageInfo>
                <li id="msg-item-4009580">
                    <a class="list-head" href="/user/${messageInfo.user.id}">
                        <img alt="头像" src="${messageInfo.user.headUrl}">
                    </a>
                    <div class="tooltip fade right in">
                        <div class="tooltip-arrow"></div>
                        <div class="tooltip-inner letter-chat clearfix" style="width: 720px">
                            <div class="letter-info">
                                <p class="letter-time">${messageInfo.message.createdDate?string('yyyy年MM月dd日 HH:mm:SS')}</p>
                                <a href="javascript:void(0);" id="del-link" name="4009580">删除</a>
                            </div>
                            <p class="chat-content">
                                ${messageInfo.message.content?html!}
                            </p>
                        </div>
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