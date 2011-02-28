<%-- 
    Document   : agregarHorario
    Created on : May 11, 2010, 10:58:55 PM
    Author     : Mario Batres
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
        <title><fmt:message key="agregarHorario.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptHorario.jspf" %>        
    </head>
    <body>
        <h1><fmt:message key="agregarHorario.titulo"/></h1>

        <%-- formulario para ingresar los datos del horario --%>
        <form:form modelAttribute="wrapperHorario" method="post">
            <fieldset>

                <c:set var="agregar" value="true" target="request" />
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioHorario.jspf" %>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>