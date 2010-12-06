<%-- 
    Document   : recordarUsuario
    Created on : 4/12/2010, 10:58:07 PM
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
        <title><fmt:message key="recordarUsuario.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="recordarUsuario.titulo"/></h1>
        <form:form modelAttribute="wrapperDatosPersonales" method="post">
            <fieldset>
                <div id="divCampos">
                    <form:label for="email" path="email"><fmt:message key="recordarUsuario.email"/>:</form:label>
                    <form:input path="email" cssStyle="width: 250px;" />
                    <form:errors path="email" cssClass="claseError" />
                </div>
            </fieldset>
            <input id="btnEditar" type="submit" value='<fmt:message key="btnEnviar"/>' />
        </form:form>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>