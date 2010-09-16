<%@tag isELIgnored="false" pageEncoding="UTF-8"%>
<%@attribute name="feature" required="true" rtexprvalue="true" type="com.queerartfilm.film.FeaturedFilm" %>
<a href="${menuMap['archive']}/${feature.urlKey}"><img width="180" height="86" src="/images/${feature.urlKey}_large.png"</a>
<h6 class="redtext">${feature.presenter} presents</h6>
<h6><a href="${menuMap['archive']}/${feature.urlKey}">${feature.title}</a></h6>