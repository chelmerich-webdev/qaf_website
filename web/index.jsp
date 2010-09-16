<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="curSeries" value="${qaf:getQAFSeriesByKey(current.qafSeriesKey)}" scope="page"/>
<c:set var="curFeature" value="${qaf:getFeaturedFilmByKey(current.featuredFilmKey)}" scope="page" />
<c:if test="${selectedFeature != null}"><c:set var="curFeature" value="${selectedFeature}" /></c:if>

<html> 
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.homepage.css']}" />
    <script type="text/javascript" src="/jquery-1.4.2.min.js"></script>
  </head>
  <body>
    <qaf:body-top-include />

    <div id="series-col" >
      <c:forEach var="featureKey" items="${curSeries.filmKeysAsListAsc}" varStatus="status">
        <c:set var="feature" value="${qaf:getFeaturedFilmByKey(featureKey)}" />
        <c:set var="showdate" value="${feature.screening.date}"/>
        <div class="series-listing">
          <%--<div id="sprite-${status.count}" class="sprite"><a href="${menuMap['home']}/${feature.urlKey}"></a></div>--%>
          <a href="${menuMap['home']}/${feature.urlKey}"><img src="/images/${feature.urlKey}_vert.png" height="90" width="45" alt="${feature.title}" /></a>
          <h6>${qaf:getMonth(showdate)} ${qaf:getDay(showdate)}, ${qaf:getYear(showdate)}</h6>
          <h6>${feature.presenter} presents</h6>
          <h2 class="${feature.urlKey eq curFeature.urlKey ? 'red-rollover' : 'gray-rollover'}" ><a href="${menuMap['home']}/${feature.urlKey}">${feature.title}</a></h2>
        </div>
      </c:forEach>
    </div>
    <div id="featured-film">
      <div id="seriesbar">
        <h3>${curSeries} <span id="at">@</span>
          <img src="/images/ifc_center_logo_small.png" alt="IFC Center" /></h3>
      </div>
        <a href="${menuMap['archive']}/${curFeature.urlKey}">
        <img src="/images/${curFeature.urlKey}_large.png" alt="${curFeature.title}" width="500" height="240"/></a>

      <div id="info-left">
        <c:set var="showdate" value="${curFeature.screening.date}"/>
        <p class="redtext">${qaf:getMonth(showdate)} ${qaf:getDay(showdate)}, ${qaf:getYear(showdate)}</p>
        <p><span class="graytext nobold">${qaf:getHour(showdate)}:${qaf:getMinute(showdate)}${qaf:getAmpm(showdate)}</span>
          <c:if test="${!empty curFeature.screening.secondTime}" >
            <span class="graytext nobold"> and ${curFeature.screening.secondTime}</span>
          </c:if>
        </p><br/>
        <p>
          ${curFeature.screening.venue}<br/>
          ${curFeature.screening.venue.address1}<br/>
          ${curFeature.screening.venue.address2}<br/>
          ${curFeature.screening.venue.phoneNumber.number}
        </p>
        <c:if test="${curFeature.screening.onSale}"> <%--&& !empty curFeature.screening.purchaseUrl}">--%>
          <div id="purchase">
            <a href="${curFeature.screening.purchaseUrl}">Purchase Tickets</a>
          </div>
        </c:if>
      </div>
      <div id="info-right">
        <img src="/images/qaf-triangle_large.png" alt="triangle"/>
      </div>
      <div id="info-center">
        <p id="info-center-presenter">${curFeature.presenter} presents</p>
        <h1 class="rollover"><a href="${menuMap['archive']}/${curFeature.urlKey}">${curFeature.title}</a></h1>
        <h6>(${curFeature.releaseYear}, ${curFeature.director})</h6>
        <div id="info-center-text">
          <p>${fn:substring(curFeature.synopsis, 0, 375)} . . .</p>
          <div id="synopsis-footer">
            <div id="more-info"><a href="${menuMap['archive']}/${curFeature.urlKey}">More Info</a></div><img src="/images/qaf-triangle_small.png" alt="triangle"/>
          </div>
        </div>
      </div>
    </div>
    <qaf:body-bottom-include />
    <script type="text/javascript">
      $(function() {
        $(".series-listing img").hover(function() {
          $(this).attr("src", $(this).attr("src").split(".").join("-hover."));
        }, function() {
          $(this).attr("src", $(this).attr("src").split("-hover.").join("."));
        });
      });
    </script>
  </body>
</html>
