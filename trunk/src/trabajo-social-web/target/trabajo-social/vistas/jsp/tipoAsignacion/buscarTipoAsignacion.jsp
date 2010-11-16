<%-- 
    Document   : buscarTipoAsignacion
    Created on : Jun 27, 2010, 7:12:34 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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
                            <sec:authorize access="hasAnyRole('ROLE_EDITAR_TIPO_ASIGN','ROLE_ELIMINAR_TIPO_ASIGN')">
                                <th colspan="2"><fmt:message key="acciones"/></th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoTipoAsignacion}" var="tipoAsignacion">
                            <tr>
                                <td><c:out value="${tipoAsignacion.nombre}" /></td>
                                <td><c:out value="${tipoAsignacion.descripcion}" /></td>
                                <sec:authorize access="hasRole('ROLE_EDITAR_TIPO_ASIGN')">
                                    <td>
                                        <a href="editarTipoAsignacion.htm?idTipoAsignacion=${tipoAsignacion.idTipoAsignacion}">
                                            <fmt:message key="editarTipoAsignacion.editar"/>
                                        </a>
                                    </td>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_ELIMINAR_TIPO_ASIGN')">
                                    <td>
                                        <a href="eliminarTipoAsignacion.htm?idTipoAsignacion=${tipoAsignacion.idTipoAsignacion}">
                                            <fmt:message key="eliminarTipoAsignacion.eliminar"/>
                                        </a>
                                    </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        </fieldset>
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
