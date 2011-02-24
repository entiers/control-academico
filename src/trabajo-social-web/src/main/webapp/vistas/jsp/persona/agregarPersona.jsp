<%--
    Document   : agregarPersona
    Created on : Feb 22, 2011, 11:43:51 PM
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
        <title><fmt:message key="agregarPersona.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPersona.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="agregarPersona.titulo"/></h1>


        <form:form modelAttribute="wrapperPersona" method="post">
            <fieldset>
                <%-- se importan los campos --%>
                <%@include file="../../jspf/formularios/formularioPersona.jspf" %>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>


        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>