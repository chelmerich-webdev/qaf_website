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

        <qaf:archive_more_info items="${ff.links}" var="link">
          <jsp:attribute name="youtube">
            <qaf:archive-youtube-embed link="${link}"/>
          </jsp:attribute>
          <jsp:attribute name="url">
            <p><a href="${link.url}">${link.label}</a></p>
          </jsp:attribute>
        </qaf:archive_more_info>

        <qaf:archive-content film="${ff}">
          <jsp:attribute name="hasScreened">
            <p>Screened: <strong>${qaf:getDate(ff.screening.date)}</strong></p>
          </jsp:attribute>
          <jsp:attribute name="onSale">
            <qaf:archive-purchase-tickets film="${ff}" />
          </jsp:attribute>
        </qaf:archive-content>
      </div>

    </div>
    <qaf:body-bottom-include />
  </body>
</html>
