<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="eventDAO" class="com.queerartfilm.dao.EventDAO" />
<html>
    <head>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <h1>Archive</h1>
        <ul id="simple">
            <c:forEach var="film" items="${films}">
                <li class="rollover">
                    <a href="/archive/film?id=${film.key['name']}">${film.title}</a>
                </li>
            </c:forEach>
        </ul>
        <qaf:body-bottom-include />
    </body>
</html>
