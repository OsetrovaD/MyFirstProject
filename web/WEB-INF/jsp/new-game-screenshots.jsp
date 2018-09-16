<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Скриншоты</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Добавьте ссылки на скриншоты (не более 3):</b></p>
<form action="${pageContext.request.contextPath}/new-game-screenshots" method="post">
    <input type="text" name="screenshot" size="100"><br>
    <input type="text" name="screenshot" size="100"><br>
    <input type="text" name="screenshot" size="100"><br>
    <input type="submit" value="Сохранить игру">
</form>
</body>
</html>
