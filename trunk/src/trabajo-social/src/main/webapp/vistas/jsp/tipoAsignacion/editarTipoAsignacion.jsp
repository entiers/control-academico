<%-- 
    Document   : editarTipoAsignacion
    Created on : Jun 27, 2010, 8:07:30 AM
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
        <title><fmt:message key="editarTipoAsignacion.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptTipoAsignacion.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarTipoAsignacion.titulo"/></h1>

        <%-- formulario para ingresar los datos del Tipo de Asignacion--%>
        <form:form modelAttribute="wrapperTipoAsignacion" method="post">
            <fieldset>
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioTipoAsignacion.jspf" %>

                <%-- botones --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
                <a  href="buscarTipoAsignacion.htm"><fmt:message key="editarTipoAsignacion.regresarABusqueda"/></a>
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>