/**
 * Created by 郭新晔 on 2019/2/13 0013.
 */
function del_site(target) {
    if (confirm('您是否要[删除]，删除可能导致部分功能无法使用？')) {
        location.href = target;
    }
}
function modify_site(target) {
    location.href = target;
}
function modify_site_tip(msg, target) {
    if (confirm(msg)) {
        location.href = target;
    }
}
function confuse_option(target) {
    if (confirm('您确定要进行[驳回]操作吗？')) {
        var option = prompt("请输入审批意见？", "数据错漏");
        location.href = target + "&option=" + option;
    }
}
function confirm_redirect(msg, target) {
    if (confirm('您确定要进行[' + msg + ']操作吗，这可能导致一定的问题？')) {
        location.href = target;
    }
}

/**
 * 加载柱状图
 * @param jsonUrl   数据链接
 * @param barId    节点id
 */
function loadBarChart(jsonUrl, barId) {
    var echartsBar = echarts.init(document.getElementById(barId));
    //柱状图
    echartsBar.clear();
    echartsBar.showLoading({text: '正在努力的读取数据中...'});
    $.getJSON(jsonUrl, function (data) {
        if (data.success) {
            var xAxis = data.data.xaxis;
            delete(data.data.xaxis);
            data.data.xAxis = xAxis;
            var yAxis = data.data.yaxis;
            delete(data.data.yaxis);
            data.data.yAxis = yAxis;
            echartsBar.setOption(data.data, true);
            echartsBar.hideLoading();
        } else {
            alert(data.msg);
        }
    });
}

/**
 * 加载饼状图
 * @param jsonUrl   数据链接
 * @param pieId    节点id
 */
function loadPieChart(jsonUrl, pieId) {
    var echartsPie = echarts.init(document.getElementById(pieId));
    //饼状图
    echartsPie.clear();
    echartsPie.showLoading({text: '正在努力的读取数据中...'});
    $.getJSON(jsonUrl, function (data) {
        if (data.success) {
            echartsPie.setOption(data.data, true);
            echartsPie.hideLoading();
        } else {
            alert(data.msg);
        }
    });
}

function validate() {
    var pwd = $("#pwd").val();
    var repwd = $("#repwd").val();
    if (pwd == repwd) {
        $(".warn_div").html(" ");
        $(".register-submit").attr('disabled', false);
    } else {
        $(".warn_div").html("密码不一致");
        $(".register-submit").attr('disabled', true);
    }
}
function validatePhone() {
    var phone = $("#phone").val();
    if (!(/^1[34578]\d{9}$/.test(phone))) {
        $(".warn_div").html("手机号码有误，请重填");
        $(".content-submit").attr('disabled', true);
    } else {
        $(".warn_div").html(" ");
        $(".content-submit").attr('disabled', false);
    }
}
/**
 * 验证更多作者的内容
 */
function validateMoreAuthor(name) {
    var moreAuthor = $(".more-author").val();
    if (moreAuthor===''||(/^((\d)+,)+\d+$/.test(moreAuthor))||(/^\d+$/.test(moreAuthor))) {
        $(".warn_div").html(" ");
        $(".content-submit").attr('disabled', false);
    } else {
        $(".warn_div").html(name + "填写有误，请重填");
        $(".content-submit").attr('disabled', true);
    }
}

$(document).ready(function () {
    $("#edit_btn").click(function () {
        $(".form-control").attr('disabled', false);
        $(".form-control_long").attr('disabled', false);
        $(".form-textarea").attr('disabled', false);

        $(".redisabled").attr('disabled', true);

        $(".content-form-submit").html('<input class="content-submit" type="submit" value="提交">');
    });

});