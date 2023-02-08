<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html lang="ru">
<head>
    <title>Add Meal</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Add Meal</h2>
<form action="meals" method="post">
    Date : <input type="datetime-local" name="localDateTime"><br><br>
    Description: <input type="text" name="description"><br><br>
    Calories: <input type="text" name="calories"><br><br>
    <input type="submit" value="Submit">
</form>
</body>
</html>