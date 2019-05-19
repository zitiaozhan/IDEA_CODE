<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>学生管理</title>
    <script src="js/jquery-2.1.1.min.js"></script>
    <script src="js/student.js"></script>
    <link rel="stylesheet" href="css/foundation.min.css" type="text/css">
    <link rel="stylesheet" href="css/student.css" type="text/css">
</head>
<body>
<h2>这里是个标题</h2>
<div id="show_message">

</div>
<div id="show_Div">
    
</div>
<hr class="page_hr"/>
<div id="update_Div">
    <table>
        <tr><td colspan="2">
        <h3>学生信息</h3>
        </td></tr>
        <tr>
            <td>学生姓名</td>
            <td>
                <input type="hidden" id="sid" name="sid">
                <input type="text" id="sname" name="sname" maxlength="22" placeholder="请输入姓名" required>
            </td>
        </tr>
        <tr>
            <td>学生性别</td>
            <td>
                <select id="sex" name="sex" required>
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>学生地址</td>
            <td>
                <input type="text" id="address" name="address" maxlength="150" placeholder="请输入地址" required>
            </td>
        </tr>
        <tr>
            <td>学生学历</td>
            <td>
                <select id="edu" name="edu">
                </select>
            </td>
        </tr>
        <tr>
            <td>学生生日</td>
            <td>
                <input type="date" id="birthdayStr" name="birthdayStr" placeholder="请输入生日" value="1995-05-02">
            </td>
        </tr>
        <tr>
            <td>学生班级</td>
            <td>
                <select id="cid" name="cid" required>
                </select>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button id="update_Btn" class="button expand">更新列表</button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>