<%-- 
    Document   : agregarDocumento
    Created on : 21/03/2011, 12:46:19 AM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="agregarDocumento.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptDocumento.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="agregarDocumento.titulo"/></h1>
        <%-- formulario para ingresar los datos del estudiante --%>
        <form:form modelAttribute="wrapperDocumento" method="post">
            <fieldset>
                <%-- se importan los campos --%>
                <%@include file="../../jspf/formularios/formularioDocumento.jspf" %>
                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
