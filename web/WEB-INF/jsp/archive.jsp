<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/WEB-INF/jsp/includes.jsp" %>
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="application"/>
<!DOCTYPE html>
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.css.archive']}" />
  </head>
  <body>
    <qaf:body-top-include />
    <div id="admin-message"><p class="nobold">${notfound}</p></div>
    <div id="archive">
      <h2>${menusMap['archive'][0]}</h2>

      <c:set var="allItems" value="${featureDAO.allAssigned}" />
      <c:set var="totalCount" value="${fn:length(allItems)}" />

      <ul id="archive-list">

        <c:forEach var="ff" items="${allItems}" varStatus="status" >

          <c:if test="${status.count % 4 eq 1}" >
            <li class="archive-row">
              <ul class="archive-items">
              </c:if>

              <c:if test="${ff.seriesKey != null}">
                <c:set var="urlKey" value="${ff.urlKey}" />
                <c:set var="firstChar" value="${fn:substring(urlKey, 0, 1)}" />
                <c:choose>
                    <c:when test="${qaf:isInteger(firstChar)}">
                        <c:set var="classKey" value="d${urlKey}" />
                    </c:when>
                    <c:otherwise>
                        <c:set var="classKey" value="${urlKey}" />
                    </c:otherwise>
                </c:choose>
                
                <li class="archive-item ${classKey}"><qaf:archive-feature-item feature="${ff}" /></li>
              </c:if>

              <c:if test="${status.count % 4 eq 0 || status.count eq totalCount}" >
              </ul>
            </li>
          </c:if>

        </c:forEach>

      </ul>
    </div>
    <qaf:body-bottom-include />
  </body>
</html>
