<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Интернет-магазин компьютерных игр</title>
    <style type="text/css">
        h1 {
            background-color: #f4f4f4;
            border: 1px solid #333333;
            color: #333333;
            text-align: center;
            margin: 0;
            padding: 5px;
        }
        .button {
            border: 1px solid #333333;
            text-decoration: none;
            width: 7em;
            padding:7px;
            background-color: #C9EBDC;
            color: #333333;
        }
    </style>
</head>
<body>
<%@ include file="/WEB-INF/jsp/header.jsp"%>
<br>
<p style="padding-top: 20px"></p>
<h1><fmt:message key="hellopage.greeting"/></h1>
<h2 align="center"><fmt:message key="hellopage.menu"/></h2>
<p align="center"><a href="${pageContext.request.contextPath}/login" class="button"><fmt:message key="hellopage.enter"/></a> <a href="${pageContext.request.contextPath}/register-page" class="button"><fmt:message key="hellopage.register"/></a></p>
</body>
</html>
