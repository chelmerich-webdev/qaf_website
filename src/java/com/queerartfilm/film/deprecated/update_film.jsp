<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="eventDAO" class="com.queerartfilm.dao.EventDAO" scope="request" />
<jsp:useBean id="film" class="com.queerartfilm.film.Film" scope="request" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="qaf" uri="qafTags" %>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/qaf_form.css">
        <script type="text/javascript" src="/qaf_form.js"></script>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <c:choose>
            <c:when test="${film.assigned && empty form}">
                <c:set var="parentEvent" value="${qaf:getEventByKey(film.parentKey)}"  />
                <div id="admin-message"><p class="nobold rev-rollover">
                        Screened on <a href='../events/update?id=${parentEvent.id}'>${parentEvent}.</a>
                        <c:if test="${parentEvent.assigned}">
                            <c:set var="parentSeries" value="${qaf:getSeriesByKey(parentEvent.parentKey)}" />
                        <br/>Part of the <a href="../series/update?id=${parentSeries.id}">${parentSeries}</a> series.
                    </c:if>
                </p></div>
            </c:when>
            <c:otherwise>
                <p id="admin-message" class="${form.valid ? 'success' : 'error'}">${form.messages.result}</p>
            </c:otherwise>
        </c:choose>
        <div id="simple-page">
            <h4>Film: ${param['id'] eq 'new' ? 'New' : film.title}</h4>
                <form method="post" action="/manage/films/update?id=${empty film.id ? 'new' : film.id}">
                    <fieldset>
                        <legend>Facts About the Film</legend>
                        <c:set var="field" value="title" />
                        <c:choose>
                            <%--Prohibit title changes--%>
                            <c:when test="${film.assigned}">
                                <qaf:input name="display-title" label="Title"
                                           value="${fn:escapeXml(film[field])}"
                                           message=""
                                           disabled="true"
                                           size="42"
                                           class="disabled" />
                                <input type="hidden" name="${field}" value="${film[field]}" />
                            </c:when>
                            <c:otherwise>
                                <qaf:input name="${field}" label="Title"
                                           value="${fn:escapeXml(film[field])}"
                                           message="${form.messages[field]}"
                                           disabled="${form.valid || film.assigned}"
                                           class="${form.valid || film.assigned ? 'disabled' : ''}"
                                           size="42"
                                           required="true"
                                           tabindex="1"  />
                            </c:otherwise>
                        </c:choose>


                        <c:set var="field" value="year" />
                        <qaf:input name="${field}" label="Year of Release"
                                   value="${fn:escapeXml(film[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   maxlength="4"
                                   required="true"
                                   tabindex="2" />

                        <c:set var="field" value="directorFirst" />
                        <qaf:input name="${field}" label="Director's First Name"
                                   value="${fn:escapeXml(film[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   size="21" tabindex="3" />

                        <c:set var="field" value="directorLast" />
                        <qaf:input name="${field}" label="Director's Last Name"
                                   value="${fn:escapeXml(film[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   size="21" tabindex="4" />

                        <c:set var="field" value="length" />
                        <qaf:input name="${field}" label="Length (in minutes)"
                                   value="${fn:escapeXml(film[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   maxlength="3" tabindex="5"/>

                        <c:set var="field" value="mpaaRating" />
                        <label for="${field}">Rating</label>
                        <div class="inputbox">
                            <select name="${field}" size="1" tabindex="6"
                                    ${form.valid ? disabled : ''}>
                                <c:forEach var="rating" items="${ratingsList}" >
                                    <c:choose>
                                        <c:when test="${film.mpaaRating eq rating}">
                                            <option value="${rating.name}" selected="selected">${rating}</option>
                                        </c:when>
                                        <c:otherwise>
                                            <option value="${rating.name}">${rating}</option>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>
                        </div>
                        <div class="inputbox">
                            <input type="submit" value="Submit" tabindex="7"
                                   class="submit"
                                   ${form.valid ? 'disabled' : ''} />
                            <input type="button" class="submit" tabindex="8"
                                   value="${form.valid ? 'Return' : 'Cancel'}"
                                   onclick="javascript:location.replace('/manage');" />
                        </div>
                    </fieldset>
                </form>
                <form method="post" action="/manage/films/delete?id=${film.id}" >
                    <div class="inputbox">
                        <c:if test="${!(film.assigned || form.valid || param.id eq 'new')}">
                            <input type="submit" value="Delete" class="submit delete" />
                        </c:if>
                    </div>
                </form>
    </div>

        <script type="text/javascript">
            setHighlight('${form != null ? form.highlight : ''}');
            setFocus('${form != null ? form.focus : ''}');
        </script>
        <qaf:body-bottom-include />
    </body>
</html>