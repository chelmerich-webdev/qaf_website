<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--<jsp:useBean id="eventDAO" class="com.queerartfilm.dao.EventDAO" scope="page" />
<jsp:useBean id="filmDAO" class="com.queerartfilm.dao.FilmDAO" scope="page" />--%>
<c:set var="curSeries" value="${qaf:getSeriesByKey(current.series)}" scope="page"/>
<c:choose>
    <c:when test="${featuredFilm != null}">
        <c:set var="curFilm"  value="${featuredFilm}" />
        <c:set var="curEvent" value="${qaf:getEventByKey(curFilm.parentKey)}" scope="page"/>
    </c:when>
    <c:otherwise>
        <c:set var="curEvent" value="${qaf:getEventByKey(current.event)}" scope="page"/>
        <c:set var="curFilm"  value="${qaf:getFilmByKey(curEvent.filmKey)}"/>
    </c:otherwise>
</c:choose>

<html>
    <head>
        <qaf:head-include />
        <link rel="stylesheet" type="text/css" href="/qaf_homepage.css" />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="series-col" >
            <c:forEach var="eventKey" items="${curSeries.events}" varStatus="status">
                <c:set var="event" value="${qaf:getEventByKey(eventKey)}" />
                <c:set var="film" value="${qaf:getFilmByKey(event.filmKey)}" />
                <div class="series-listing">
                    <div id="sprite-${status.count}" class="sprite"><a href="/films/${film.id}"></a></div>
                    <h6>${event.month} ${event.day}, ${event.year}</h6>
                    <h2 class="${film.id eq curFilm.id ? 'red-rollover' : 'gray-rollover'}" ><a href="/current/${film.id}">${film}</a></h2>
                </div>
            </c:forEach>
        </div>
        <div id="featured-film">
            <div id="seriesbar">
                <h3>${curSeries} <span id="at">@</span>
                    <img src="/images/ifc_center_logo_small.png" alt="IFC Center" /></h3>
            </div><a href="${menuMap['archive']}/${curFilm.id}">
                <img src="/images/${curFilm.id}_large.png" alt="${curFilm.title}" width="500" height="240"/></a>

            <div id="info-left">
                <div id="venue">
                    <p class="redtext">${curEvent.month} ${curEvent.day}, ${curEvent.year}</p>
                    <p><span class="graytext nobold">${curEvent.hour}:${curEvent.minute}${curEvent.ampm}</span></p><br/>
                    <p>${curEvent.venue}<br/>
                        ${curEvent.venue.address1}<br/>
                        ${curEvent.venue.address2}<br/>
                    ${curEvent.venue.phoneNumber.number}<p>
                <div id="purchase">Purchase Tickets</div>
                </div>
            </div>
            <div id="info-right">
                <img src="/images/qaf-triangle_large.png" alt="triangle"/>
            </div>
            <div id="info-center">
                <p id="info-center-presenter">${curEvent.presenterFirst} ${curEvent.presenterLast} presents</p>
                <h1 class="rollover"><a href="${menuMap['archive']}/${curFilm.id}">${curFilm.title}</a></h1>
                <h6>(${curFilm.year}, ${curFilm.directorFirst} ${curFilm.directorLast})</h6>
                <div id="info-center-text">
                    <p>${fn:substring(curEvent.synopsis, 0, 375)} . . .</p>
                    <a href="${menuMap['archive']}/${curFilm.id}"><div id="synopsis-footer">
                    <div id="more-info">More Info</div><img src="/images/qaf-triangle_small.png" alt="triangle"/></div></a>
                </div>
            </div>
        </div>

        <qaf:body-bottom-include />
    </body>
</html>
