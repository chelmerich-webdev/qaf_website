<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="featureDAO" class="com.queerartfilm.dao.FeaturedFilmDAO" scope="application"/>
<!DOCTYPE html>
<html>
  <head>
    <qaf:head-include />
    <link rel="stylesheet" type="text/css" href="${initParam['com.queerartfilm.form.css']}" />
    <script type="text/javascript" src="/qaf_form.js"></script>
  </head>
  <body>
    <qaf:body-top-include />
    <p id="admin-message" class="${form.valid ? 'success' : 'error'}">${form.messages.result}</p>
  
          <div id="simple-page">
      <h4>${initParam['com.queerartfilm.wordmark']} Series: ${param['id'] eq 'new' || empty param['id'] ? 'New' : qafSeries}</h4>

      <form method="post" action="/manage/qafseries/update?id=${empty qafSeries.id || qafSeries.id <= 0 ? 'new' : qafSeries.id}">
        <fieldset>
          <legend>Facts About the Series</legend>
          <c:set var="field" value="title" />
          <qaf:input name="${field}" label="Name of the Series"
                     value="${fn:escapeXml(qafSeries[field])}"
                     message="${form.messages[field]}"
                     disabled="${form.valid}"
                     class="${form.valid ? 'disabled' : ''}"
                     size="42"
                     tabindex="1"  />
        </fieldset>

        <label>Featured Films</label>
        <c:set var="field" value="filmKeys" />
        <div class="inputbox">
          <fieldset class="screenings">
            <ul>
              <c:forEach var="ff" items="${featureDAO.allItems}" varStatus="status">
                <%--<li>--%>
                <c:choose>

                  <%--if ff is has no parent, show it--%>
                  <c:when test="${ff['assigned'] eq false}" >
                    <li><input type="checkbox" name="${field}" id="ckbx${status.count}"
                               class="checkbox" value="${ff.id}"/>
                      <label for="ckbx${status.count}">${ff}</label></li>
                    </c:when>
                    <%--if ff has a parent, then see if it is this qafSeries--%>
                    <c:otherwise>
                      <%--if qafSeries has any children keys--%>
                      <c:if test="${fn:length(qafSeries.filmKeysAsListDesc) > 0}" >
                        <%--then for each one of these keys--%>
                        <c:forEach var="childKey" items="${qafSeries.filmKeysAsListDesc}" >
                          <%--see if its id is the same as the current assigned ff id--%>
                          <c:if test="${childKey.id eq ff.id}">
                            <%--if so, add as a selected checkbox--%>
                          <li><input type="checkbox" name="${field}" id="ckbx${status.count}"
                                     class="checkbox" value="${ff.id}" checked="checked"/>
                            <label for="ckbx${status.count}">${ff}</label></li>
                          </c:if>
                        </c:forEach>
                      </c:if>
                    </c:otherwise>
                  </c:choose>
                  <%--</li>--%>
                </c:forEach>
            </ul>
          </fieldset>
          <span class="error clear">${form.messages[field]}</span>
        </div>

        <div class="inputbox">
          <input type="submit" value="Submit"
                 class="submit" ${form.valid ? 'disabled' : ''} />
          <%--                    <input type="button" class="submit"
                                     value="${form.valid ? 'Return' : 'Cancel'}"
                                     onclick="javascript:location.replace('/manage');" />--%>
          <a href="/manage">
            <input value="${form.valid ? 'Return' : 'Cancel'}"
                   type="button" class="submit" id="cancel"/>
          </a>
        </div>
      </form>

      <form method="post" action="/manage/qafseries/delete?id=${qafSeries.id}" >
        <div class="inputbox" id="delete">
          <c:if test="${!(form.valid || param.id eq 'new' || empty param.id)}">
            <input type="submit" value="Delete" class="submit delete" />
          </c:if>
        </div>
      </form>
          <p class="withoutLabel">Note: Selecting films for this series will publish the films' pages to ALL FILMS.<br>You may want to verify first that those pages' data and images are up-to-date.</p>
    </div>
    <script type="text/javascript">
      setHighlight('${form != null ? form.highlight : ''}');
      setFocus('${form != null ? form.focus : ''}');
    </script>
    <qaf:body-bottom-include />
  </body>
</html>