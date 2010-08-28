<%-- 
    Document   : asignarRolPerfil
    Created on : Aug 6, 2010, 8:09:31 AM
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
        <title><fmt:message key="asignarRolPerfil.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPerfil.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="asignarRolPerfil.titulo" /></h1>

        <form:form modelAttribute="asignacionRolPerfil" method="post">
            <fieldset>
                <legend><fmt:message key="perfil.rolesNoAsignados"/></legend>

                <div id="divCampos">
                    <label for="perfil"><fmt:message key="asignarRolPerfil.perfil"/>: </label>
                    <span>${perfil.nombre}</span>
                </div>

                <div id="divCampos">
                    <label for="rol"><fmt:message key="asignarRolPerfil.rol"/>: </label>
                    <form:select path="rol.idRol" itemLabel="nombre" itemValue="idRol"
                                 items="${listadoRolNoAsignado}" />
                    <input type="submit" value='<fmt:message key="btnAgregar"/>' />
                </div>
            </fieldset>
        </form:form>

        <fieldset>
            <legend><fmt:message key="perfil.rolesAsignados"/></legend>
            <table>
                <thead>
                    <tr>
                        <th><fmt:message key="agregarRol.nombre"/></th>
                        <th><fmt:message key="acciones"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listadoAsignacionRolPerfil}" var="asignacionRolPerfil">
                        <tr>
                            <td>${asignacionRolPerfil.rol.nombre}</td>
                            <td>
                                <a href="desasignarRolPerfil.htm?idAsignacionRolPerfil=${asignacionRolPerfil.idAsignacionRolPerfil}">
                                    <fmt:message key="asignarRolPerfil.eliminar"/>
                                </a>
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
