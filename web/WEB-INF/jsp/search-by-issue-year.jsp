<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Поиск по году выпуска</title>
</head>
<body>
<%@ include file="header.jsp"%>
<br>
<p style="padding-top: 20px">Год выпуска: </p>
<form method="post" action="${pageContext.request.contextPath}/search-result?url=/by-issue-year-search">
    <label>
        <input type="text" name="values" pattern="\d{4}" maxlength="4" required autofocus>
    </label><span>*</span>
    <input type="submit" value="Искать"><br>
</form>
</body>
</html>
