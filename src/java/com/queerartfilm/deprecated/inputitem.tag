<%@tag description="form input for text" pageEncoding="UTF-8"
       body-content="empty" isELIgnored="false"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@attribute name="label" required="true"%>
<%@attribute name="name" required="true" %>
<%@attribute name="type" required="false" %>

<dt class="clear"><label for="${name}">${label}</label></dt>
<dd><input type="<c:out value='${type}' default='text'/>" name="${name}"/></dd>