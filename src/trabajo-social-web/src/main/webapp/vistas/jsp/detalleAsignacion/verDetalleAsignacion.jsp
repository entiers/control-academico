<%-- 
    Document   : verDetalleAsignacion
    Created on : Dec 1, 2012, 10:17:24 AM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title><fmt:message key="detalleAsignacion.titulo"/></title>
    </head>
    <body>
        <h1><fmt:message key="detalleAsignacion.titulo"/></h1>
        <c:choose>
            <c:when test="${errorEntidad}">
                <th></th>
            </c:when>
            <c:when test="${errorEstudianteAsignacion}">
                <th></th>
            </c:when>
            <c:otherwise>
                <fieldset>
                    <legend><fmt:message key="modificarDatosPersonales.DatosFijos"/></legend>
                    <table align="center">
                        <tbody>
                            <tr>
                                <td><fmt:message key="agregarEstudiante.carne"/></td>
                                <td><c:out value="${asignacion.asignacionEstudianteCarrera.estudiante.carne}" /></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="agregarEstudiante.nombre"/></td>
                                <td><c:out value="${asignacion.asignacionEstudianteCarrera.estudiante.nombre}" /></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="asignacion.fecha"/></td>
                                <td><fmt:formatDate pattern="dd/MM/yyyy hh:mm:ss"  value="${asignacion.fecha}" /></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="asignacion.transaccion"/></td>
                                <td><c:out value="${asignacion.transaccion}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>
                <br/>
                <fieldset>
                    <legend><fmt:message key="miscursos.asignaciones"/></legend>
                    <table align="center">
                        <thead>
                            <tr>
                                <th><fmt:message key="agregarCurso.codigo"/></th>
                                <th><fmt:message key="agregarHorario.curso"/></th>
                                <th><fmt:message key="agregarHorario.seccion"/></th>
                                <th><fmt:message key="salon.menu"/></th>
                                <th><fmt:message key="agregarHorario.horaInicio"/></th>
                                <th><fmt:message key="agregarHorario.horaFin"/></th>
                                <th><fmt:message key="agregarHorario.dia"/></th>
                                <c:choose>
                                    <c:when test="${periodoAsignacion}">
                                        <th></th>
                                    </c:when>
                                </c:choose>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listaDetalleAsignacion}" var="detalleAsignacion">
                                <tr>
                                    <td>${detalleAsignacion.horario.asignacionCursoPensum.curso.codigo}</td>
                                    <td>${detalleAsignacion.horario.asignacionCursoPensum.curso.nombre}</td>
                                    <td>${detalleAsignacion.horario.seccion}</td>
                                    <td>
                                       ${detalleAsignacion.horario.salon.numero} ${detalleAsignacion.horario.salon.edificio}
                                    </td>                            
                                    <td><fmt:formatDate pattern="hh:mm" value="${detalleAsignacion.horario.horaInicio}" /></td>
                                    <td><fmt:formatDate pattern="hh:mm" value="${detalleAsignacion.horario.horaFin}" /></td>
                                    <td>${detalleAsignacion.horario.horarioDiasAsString}</td>
                                    <c:choose>
                                        <c:when test="${periodoAsignacion}">
                                            <td>Eliminar</td>
                                        </c:when>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </c:otherwise>
        </c:choose>
    </body>
</html>
