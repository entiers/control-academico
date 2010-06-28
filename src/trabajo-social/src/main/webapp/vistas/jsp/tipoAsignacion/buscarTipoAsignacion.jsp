<%-- 
    Document   : buscarTipoAsignacion
    Created on : Jun 27, 2010, 7:12:34 AM
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
        <title><fmt:message key="buscarTipoAsignacion.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptTipoAsignacion.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarTipoAsignacion.titulo"/></h1>

        <fieldset>
                <legend><fmt:message key="buscarTipoAsignacion.tituloListado"/></legend>
                <table id="tablaTipoAsignacion" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarTipoAsignacion.nombre"/></th>
                            <th><fmt:message key="agregarTipoAsignacion.descripcion"/></th>
                            <th><fmt:message key="acciones"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoTipoAsignacion}" var="tipoAsignacion">
                            <tr>
                                <td><c:out value="${tipoAsignacion.nombre}" /></td>
                                <td><c:out value="${tipoAsignacion.descripcion}" /></td>
                                <td>
                                    <a href="editarHorario.htm?idTipoAsignacion=${tipoAsignacion.idTipoAsignacion}">
                                        <fmt:message key="editarTipoAsignacion.editar"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        </fieldset>
    </body>
</html>
