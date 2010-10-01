<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<%@include file="/WEB-INF/jsp/includes.jsp" %>
<jsp:useBean id="feature" class="com.queerartfilm.film.FeaturedFilm" scope="request" />
<jsp:useBean id="seriesDAO" class="com.queerartfilm.dao.QAFSeriesDAO" />
<c:set var="disabled" value="disabled=\"disabled\" class=\"disabled\""/>
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
      <h4>Featured Film: ${param['id'] eq 'new' || empty param['id'] ? 'New' : feature.title}</h4>
      <form method="post" action="/manage/features/update?id=${empty feature.id || feature.id <= 0 ? 'new' : feature.id}">

        <fieldset>
          <legend>Film Information</legend>

          <label>Film Title</label>
          <div class="inputbox">
            <c:set var="field" value="title"/>
            <input type="text" name="${field}" id="${field}" size="49"
                   value="${feature[field]}" ${form.valid ? disabled : ''} />
            <span class="error">${form.messages[field]}</span>
          </div>

          <label>Director First and Last</label>
          <div class="inputbox">
            <c:set var="parentfield" value="director" />
            <c:set var="childfield" value="first" />
            <c:set var="field">${parentfield}.${childfield}</c:set>
            <input type="text" name="${field}" id="${field}" size="21"
                   value="${feature[parentfield][childfield]}" ${form.valid ? disabled : ''} />

            <c:set var="childfield" value="last" />
            <c:set var="field">${parentfield}.${childfield}</c:set>
            <input type="text" name="${field}" id="${field}" size="21"
                   value="${feature[parentfield][childfield]}" ${form.valid ? disabled : ''} />
            <span class="error">${form.messages[field]}</span>
          </div>

          <label>Year Released</label>
          <div class="inputbox">
            <c:set var="field" value="releaseYear" />
            <c:set var="combofield" value="${field}" />
            <input type="text" name="${field}" id="${field}" size="7" maxlength="4"
                   value="${feature[field]}" ${form.valid ? disabled : ''} />
            <%--<span class="error">${form.messages[field]}</span>--%>

            <label>Length</label>
            <c:set var="field" value="length" />
            <c:set var="combofield" value="${combofield}-${field}" />
            <input type="text" name="${field}" id="${field}" size="7" maxlength="3"
                   value="${feature[field]}" ${form.valid ? disabled : ''} /> minutes
            <%--<span class="error">${form.messages[field]}</span>--%>

            <label>Rating</label>
            <c:set var="field" value="rating" />
            <c:set var="combofield" value="${combofield}-${field}" />
            <select name="${field}" size="1" ${form.valid ? disabled : ''}>
              <c:forEach var="rating" items="${ratingsList}" >
                <c:choose>
                  <c:when test="${feature.rating eq rating}">
                    <option value="${rating.name}" selected="selected">${rating}</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${rating.name}">${rating}</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
            <span class="error">${form.messages[combofield]}</span>
          </div>
        </fieldset>



        <fieldset>
          <legend>Series Information</legend>
          <label>Series</label>
          <div class="inputbox">
            <c:set var="field" value="seriesKey" />
            <c:set var="series" value="${qaf:getQAFSeriesByKey(feature[field])}"/>
            <input type="text" name="dispaly-${field}" disabled class="${form.valid ? 'disabled' : ''}"
                   value="${empty series ? 'None' : series}" size="21" />
            <input type="hidden" name="${field}"  value="${series.id}" />
          </div>

          <%--Presenter--%>
          <label>Presenter First and Last</label>
          <div class="inputbox">
            <c:set var="parentfield" value="presenter" />
            <c:set var="childfield" value="first" />
            <c:set var="field">${parentfield}.${childfield}</c:set>
            <input type="text" name="${field}" id="${field}" size="21"
                   value="${feature[parentfield][childfield]}" ${form.valid ? disabled : ''} />

            <c:set var="childfield" value="last" />
            <c:set var="field">${parentfield}.${childfield}</c:set>
            <input type="text" name="${field}" id="${field}" size="21"
                   value="${feature[parentfield][childfield]}" ${form.valid ? disabled : ''} />
            <span class="error">${form.messages[field]}</span>
          </div>
          <%--Synopsis--%>
          <label>Synopsis / Review</label>
          <c:set var="field" value="synopsis" />
          <c:choose>
            <c:when test="${!empty feature[field]}">
              <c:set var="text" value="${fn:escapeXml(feature[field])}" />
            </c:when>
          </c:choose>
          <div class="inputbox">
            <textarea rows="7" cols="60" name="${field}"
                      ${form.valid ? disabled : ''}>${text}</textarea>
            <br/><br/>
            <span class="error">${form.messages[field]}</span>
          </div>
        </fieldset>


        <%--Extnernal Links--%>
        <fieldset>
          <legend>External Links</legend>
          <c:set var="parentfield" value="links" />
          <c:set var="keyfield" value="label" />
          <c:set var="valuefield" value="url" />
          <p class="input-list-header">Label <span id="col2">URL or YouTube Video ID</span></p>
          <ul class="inputbox">
            <c:if test="${!form.valid}" >


              <%--<div class="inputbox" >--%><li>
                <label>New Link</label>
                <input type="text" name="${parentfield}.${keyfield}" id="${parentfield}.${keyfield}"
                       size="28" value="" ${form.valid ? disabled : ''} />
                <input type="text" name="${parentfield}.${valuefield}" id="${parentfield}.${valuefield}"
                       size="35" value="" ${form.valid ? disabled : ''} />

                <%--</div>--%></li>
              </c:if>
              <c:forEach var="item" items="${feature.links}" varStatus="status">
              <li><label>Link #${fn:length(feature.links) - status.count + 1}</label>
                <%--<div class="inputbox">--%>
                <input type="text" name="${parentfield}.${keyfield}" id="${parentfield}.${keyfield}"
                       size="28" value="${item.label}" ${form.valid ? disabled : ''} />
                <input type="text" name="${parentfield}.${valuefield}" id="${parentfield}.${valuefield}"
                       size="35" value="${item.url}" ${form.valid ? disabled : ''} />
              </li>
              <c:if test="${fn:length(feature.links) eq status.count && !form.valid}">
                <li><span class=" clear withoutLabel"><em>To remove a link, delete its label and save.</em></span></li>
              </c:if>
              <%--</div>--%>
            </c:forEach>
          </ul>
        </fieldset>

        <fieldset>
          <legend>Screening Information</legend>
          <c:set var="parentfield" value="screening" />

          <label> Purchase Tickets URL</label>
          <div class="inputbox" >
            <c:set var="childfield" value="purchaseUrl" />
            <c:set var="field">${parentfield}.${childfield}</c:set>
            <input type="text" name="${field}" id="${field}" size="49"
                   value="${feature[parentfield][childfield]}" ${form.valid ? disabled : ''} />
            <span class="error">${form.messages[field]}</span>
          </div>


          <label>Date</label>
          <div class="inputbox">
            <%--Day--%>
            <c:set var="field" value="day"/>
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''}>
              <c:forEach var="item" begin="1" end="31" step="1">
                <option ${qaf:getDay(feature.screening.date) eq item ? 'selected' : ''}>${item}</option>
              </c:forEach>
            </select>

            <%--Month--%>
            <c:set var="field" value="month"/>
            <label>Month</label>
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''}>
              <c:forEach var="item" items="${monthList}">
                <option ${qaf:getMonth(feature.screening.date) eq item ? 'selected' : ''}>${item}</option>
              </c:forEach>
            </select>

            <%--Year--%>
            <c:set var="field" value="year"/>
            <label>Year</label>
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''}>
              <c:forEach var="item" begin="${initParam['com.queerartfilm.startyear']}" end="${current.year + 1}" step="1">
                <option ${qaf:getYear(feature.screening.date) eq item ? 'selected' : ''}>${item}</option>
              </c:forEach>
            </select>
            <span class="error">${form.messages[parentfield]}</span>
          </div>


          <label>Time and Venue</label>
          <div class="inputbox">

            <%--Hour--%>
            <c:set var="field" value="hour"/>
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''}>
              <option value="">${fn:toUpperCase(field)}</option>
              <c:forEach var="item" begin="1" end="12" step="1" >
                <option ${qaf:getHour(feature.screening.date) eq item ? 'selected' : ''}>${item}</option>
              </c:forEach>
            </select>

            <span><strong>:</strong></span>

            <%--Minute--%>
            <c:set var="field" value="minute"/>
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''}>
              <c:set var="mins" value="${qaf:getMinute(feature.screening.date)}"/>
              <c:forEach var="item" begin="0" end="45" step="15" >
                <option ${mins - (mins % 15) eq item ? 'selected' : ''}>${qaf:doubleZero(item)}</option>
              </c:forEach>
            </select>

            <%--AM PM--%>
            <c:set var="field" value="ampm"/>
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''}>
              <c:forEach var="item" items="PM,AM" >
                <option ${qaf:getAmpm(feature.screening.date) eq item ? 'selected' : ''}>${item}</option>
              </c:forEach>
            </select>

            <span><strong> at </strong></span>

            <%--Venue--%>
            <c:set var="field" value="venue" />
            <select name="${parentfield}.${field}" size="1" ${form.valid ? disabled : ''} >
              <c:forEach var="item" items="${venueList}" >
                <option value="${item.name}" ${feature.screening.venue.name eq item ? 'selected' : ''}>${item}</option>
              </c:forEach>
            </select>
            <span class="error">${form.messages['screening.venue']}</span>
          </div>

          <%--Second Screening Time--%>
          <c:set var="field" value="secondTime" />
          <label>Second Screening Time</label>
          <div class="inputbox">
          <input  name="${parentfield}.${field}" id="${parentfield}.${field}"
                  ${form.valid ? disabled : ''}
                  size ="7" value="${feature[parentfield][field]}" /> ex. 10:00PM
                  <span class="error">${form.messages['screening.secondTime']}</span>
          </div>


        </fieldset>

        <div class="inputbox">
          <input type="submit" value="Submit"
                 class="submit ${form.valid ? 'disabled' : ''}" ${form.valid ? 'disabled' : ''} />
          <a href="/manage">
            <input value="${form.valid ? 'Return' : 'Cancel'}"
                   type="button" class="submit "/>
          </a>
        </div>
      </form>

      <form method="post" action="/manage/features/delete?id=${qafSeries.id}" >
        <div class="inputbox" id="delete">
          <c:if test="${!(form.valid || param.id eq 'new' || empty param.id)}">
            <input type="submit" value="Delete" class="submit delete" />
          </c:if>
        </div>
      </form>
    </div>

    <script type="text/javascript">
      setHighlight('${form != null ? form.highlight : ''}');
      setFocus('${form != null ? form.focus : ''}');
    </script>
    <qaf:body-bottom-include />
  </body>
</html>
