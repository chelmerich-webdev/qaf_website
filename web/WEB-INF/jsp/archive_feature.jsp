<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<%--<jsp:useBean id="film" class="com.queerartfilm.film.Film" scope="request"/>
<c:set var="event" value="${qaf:getEventByKey(film.parentKey)}" />--%>
<jsp:useBean id="feature" class="com.queerartfilm.film.FeaturedFilm" scope="request" />
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.homepage.css']}" />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.archive.css']}" />
  </head>
  <body>
    <qaf:body-top-include />
    <div id="listing">
      <div id="listing-header" class="showborder">
        <h3><a href="${menuMap['archive']}">Archive</a></h3>
        <img src="/images/${feature.urlKey}_large.png" alt="${feature.title}" height="240px" />
        <div id="listing-header-text">
          <p class="redtext nobold">${feature.presenter}<br/>presents</p>
          <h1>${feature.title}</h1>
          <h6>(${feature.releaseYear}, ${feature.director})</h6>
        </div>
        <div id="listing-header-shadow">
          <h1>${feature.title}</h1>
          <h6>&nbsp;</h6>
        </div>
      </div>

      <div id="listing-content">

        <div id="listing-links-col">
          <c:set var="size" value="${fn:length(feature.links)}" />
          <c:if test="${size > 0}">
            <div id="more-info">More Info</div><img src="/images/qaf-triangle_small.png" alt="triangle"/>
            <ul>
              <c:forEach var="item" items="${feature.links}" varStatus="status">
                <li${status.count eq size ? ' class="last"': ''}>
                  <c:choose>
                    <c:when test="${!fn:contains(item.url, 'http')}">
                      <qaf:archive-youtube-embed link="${item}"/>
                    </c:when>
                    <c:otherwise>
                      <p><a href="${item.url}">${item.label}</a></p>
                    </c:otherwise>
                  </c:choose>
                </li>
              </c:forEach>
            </ul>
          </c:if>
        </div>
        <div id="listing-content-col">
          <p>${feature.synopsis}</p>
          <c:set var="showdate" value="${feature.screening.date}" />
          <%--          <c:choose>
                      <c:when test="true">
                        <div id="purchase">
                          <a href="${feature.screening.purchaseUrl}">Purchase Tickets</a>
                        </div>
                        <p><strong>${qaf:getMonth(showdate)} ${qaf:getDay(showdate)}, ${qaf:getYear(showdate)}</strong>
                      </c:when>
                      <c:otherwise>--%>
          <p>Screened: <strong>${qaf:getMonth(showdate)} ${qaf:getDay(showdate)}, ${qaf:getYear(showdate)}</strong></p>
          <%--            </c:otherwise>
                    </c:choose>--%>
        </div>
      </div>
    </div>
    <qaf:body-bottom-include />
  </body>
</html>
