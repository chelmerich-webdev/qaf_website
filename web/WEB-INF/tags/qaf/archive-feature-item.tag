<%@tag isELIgnored="false" pageEncoding="UTF-8"%>
<%@attribute name="feature" required="true" rtexprvalue="true" type="com.queerartfilm.film.FeaturedFilm" %>
<a href="${menusMap['archive'][1]}/${feature.urlKey}"><img width="180" height="86" src="/images/${feature.urlKey}_large.png"</a>
<h6 class="redtext">${feature.presenter} presents</h6>
<h6><a class="qaf-film" href="${menusMap['archive'][1]}/${feature.urlKey}">${feature.title}</a></h6>