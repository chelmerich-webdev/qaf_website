<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="qafSeriesDAO" class="com.queerartfilm.dao.QAFSeriesDAO" scope="request" />
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="request" />
<jsp:useBean id="now" class="java.util.Date" scope="request" />
<!DOCTYPE html>
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.form.css']}" />
    <script type="text/javascript" src="/qaf_form.js"></script>
  </head>
  <body>
    <qaf:body-top-include />
    <div id="admin-message"><p>${!empty message ? message : ''}</p></div>
    <div id="simple-page">
      <h4>Web Site Configuration</h4>
      <form method="post" action="/manage/config/update">
        <fieldset>
          <legend>Current Settings</legend>
          <c:set var="field" value="qafSeriesKey"/>
          <label>Homepage Series</label>
          <div class="inputbox" >
            <select name="${field}">
              <c:forEach var="item" items="${qafSeriesDAO.allItems}">
                <option value="${item.id}" ${current[field].id eq item.id ? "selected" : ""}>${item}</option>
              </c:forEach>
            </select>
          </div>

          <c:set var="field" value="featuredFilmKey" />
          <label>Current Feature</label>
          <div class="inputbox" >
            <select name="${field}">
              <c:forEach var="item" items="${featureDAO.allItems}" >
                <option value="${item.id}" ${current[field].id eq item.id ? "selected" : ""}>${item}</option>
              </c:forEach>
            </select>
          </div>

          <c:set var="field" value="facebookUrl" />
          <label>Facebook URL</label>
          <div class="inputbox">
            <input type="text" name="${field}" value="${current[field]}" size="49" />
          </div>

          <fieldset>
            <legend>Subscription Email</legend>
            <c:set var="field" value="subscribeEmailAddress" />
            <label>Email Address</label>
            <div class="inputbox">
              <input type="text" name="${field}" value="${current[field]}" size="49" />
            </div>

            <c:set var="field" value="subscribeEmailSubject" />
            <label>Subject</label>
            <div class="inputbox">
              <input type="text" name="${field}" value="${current[field]}" size="49" />
            </div>

            <c:set var="field" value="subscribeEmailBody" />
            <label>Message</label>
            <div class="inputbox">
              <textarea name="${field}" rows="4" cols="60" >${current[field]}</textarea><br/>
            </div>
          </fieldset>
          <c:set var="field" value="year"/>
          <label>Year</label>
          <div class="inputbox">
            <input type="text" readonly="readonly" class="disabled"
                   name="display-${field}" value="${qaf:getYear(now)}" size="7"/>
            <input type="hidden" name="${field}" value="${qaf:getYear(now)}" />
          </div>

          <div class="inputbox">
            <input type="submit" class="submit" value="Submit"  />
            <input type="button" class="submit" value="Cancel"
                   onclick="javascript:location.replace('/manage');" />
          </div>
        </fieldset>
      </form>
    </div>
    <qaf:body-bottom-include />
  </body>
</html>
