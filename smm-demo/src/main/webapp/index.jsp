<%--
  Created by IntelliJ IDEA.
  User: 11690
  Date: 2022/6/21
  Time: 20:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    application.setAttribute("ctp", request.getContextPath());
%>
<html>
<head>
    <title>index</title>
</head>
<body>
<h1>hello</h1>
<%--在使用包含文件上传控件的表单时，必须使用该值。--%>
<form action="${ctp}/upload" enctype="multipart/form-data" method="post">
    username: <input type="text" name="username"/>
    file: <input type="file" name="file"/>
    <input type="submit" value="提交">
</form>
<a href="${ctp}/download?file=pdf">下载</a>

<a href="${ctp}/cookie">添加cookie</a>
</body>
</html>
