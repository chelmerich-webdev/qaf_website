<%@tag description="put the tag description here" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="application"/>
<div id="about">
<p>${initParam['com.queerartfilm.wordmark']} is a monthly series held at the IFC Center in downtown, New York City. Each month, we invite one of the city’s most unique and homosexual artists to pick a film that has most inspired them, and present it to an audience. Afterwards, there’s an informal discussion, and then we often end up down the street at <a href="http://juliusbarnyc.com/">Julius’s</a>, the oldest gay bar in NYC.  Co-curated by Butt magazine editor Adam Baran and filmmaker Ira Sachs, ${initParam['com.queerartfilm.wordmark']} is intimate, provocative, inspiring.....and like nothing else out there.</p>

<p>Founded in June, 2009, ${initParam['com.queerartfilm.wordmark']} has been honored to present the following artists and films:</p>
<ul>
  <c:forEach var="ff" items="${featureDAO.allItems}">
    <li class="rollover">
      ${ff.presenter} presents <a class="qaf-film" href="${menusMap['archive'][1]}/${ff.urlKey}">${ff.title}</a> (${ff.director})
    </li>
  </c:forEach>
</ul>
<p>For showtimes, tickets and more information, visit: <a href="http://www.ifccenter.com/series/queerartfilm">ifccenter.com/series/queerartfilm</a></p>

<p>IFC Center 323 Avenue of the Americas at West 3rd Street,<br/>
  box office: 212 924-7771. </p>
</div>
  <%--   <li><p>John Kelly presents BLOOD OF A POET (Jean Cocteau)
          </p></li><li><p>Matt Wolf presents BLUE (Derek Jarman)
          </p></li><li><p>Jennie Livingston presents 8 1/2 (Federico Fellini)
          </p></li><li><p>Thomas Allen Harris presents TONGUES UNTIED  (Marlon Riggs)
          </p></li><li><p>Everett Quinton presents SO EVIL MY LOVE (Lewis Allen)
          </p></li><li><p>John Cameron Mitchell presents Entertaining Mr. Sloane (Douglas Hickox)
          </p></li><li><p>Angela Dufresne presents TROPICAL MALADY (Apichatpong Weerasethakul)
          </p></li><li><p>Hilton Als presents THE GROUP (Sidney Lumet)
          </p></li><li><p>Justin Bond presents THE DEVILS (Ken Russell)
          </p></li><li><p>Antony Hegarty presents THE FILMS OF CHARLES LUDLAM
          </p></li><li><p>Rodney Evans presents PORTRAIT OF JASON (Shelley Clarke)
          </p></li><li><p>Barbara Hammer presents BORN IN FLAMES (Lizzie Borden)
          </p></li><li><p>Ridykeulous presents TIMES SQUARE (Allan Moyle)
          </p></li><li><p>Wayne Koestenbaum presents MADEMOISELLE (Tony Richardson)
          </p></li><li><p>Joe Gage presents FASTER, PUSSYCAT! KILL! KILL! (Russ Meyer)
          </p></li><li><p>Zihan Loo presents FUNERAL PARADE OF ROSES (Toshio Matsumoto)
          </p></li><li><p>Douglas Crimp presents THE FILMS OF ACT UP
          </p></li>--%>
