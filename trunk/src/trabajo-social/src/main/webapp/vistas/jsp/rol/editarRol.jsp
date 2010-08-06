<%-- 
    Document   : editarRol
    Created on : Aug 6, 2010, 5:37:22 AM
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
        <title><fmt:message key="editarRol.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptRol.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarRol.titulo"/></h1>

        <%-- formulario para ingresar los datos del Tipo de Asignacion--%>
        <form:form modelAttribute="wrapperRol" method="post">
            <fieldset>
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioRol.jspf" %>

                <%-- botones --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
                <a  href="buscarRol.htm"><fmt:message key="editarRol.regresarABusqueda"/></a>
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>