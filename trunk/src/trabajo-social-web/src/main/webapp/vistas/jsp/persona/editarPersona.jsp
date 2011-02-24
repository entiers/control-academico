<%-- 
    Document   : editarPersona
    Created on : Feb 22, 2011, 11:44:23 PM
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
        <title><fmt:message key="editarPersona.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPersona.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarPersona.titulo"/></h1>


        <form:form modelAttribute="wrapperPersona" method="post">
            <fieldset>
                <%-- se importan los campos --%>
                <%@include file="../../jspf/formularios/formularioPersona.jspf" %>

                <div id="divCampos">
                    <form:label for="habilitado" path="nombreUsuario"><fmt:message key="editarPersona.habilitado"/>:</form:label>
                    <form:checkbox path="habilitado"  />
                    <form:errors path="habilitado" cssClass="claseError" />
                </div>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
            </fieldset>
        </form:form>


        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>