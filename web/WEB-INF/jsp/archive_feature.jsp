<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="feature" class="com.queerartfilm.film.FeaturedFilm" scope="request" />
<jsp:useBean id="today" class="java.util.Date" scope="request" />
<!DOCTYPE html>
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
        <h3><a href="${menusMap['archive'][1]}">${menusMap['archive'][0]}</a></h3>
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
                    <c:when test="${item.youTubeId}">
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
          <c:choose>
            <c:when test="${feature.screening.past}" >
              <p>Screened: <strong>${qaf:getMonth(showdate)} ${qaf:getDay(showdate)}, ${qaf:getYear(showdate)}</strong></p>
            </c:when>
            <c:otherwise>
              <c:if test="${feature.screening.onSale}" >
              <div id="buy-tickets">
                <div id="purchase"><a href="${fn:escapeXml(feature.screening.purchaseUrl)}">Purchase Tickets</a></div>
                <p><strong>${qaf:getDate(showdate)}</strong>
                  <span class="nobold">${qaf:getTime(showdate)}</span>
                  <c:if test="${!empty feature.screening.secondTime}" >
                    <span class="nobold"> and ${feature.screening.secondTime}</span>
                  </c:if>
                </p>
              </div>
              </c:if>
            </c:otherwise>
          </c:choose>
          <div class="clear"></div>
        </div>
      </div>
    </div>
    <qaf:body-bottom-include />
  </body>
</html>
