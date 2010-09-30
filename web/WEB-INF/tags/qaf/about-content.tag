<%@tag description="put the tag description here" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="application"/>
<div id="about">
<p>${initParam['com.queerartfilm.wordmark']} is a monthly series held at the IFC Center in downtown, New York City. Each month, we invite one of the city’s most unique and homosexual artists to pick a film that has most inspired them, and present it to an audience. Afterwards, there’s an informal discussion, and then we often end up down the street at <a href="http://juliusbarnyc.com/">Julius’s</a>, the oldest gay bar in NYC.  Co-curated by Butt magazine editor Adam Baran and filmmaker Ira Sachs, ${initParam['com.queerartfilm.wordmark']} is intimate, provocative, inspiring.....and like nothing else out there.</p>

<p>Founded in June, 2009, ${initParam['com.queerartfilm.wordmark']} has been honored to present the following artists and films:</p>
<ul>
  <c:forEach var="ff" items="${featureDAO.allItems}">
    <li class="rollover">
      ${ff.presenter} presents <qaf:film-link film="${ff.title}"/> (${ff.director})
    </li>
  </c:forEach>
</ul>
<p>For showtimes, tickets and more information, visit: <a href="http://www.ifccenter.com/series/queerartfilm">ifccenter.com/series/queerartfilm</a></p>

<p>IFC Center 323 Avenue of the Americas at West 3rd Street,<br/>
  box office: 212 924-7771. </p>
</div>
