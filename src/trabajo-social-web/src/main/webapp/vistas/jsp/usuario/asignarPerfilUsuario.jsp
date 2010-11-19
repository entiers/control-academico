<%-- 
    Document   : asignarPerfilUsuario
    Created on : 17/11/2010, 09:21:12 PM
    Author     : Carlos Solórzano
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
        <title><fmt:message key="asignarPerfilUsuario.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="asignarPerfilUsuario.titulo" /></h1>

        <form:form modelAttribute="asignacionUsuarioPerfil" method="post" >
        <fieldset>
            <legend><fmt:message key="usuario.perfilesNoAsignados"/></legend>

            <div id="divCampos">
                <label for="usuario"><fmt:message key="asignarPerfilUsuario.usuario"/>: </label>
                <span>${usuario.nombreUsuario}</span>
            </div>

            <div id="divCampos">
                <label for="perfil"><fmt:message key="asignarPerfilUsuario.perfil"/>: </label>
                <form:select itemLabel="nombre"path="perfil.idPerfil" itemValue="idPerfil"
                             items="${listadoPerfilNoAsignado}" />
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </div>
        </fieldset>
    </form:form>

     <fieldset>
            <legend><fmt:message key="usuario.perfilesAsignados"/></legend>
            <table>
                <thead>
                    <tr>
                        <th><fmt:message key="Perfil.menu"/></th>
                        <th><fmt:message key="acciones"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listadoAsignacionPerfilUsuario}" var="asignacionPerfilUsuario">
                        <tr>
                            <td>${asignacionPerfilUsuario.perfil.nombre}</td>
                            <td>
                                <a href="desasignarUsuarioPerfil.htm?idAsignacionUsuarioPerfil=${asignacionPerfilUsuario.idAsignacionUsuarioPerfil}">
                                    <fmt:message key="asignarPerfilUsuario.eliminar"/>
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
