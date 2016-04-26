<%@tag description="standard web page template for top of <body> tag"
       pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="redbar">&nbsp;</div>
<div id="container">
  <div id="nav"><a href="/">
        <img id="logo" src="/images/qaf_logo.png" alt="Queer Art Film Logo" /></a><br/>
        <ul id="menu">
<%--          <c:set var="labels" value="${fn:split(initParam['com.queerartfilm.menu.keys'], ',')}" />
            <c:forEach var="item" items="${labels}" varStatus="status">
                <li><a href="${menuMap[item]}">${item eq 'archive' ? 'all films' : item}</a></li>
            </c:forEach>--%>
            <c:forEach var="key" items="${initParam['com.queerartfilm.menu.keys']}">
              <li><a href="${menusMap[key][1]}">${menusMap[key][0]}</a></li>
            </c:forEach>
        </ul>
        <img id='hbo-logo' src='/images/logo-hbo-100x56.png' alt='HBO' />
    </div>
    <div id="main">
<%-- These closing <div> tags are located in the <qaf:body-bottom-include> tag
    </div>
</div>--%>