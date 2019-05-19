/**
 * Created by 郭新晔 on 2018/6/20 0020.
 */
$(function(){
    $("#mylayout").layout('collapse','east');

    $('#tt').tree(
        {
            data:
                [
                    {
                        text: '部门管理',
                        state: 'closed',
                        children: [
                            {
                                text: '部门新增',
                                attributes:{
                                    url:"depadd.html",
                                }
                            },
                            {
                                text: '部门列表',
                                attributes:{
                                    url:"deplist.html",
                                }
                            },
                            {
                                text: '部门更新',
                                attributes:{
                                    url:"depupdate.html",
                                }
                            }
                        ]
                    },
                    {
                        text: '职员管理',
                        state: 'closed',
                        children: [
                            {
                                text: '职员新增',
                                attributes:{
                                    url:"empadd.html",
                                }
                            },
                            {
                                text: '职员列表',
                                attributes:{
                                    url:"emplist.html",
                                }
                            },
                            {
                                text: '职员更新',
                                attributes:{
                                    url:"empupdate.html",
                                }
                            }
                        ]
                    },
                    {
                        text: '学生管理',
                        state: 'opened',
                        children: [
                            {
                                text: '学生新增',
                                attributes:{
                                    url:"studentadd.html",
                                }
                            },
                            {
                                text: '学生列表',
                                attributes:{
                                    url:"studentlist.html",
                                }
                            },
                            {
                                text: '学生更新',
                                attributes:{
                                    url:"studentupdate.html",
                                }
                            }
                        ]
                    },
                    {
                        text: '福利管理',
                        state: 'closed',
                        children: [
                            {
                                text: '福利新增',
                                attributes:{
                                    url:"wfadd.html",
                                }
                            },
                            {
                                text: '福利列表',
                                attributes:{
                                    url:"wflist.html",
                                }
                            },
                            {
                                text: '福利更新',
                                attributes:{
                                    url:"wfupdate.html",
                                }
                            }
                        ]
                    },
                    {
                        text: '测试功能',
                        state: 'closed',
                        children: [
                            {
                                text: '测试页面1',
                                attributes:{
                                    url:"test1.html",
                                }
                            },
                            {
                                text: '测试页面2',
                                attributes:{
                                    url:"test2.html",
                                }
                            },
                            {
                                text: '测试页面3',
                                attributes:{
                                    url:"test3.html",
                                }
                            }
                        ]
                    },
                ]
        }
    );


    $('#tt').tree({
        onClick: function(node){
            addTab(node.text,node.attributes.url);
        }
    });
});

function addTab(title,content){
    // add a new tab panel
    if($("#tabs").tabs("exists",title)){
        $("#tabs").tabs("select",title);
    }else{
        $('#tabs').tabs('add',{
            title:title,
            content:"<iframe src='"+content+"' frameborder='0' width='100%' height='100%' marginwidth='0' marginheight='0'></iframe",
            //href:content,
            closable:true,
            tools:[{
                iconCls:'icon-mini-refresh',
                //点击选项卡刷新时执行函数
                handler:function(){
                    console.log("刷新选项卡["+title+"]");
                }
            }]
        });
    }
}