<%--
  Created by IntelliJ IDEA.
  User: 郭新晔
  Date: 2018/5/8 0008
  Time: 19:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>更新商品</title>
    <style>
      input{
        width: 200px;
        height:30px;
        text-align: center;
        font-size: 25px;
      }
    </style>
  </head>
  <body>
    <h2 style="margin: 0px auto;">更新商品</h2>
    <form action="update_Goods" method="post">
      <input type="hidden" name="goods.id" value="${sessionScope.goods.id}"/>
      <table>
        <tr>
          <td>商品名称</td>
          <td><input type="text" name="goods.goodName" value="${sessionScope.goods.goodName}" placeholder="请输入商品名称"/></td>
        </tr>
        <tr>
          <td>商品价格</td>
          <td><input type="text" name="goods.goodPrice" value="${sessionScope.goods.goodPrice}" placeholder="请输入商品价格"/></td>
        </tr>
        <tr>
          <td>商品数量</td>
          <td><input type="number" name="goods.goodCount" value="${sessionScope.goods.goodCount}" placeholder="请输入商品数量"/></td>
        </tr>
        <tr>
          <td colspan="2"><input type="submit" value="更新"/></td>
        </tr>
      </table>
    </form>
  <a href="list.jsp" style="text-decoration: none;color: lawngreen;margin: 10px auto;font-size: 30px;">查看列表</a>
  </body>
</html>
