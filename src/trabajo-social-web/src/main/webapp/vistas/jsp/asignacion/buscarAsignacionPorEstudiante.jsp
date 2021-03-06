<%-- 
    Document   : asignacionPorEstudiante
    Created on : 25/01/2011, 09:25:46 PM
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
        <title><fmt:message key="miscursos.asignados"/></title>
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="miscursos.asignados" /></h1>
        <c:choose>
            <c:when test="${error}">
                <div id="divCampos">
                    <p class="claseError"><fmt:message key="miscursos.asignados.error" /></p>
                </div>
            </c:when>
            <c:otherwise>
                <form:form modelAttribute="datosBusquedaAsignacion" method="post" >
                    <fieldset>
                        <div id="divCampos">
                            <form:label for="anio" path="anio"><fmt:message key="agregarSemestre.anio"/>: </form:label>
                            <form:select path="anio" items="${lstAnio}" />
                            <form:errors path="anio" cssClass="claseError" />
                        </div>
                        <div id="divCampos">
                            <form:label for="tipoAsignacion" path="tipoAsignacion"><fmt:message key="tipoAsignacion.menu"/>: </form:label>
                            <form:select path="tipoAsignacion"
                                         itemLabel="descripcion"
                                         items="${listadoTipoAsignacion}" />
                            <form:errors path="tipoAsignacion" cssClass="claseError" />
                        </div>
                        <input type="submit" value='<fmt:message key="btnBuscar"/>' />
                    </fieldset>
                </form:form>
                <c:choose>
                    <c:when test="${post}">
                        <fieldset>
                            <legend><fmt:message key="miscursos.asignaciones"/></legend>
                            <table align="center">
                                <thead>
                                    <tr>
                                        <th>Codigo</th>
                                        <th>Nombre Curso</th>
                                        <th>Fecha de Asignacion</th>
                                        <th>Secci&oacute;n</th>
                                        <th>Detalle</th>
                                        <th>Boleta</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${listadoAsignacion}" var="asignacion">
                                        <c:forEach items="${asignacion.detalleAsignacions}" var="detalle">
                                        <tr>
                                            <td>${detalle.horario.asignacionCursoPensum.curso.codigo}</td>
                                            <td>${detalle.horario.asignacionCursoPensum.curso.nombre}</td>
                                            <td>${asignacion.fecha}</td>
                                            <td>${detalle.horario.seccion}</td>
                                            <td align="center">
                                                <a href="verDetalleAsignacion.htm?idAsignacion=${asignacion.idAsignacion}"><fmt:message key="asignacion.verDetalle"/></a>
                                            </td>
                                            <td align="center">
                                                <form:form action="generarReporte.htm" method="post">
                                                    <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}" />

                                                    <input type="hidden" name="nombreParametro" value="idAsignacion" />
                                                    <input type="hidden" name="valorParametro" value="${asignacion.idAsignacion}" />
                                                    <input type="hidden" name="tipoParametro" value="integer" />

                                                    <input type="submit" value="<fmt:message key="asignacion.imprimirBoleta"/>"/>
                                                </form:form>
                                            </td>
                                        </tr>
                                        </c:forEach>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </fieldset>
                    </c:when>
                </c:choose>
            </c:otherwise>
        </c:choose>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>

    </body>
</html>

