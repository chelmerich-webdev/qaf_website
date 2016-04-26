<%@tag description="standard for footer"
       pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div id="footer1" >
  <!-- Begin MailChimp Signup Form -->
  <div id="mc_embed_signup" class="left">
  <form action="http://queerartfilm.us2.list-manage1.com/subscribe/post?u=c860069378afdd2978d546b65&id=3843494149" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" class="validate" target="_blank">
        <label for="mce-EMAIL">Subscribe to our mailing list</label>
        <input type="email" value="" name="EMAIL" class="email" id="mce-EMAIL" placeholder="email address" required>
        <input type="submit" value="Subscribe" name="subscribe" id="mc-embedded-subscribe" class="submit button">
  </form>
  </div>
<!--End MailChimp Signup Form -->
  <a class="right" href='<c:out value="${current.facebookUrl}"/>' ><img src="/images/f_logo.png" alt="Facebook"/></a><p class="right">Friend us on Facebook</p>
</div>
<div id="footer2">
  <%--Remove footer menu per client--%>
    <ul class="left">
        <li>&copy; ${initParam['com.queerartfilm.wordmark']}</li>
    </ul>
    <span class="right gray-rollover">
      Site by <a href="http://www.shaneluitjens.com">Torquere Creative</a>
      &amp; <a href="http://www.linkedin.com/in/curthelmerich">Curt Helmerich</a></span>
</div>
</div></div>
