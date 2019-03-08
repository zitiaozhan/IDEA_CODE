/**
 * Created by 郭新晔 on 2018/12/21 0021.
 */
$(function () {
    $(".right_function").click(function () {
        $(".add_summary").slideToggle(500);
    });
});
function list_delete(id,mode,nowPage) {
    if (confirm("确定要删除吗？")) {
        window.location.href = "/delete/" + id+"?mode="+mode+"&nowPage="+nowPage;
    }
}
function last_page_search(page, keyword) {
    window.location.href = "/search/" + page + "?keyword=" + keyword;
}
function last_page(page) {
    window.location.href = "/page/" + page;
}
function next_page_search(page, keyword) {
    window.location.href = "/search/" + page + "?keyword=" + keyword;
}
function next_page(page) {
    window.location.href = "/page/" + page;
}