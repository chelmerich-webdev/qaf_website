<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="filmDAO" class="com.queerartfilm.dao.FilmDAO" />
<jsp:useBean id="eventDAO" class="com.queerartfilm.dao.EventDAO" />
<jsp:useBean id="seriesDAO" class="com.queerartfilm.dao.SeriesDAO" />
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" />
<jsp:useBean id="qafSeriesDAO" class="com.queerartfilm.dao.QAFSeriesDAO" />
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/qaf_form.css">
        <script type="text/javascript" src="/qaf_form.js"></script>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="simple-page">
            <h4>Manage Records</h4>


            <form method="get" action="/manage/config/update">
                <fieldset>
                    <legend>Web Site Configuration</legend>
                    <label><input type="submit" value="edit" class="submit" /></label>
                    <div class="inputbox">
                        Series: ${qaf:getSeriesByKey(current.series)}<br/>
                        Screening: ${qaf:getEventByKey(current.event)}
                    </div>
                </fieldset>
            </form>

            <form method="get" action="/manage/series/update">
                <fieldset>
                    <legend>Series</legend>
                    <label>
                        <input type="submit" value="edit" class="submit" />
                    </label>
                    <div class="inputbox">
                        <select name="id" size="1">
                            <option value="new">A New Series Record</option>
                            <c:forEach var="series" items="${seriesDAO.allItems}">
                                <option value="${series.id}">${series.title}</option>
                            </c:forEach>
                        </select>
                    </div>
                </fieldset>
            </form>

            <form method="get" action="/manage/events/update">
                <fieldset>
                    <legend>Screenings</legend>
                    <label>
                        <input type="submit" value="edit" class="submit" />
                    </label>
                    <div class="inputbox">
                        <select name="id" size="1">
                            <option value="new">A New Screening Record</option>
                            <c:forEach var="event" items="${eventDAO.allItems}">
                                <option value="${event.id}">${event}</option>
                            </c:forEach>
                        </select>
                    </div>
                </fieldset>
            </form>

            <form method="get" action="/manage/films/update">
                <fieldset>
                    <legend>Films</legend>
                    <label>
                        <input type="submit" value="edit" class="submit" />
                    </label>
                    <div class="inputbox">
                        <select name="id" size="1">
                            <option value="new">A New Film Record</option>
                            <c:forEach var="film" items="${filmDAO.allItems}">
                                <option value="${film.id}">${film}</option>
                            </c:forEach>
                        </select>
                    </div>
                </fieldset>
            </form>

        </div>
        <qaf:body-bottom-include />
    </body>
</html>
