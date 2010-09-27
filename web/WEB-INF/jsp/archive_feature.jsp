<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="ff" class="com.queerartfilm.film.FeaturedFilm" scope="request" />
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
        <img src="/images/${ff.urlKey}_large.png" alt="${ff.title}" height="240px" />
        <div id="listing-header-text">
          <p class="redtext nobold">${ff.presenter}<br/>presents</p>
          <h1>${ff.title}</h1>
          <h6>(${ff.releaseYear}, ${ff.director})</h6>
        </div>
        <div id="listing-header-shadow">
          <h1>${ff.title}</h1>
          <h6>&nbsp;</h6>
        </div>
      </div>

      <div id="listing-content">

        <div id="more-info-col">
          <qaf:archive_more_info items="${ff.links}" var="link">
            <jsp:attribute name="youtube">
              <qaf:archive-youtube-embed link="${link}"/>
            </jsp:attribute>
            <jsp:attribute name="url">
              <p><a href="${link.url}">${link.label}</a></p>
            </jsp:attribute>
          </qaf:archive_more_info>
        </div>
        <%--          <c:set var="size" value="${fn:length(ff.links)}" />
                  <c:if test="${size > 0}">
                    <div id="more-info">More Info</div><img src="/images/qaf-triangle_small.png" alt="triangle"/>
                    <ul>--%>
        <%--              <c:forEach var="item" items="${ff.links}" varStatus="status">
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
                      </c:forEach>--%>
        <%--            </ul>
                  </c:if>--%>
        <div id="listing-content-col">
          <p>${ff.synopsis}</p>
          <c:set var="showdate" value="${ff.screening.date}" />
          <c:choose>
            <c:when test="${ff.screening.past}" >
              <p>Screened: <strong>${qaf:getMonth(showdate)} ${qaf:getDay(showdate)}, ${qaf:getYear(showdate)}</strong></p>
            </c:when>
            <c:otherwise>
              <c:if test="${ff.screening.onSale}" >
                <div id="buy-tickets">
                  <div id="purchase"><a href="${fn:escapeXml(ff.screening.purchaseUrl)}">Purchase Tickets</a></div>
                  <p><strong>${qaf:getDate(showdate)}</strong>
                    <span class="nobold">${qaf:getTime(showdate)}</span>
                    <c:if test="${!empty ff.screening.secondTime}" >
                      <span class="nobold"> and ${ff.screening.secondTime}</span>
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
