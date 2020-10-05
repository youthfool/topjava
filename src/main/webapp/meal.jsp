<%--
  Created by IntelliJ IDEA.
  User: klpkv
  Date: 05.10.2020
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Edit meal</h2>
<form method="POST" action="${pageContext.request.contextPath}/meals" name="frmAddMeal">
    <input type="hidden" name="mealId" value="<c:out value="${requestScope.meal.id}"/>" hidden/>
    DateTime: <input type="datetime-local" name="dateTime" value="<c:out value="${requestScope.meal.dateTime}"/>" /> <br />
    Description: <input type="text" name="description" value="<c:out value="${requestScope.meal.description}"/>" /> <br />
    Calories: <input type="text" name="calories" value="<c:out value="${requestScope.meal.calories}"/>" /> <br />
    <input type="submit" value="Save"> <a href="meals"><input type="button" value="Cancel" /></a>
</form>
</body>
</html>
