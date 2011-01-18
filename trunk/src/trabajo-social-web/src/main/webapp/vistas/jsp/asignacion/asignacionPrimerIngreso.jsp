<%--
    Document   : asignacionPrimerIngreso
    Created on : 15/01/2011, 12:09:51 PM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="asignacionPrimerIngreso.titulo"/></title>
    </head>
    <body>
        <h1><fmt:message key="asignacionPrimerIngreso.titulo"/></h1>

        <form:form name="form" method="post" enctype="multipart/form-data">
            <c:choose>
                <c:when test="${periodoValido}">
                    <label for="sumbit"><fmt:message key="asignacionPrimerIngreso.confirmar"/></label>                    
                    <input type="submit" value='<fmt:message key="etl.btnRealizar"/>' />
                </c:when>
                <c:otherwise>
                    <label for="sumbit"><fmt:message key="asignacionPrimerIngreso.fueraPeriodo"/></label>
                </c:otherwise>
            </c:choose>
        </form:form>
    </body>
</html>