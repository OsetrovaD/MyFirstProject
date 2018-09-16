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
<form action="${pageContext.request.contextPath}/developer-company" method="post" style="padding-top: 50px">
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
