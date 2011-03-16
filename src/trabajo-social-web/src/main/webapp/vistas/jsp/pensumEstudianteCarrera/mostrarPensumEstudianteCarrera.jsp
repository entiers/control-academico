<%-- 
    Document   : mostrarPensumEstudianteCarrera
    Created on : Mar 15, 2011, 10:50:32 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <title><fmt:message key="mostrarPensumEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="mostrarPensumEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <c:set scope="request" var="legendPensum" value="pensum.informacion.valido"/>
        
        <%@include file="../../jspf/formularios/informacion/formularioInformacionPensum.jspf" %>

        <display:table class="ui-widget ui-widget-content" name="listadoPensumEstudianteCarreraNoValidos" id="pensumEstudianteCarrera">
            
        </display:table>

        <div style="margin: 20px 0 0 0; cursor: pointer;">
            <a onclick="javascript:history.back()"><fmt:message key="mostrarPensumEstudianteCarrera.regresar"/></a>
        </div>
    </body>
</html>
