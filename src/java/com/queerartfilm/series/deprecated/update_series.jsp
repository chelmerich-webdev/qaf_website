<%@page contentType="text/html" pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="qaf" uri="qafTags" %>
<jsp:useBean id="eventDAO" class="com.queerartfilm.dao.EventDAO" scope="request"/>
<html>
    <head>
        <qaf:head-include />
        <link type="text/css" rel="stylesheet" href="/qaf_form.css">
        <script type="text/javascript" src="/qaf_form.js"></script>
    </head>
    <body>
        <qaf:body-top-include />
        <p id="admin-message" class="${form.valid ? 'success' : 'error'}">${form.messages.result}</p>
        <div id="simple-page">
            <h4>Series: ${param['id'] eq 'new' ? 'New' : series}</h4>
                <form method="post" action="/manage/series/update?id=${empty series.id || series.id <= 0 ? 'new' : series.id}">
                    <fieldset>
                        <legend>Facts About the Series</legend>
                        <c:set var="field" value="title" />
                        <qaf:input name="${field}" label="Name of the Series"
                                   value="${fn:escapeXml(series[field])}"
                                   message="${form.messages[field]}"
                                   disabled="${form.valid}"
                                   class="${form.valid ? 'disabled' : ''}"
                                   size="42"
                                   required="true"
                                   tabindex="1"  />
                    </fieldset>

                    <label>Available Screenings</label>
                    <c:set var="field" value="events" />
                    <div class="inputbox">
                        <fieldset class="screenings">
                            <%--Loop thru all event items--%>
                            <c:forEach var="event" items="${eventDAO.allItems}" varStatus="status">

                                <c:choose>
                                    <%--if event is unassigned, go ahead and display as a choice--%>
                                    <c:when test="${!event.assigned}">
                                        <input type="checkbox" name="${field}" id="ckbx${status.count}"
                                               class="checkbox" value="${event.id}"/>
                                        <label for="ckbx${status.count}">${event}</label>
                                    </c:when>
                                        <%--else event is assigned, so display as checked--%>
                                        <%--if it is part of this series' event list--%>
                                    <c:otherwise>
                                        <c:forEach var="chosenEvent" items="${series[field]}">
                                            <c:if test="${event.id eq chosenEvent.id}">
                                                    <input type="checkbox" name="${field}" id="ckbx${status.count}"
                                                           class="checkbox" value="${event.id}" checked="checked"/>
                                                    <label for="ckbx${status.count}">${event}</label>
                                            </c:if>
                                        </c:forEach>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </fieldset>
                        <span class="error clear">${form.messages[field]}</span>
                    </div>


<%--                                <c:forEach var="chosenEvent" items="${series[field]}">
                                    <c:choose>
                                        <c:when test="${event.id eq chosenEvent.id}">
                                            <input type="checkbox" name="${field}" id="ckbx${status.count}"
                                                   class="checkbox" value="${event.id}" checked="checked"/>
                                            <label for="ckbx${status.count}">${event}</label>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${!event.assigned}">
                                                <input type="checkbox" name="${field}" id="ckbx${status.count}"
                                                       class="checkbox" value="${event.id}"/>
                                                <label for="ckbx${status.count}">${event}</label>
                                            </c:if>
                                        </c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </c:forEach>--%>

                    <%--
                     <label>Selected Screenings</label>
                     <div class="inputbox">
                         <fieldset class="screenings">
                             first, get all the ones existing as checked
                             <c:set var="field" value="events" />
                             <c:forEach var="key" items="${series.events}" varStatus="status">
                                 <input type="checkbox" name="events" class="checkbox" id="ckbxA${status.count}"
                                        value="${key.id}" checked="checked" />
                                 <label for="ckbxA${status.count}">${qaf:getEventByKey(key)}</label>
                             </c:forEach>
                         </fieldset></div>
                     <label>Available Screenings</label>
                     <div class="inputbox">
                         <fieldset class="screenings">
                             Then get any eligible events for more choices
                             <c:forEach var="event" items="${eventDAO.allItems}" varStatus="status">
                                 <c:if test="${!event.assigned}">
                                     <input type="checkbox" name="events" id="ckbxB${status.count}"
                                            class="checkbox" value="${event.id}"/>
                                     <label for="ckbxB${status.count}">${event}</label>
                                 </c:if>
                             </c:forEach>
                         </fieldset>
                         <span class="error clear">${form.messages[field]}</span>
                     </div>--%>

                    <div class="inputbox">
                        <input type="submit" value="Submit" tabindex="12"
                               class="submit" ${form.valid ? 'disabled' : ''} />
                        <input type="button" class="submit" tabindex="13"
                               value="${form.valid ? 'Return' : 'Cancel'}"
                               onclick="javascript:location.replace('/manage');" />
                    </div>
                </form>
                <form method="post" action="/manage/series/delete?id=${series.id}" >
                    <div class="inputbox">
                        <c:if test="${!(form.valid || param.id eq 'new')}">
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