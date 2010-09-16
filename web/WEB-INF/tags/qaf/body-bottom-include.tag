<%@tag description="standard for footer"
       pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="maillink">
  mailto:${current.subscribeEmailAddress}?subject=${current.subscribeEmailSubject}&body=${current.subscribeEmailBody}
</c:set>
<div id="footer1" >
  <p class="left">For email updates, <a href="${maillink}">join the ${initParam['com.queerartfilm.wordmark']} mailing list</a></p>
    <a class="right" href="${current.facebookUrl}"><img src="/images/f_logo.png" /></a><p class="right">Friend us on Facebook</p>
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
    <ul class="left">
        <li>${initParam['com.queerartfilm.wordmark']}: </li>
        <c:forEach var="item" items="${menuMap}" varStatus="status">
            <li class="gray-rollover"><a href="${item.value}">${item.key}</a></li>
        </c:forEach>
    </ul>
    <span class="right gray-rollover">&copy; 2010<%--| <a href="${logInOutLink}">${logInOutLabel}</a>--%> | Design by <a href="http://www.shaneluitjens.com">Torquere Creative</a></span>
</div>
</div></div>