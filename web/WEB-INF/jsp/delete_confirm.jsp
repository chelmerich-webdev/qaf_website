<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<!DOCTYPE html>
<html>
    <head>
        <qaf:head-include />
        <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.css.formw']}" />
    </head>
    <body>
        <qaf:body-top-include />
        <div id="simple-page">
            <h4>Delete Record</h4>
            <p>${message}</p><br/>
            <a href="/manage">
            <input type="button" value="Return" class="submit"/></a>
        </div>
        <qaf:body-bottom-include />
    </body>
</html>
