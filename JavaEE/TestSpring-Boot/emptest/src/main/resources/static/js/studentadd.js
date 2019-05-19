/**
 * Created by 郭新晔 on 2018/6/21 0021.
 */
$(function(){
    /*$("#update_Btn").on('click',function(){
        $.messager.progress();	// 显示进度条
        $('#add_stu').form('submit', {
            url: 'save_Student.action',
            /!*onSubmit: function(){
                var isValid = $(this).form('validate');
                if (!isValid){
                    $.messager.progress('close');	// 如果表单是无效的则隐藏进度条
                }
                return isValid;	// 返回false终止表单提交
            },*!/

            success: function(code){
                console.log("进入success回调方法");
                if(code=='1'){
                    console.log("进入成功");
                    $.messager.alert('提示','保存成功！');
                }else{
                    console.log("进入失败");
                    $.messager.alert('提示','保存失败！');
                }
                $.messager.progress('close');	// 如果提交成功则隐藏进度条
            }
        });
    });*/

    $("#update_Btn").on('click',function(){
        $.messager.show({
            title:'提示',
            msg:'信息保存完成!',
            timeout:2000,
            showType:'slide'
        });
    });
});