/**
 * Created by 郭新晔 on 2018/6/20 0020.
 */
$(function(){
    $("#btn1").on('click',function(){
        $.messager.alert('警告','警告消息');
    });
    $("#btn6").on('click',function(){
        $.messager.show({
            title:'艾玛,真香',
            msg:'我孙悟空打死也不会戴紧箍咒!',
            timeout:2000,
            showType:'slide'
        });
    });
    $("#btn7").on('click',function(){
        $.messager.prompt('请发言', '扁桃体还有30秒到达战场', function(message){
            if (message){
                alert('扁桃体的发言是：' + message);
            }
        });
    });
    $("#btn8").on('click',function(){
        $.messager.progress();
    });
    $("#btn2").on('click',function(){
        $.messager.confirm('确认','您确认想要删除记录吗？',function(r){
            if (r){
                alert('确认删除');
            }
        });
    });

    $("#btn3").on('click',function(){
        $('#dd').dialog({
            title: 'My Dialog',
            width: 400,
            height: 200,
            closed: false,
            cache: false,
            content:'对话框内容',
            href: '',
            modal: true
        });
    });

    $("#btn4").on('click',function(){
        $('#dd').window({
            title: '窗口',
            width:300,
            height:200,
            collapsible:false,
            minimizable:false,
            maximizable:false,
            content:'窗口内容',
            modal:false
        });
    });
    $("#btn5").on('click',function(){
        $('#dd').window({
            title: '窗口',
            width:300,
            height:200,
            collapsible:false,
            minimizable:false,
            maximizable:false,
            content:'窗口内容',
            modal:true
        });
    });
});