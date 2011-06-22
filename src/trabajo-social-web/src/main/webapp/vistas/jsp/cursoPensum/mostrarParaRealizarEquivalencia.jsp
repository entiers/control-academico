<%-- 
    Document   : mostrarParaRealizarEquivalencia
    Created on : Jun 22, 2011, 10:05:46 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="cursoPensum.titulo.realizarEquivalencias" /></title>
    </head>
    <body>
        <h1><fmt:message key="cursoPensum.titulo.realizarEquivalencias" /></h1>

        
        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <fieldset>
            <legend><fmt:message key="cursoPensum.label.realizarEquivalencias"/></legend>
            <display:table class="ui-widget ui-widget-content" 
                           name="listadoEquivalencias" id="asignacionCursoPensum">
                <display:column property="curso.codigo" titleKey="agregarCurso.codigo" />
                <display:column property="curso.nombre" titleKey="agregarCurso.nombre" />
            </display:table>
        </fieldset>
    </body>
</html>
