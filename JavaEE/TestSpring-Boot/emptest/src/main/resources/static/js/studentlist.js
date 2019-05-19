/**
 * Created by 郭新晔 on 2018/7/2 0002.
 */
$(function(){
    $("#list_tab").datagrid({
        url:'/findByPage_Student.action',
        width : 'fit',
        striped : true,
        singleSelect : true,
        loadMsg : '正在加载数据，请稍后......',
        pagination : true,
        pageSize : 5,
        pageList : [ 5, 10, 15, 20 ],
        columns:[[
            {field:'sid',title:'编号',width:100,align:'center'},
            {field:'sname',title:'姓名',width:100,align:'center'},
            {field:'sex',title:'性别',width:100,align:'center'},
            {field:'address',title:'地址',width:100,align:'center'},
            {field:'edu',title:'教育水平',width:100,align:'center'},
            {field:'birthdayStr',title:'生日',width:100,align:'center'},
            {field:'clazz',title:'班级',width:100,align:'center'},
            {field:'opera',title:'操作',width:100,align:'center'}
        ]]
    });
});