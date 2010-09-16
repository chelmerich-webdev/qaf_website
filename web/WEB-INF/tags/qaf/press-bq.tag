<%@tag description="put the tag description here" pageEncoding="UTF-8" isELIgnored="false"%>
<%@attribute name="author" required="true" rtexprvalue="true" %>
<%@attribute name="publication" required="true" rtexprvalue="true" %>
<%@attribute name="url" required="true" rtexprvalue="true" %>
<blockquote><jsp:doBody/></blockquote><p class="source">&mdash; <a href="${url}">${author}, <cite>${publication}</cite></a></p>
