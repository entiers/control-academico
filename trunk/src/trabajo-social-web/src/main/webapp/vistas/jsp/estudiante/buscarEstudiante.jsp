<%--
    Document   : buscarEstudiante
    Created on : 3/05/2010, 11:34:08 AM
    Author     : Daniel Castillo, Mario Batres
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
        <title><fmt:message key="buscarEstudiante.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptEstudiante.jspf" %>
        <%@include file="../../jspf/scripts/scriptPopupReportes.jspf" %>
        <script type="text/javascript">
            var validar = function(){
                if($("#valorParametro").attr("value") != ""){
                    return true;
                }

                $("#errorFormulario").dialog("open");
                return false;
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="buscarEstudiante.titulo"/></h1>

        <sec:authorize access="hasAnyRole('ROLE_REPORTE_LISTADO_CARRERA_SIMULTANEAS', 'ROLE_REPORTE_LISTADO_ASIGNACION_ESTUDIANTE_POR_CARRERA')">
            <div class="contenedor-reporte-arriba">
                <fieldset>
                    <legend><fmt:message key="legend.reportes" /></legend>
                    <sec:authorize access="hasRole('ROLE_REPORTE_LISTADO_CARRERA_SIMULTANEAS')">
                        <div style="float:left;">
                            <form:form action="generarReporte.htm" method="POST" target="_BLANK">
                                <input type="hidden" name="nombreControlReporte" value="${reporteListadoCarreraSimultaneas}" />

                                <input type="submit" value="<fmt:message key="estudiante.imprimir.simultaneas" />" />
                            </form:form>
                        </div>
                    </sec:authorize>

                    <sec:authorize access="hasRole('ROLE_REPORTE_LISTADO_ASIGNACION_ESTUDIANTE_POR_CARRERA')">
                        <div style="float:left;">
                            <div id="verReporte" title="<fmt:message key="estudiante.reporte.asignacionPorCarrera.title" />">
                                <div id="errorFormulario" title="Error">
                                    <fmt:message key="estudiante.reporte.asignacionPorCarrera.error" />
                                </div>

                                <form:form id="formVerReporte" action="generarReporte.htm" method="POST" target="_BLANK">
                                    <input type="hidden" name="nombreControlReporte" value="${reporteAsignacionPorCarrera}" />
                                    <div id="divCampos">
                                        <input type="hidden" name="nombreParametro" value="ID_CARRERA" />

                                        <label><fmt:message key="asignacionEstudianteCarrera.carrera"/>: *</label>
                                        <select name="valorParametro" id="valorParametro" style="width: 250px;" >
                                            <option value="">Seleccionar un valor</option>
                                            <c:forEach items="${listadoCarreras}" var="carrera">
                                                <option value="${carrera.idCarrera}">${carrera.codigoNombre}</option>
                                            </c:forEach>
                                        </select>
                                        <input type="hidden" name="tipoParametro" value="integer" />
                                    </div>
                                </form:form>
                            </div>
                            <button id="botonVerReporte"> <fmt:message key="estudiante.imprimir.asignacionPorCarrera" /></button>
                        </div>
                    </sec:authorize>
                </fieldset>
            </div>
        </sec:authorize>

        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaEstudiante" method="post" action="buscarEstudiante.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="carneBusqueda" path="carneBusqueda"><fmt:message key="agregarEstudiante.carne"/>:</form:label>
                    <form:input path="carneBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="carneBusqueda" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="nombreBusqueda" path="nombreBusqueda"><fmt:message key="agregarEstudiante.nombre"/>:</form:label>
                    <form:input path="nombreBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="nombreBusqueda" cssClass="claseError" />
                </div>               
                <br/>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <fieldset>
            <legend><fmt:message key="buscarEstudiante.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content" name="listadoEstudiantes"
                           id="estudiante" requestURI="buscarEstudiantePag.htm" pagesize="${pageSize}" >
                <display:column property="carne" titleKey="agregarEstudiante.carne"  />
                <display:column property="nombre" titleKey="agregarEstudiante.nombre"  />
                <sec:authorize access="hasAnyRole('ROLE_MOSTRAR_ASIGNACION_ESTUDIANTE_CARRERA','ROLE_ASIGNACION_CURSOS_ADMINISTRACION')">
                    <display:column titleKey="acciones" style="text-align: center;" >
                        <sec:authorize access="hasRole('ROLE_MOSTRAR_ASIGNACION_ESTUDIANTE_CARRERA')">
                            <a href="mostrarAsignacionEstudianteCarrera.htm?idEstudiante=${estudiante.idEstudiante}">
                                <fmt:message key="mostrarAsignacionEstudianteCarrera.link"/>
                            </a>
                        </sec:authorize>
                        <%--Operacion asignacion de cursos de administracion--%>
                        <sec:authorize access="hasRole('ROLE_ASIGNACION_CURSOS_ADMINISTRACION')">
                            <br/><a href="asignacionCursosAdmin.htm?idEstudiante=${estudiante.idEstudiante}">
                                <fmt:message key="admin.asignacionCursos.titulo"/>
                            </a>
                        </sec:authorize>
                        <%--Operacion ingreso de curso aprobado--%>
                        <sec:authorize access="hasRole('ROLE_AGREGAR_CURSO_APROBADO')">
                            <br/><a href="agregarCursoAprobado.htm?idEstudiante=${estudiante.idEstudiante}">
                                <fmt:message key="admin.agregarCursoAprobado.titulo"/>
                            </a>
                        </sec:authorize>
                        <%--Operacion modificación de curso aprobado--%>
                        <sec:authorize access="hasRole('ROLE_AGREGAR_CURSO_APROBADO')">
                            <br/><a href="modificarCursoAprobadoLista.htm?idEstudiante=${estudiante.idEstudiante}">
                                <fmt:message key="admin.modificarCursoAprobado.titulo"/>
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
