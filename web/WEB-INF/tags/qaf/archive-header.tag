<%@tag description="Render film detail header section" pageEncoding="UTF-8" 
       isELIgnored="false" body-content="empty"%>
<%@attribute name="film" required="true" rtexprvalue="true" 
             type="com.queerartfilm.film.FeaturedFilm" %>

<h3><a href="${menusMap['archive'][1]}">${menusMap['archive'][0]}</a></h3>
<img src="/images/${film.urlKey}_large.png" alt="${film.title}" height="240px" />
<div id="listing-header-text">
  <p class="redtext nobold">${film.presenter}<br/>presents</p>
  <h1>${film.title}</h1>
  <h6>(${film.releaseYear}, ${film.director})</h6>
</div>
<div id="listing-header-shadow">
  <h1>${film.title}</h1>
  <h6>&nbsp;</h6>
</div>