<%@tag isELIgnored="false" pageEncoding="UTF-8"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<%@attribute name="feature" required="true" rtexprvalue="true" type="com.queerartfilm.film.FeaturedFilm" %>
<qaf:film-link film="${feature.title}" label="<img width='180' height='86' src='/images/${feature.urlKey}_large.png' />" />
<h6 class="redtext">${feature.presenter} presents</h6>
<h6><qaf:film-link film="${feature.title}" /></h6>