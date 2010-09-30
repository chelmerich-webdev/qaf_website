<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="series" value="${qaf:getQAFSeriesByKey(current.qafSeriesKey)}" scope="page"/>
<c:set var="feature" value="${qaf:getFeaturedFilmByKey(current.featuredFilmKey)}" scope="page" />
<c:if test="${selectedFeature != null}"><c:set var="feature" value="${selectedFeature}" /></c:if>
<!DOCTYPE html>
<html> 
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.homepage.css']}" >
    <script type="text/javascript" src="/jquery-1.4.2.min.js"></script>
  </head>
  <body>
    <qaf:body-top-include />

    <div id="series-col" >
      <c:forEach var="featureKey" items="${series.filmKeysAsListAsc}" varStatus="status">
        <c:set var="ff" value="${qaf:getFeaturedFilmByKey(featureKey)}" />
        <c:set var="showdate" value="${ff.screening.date}"/>
        <div class="series-listing">
          <%--<div id="sprite-${status.count}" class="sprite"><a href="${menusMap['home'][1]}/${feature.urlKey}"></a></div>--%>
          <c:set var="currentTest" value="${ff.urlKey == feature.urlKey}" />
          <a href="${menusMap['home'][1]}/${ff.urlKey}"><img class="${!currentTest ? 'rollover' : ''}" src="/images/${ff.urlKey}_vert${currentTest ? '-hover' : ''}.png" alt="${ff.title}" /></a>
          <h6>${qaf:getDate(showdate)}</h6>
          <h6>${ff.presenter} presents</h6>
          <h2 class="${currentTest ? 'red-rollover' : 'gray-rollover'}" ><qaf:film-link film="${ff.title}"/></h2>
        </div>
      </c:forEach>
    </div>
    <div id="featured-film">
      <div id="seriesbar">
        <h3>${series} <span id="at">@</span>
          <img src="/images/ifc_center_logo_small.png" alt="IFC Center" /></h3>
      </div>
      <div id="feature-image">
          <qaf:film-link film="${feature.title}"
                         label="<img src='/images/${feature.urlKey}_large.png' alt='${feature.title}' />" />
      </div>
      <div id="info-left">
        <c:set var="showdate" value="${feature.screening.date}"/>
        <p class="redtext">${qaf:getDate(showdate)}</p>
        <p><span class="graytext nobold">${qaf:getTime(showdate)}</span>
          <c:if test="${!empty feature.screening.secondTime}" >
            <span class="graytext nobold"> and ${feature.screening.secondTime}</span>
          </c:if>
        </p><br/>
        <p>
          ${feature.screening.venue}<br/>
          ${feature.screening.venue.address1}<br/>
          ${feature.screening.venue.address2}<br/>
          ${feature.screening.venue.phoneNumber.number}
        </p>
        <c:if test="${feature.screening.onSale}">
          <div id="purchase">
            <a href="${fn:escapeXml(feature.screening.purchaseUrl)}">Purchase Tickets</a>
          </div>
        </c:if>
      </div>
      <div id="info-right">
        <img src="/images/qaf-triangle_large.png" alt="triangle"/>
      </div>
      <div id="info-center">
        <p id="info-center-presenter">${feature.presenter} presents</p>
        <h1 class="rollover"><qaf:film-link film="${feature.title}" /></h1>
        <h6>(${feature.releaseYear}, ${feature.director})</h6>
        <div id="info-center-text">
          <p>${fn:substring(feature.synopsis, 0, 375)} . . .</p>
          <div id="synopsis-footer">
            <div id="more-info">
              <qaf:film-link film="${feature.title}" label="More Info"/>
            </div>
            <img src="/images/qaf-triangle_small.png" alt="triangle"/>
          </div>
        </div>
      </div>
    </div>
    <qaf:body-bottom-include />
    <script type="text/javascript">
      $(function() {
        $(".series-listing img.rollover").hover(function() {
          $(this).attr("src", $(this).attr("src").split(".").join("-hover."));
        }, function() {
          $(this).attr("src", $(this).attr("src").split("-hover.").join("."));
        });
      });
    </script>
  </body>
</html>
