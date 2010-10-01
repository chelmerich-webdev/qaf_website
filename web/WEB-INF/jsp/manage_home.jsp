<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.google.appengine.api.users.*" %>
<%@include file="/WEB-INF/jsp/includes.jsp" %>
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" />
<jsp:useBean id="qafSeriesDAO" class="com.queerartfilm.dao.QAFSeriesDAO" />
<!DOCTYPE html>
<html>
  <head>
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.form.css']}" />
    <script type="text/javascript" src="/qaf_form.js"></script>
    <qaf:head-include />
  </head>
  <body>
    <qaf:body-top-include />
    <div id="simple-page">
<h4>Website Data Management</h4>
      <%
                  UserService userService = UserServiceFactory.getUserService();
                  User user = userService.getCurrentUser();
                  if (user == null || !userService.isUserAdmin()) {
                      //if (!userService.isUserLoggedIn() || !userService.isUserAdmin()) {
%>

      <p>${initParam['com.queerartfilm.wordmark']} Administrators, please log in.</p>
      <% if (userService.isUserLoggedIn() && !userService.isUserAdmin()) {%>
      <p>You do not have Administrator access.</p>
      <% }%>
      <form><a href="<%=userService.createLoginURL("/login")%>">
          <input type="button" value="log in" class="submit withoutLabel" /></a></form>
          <% } else {%>

      <form method="get" action="/manage/qafseries/update">
        <fieldset>
          <legend>${initParam['com.queerartfilm.wordmark']} Series</legend>
          <label>
            <input type="submit" value="edit" class="submit" />
          </label>
          <div class="inputbox">
            <select name="id" size="1">
              <option value="new">A New Series Record</option>
              <c:forEach var="series" items="${qafSeriesDAO.allItems}">
                <option value="${series.id}">${series.title}</option>
              </c:forEach>
            </select>
          </div>
        </fieldset>
      </form>


      <form method="get" action="/manage/features/update">
        <fieldset>
          <legend>Featured Films</legend>
          <label>
            <input type="submit" value="edit" class="submit" />
          </label>
          <div class="inputbox">
            <select name="id" size="1">
              <option value="new">A New Film Record</option>
              <c:forEach var="film" items="${featureDAO.allItems}">
                <option value="${film.id}">${film}</option>
              </c:forEach>
            </select>
          </div>
        </fieldset>
      </form>

      <form method="get" action="/manage/config/update">
        <fieldset>
          <legend>Web Site Configuration</legend>
          <label><input type="submit" value="edit" class="submit clear" /></label>
          <div class="inputbox">
            <ul>
              <li><strong>Homepage Series:</strong> ${qaf:getQAFSeriesByKey(current.qafSeriesKey)}</li>
              <li><strong>Current Feature:</strong> ${qaf:getFeaturedFilmByKey(current.featuredFilmKey)}</li>
            </ul>
          </div>

        </fieldset>
        <fieldset>
          <legend>Please log out when finished</legend>
          <a href="<%=userService.createLogoutURL("/login")%>"><input type="button" value="log out" class="submit withoutLabel" /></a>
        </fieldset>

      </form>
      <% }%>
    </div>
    <qaf:body-bottom-include />
  </body>
</html>
