<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
  "http://www.w3.org/TR/html4/loose.dtd">
<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="application"/>
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.archive.css']}" />
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
                <li class="archive-item"><qaf:archive-feature-item feature="${ff}" /></li>
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
