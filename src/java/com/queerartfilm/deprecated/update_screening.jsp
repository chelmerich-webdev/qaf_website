<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="now" class="java.util.Date" />
<fmt:setLocale value="en-US" scope="page" />
<%--<fmt:setTimeZone value="America/New_York" />--%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/qaf-form.css">
        <script type="text/javascript" src="/qaf-form.js"></script>
        <qaf:head-include />

    </head>
    <body>
        <qaf:body-top-include />
        <p id="admin-message" class="${form.valid ? 'success' : 'error'}">${form.messages.result}</p>
        <div id="simple-page">
            <h2>Add or Edit an Event</h2>
                       <div class="content">
                <form method="post" action="/manage/screenings/update">
                    <fieldset>
                        <label>Date</label>
                        <div class="inputbox">

                            <c:set var="field" value="month"/>
                            <select name="${field}" size="1">
                                <c:forEach var="item" items="${monthList}">
                                    <c:choose>
                                        <c:when test="${event[field] eq item}">
                                            <option selected="selected">${item}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option>${item}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

                            <c:set var="field" value="day"/>
                            <select name="${field}" size="1">
                                <c:forEach var="item" items="${dayList}">
                                    <c:choose>
                                        <c:when test="${screening[field] eq item}">
                                            <option selected="selected">${item}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option>${item}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>

                            <strong>,</strong>

                            <c:set var="field" value="year"/>
                            <c:set var="curYear"><fmt:formatDate value="${now}" pattern="yyyy" /></c:set>
                            <select name="${field}" size="1">
                                <c:forEach var="item" begin="${curYear}" end="${curYear+5}">
                                    <c:choose>
                                        <c:when test="${screening[field] eq item}">
                                            <option selected="selected">${item}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option>${item}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                        </div>

                        <label>Time</label>
                        <div id="time" class="inputbox">

                            <c:set var="field" value="hour"/>
                            <select name="${field}" size="1">
                                <c:forEach var="item" items="${hourList}" >
                                    <option value="${item}"><fmt:formatNumber value="${item}" pattern="00"/></option>
                                </c:forEach>
                            </select>

                            <strong>:</strong>

                            <c:set var="field" value="minute"/>
                            <select name="${field}" size="1">
                                <c:forEach var="item" items="${minuteList}" >
                                    <option><fmt:formatNumber value="${item}" pattern="00"/></option>
                                </c:forEach>
                            </select>

                            <c:set var="field" value="ampm"/>
                            <select name="${field}" size="1">
                                <c:forEach var="item" items="${ampmList}" >
                                    <option>${item}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <%--                        <c:set var="field" value="showDateAndTime" scope="page"/>
                                                <qaf:input name="${field}" label="Date and Time"
                                                           value="${screening[field]}"
                                                           message="${form.messages[field]}"
                                                           disabled="${form.valid}"
                                                           size="35"
                                                           required="true" />--%>

                        <c:set var="field" value="venue" />
                        <label for="${field}">Venue</label>
                        <div class="inputbox">
                            <select name="${field}" size="1">
                                <c:forEach var="venue" items="${venueList}" >
                                    <c:choose>
                                        <c:when test="${screening.venue eq venue}">
                                            <option value="${venue.name}" selected="selected">${venue}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${venue.name}">${venue}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>
                        </div>
                        <!-- yyyy-M-dd hh:mm a -->
                        <%-- <input type="hidden" name="screeningDateAndTime"
                                value="${year}-${month.selected}-${day} ${hour}:${minute} ${ampm}" />--%>
                        <input type="submit" value="Submit" class="withoutLabel rollover submit" />
                    </fieldset>
                </form>
                <script type="text/javascript">
                    setHighlight('${form != null ? form.highlight : ''}');
                    setFocus('${form != null ? form.focus : ''}');
                </script>
            </div></div>
            <qaf:body-bottom-include />
    </body>
</html>