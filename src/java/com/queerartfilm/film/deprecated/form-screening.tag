<%@tag description="screening subform" pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="qaf" uri="qafTags" %>
<%@attribute name="screening" required="true" rtexprvalue="true" type="com.queerartfilm.film.Screening" %>
<%@attribute name="form" required="true" rtexprvalue="true" type="com.queerartfilm.film.FilmFormV" %>
<fieldset>
    <legend>Screening Information</legend>

    <label>Date</label>
    <div class="inputbox">

        <%--Day--%>
        <c:set var="field" value="day"/>
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''}>
            <option value="">${fn:toUpperCase(field)}</option>
            <c:forEach var="item" begin="1" end="31" step="1">
                <option ${qaf:getDay(screening.date) eq item ? 'selected' : ''}>${item}</option>
            </c:forEach>

        </select>
        <span class="error">${form.messages[field]}</span>

        <%--Month--%>
        <c:set var="field" value="month"/>
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''}>
            <option value="">${fn:toUpperCase(field)}</option>
            <c:forEach var="item" items="${monthList}">
                <option ${qaf:getMonth(screening.date) eq item ? 'selected' : ''}>${item}</option>
            </c:forEach>
        </select>
        <span class="error">${form.messages[field]}</span>

        <%--Year--%>
        <c:set var="field" value="year"/>
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''}>
            <option value="">${fn:toUpperCase(field)}</option>
            <c:forEach var="item" begin="${initParam['com.queerartfilm.startyear']}" end="${current.year + 1}" step="1">
                <option ${qaf:getYear(screening.date) eq item ? 'selected' : ''}>${item}</option>
            </c:forEach>
        </select>
        <span class="error">${form.messages[field]}</span>
    </div>


    <label>Time and Venue</label>
    <div class="inputbox">

        <%--Hour--%>
        <c:set var="field" value="hour"/>
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''}>
            <option value="">${fn:toUpperCase(field)}</option>
            <c:forEach var="item" begin="1" end="12" step="1" >
                <option ${feature[field] eq item ? 'selected' : ''}>${item}</option>
            </c:forEach>
        </select>
        <span class="error">${form.messages[field]}</span>

        <span><strong>:</strong></span>

        <%--Minute--%>
        <c:set var="field" value="minute"/>
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''}>
            <option value="">${fn:toUpperCase(field)}S</option>
            <c:forEach var="item" begin="0" end="59" step="15" >
                <c:set var="mins" value="${qaf:doubleZero(item)}" />
                <option ${feature[field] eq mins ? 'selected' : ''}>${mins}</option>
            </c:forEach>
        </select>
        <span class="error">${form.messages[field]}</span>

        <%--AM PM--%>
        <c:set var="field" value="ampm"/>
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''}>
            <c:forEach var="item" items="PM,AM" >
                <option ${feature[field] eq item ? 'selected' : ''}>${item}</option>
            </c:forEach>
        </select>
        <span class="error">${form.messages[field]}</span>

        <span><strong> at </strong></span>

        <%--Venue--%>
        <c:set var="field" value="venue" />
        <select name="${field}" size="1" ${form.valid ? 'disabled' : ''} >
            <option value="">${fn:toUpperCase(field)}</option>
            <c:forEach var="item" items="${venueList}" >
                <option value="${item.name}" ${feature[field] eq item ? 'selected' : ''}>${item}</option>
            </c:forEach>
        </select>
        <span class="error">${form.messages[field]}</span>

    </div>
</fieldset>