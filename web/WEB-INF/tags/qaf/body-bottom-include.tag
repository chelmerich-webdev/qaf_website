<%@tag description="standard for footer"
       pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="maillink">
  mailto:${current.subscribeEmailAddress}?subject=${current.subscribeEmailSubject}&body=${current.subscribeEmailBody}
</c:set>
<%--  <c:url var="mailtolink" value="mailto:${current.subscribeEmailAddress}">
    <c:param  name="subject" value="${current.subscribeEmailSubject}"/>
    <c:param name="body" value="${current.subscribeEmailBody}" />
  </c:url>--%>
<div id="footer1" >
  <!-- Begin MailChimp Signup Form -->
<%--<link href="http://cdn-images.mailchimp.com/embedcode/slim-081711.css" rel="stylesheet" type="text/css">--%>
<style type="text/css">
	#mc_embed_signup{background:#fff; clear:left;  }
        #mc_embed_signup form {margin-top: 0; font-size: 80%; }
        #mc_embed_signup label {padding-right: 5px; font-weight: normal; text-align: left; width: auto;vertical-align: top;}
        #mc_embed_signup input {margin-top: -4px; padding: 0 5px; border: 1px solid #999999; width: 200px; height: 20px;vertical-align: top;}
        #mc_embed_signup .submit.button {width: 90px; height: 22px; background: #ccc; color:#333;}
	/* Add your own MailChimp form style overrides in your site stylesheet or in this style block.
	   We recommend moving this block and the preceding CSS link to the HEAD of your HTML file. */
</style>
<div id="mc_embed_signup" class="left">
<form action="http://queerartfilm.us2.list-manage1.com/subscribe/post?u=c860069378afdd2978d546b65&amp;id=e08f631633" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" class="validate" target="_blank">
	<label for="mce-EMAIL">Subscribe to our mailing list</label>
	<input type="email" value="" name="EMAIL" class="email" id="mce-EMAIL" placeholder="email address" required>
	<input type="submit" value="Subscribe" name="subscribe" id="mc-embedded-subscribe" class="submit button">
</form>
</div>

<!--End mc_embed_signup-->
  <%--<p class="left">For email updates, <strong><a href='<c:out value="${fn:replace(maillink,' ','%20')}" />' >join the ${initParam['com.queerartfilm.wordmark']} mailing list</a></strong></p>--%>
    <a class="right" href='<c:out value="${current.facebookUrl}"/>' ><img src="/images/f_logo.png" alt="Facebook"/></a><p class="right">Friend us on Facebook</p>
<%--    <form id="mailing" name="mailing" action="" method="POST">
        <input type="text" name="email" id="email"/>
        <input type="hidden" name="return-page" value="${pageContext.request.requestURI}"/>
        <input type="submit" class="submit" value="submit" />
        <span class="rollover submit">
            <a href="#" onclick='javascript:document.mailing.submit();'>Submit</a>
        </span>
    </form>--%>
</div>
<div id="footer2">
  <%--Remove footer menu per client--%>
    <ul class="left">
        <li>&copy; 2010 ${initParam['com.queerartfilm.wordmark']}</li>
<%--                        <c:forEach var="key" items="${initParam['com.queerartfilm.menu.keys']}">
              <li><a href="${menusMap[key][1]}">${menusMap[key][0]}</a></li>
            </c:forEach>--%>
    </ul>
    <span class="right gray-rollover"><%--&copy; 2010 | <a href="${logInOutLink}">${logInOutLabel}</a>--%>
      Site by <a href="http://www.shaneluitjens.com">Torquere Creative</a>
      &amp; <a href="http://www.curthelmerich.net">curthelmerich.net</a></span>
</div>
</div></div>
