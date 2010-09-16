<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="film" class="com.queerartfilm.film.Film" scope="request"/>
<c:set var="event" value="${qaf:getEventByKey(film.parentKey)}" />
<html>
    <head>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="listing" class="showborder">
            <div id="listing-header" class="showborder">
                <h3>Archive</h3>
                <img src="/images/${film.id}_large.png" alt="${film.title}" height="240px" />
                <div id="listing-header-text">
                    <p class="redtext nobold">${event.presenterFirst} ${event.presenterLast}<br/>presents</p>
                    <h1>${film.title}</h1>
                    <h6>(${film.year}, ${film.directorFirst} ${film.directorLast})</h6>
                </div>
                <div id="listing-header-shadow">
                    <h1>${film.title}</h1>
                    <h6>&nbsp;</h6>
                </div>
            </div>
                <div id="listing-links-col" class="showborder"></div>
            <div id="listing-content" class="showborder">
                <p>${event.synopsis}</p>
                <p>Screened: <strong>${event.month} ${event.day}, ${event.year}</strong></p>
            </div>
            <%--            <div id="listing-content">
                            <qaf:def-list-item
                                obj="${film}"
                                proplist="title,key,year,directorFirst,directorLast,synopsis"/>
                            <qaf:def-list-item
                                obj="${film}"
                                proplist="series,screeningVenue,venueAddress1,venueAddress2,venuePhoneNumber,presenterFirst,presenterLast" />
                        </div>--%>
        </div>
        <qaf:body-bottom-include />
    </body>
</html>
