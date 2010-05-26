<%-- 
    Document   : agregarSemestre
    Created on : May 7, 2010, 7:46:39 PM
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
        <title><fmt:message key="agregarSemestre.titulo"/></title>
        <%@include file="../../jspf/semestre/scriptsSemestre.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="agregarSemestre.titulo"/></h1>
        <%-- formulario para ingresar los datos del estudiante --%>
        <form:form modelAttribute="wrapperSemestre" method="post">
            <fieldset>
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/semestre/formularioSemestre.jspf" %>
                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/mensaje/resultado.jspf" %>
    </body>
</html>
