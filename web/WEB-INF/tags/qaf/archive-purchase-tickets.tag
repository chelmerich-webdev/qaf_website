<%@tag description="Purchase Ticket disply on film detail page" 
       pageEncoding="UTF-8" isELIgnored="false" body-content="empty"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@attribute name="film" required="true" rtexprvalue="true" type="com.queerartfilm.film.FeaturedFilm" %>
<div id="buy-tickets">
  <div id="purchase"><a href="${fn:escapeXml(film.screening.purchaseUrl)}">Purchase Tickets</a></div>
  <p><strong>${qaf:getDate(film.screening.date)}</strong>
    <span class="nobold">${qaf:getTime(film.screening.date)}</span>
  <c:if test="${!empty film.screening.secondTime}" >
    <span class="nobold"> and ${film.screening.secondTime}</span>
  </c:if>
</p>
</div>