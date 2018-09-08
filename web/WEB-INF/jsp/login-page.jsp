<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 06.09.2018
  Time: 8:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Вход</title>
</head>
<body>
<p>Авторизируйтесь</p>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <input type="text" id="login_01" name="login" size="30" required>
        <label for="login_01"><span>*</span>Логин</label><br>
        <input type="password" id="pass_01" name="password" size="30" required>
        <label for="pass_01"><span>*</span>Пароль</label><br>
        <input type="submit" value="Войти">
    </form>
<p><a href="${pageContext.request.contextPath}/index.jsp">Назад</a>
</body>
</html>
