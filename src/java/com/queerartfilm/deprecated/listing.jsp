<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="ch67" tagdir="/WEB-INF/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Q | A | F Archive</title>
    </head>
    <body>
        <h1>Q | A | F <a href="/archive">Archive</a></h1>
        <h2>${film.title}</h2>
        <ch67:def-list-item obj="${film}" proplist="title,key,year,directorFirst,directorLast,synopsis" />

        <ch67:def-list-item obj="${film}" proplist="series,screeningVenue,venueAddress1,venueAddress2,venuePhoneNumber,presenterFirst,presenterLast" />
    </body>
</html>