<%@tag isELIgnored="false" pageEncoding="UTF-8"%>
<%@attribute name="link" required="true" rtexprvalue="true" type="com.queerartfilm.film.Link"%>
<object width="200" height="175">
  <param name="movie" value="http://www.youtube.com/v/${link.url}?fs=1&amp;hl=en_US&amp;rel=0"></param>
  <param name="allowFullScreen" value="true"></param><param name="allowscriptaccess" value="always"></param>
  <embed src="http://www.youtube.com/v/${link.url}?fs=1&amp;hl=en_US&amp;rel=0" type="application/x-shockwave-flash" allowscriptaccess="always" allowfullscreen="true" width="200" height="137"></embed>
</object>
  <p><a href="http://www.youtube.com/watch?v=${link.url}">${link.label}</a></p>