<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Страница пользователя</title>
</head>
<body>
<p>Привет, ${sessionScope.user.login}</p>
<p><a href="${pageContext.request.contextPath}/change-data">Изменить данные</a> | <a href=
    "${pageContext.request.contextPath}/orders">Мои заказы</a> | <a href=
    "${pageContext.request.contextPath}/game-search">Поиск игр</a> | <a href=
    "${pageContext.request.contextPath}/comments">Мои комментарии</a></p>
</body>
</html>
