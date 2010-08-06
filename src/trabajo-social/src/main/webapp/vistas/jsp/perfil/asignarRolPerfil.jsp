<%-- 
    Document   : asignarRolPerfil
    Created on : Aug 6, 2010, 8:09:31 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="asignarRolPerfil.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPerfil.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="asignarRolPerfil.title" /></h1>

        <form:form modelAttribute="perfil" method="post">
            <fieldset>
                <legend><fmt:message key="perfil.rolesAsignados" /></legend>
                <div>
                    <form:checkboxes path="asignacionRolPerfils" items="${listadoRol}"
                                     itemLabel="nombre" itemValue="idRol" delimiter="<br/>"  />
                </div>
            </fieldset>
            <input type="submit" value='<fmt:message key="profile.button.addRoles"/>' />
        </form:form>
    </body>
</html>
