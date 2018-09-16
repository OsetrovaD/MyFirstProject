<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Добавление комментария</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px">Введите текст:</p>
<form action="${pageContext.request.contextPath}/add-comment?id=${requestScope.id}" method="post">
    <textarea name="comment" placeholder="Комментарий"></textarea><br>
    <input type="submit" value="Отправить">
</form>
</body>
</html>
