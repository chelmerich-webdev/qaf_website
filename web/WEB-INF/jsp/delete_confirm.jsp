<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="qaf" uri="qafTags" %>
<html>
    <head>
        <qaf:head-include />
        <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.form.css']}" />
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
