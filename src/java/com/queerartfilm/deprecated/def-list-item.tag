<%@tag pageEncoding="UTF-8"
       body-content="empty" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="obj" required="true" rtexprvalue="true" type="com.queerartfilm.model.FilmScreening"%>
<%@attribute name="proplist" required="true"%>
<dl>
    <c:forEach var="prop" items="${proplist}">
        <dt>${prop}</dt><dd>${obj[prop]}</dd>
    </c:forEach>
</dl>