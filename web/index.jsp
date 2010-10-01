<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" session="false"%>
<%@include file="/WEB-INF/jsp/includes.jsp" %>
<c:set var="series" value="${qaf:getQAFSeriesByKey(current.qafSeriesKey)}" scope="page"/>
<c:if test="${empty selectedFilm}">
  <c:set var="selectedFilm" >${qaf:getFeaturedFilmByKey(current.featuredFilmKey)}</c:set>
</c:if>
<!DOCTYPE html>
<html> 
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.css.homepage']}" >
    <script type="text/javascript" src="/jquery-1.4.2.min.js"></script>
  </head>
  <body>
    <qaf:body-top-include />

    <div id="series-col" >
      <c:forEach var="key" items="${series.filmKeysAsListAsc}" varStatus="status">
        <c:set var="seriesFilm" value="${qaf:getFeaturedFilmByKey(key)}" />
        <c:set var="showdate" value="${seriesFilm.screening.date}"/>
        <div class="series-listing">
          <%--<div id="sprite-${status.count}" class="sprite"><a href="${menusMap['home'][1]}/${selectedFilm.urlKey}"></a></div>--%>
          <c:set var="isSelected" value="${seriesFilm.urlKey == selectedFilm.urlKey}" />
          <a href="${menusMap['home'][1]}/${seriesFilm.urlKey}"><img class="${!isSelected ? 'rollover' : ''}" src="/images/${seriesFilm.urlKey}_vert${isSelected ? '-hover' : ''}.png" alt="${seriesFilm.title}" /></a>
          <h6>${qaf:getDate(showdate)}</h6>
          <h6>${seriesFilm.presenter} presents</h6>
          <h2 class="${isSelected ? 'red-rollover' : 'gray-rollover'}" ><a href="${menusMap['home'][1]}/${seriesFilm.urlKey}">${seriesFilm.title}</a></h2>
        </div>
      </c:forEach>
    </div>
    <div id="featured-film">
      <div id="seriesbar">
        <h3>${series} <span id="at">@</span>
          <img src="/images/ifc_center_logo_small.png" alt="IFC Center" /></h3>
      </div>
      <div id="feature-image">
          <qaf:film-link film="${selectedFilm.title}"
                         label="<img src='/images/${selectedFilm.urlKey}_large.png' alt='${selectedFilm.title}' />" />
      </div>
      <div id="info-left">
        <c:set var="showdate" value="${selectedFilm.screening.date}"/>
        <p class="redtext">${qaf:getDate(showdate)}</p>
        <p><span class="graytext nobold">${qaf:getTime(showdate)}</span>
          <c:if test="${!empty selectedFilm.screening.secondTime}" >
            <span class="graytext nobold"> and ${selectedFilm.screening.secondTime}</span>
          </c:if>
        </p><br/>
        <p>
          ${selectedFilm.screening.venue}<br/>
          ${selectedFilm.screening.venue.address1}<br/>
          ${selectedFilm.screening.venue.address2}<br/>
          ${selectedFilm.screening.venue.phoneNumber.number}
        </p>
        <c:if test="${selectedFilm.screening.onSale}">
          <div id="purchase">
            <a href="${fn:escapeXml(selectedFilm.screening.purchaseUrl)}">Purchase Tickets</a>
          </div>
        </c:if>
      </div>
      <div id="info-right">
        <img src="/images/qaf-triangle_large.png" alt="triangle"/>
      </div>
      <div id="info-center">
        <p id="info-center-presenter">${selectedFilm.presenter} presents</p>
        <h1 class="rollover"><qaf:film-link film="${selectedFilm.title}" /></h1>
        <h6>(${selectedFilm.releaseYear}, ${selectedFilm.director})</h6>
        <div id="info-center-text">
          <p>${fn:substring(selectedFilm.synopsis, 0, 375)} . . .</p>
          <div id="synopsis-footer">
            <div id="more-info">
              <qaf:film-link film="${selectedFilm.title}" label="More Info"/>
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
