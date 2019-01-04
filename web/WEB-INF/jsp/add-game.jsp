<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Добавить игру</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Введите параметры (все поля являются обязательными):</b></p>
<form action="${pageContext.request.contextPath}/add-game" method="post">
    <label for="name">Название</label><br>
    <input type="text" id="name" name="game_name" size="80" required><br>

    <label for="game_description">Описание</label><br>
    <textarea name="description" id="game_description" required></textarea><br>

    <label for="min_sys_req">Минимальные системные требования</label><br>
    <textarea name="min_sys_req" id="min_sys_req"></textarea><br>

    <label for="max_sys_req">Рекоммендуемые системные требования</label><br>
    <textarea name="max_sys_req" id="max_sys_req"></textarea><br>

    <label for="issue_year">Год выпуска</label><br>
    <input type="text" id="issue_year" pattern="\d{4}" name="issue_year" size="4" required><br>

    <label for="poster">Ссылка на постер</label><br>
    <input type="text" id="poster" name="poster" size="80" required><br>

    <label>Возрастное ограничение(ESRB)
        <select name="age_limit" required>
            <c:forEach var="age_limit" items="${requestScope.ageLimits}">
                <option value="${age_limit.name}">${age_limit.name}</option>
            </c:forEach>
        </select>
    </label>
    <input type="submit" value="Сохранить">
</form>
<p><a href="${pageContext.request.contextPath}/user">Домой</a>
</body>
</html>
