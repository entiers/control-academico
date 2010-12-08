<%-- 
    Document   : recordarContrasenia
    Created on : 6/12/2010, 09:49:19 PM
    Author     : Carlos Solorzano
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
        <title><fmt:message key="recordarContrasenia.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="recordarContrasenia.titulo"/></h1>
        <form:form modelAttribute="wrapperRecordarContrasenia" method="post">
            <fieldset>
                <div id="divCampos">
                    <form:label for="email" path="email"><fmt:message key="recordarUsuario.email"/>:</form:label>
                    <form:input path="email" cssStyle="width: 250px;" />
                    <form:errors path="email" cssClass="claseError" />
                </div>
                <div id="divCampos">
                    <img alt="" src="simpleImg" />
                </div>
                <div id="divCampos">
                    <form:label for="captcha" path="captcha"><fmt:message key="recordarContrasenia.captcha"/>:</form:label>
                    <form:input path="captcha" cssStyle="width: 75px;" />
                    <form:errors path="captcha" cssClass="claseError" />
                </div>                
            </fieldset>
            <input id="btnEditar" type="submit" value='<fmt:message key="btnEnviar"/>' />
        </form:form>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>