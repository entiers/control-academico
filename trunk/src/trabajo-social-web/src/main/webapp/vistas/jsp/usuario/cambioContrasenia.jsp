<%-- 
    Document   : cambioContrasenia.jsp
    Created on : 09/10/2012 04:31pm
    Author     : Ing. Carlos Solórzano
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
        <title><fmt:message key="cambioContrasenia.titulo"/></title>      
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="cambioContrasenia.titulo"/></h1>        
        <%-- formulario para realizar el cambio de contraseña --%>
        <form:form modelAttribute="wrapperCambioContrasenia" method="post" action="">
            <fieldset>
                <div id="divCampos">
                    <form:label for="nombreUsuario" path="nombreUsuario"><fmt:message key="buscarUsuario.nombreUsuario"/>:</form:label>
                    <form:input path="nombreUsuario" cssStyle="width: 250px;" />
                    <form:errors path="nombreUsuario" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="contrasenia1" path="contrasenia1"><fmt:message key="modificarContrasenia.contrasenia1"/>:</form:label>
                    <form:password path="contrasenia1" cssStyle="width: 250px;" />
                    <form:errors path="contrasenia1" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="contrasenia2" path="contrasenia2"><fmt:message key="modificarContrasenia.contrasenia2"/>:</form:label>
                    <form:password path="contrasenia2" cssStyle="width: 250px;"  />
                    <form:errors path="contrasenia2" cssClass="claseError" />
                </div>
            </fieldset>
            <br/>
            <input id="btnEditar" type="submit" value='<fmt:message key="modificarContrasenia.titulo"/>' />
        </form:form>
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>