<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Q | A | F Archive</title>
    </head>
    <body>
        <h1>Q | A | F Archive</h1>
        <%= 10 / 0%>
        <ul>
            <c:forEach var="film" items="${films}">
                <li>
                    <a href="/archive/film?id=${film.key['name']}">${film.title}</a>
                </li>
            </c:forEach>
        </ul>
    </body>
</html>
