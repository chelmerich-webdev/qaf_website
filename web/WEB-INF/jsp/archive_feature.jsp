<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="ff" class="com.queerartfilm.film.FeaturedFilm" scope="request" />
<!DOCTYPE html>
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.css.homepage']}" />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.css.archive']}" />
  </head>
  <body id="${ff.urlKey}">
    <qaf:body-top-include />
    <div id="listing">
      <div id="listing-header">
        <qaf:archive-header film="${ff}"/>
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
