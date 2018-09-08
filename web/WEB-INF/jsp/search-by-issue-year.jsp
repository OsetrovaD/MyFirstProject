<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 06.09.2018
  Time: 20:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск по году выпуска</title>
</head>
<body>
<p>Год выпуска: </p>
<form method="post" action="${pageContext.request.contextPath}/search-result?url=/by-issue-year-search">
    <label>
        <input type="text" name="values" pattern="[0-9]{4}" maxlength="4" required autofocus>
    </label><span>*</span>
    <input type="submit" value="Искать"><br>
</form>
<p><a href="${pageContext.request.contextPath}/game-search">Назад</a> | <a href="${pageContext.request.contextPath}/user">Домой</a></p>
</body>
</html>
