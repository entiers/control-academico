<%-- 
    Document   : editarCalendarioActividades
    Created on : Jun 7, 2010, 6:47:23 PM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="editarCalendarioActividades.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptCalendarioActividades.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarCalendarioActividades.titulo"/></h1>
        <%-- formulario para ingresar los datos del calendario de actividades --%>
        <form:form modelAttribute="wrapperCalendarioActividades" method="post">
            <fieldset>
                <%-- se importan los campos --%>
                <%@include file="../../jspf/formularios/formularioCalendarioActividades.jspf" %>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
