<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<!DOCTYPE html>
<html>
    <head>
        <qaf:head-include />
        <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.simple.css']}" />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="simple-page">
            <h4>${title}</h4>
                <c:choose>
                    <c:when test="${fn:containsIgnoreCase(title, 'about')}">
                        <qaf:about-content />
                    </c:when>
                    <c:when test="${fn:containsIgnoreCase(title, 'contact')}">
                        <qaf:contact-content />
                    </c:when>
                    <c:when test="${fn:containsIgnoreCase(title, 'press')}">
                        <qaf:press-content />
                    </c:when>
                    <c:otherwise>

                    </c:otherwise>
                </c:choose>
            </div>
        <qaf:body-bottom-include />
    </body>
</html>
