<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="seriesDAO" class="com.queerartfilm.dao.QAFSeriesDAO" scope="request" />
<jsp:useBean id="eventDAO" class="com.queerartfilm.dao.EventDAO" scope="request" />
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="request" />

<html>
    <head>
        <qaf:head-include />
        <link type="text/css" rel="stylesheet" href="/qaf_form.css">
        <script type="text/javascript" src="/qaf_form.js"></script>
    </head>
    <body>
        <qaf:body-top-include />
        <div id="admin-message">
            <c:if test="${!empty message}">
                <p>${message}</p>
            </c:if>
        </div>
        <div id="simple-page">
            <h4>Web Site Configuration</h4>
            <form method="post" action="/manage/config/update">
                <fieldset>
                    <legend>Current Settings</legend>
                    <c:set var="field" value="series"/>
                    <label>Series</label>
                    <div class="inputbox" >
                        <select name="${field}">
                            <c:forEach var="item" items="${seriesDAO.allItems}">
                                <option value="${item.id}" ${current[field].id eq item.id ? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <c:set var="field" value="event" />
                    <label>Screening</label>
                    <div class="inputbox" >
                        <select name="${field}">
                            <c:forEach var="item" items="${eventDAO.allItems}" >
                                <option value="${item.id}" ${current[field].id eq item.id ? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <c:set var="field" value="feature" />
                    <label>Featured Film</label>
                    <div class="inputbox" >
                        <select name="${field}">
                            <c:forEach var="item" items="${featureDAO.allItems}" >
                                <option value="${item.id}" ${current[field].id eq item.id ? "selected" : ""}>${item}</option>
                            </c:forEach>
                        </select>
                    </div>

                    <c:set var="field" value="facebookUrl" />
                    <label>Facebook URL</label>
                    <div class="inputbox" >
                        <input type="text" name="${field}" value="${current[field]}" size="49" />
                    </div>
                    <c:set var="field" value="year"/>
                    <label>Year</label>
                    <div class="inputbox" >
                        <input type="text" readonly="readonly" class="disabled"
                               name="${field}" value="2010" size="7"/>
                    </div>
                    <div class="inputbox">
                        <input type="submit" class="submit" value="Submit"  />
                        <input type="button" class="submit" value="Cancel"
                               onclick="javascript:location.replace('/manage');" />
                    </div>
                </fieldset>
            </form>
        </div>
        <qaf:body-bottom-include />
    </body>
</html>
