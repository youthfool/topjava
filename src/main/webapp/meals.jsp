<%--
  Created by IntelliJ IDEA.
  User: klpkv
  Date: 04.10.2020
  Time: 15:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meals</title>
    <style type="text/css">
        table {
            border-collapse: collapse;
            margin-top: 10px;
        }
        th {
            padding: 5px;
            border: 2px solid black;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<p><a href="${pageContext.request.contextPath}/meals?action=insert">Add meal</a></p>
<table>
    <thead>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="m" items="${requestScope.mealsList}">
        <tr style="color: <c:out value="${m.excess ? 'red' : 'green'}"/>">
            <th>
                <c:out value="${requestScope.dateTimeFormatter.format(m.dateTime)}"/>
            </th>
            <th>
            <c:out value="${m.description}"/>
            </th>
            <th>
                <c:out value="${m.calories}"/>
            </th>
            <th>
                <a href="${pageContext.request.contextPath}/meals?action=edit&mealId=<c:out value="${m.id}"/>">Update</a>
            </th>
            <th>
                <a href="${pageContext.request.contextPath}/meals?action=delete&mealId=<c:out value="${m.id}"/>">Delete</a>
            </th>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
