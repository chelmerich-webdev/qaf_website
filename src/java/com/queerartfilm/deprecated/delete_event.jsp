<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib prefix="qaf" uri="qafTags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="qaf" uri="qafTags" %>
<html>
    <head>
        <qaf:head-include />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="simple-page">
            <div id="content" >
                <ul>
                <c:forEach var="item" items="${dao.allItems}">
                    <li><a href="/manage/events/delete?id=${item.id}">${item.id}</a></li>
                </c:forEach>
                </ul>
            </div>
        </div>
        <qaf:body-bottom-include />
    </body>
</html>
