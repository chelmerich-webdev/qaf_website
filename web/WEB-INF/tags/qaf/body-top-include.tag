<%@tag description="standard web page template for top of <body> tag"
       pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="redbar">&nbsp;</div>
<div id="container">
  <div id="nav"><a href="/">
        <img id="logo" src="/images/qaf_logo.png" alt="Queer Art Film Logo" /></a><br/>
        <ul id="menu">
          <c:set var="labels" value="${fn:split(initParam['com.queerartfilm.menu.labels'], ',')}" />
            <c:forEach var="item" items="${labels}" varStatus="status">
                <li><a href="${menuMap[item]}">${item}</a></li>
            </c:forEach>
        </ul>
    </div>
    <div id="main">
<%-- These closing <div> tags are located in the <qaf:body-bottom-include> tag
    </div>
</div>--%>