<%-- 
    Document   : asignarCursoPensum
    Created on : Mar 5, 2011, 12:21:33 PM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<html>
    <head>        
        <title><fmt:message key="asignarCursoPensum.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>


        <h1><fmt:message key="asignarCursoPensum.titulo"/></h1>        

        <sec:authorize access="hasRole('ROLE_AGREGAR_CURSO_A_PENSUM')">
            <button onclick="location.href='agregarCursoAPensum.htm'"><fmt:message key="agregarCursoAPensum.titulo" /></button>
        </sec:authorize>
        <%@include file="../../jspf/formularios/informacion/formularioInformacionPensum.jspf" %>

        <div class="contenedor-reporte-arriba">
            <fieldset>
                <legend><fmt:message key="asignarCursoPensum.texto.imprimir" /></legend>
                <form:form action="generarReporte.htm" method="POST" target="_BLANK">
                    <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}" />
                    <input type="hidden" name="nombreParametro" value="ID_PENSUM" />
                    <input type="hidden" name="valorParametro" value="${pensum.idPensum}" />
                    <input type="hidden" name="tipoParametro" value="integer" />
                    <input type="submit" value="<fmt:message key="btnImprimir"/>"/>
                </form:form>
            </fieldset>
        </div>





        <fieldset>
            <legend><fmt:message key="asignarCursoPensum.legend.listadoAsigancionCursoPensum" /></legend>
            <display:table class="ui-widget ui-widget-content" name="pensum.asignacionCursoPensums" id="asignacionCursoPensum"
                           requestURI="asignarCursoPensumPag.htm" pagesize="${pageSize}" >
                <display:column titleKey="asignarCursoPensum.obligatorio" style="text-align:center;">
                    <input type="checkbox" checked="${asignacionCursoPensum.obligatorio}" disabled="true"/>
                </display:column>
                <display:column property="curso.codigo" titleKey="agregarCurso.codigo" style="text-align:center;"/>
                <display:column property="curso.nombre" titleKey="agregarCurso.nombre" />
                <display:column property="numeroSemestre" titleKey="asignarCursoPensum.numeroSemestre" style="text-align:center;"/>
                <display:column titleKey="asignarCursoPensum.creditos">
                    <b><fmt:message key="asignarCursoPensum.creditosPracticos"/>: </b>${asignacionCursoPensum.creditosPracticos} <br/>
                    <b><fmt:message key="asignarCursoPensum.creditosPrerrequisito"/>: </b>${asignacionCursoPensum.creditosPrerrequisito} <br/>
                    <b><fmt:message key="asignarCursoPensum.creditosTeoricos"/>: </b>${asignacionCursoPensum.creditosTeoricos} <br/>
                </display:column>
                <sec:authorize access="hasAnyRole('ROLE_EDITAR_ASIGNACION_CURSO_PENSUM', 'ROLE_ADMINISTRAR_PRERREQUISITOS', 'ROLE_ELIMINAR_ASIGNACION_CURSO_PENSUM')">
                    <display:column titleKey="acciones" style="text-align:center;">
                        <sec:authorize access="hasRole('ROLE_EDITAR_ASIGNACION_CURSO_PENSUM')">
                            <a href="editarCursoDePensum.htm?idAsignacionCursoPensum=${asignacionCursoPensum.idAsignacionCursoPensum}">
                                <fmt:message key="pensum.accion.editarCursos"/>
                            </a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ADMINISTRAR_PRERREQUISITOS')">
                            <br/><a href="administrarPrerrequisitos.htm?idAsignacionCursoPensum=${asignacionCursoPensum.idAsignacionCursoPensum}">
                                <fmt:message key="pensum.accion.prerrequisitos"/>
                            </a>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_ELIMINAR_ASIGNACION_CURSO_PENSUM')">
                            <br/><a href="eliminarCursoDePensum.htm?idAsignacionCursoPensum=${asignacionCursoPensum.idAsignacionCursoPensum}">
                                <fmt:message key="pensum.accion.eliminarCurso"/>
                            </a>
                        </sec:authorize>
                    </display:column>
                </sec:authorize>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
