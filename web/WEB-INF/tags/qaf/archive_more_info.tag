<%@tag description="Iterates any external links for a film's More Info section"
       pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@attribute name="items" required="true" rtexprvalue="true"
             type="java.util.List"%>
<%@attribute name="var" required="true" rtexprvalue="false" %>
<%@attribute name="youtube" fragment="yes" required="true" %>
<%@attribute name="url" fragment="yes" required="true" %>
<%@variable name-from-attribute="var" alias="alink" scope="NESTED"
            variable-class="com.queerartfilm.film.Link" %>
<c:set var="size" value="${fn:length(items)}" />
<c:if test="${size gt 0}">
  <div id="more-info">More Info</div><img src="/images/qaf-triangle_small.png" alt="triangle"/>
  <ul>
    <c:forEach var="alink" items="${items}" varStatus="status" >
      <li${status.count eq size ? ' class="last"': ''}>
        <c:choose>
          <c:when test="${alink.youTubeId}">
            <jsp:invoke fragment="youtube" />
          </c:when>
          <c:otherwise>
            <jsp:invoke fragment="url" />
          </c:otherwise>
        </c:choose>
      </li>
    </c:forEach>
  </ul>
</c:if>