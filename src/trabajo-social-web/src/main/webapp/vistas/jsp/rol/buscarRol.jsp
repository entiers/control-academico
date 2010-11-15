<%-- 
    Document   : buscarRoles
    Created on : Aug 6, 2010, 5:38:10 AM
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
        <title><fmt:message key="buscarRol.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptRol.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarRol.titulo"/></h1>

        <fieldset>
                <legend><fmt:message key="buscarRol.tituloListado"/></legend>
                <table id="tablaRol" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarRol.nombre"/></th>
                            <th><fmt:message key="agregarRol.descripcion"/></th>
                            <th colspan="2"><fmt:message key="acciones"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoRol}" var="rol">
                            <tr>
                                <td><c:out value="${rol.nombre}" /></td>
                                <td><c:out value="${rol.descripcion}" /></td>
                                <td>
                                    <a href="editarRol.htm?idRol=${rol.idRol}">
                                        <fmt:message key="editarRol.editar"/>
                                    </a>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        </fieldset>
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
