<%@tag isELIgnored="false" pageEncoding="UTF-8"%>
<%--<%@taglib prefix="qaf" uri="qafTags" %>--%>
<%@attribute name="film" required="true" rtexprvalue="true" type="com.queerartfilm.film.Film"%>
<%@attribute name="event" required="true" rtexprvalue="true" type="com.queerartfilm.event.Event"%>

<a href="${menuMap['archive']}/${film.id}"><img src="/images/${film.id}_small.png"</a>
<h6 class="redtext">${event.presenterFirst} ${event.presenterLast} presents</h6>
<h6><a href="${menuMap['archive']}/${film.id}">${film.title}</a></h6>