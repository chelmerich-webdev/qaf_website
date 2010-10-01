<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" isErrorPage="true"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<!DOCTYPE html>
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.css.form']}" />
  </head>
  <body>
    <qaf:body-top-include />
    <div id="simple-page">
      <h4>Error ${requestScope["javax.servlet.error.status_code"]}</h4>
      <p>${requestScope["javax.servlet.error.message"]}: ${requestScope["javax.servlet.error.request_uri"]}</p>
      <p>${requestScope["javax.servlet.error.exception"]}</p>
      <%--Code: ${requestScope["javax.servlet.error.status_code"]}<br/>--%>
      <%--Type: ${requestScope["javax.servlet.error.exception_type"]}<br/>--%>
      <%--Message: ${requestScope["javax.servlet.error.message"]}<br/>--%>
      <%--   URI: ${requestScope["javax.servlet.error.request_uri"]}<br/>
         Exception: ${requestScope["javax.servlet.error.exception"]}<br/>--%>
      <%--Servlet: ${requestScope["javax.servlet.error.servlet_name"]}<br/>--%>
      <%--<%= (String) request.getAttribute("javax.servlet.error.status_code")%>--%>
      <a href="/current"><input value="Return" type="button" class="submit withoutLabel"/></a>
    </div>
    <qaf:body-bottom-include />
  </body>
</html>
