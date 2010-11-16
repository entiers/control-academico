<%-- 
    Document   : buscarPerfil
    Created on : Aug 6, 2010, 5:37:08 AM
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
        <title><fmt:message key="buscarPerfil.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPerfil.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarPerfil.titulo"/></h1>

        <fieldset>
                <legend><fmt:message key="buscarPerfil.tituloListado"/></legend>
                <table id="tablaPerfil" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarPerfil.nombre"/></th>
                            <th><fmt:message key="agregarPerfil.descripcion"/></th>
                            <sec:authorize access="hasAnyRole('ROLE_EDITAR_PERFIL','ROLE_ASIGNAR_ROL_PERFIL')">
                                <th colspan="2"><fmt:message key="acciones"/></th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoPerfil}" var="perfil">
                            <tr>
                                <td><c:out value="${perfil.nombre}" /></td>
                                <td><c:out value="${perfil.descripcion}" /></td>
                                <sec:authorize access="hasRole('ROLE_EDITAR_PERFIL')">
                                    <td>
                                        <a href="editarPerfil.htm?idPerfil=${perfil.idPerfil}">
                                            <fmt:message key="editarPerfil.editar"/>
                                        </a>
                                    </td>
                                </sec:authorize>
                                <sec:authorize access="hasRole('ROLE_ASIGNAR_ROL_PERFIL')">
                                    <td>
                                        <a href="asignarRolPerfil.htm?idPerfil=${perfil.idPerfil}">
                                            <fmt:message key="asignarRolPerfil.editar"/>
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
