<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Разработчик</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px"><b>Проверьте правильность данных, введённых на предыдущей странице</b></p>
<p><b>Название:</b> ${sessionScope.game_info.name}</p>
<p><b>Описание:</b> ${sessionScope.game_info.description}</p>
<p><b>Год выпуска:</b> ${sessionScope.game_info.yearOfIssue}</p>
<p><b>Минимальные системные требования:</b> ${sessionScope.game_info.minimalSystemRequirements}</p>
<p><b>Рекомендуемые системные требования:</b> ${sessionScope.game_info.recommendedSystemRequirements}</p>
<p><b>Возрастное ограничение:</b> ${sessionScope.game_info.ageLimit.name}</p>
<p><b>Ссылка на постер:</b> ${sessionScope.game_info.image}</p>
<br>
<form action="${pageContext.request.contextPath}/developer-company" method="post">
    <label>Выберите компанию из списка:
        <select name="dev_company">
            <c:forEach var="company" items="${requestScope.companies}">
                <option value="${company.id}">${company.name}</option>
            </c:forEach>
            <option value="default" selected>Компания</option>
        </select>
    </label><br>
    <label for="name">Или введите название, если компания отсутствует в списке:</label><br>
    <input type="text" id="name" name="name" size="50"><br>
    <input type="submit" value="Добавить">
</form>
</body>
</html>
