<%-- 
    Document   : buscarPensum
    Created on : Mar 5, 2011, 12:11:15 AM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="buscarPensum.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>                
    </head>
    <body>
        <h1><fmt:message key="buscarPensum.titulo"/></h1>

        <fieldset>
            <legend><fmt:message key="buscarPensum.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content" name="listadoPensums" id="pensum" requestURI="buscarPensum.htm" pagesize="10" >
                <display:column property="codigo" titleKey="agregarPensum.codigo" style="text-align: center;" />

                <display:column titleKey="agregarPensum.fechaInicio" style="text-align: center;" >
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${pensum.fechaInicio}" />
                </display:column>
                <display:column titleKey="agregarPensum.fechaFin" style="text-align: center;">
                    <fmt:formatDate pattern="dd-MM-yyyy" value="${pensum.fechaFin}" />
                </display:column>

                <display:column property="carrera.codigoNombre" titleKey="buscarPensum.carrera" />


                <sec:authorize access="hasAnyRole('ROLE_EDITAR_PENSUM')">
                    <display:column titleKey="acciones" style="text-align:center;">

                        <sec:authorize access="hasRole('ROLE_EDITAR_PENSUM')">
                            <a href="editarPensum.htm?idPensum=${pensum.idPensum}">
                                <fmt:message key="editarPersona.editar"/>
                            </a>
                        </sec:authorize>

                        <sec:authorize access="hasRole('ROLE_ASIGNAR_CURSO_PENSUM')">
                            <br/><a href="asignarCursoPensum.htm?idPensum=${pensum.idPensum}">
                                <fmt:message key="pensum.accion.asignarCursos"/>
                            </a>
                        </sec:authorize>
                        <br/>
                        <form:form action="generarReporte.htm" method="POST" target="_BLANK">
                            <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}" />
                            <input type="hidden" name="nombreParametro" value="ID_PENSUM" />
                            <input type="hidden" name="valorParametro" value="${pensum.idPensum}" />
                            <input type="hidden" name="tipoParametro" value="integer" />
                            <input type="submit" value="<fmt:message key="btnImprimir"/>"/>
                        </form:form>
                    </display:column>
                </sec:authorize>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>