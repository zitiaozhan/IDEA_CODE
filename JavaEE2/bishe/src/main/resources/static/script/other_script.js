/**
 * Created by 郭新晔 on 2019/2/13 0013.
 */
function del_site(target) {
    if (confirm('您是否要删除，删除可能导致部分功能无法使用？')) {
        location.href = target;
    }
}
function modify_site(target) {
    location.href = target;
}
function confirm_redirect(msg,target) {
    if (confirm(msg)) {
        location.href = target;
    }
}

function validate(){
    var pwd=$("#pwd").val();
    var repwd=$("#repwd").val();
    if(pwd==repwd){
        $(".warn_div").html(" ");
        $("#register-submit").attr('disabled', false);
    }else{
        $(".warn_div").html("密码不一致");
        $("#register-submit").attr('disabled', true);
    }
}
function validatePhone(){
    var phone=$("#phone").val();
    if(!(/^1[34578]\d{9}$/.test(phone))){
        $(".warn_div").html("手机号码有误，请重填");
        $(".content-submit").attr('disabled', true);
    } else{
        $(".warn_div").html(" ");
        $(".content-submit").attr('disabled', false);
    }
}

$(document).ready(function(){
    $("#edit_btn").click(function(){
        $(".form-control").attr('disabled', false);
        $(".form-control_long").attr('disabled', false);
        $(".form-textarea").attr('disabled', false);

        $(".redisabled").attr('disabled', true);

        $(".content-form-submit").html('<input class="content-submit" type="submit" value="提交">');
    });

});