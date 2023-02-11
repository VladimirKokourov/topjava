<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <title>Update Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Update Meal</h2>
<form action="meals" method="post">
    <input type="hidden" readonly="readonly" name="mealId" value="${meal.id}">
    Date : <input type="datetime-local" name="localDateTime" value="${meal.dateTime}"><br><br>
    Description: <input type="text" name="description" value="${meal.description}"><br><br>
    Calories: <input type="number" name="calories" value="${meal.calories}"><br><br>
    <input type="submit" value="Update">
    <button onclick="window.history.back()" type="button">Cancel</button>
</form>
</body>
</html>