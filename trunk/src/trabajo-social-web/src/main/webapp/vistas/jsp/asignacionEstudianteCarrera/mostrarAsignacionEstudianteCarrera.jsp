<%-- 
    Document   : mostrarAsignacionEstudianteCarrera
    Created on : Mar 15, 2011, 11:59:11 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <title><fmt:message key="mostrarAsignacionEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="mostrarAsignacionEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>        


        <center>
            <fieldset>
                <legend><fmt:message key="mostrarAsignacionEstudianteCarrera.listadoCarrerasAsignadas" /></legend>
                <display:table class="ui-widget ui-widget-content" name="listadoAsignacionEstudianteCarrera" id="asignacionEstudianteCarrera">
                    <display:column property="carrera.codigo" titleKey="agregarCarrera.codigo" style="text-align: center;"  />
                    <display:column property="carrera.nombre" titleKey="agregarCarrera.nombre"  />

                    <display:column titleKey="asignacionEstudianteCarrera.fechaCierre"  style="text-align:center;">
                        <fmt:formatDate value="${asignacionEstudianteCarrera.fechaCierre}" pattern="dd-MM-yyyy" />
                    </display:column>

                    <sec:authorize access="hasRole('ROLE_MOSTRAR_PENSUM_ESTUDIANTE_CARRERA')">
                        <display:column titleKey="acciones" style="text-align:center;">
                            <a href="mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                <fmt:message key="mostrarPensumEstudianteCarrera.link"/>
                            </a>
                        </display:column>
                    </sec:authorize>


                </display:table>
            </fieldset>
        </center>

        <div style="margin: 20px 0 0 0; cursor: pointer;">
            <a onclick="javascript:history.back()"><fmt:message key="mostrarAsignacionEstudianteCarrera.regresar"/></a>
        </div>
    </body>
</html>
