<%--
  Created by IntelliJ IDEA.
  User: 郭新晔
  Date: 2018/5/8 0008
  Time: 20:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>商品列表</title>
    <style>
        tr{
            border: 1px solid gray;
        }
        td,th{
            border: 1px solid gray;
        }
    </style>
</head>
<body>
    <h2 style="margin: 0px auto">商品列表</h2>
    <table style="margin: 0px auto;">
        <caption>商品列表</caption>
        <tr>
            <th>序号</th>
            <th>商品名称</th>
            <th>商品价格</th>
            <th>商品数量</th>
            <th>商品小计</th>
            <th>商品操作</th>
        </tr>
        <c:forEach items="${sessionScope.goodsList}" var="goods" varStatus="vs">
            <tr>
                <td>${vs.index+1}</td>
                <td>[${goods.goodName}]</td>
                <td>${goods.goodPrice}元</td>
                <td>${goods.goodCount}件</td>
                <td>${goods.goodSum}元</td>
                <td>
                    <a href="findById_Goods?index=${vs.index+1}" style="text-decoration: none;color: lawngreen;">更新</a>
                    <a href="remove_Goods?index=${vs.index+1}" style="text-decoration: none;color: lawngreen;">删除</a>
                </td>
            </tr>
        </c:forEach>
    </table>
    <hr/>
    <a style="text-decoration: none;color: lawngreen;margin: 10px auto;font-size: 30px;" href="save.jsp">返回添加</a>
</body>
</html>
