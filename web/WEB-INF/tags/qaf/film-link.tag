<%@tag description="Link to a Featured Film's webpage" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@attribute name="film" required="true" rtexprvalue="true" %>
<%@attribute name="label" required="false" rtexprvalue="true" %>
<a class="qaf-film" href="${menusMap['archive'][1]}/${qaf:createId(film)}">${empty label ? film : label}</a>