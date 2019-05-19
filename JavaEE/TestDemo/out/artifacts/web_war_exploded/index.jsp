<%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
  <head>
    <title></title>
  </head>
  <body>
    <form action="SimpleServlet" method="post">
      <input type="text" name="textpost" placeholder="请输入文本"/>
      <input type="submit" value="post提交"/>
    </form>
    <form action="SimpleServlet" method="get">
      <input type="text" name="textget" placeholder="请输入文本"/>
      <input type="submit" value="post提交"/>
    </form>

    <div style="width: 100%;height: 100px;border: 2px solid lightgray;box-shadow: 0px 0px 5px gray;text-align: center">
      <p style="background-color: snow;width: 15%;box-shadow: 2px 2px 3px gray;font-size: 30px;margin: 0px auto;">${requestScope.method}: ${sessionScope.text}</p>
    </div>
  </body>
</html>