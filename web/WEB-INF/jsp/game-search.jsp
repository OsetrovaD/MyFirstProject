<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 06.09.2018
  Time: 19:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск игр</title>
</head>
<body>
<h3>По какому параметру будем искать?</h3>
    <form method="post" action="${pageContext.request.contextPath}/game-search">
        <input type="radio" name="radio_group" value="by_name" id="check_group_01" checked>
        <label for="check_group_01">Название игры</label><br>
        <input type="radio" name="radio_group" value="by_platform" id="check_group_02">
        <label for="check_group_02">Игровая платформа</label><br>
        <input type="radio" name="radio_group" value="by_genre" id="check_group_03">
        <label for="check_group_03">Жанр</label><br>
        <input type="radio" name="radio_group" value="by_subgenre" id="check_group_04">
        <label for="check_group_04">Поджанр</label><br>
        <input type="radio" name="radio_group" value="by_issue_year" id="check_group_05">
        <label for="check_group_05">Год выпуска</label><br>
        <input type="submit" value="Отправить">
    </form>
<p><a href="${pageContext.request.contextPath}/user">Домой</a>
</body>
</html>
