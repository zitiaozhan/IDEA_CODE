//声明全局变量
var page=1;
var rows=5;
var pageNum=1;
var rand=0;

$(function(){
	$("#show_message").hide();
	showData();
    initData();

    $("#update_Btn").on('click',saveData);
    $("#update_Btn").text('新增数据');
});

//用来显示数据的函数
function showData(){
	$.getJSON('findByPage_Student.action?nowPage='+page+'&pageSize='+rows+"&num="+Math.random(),function(pageBean){
		page=pageBean.nowPage;
		rows=pageBean.pageSize;
		pageNum=pageBean.pageNum;
		var pageData=pageBean.pageData;

		var headHtml=
		"<table><tr>"
		+"<td>编号</td>"
		+"<td>姓名</td>"
		+"<td>性别</td>"
		+"<td>地址</td>"
		+"<td>学历</td>"
        +"<td>生日</td>"
        +"<td>班级</td>"
        +"<td>操作</td>"
        +"</tr>";
        var itemHtml="";
		for(var i=0;i<pageData.length;i++){
			var it=pageData[i];
			itemHtml+=
			"<tr>"
			+"<td>"+it.sid+"</td>"
			+"<td>"+it.sname+"</td>"
			+"<td>"+it.sex+"</td>"
			+"<td>"+it.address+"</td>"
			+"<td>"+it.edu+"</td>"
	        +"<td>"+it.birthdayStr+"</td>"
	        +"<td>"+it.clazz.cname+"</td>"
	        +"<td>"
            +"<button class='button tiny' onclick=\"findData("+it.sid+")\">更新</button>&nbsp;&nbsp;"
            +"<button class='button tiny' onclick=\"removeData("+it.sid+",'"+it.sname+"')\">删除</button>"
            +"</td>"
	        +"</tr>";
		}
		var footHeadHtml="<tr>"
            +"<td colspan='8'>"
               +"<div class='pagination-centered' id='page_Div'>"
                   +"<ul class='pagination' id='page_Ul'>";
        var footFootHtml="</ul>"
               +"</div>"
            +"</td>"
	        +"</tr>"
	    	+"</table>";
	    var divHtml=headHtml+itemHtml+footHeadHtml+initPage()+footFootHtml;
	    $("#show_Div").html(divHtml);
	});
}
//初始化更新数据
function initData(){
	var eduArr=new Array(
		'小学',
        '初中',
        '高中',
        '大专',
        '本科',
        '硕士',
        '博士'
	);
	for(j in eduArr){
        $("#edu").append("<option value="+eduArr[j]+">"+eduArr[j]+"</option>");
	}

	$.getJSON('doInit_Student.action',function(clazzs){
		for(j in clazzs){
			var it=clazzs[j];
			$("#cid").append('<option value='+it.cid+'>'+it.cname+'</option>');
		}
	});

}
//保存数据
function saveData(){
	$("#show_message").hide();
	var sname=$("#sname").val();
	var sex=$("#sex").val();
	var address=$("#address").val();
	var edu=$("#edu").val();
	var birthdayStr=$("#birthdayStr").val();
	var cid=$("#cid").val();

	var stu={
		'sname':sname,
        'sex':sex,
        'address':address,
        'edu':edu,
        'birthdayStr':birthdayStr,
        'cid':cid
	};
	$.get('save_Student.action',stu,function(code){
		showMessage(code,"信息保存");
	});
    showData();
}
//显示最终结果消息
function showMessage(code,message){
    $("#show_message").empty();
	if(code==1){
		$("#show_message").text(message+"成功");
	}else if(code==0){
        $("#show_message").text(message+"失败");
	}
	$("#show_message").toggle(500);
}
//删除操作
function removeData(sid,sname){
	$("#show_message").hide();
	if(window.confirm("确定要删除["+sname+"]么?")){
		$.get('remove_Student.action?sid='+sid,function(code){
			showMessage(code,"信息删除");
		});
	}
	showData();
}
//准备更新数据
function findData(sid){
	$("#show_message").hide();
	$.getJSON('findById_Student.action?sid='+sid,function(oldStu){
		$("#sid").val(oldStu.sid);
		$("#sname").val(oldStu.sname);
		$("#sex").val(oldStu.sex);
		$("#address").val(oldStu.address);
		$("#edu").val(oldStu.edu);
		$("#birthdayStr").val(oldStu.birthdayStr);
		$("#cid").val(oldStu.cid);
	});
	$("#update_Btn").text('更新列表');
	$("#update_Btn").off('click',saveData);
	$("#update_Btn").on('click',updateData);
}
//正式开始更新数据
function updateData(){
	var sid=$("#sid").val();
	var sname=$("#sname").val();
	var sex=$("#sex").val();
	var address=$("#address").val();
	var edu=$("#edu").val();
	var birthdayStr=$("#birthdayStr").val();
	var cid=$("#cid").val();

	var stu={
		'sid':sid,
		'sname':sname,
        'sex':sex,
        'address':address,
        'edu':edu,
        'birthdayStr':birthdayStr,
        'cid':cid
	};
	$.get('update_Student.action',stu,function(code){
		showMessage(code,"信息更新");
	});
    showData();

	$("#update_Btn").text('新增数据');
    $("#update_Btn").off('click',updateData);
	$("#update_Btn").on('click',saveData);
}

//分页的功能
function initPage(){
	rand=Math.random();
	var pageHtml="";
    if(page>0&&page<=5){
    	pageHtml+="<li class='unavailable' id='last_group'><a>&laquo;</a></li>";
    }else{
    	pageHtml+="<li class='arrow' id='last_group'><a onclick='changePage("+(parseInt((page-5)/5)*5+1)+")'>&laquo;</a></li>";
    }
    for(var i=parseInt(page/6)*5+1;i<=(parseInt(page/6)+1)*5&&i<=pageNum;i++){
    	if(i==page){
    		pageHtml+="<li class='current'><a onclick='changePage("+i+")'>"+i+"</a></li>";
    	}else{
    		pageHtml+="<li><a onclick='changePage("+i+")'>"+i+"</a></li>";
    	}
    }
    if(page+5>=pageNum){
    	pageHtml+="<li class='unavailable' id='next_group'><a>&raquo;</a></li>";
    }else{
    	pageHtml+="<li class='arrow' id='next_group'><a onclick='changePage("+(parseInt((page+5)/5)*5+1)+")'>&raquo;</a></li>";
    }
    console.log(pageHtml);
    return pageHtml;
}
function changePage(nowPage){
	page=nowPage;
	showData();
}
