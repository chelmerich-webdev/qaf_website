<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@page import="com.google.appengine.api.users.*" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<!DOCTYPE html>
<html>
    <head>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="simple-page">
            <h1>Please Login</h1>
        <%
                    UserService userService = UserServiceFactory.getUserService();
                    if (!userService.isUserLoggedIn()) {
        %>
        <p>Click here to <a href="
                             <%=userService.createLoginURL("/login.jsp") %>">log in</a></p>



        <% } else { %>
                  Welcome, <%= userService.getCurrentUser().getNickname() %>!
                  (<a href="<%=userService.createLogoutURL("/")%>">log out</a>)

                  <br/>

            <% if (userService.isUserAdmin()) {%>
                You are an Administrator!
            <% }%>
                              
        <% } %>
        </div>
                  <qaf:body-bottom-include />
</body>
</html>
