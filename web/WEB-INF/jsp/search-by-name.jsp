<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 06.09.2018
  Time: 20:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск по названию</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px">Название: </p>
<form method="post" action="${pageContext.request.contextPath}/search-result?url=/by-name-search">
    <label>
        <input type="text" name="values" maxlength="60" required autofocus>
    </label><span>*</span>
    <input type="submit" value="Искать"><br>
</form>
</body>
</html>
