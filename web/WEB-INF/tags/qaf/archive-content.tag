<%@tag description="put the tag description here" pageEncoding="UTF-8" isELIgnored="false"%>
<%@attribute name="film" required="true" rtexprvalue="true" type="com.queerartfilm.film.FeaturedFilm" %>
<%@attribute name="hasScreened" fragment="true" required="true" %>
<%@attribute name="onSale" fragment="true" required="true" %>
<%--<%@attribute name="var" required="true" rtexprvalue="false" %>--%>
<%--<%@variable name-from-attribute="var" alias="showdate" scope="AT_BEGIN" %>--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="listing-content-col">
  <p>${film.synopsis}</p>
  <c:set var="showdate" value="${film.screening.date}" />
  <c:choose>
    <c:when test="${film.screening.past}">
      <jsp:invoke fragment="hasScreened" />
    </c:when>
    <c:otherwise>
      <c:if test="${film.screening.onSale}" >
        <jsp:invoke fragment="onSale" />
      </c:if>
    </c:otherwise>
  </c:choose>
  <div class="clear"></div>
</div>


