<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<% pageContext.setAttribute("context", request.getContextPath()); %>
<body>
<h1><%= "Hello World!" %>
</h1>
<br/>
<%--<a href="hello-servlet">Hello Servlet</a>--%>
<form action="${context}/test1" method="post">
    <input name="username" value="tomcat">
    <input name="pwd" value="12345">
    <input type="submit">
</form>
</body>
</html>