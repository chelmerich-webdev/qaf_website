<%@page pageEncoding="UTF-8" contentType="text/html" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="filmDAO" class="com.queerartfilm.dao.FilmDAO" />
<c:set var="disabled" value="disabled" /><%--class=\"disabled\"" />--%>
<c:set var="selected" value="selected=\"selected\"" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/qaf_form.css">
        <script type="text/javascript" src="/qaf_form.js"></script>
        <qaf:head-include />

    </head>
    <body>
        <qaf:body-top-include />

        <c:choose>
            <c:when test="${event.assigned && empty form}">
                <c:set var="parentSeries" value="${qaf:getSeriesByKey(event.parentKey)}" />
                <div id="admin-message"><p class="nobold rev-rollover">
                        Part of the <a href="../series/update?id=${parentSeries.id}">${parentSeries}</a> series.</p>
                </div>
            </c:when>
            <c:otherwise>
                <p id="admin-message" class="${form.valid ? 'success' : 'error'}">${form.messages.result}</p>
            </c:otherwise>
        </c:choose>

        <%--<p id="admin-message" class="${form.valid ? 'success' : 'error'}">${form.messages.result}</p>--%>
        <div id="simple-page">
            <h4>Screening: ${param['id'] eq 'new' ? 'New' : event}</h4>
                <form method="post" action="/manage/events/update?id=${empty event.id || event.id <= 0 ? 'new' : event.id}">

                    <fieldset>
                        <legend>Date, Time, and Location</legend>
                        <label>Month and Year</label>
                        <c:choose>
                            <c:when test="${event.assigned}">
                                <div class="inputbox">
                                    <c:set var="field" value="month"/>
                                    <select name="display-${field}" size="1" disabled="disabled">
                                        <option selected="selected">${event[field]}</option>
                                    </select>
                                    <input type="hidden" name="${field}" value="${event[field]}"/>

                                    <c:set var="field" value="year" />
                                    <select name="display-${field}" size="1" disabled="disabled">
                                        <option selected="selected">${event[field]}</option>
                                    </select>
                                    <input type="hidden" name="${field}" value="${event[field]}"/>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="inputbox">
                                    <c:set var="field" value="month"/>
                                    <select name="${field}" size="1" tabindex="1"
                                            ${form.valid ? disabled : ''}>
                                        <c:forEach var="item" items="${monthList}">
                                            <c:choose>
                                                <c:when test="${event[field] eq item}">
                                                    <option selected="selected">${item}</option>
                                                </c:when>
                                                <c:otherwise>
                                                    <option>${item}</option>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:forEach>
                                    </select>

                                    <c:set var="field" value="year"/>
                                    <c:set var="seriesBeginYear" value="2008"/>
                                    <c:set var="seriesEndYear" value="2015" />
                                    <select name="${field}" size="1" tabindex="2" ${form.valid ? disabled : ''} >
                                        <c:forEach var="item" begin="${seriesBeginYear}" end="${seriesEndYear}">
                                            <option ${event[field] eq item ? selected : ''}>${item}</option>
                                        </c:forEach>
                                    </select>
                                    <span class="error">${form.messages['id']}</span>
                                </div>
                            </c:otherwise>
                        </c:choose>

                        <label>Day of the Month</label>
                        <div class="inputbox">
                            <c:set var="field" value="day"/>
                            <select name="${field}" size="1" tabindex="3"
                                    ${form.valid ? disabled : ''}>
                                <option value="">Choose a ${field}</option>
                                <c:forEach var="item" begin="1" end="31" step="1">
                                    <option ${event[field] eq item ? selected : ''}>${item}</option>
                                </c:forEach>
                            </select>
                                <span class="error">${form.messages[field]}</span>
                        </div>
                        <label>Screening Time</label>
                        <div id="time" class="inputbox">
                            <c:set var="field" value="hour"/>
                            <select name="${field}" size="1" tabindex="4" ${form.valid ? disabled : ''}>
                                <c:forEach var="item" begin="1" end="12" step="1" >
                                    <option value="${item}"${event[field] eq item ? selected : ''}>${qaf:doubleZero(item)}</option>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>

                            <strong>:</strong>

                            <c:set var="field" value="minute"/>
                            <select name="${field}" size="1" tabindex="5"
                                    ${form.valid ? disabled : ''}>
                                <c:forEach var="mins" begin="0" end="59" step="15" >
                                    <c:set var="item" value="${qaf:doubleZero(mins)}" />
                                    <option ${event[field] eq item ? selected : ''}>${item}</option>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>

                            <c:set var="field" value="ampm"/>
                            <select name="${field}" size="1" tabindex="6"
                                    ${form.valid ? disabled : ''}>
                                <c:forEach var="item" items="AM,PM" >
                                    <option ${event[field] eq item ? selected : ''}>${item}</option>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>
                        </div>

                        <c:set var="field" value="venue" />
                        <label for="${field}">Venue</label>
                        <div class="inputbox">
                            <select name="${field}" size="1" tabindex="7"
                                    ${form.valid ? disabled : ''} >
                                <c:forEach var="venue" items="${venueList}" >
                                    <option value="${venue.name}" ${event[field] eq item ? selected : ''}>${venue}</option>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>
                        </div>
                    </fieldset>


                    <fieldset>
                        <legend>Featured Film</legend>

                        <label>Film Title</label>
                        <div class="inputbox" >
                            <c:set var="field" value="filmKey"/>
                            <select name="${field}" size="1" tabindex="8" <%--${form.valid || event.assigned ? disabled : ''}--%>>
                                <c:forEach var="item" items="${filmDAO.allItems}" >
                                    <c:choose>
                                        <c:when test="${event[field].name eq item.id}">
                                            <option selected="selected" value="${item.id}">${item.title}</option>
                                        </c:when>
                                        <c:when test="${item.assigned eq false}">
                                            <option value="${item.id}">${item.title}</option>
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </select>
                            <span class="error">${form.messages[field]}</span>
                            <%--<c:if test="${event.assigned}">
                                <input type="hidden" value="${event[field].name}" />
                            </c:if>--%>
                        </div>

                        <c:set var="field" value="presenterFirst" />
                        <qaf:input name="${field}" label="Presenter's First Name"
                                   value="${fn:escapeXml(event[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   size="21" tabindex="9"/>

                        <c:set var="field" value="presenterLast" />
                        <qaf:input name="${field}" label="Presenter's Last Name"
                                   value="${fn:escapeXml(event[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   size="21" tabindex="10" />

                        <c:set var="field" value="synopsis" />
                        <label for="${field}">Synopsis</label>
                        <c:choose>
                            <c:when test="${!empty event[field]}">
                                <c:set var="text" value="${fn:escapeXml(event[field])}" />
                            </c:when>
                        </c:choose>
                        <div class="inputbox">
                            <textarea rows="7" cols="60" name="${field}"
                                      tabindex="11" ${form.valid ? disabled : ''}>${text}</textarea>
                            <br/><br/>
                            <span class="error">${form.messages[field]}</span>
                        </div>


                        <div class="inputbox">
                            <input type="submit" value="Submit" tabindex="12"
                                   class="submit" ${form.valid ? 'disabled' : ''} />
                            <input type="button" class="submit" tabindex="13"
                                   value="${form.valid ? 'Return' : 'Cancel'}"
                                   onclick="javascript:location.replace('/manage');" />
                        </div>
                    </fieldset>
                </form>
                <form method="post" action="/manage/events/delete?id=${event.id}" >
                    <div class="inputbox">
                        <c:if test="${!(event.assigned || form.valid || param.id eq 'new')}">
                            <input type="submit" value="Delete" class="submit delete" />
                        </c:if>
                    </div>
                </form>

                <script type="text/javascript">
                    setHighlight('${form != null ? form.highlight : ''}');
                    setFocus('${form != null ? form.focus : ''}');
                </script>
            </div>
            <qaf:body-bottom-include />
    </body>
</html>