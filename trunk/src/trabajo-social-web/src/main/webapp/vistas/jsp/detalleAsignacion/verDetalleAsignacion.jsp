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
        <script type="text/javascript">
            $(function (){
                $('div[name="divQuitarPopup"]').dialog({
                    autoOpen: false,
                    modal: true,
                    width: 550,
                    buttons: {
                        "<fmt:message key="btnSalir" />" : function() {                    
                            $(this).dialog('close');
                        },
                        "<fmt:message key="btnAceptar" />" : function() {
                            $(this).children("form").submit();
                            $(this).dialog('close');
                        }
                    },
                    close : function(){
                        $(this).dialog('close');
                    }
                });
                
                $('#divConfirmPopup').dialog({
                    autoOpen: false,
                    modal: true,
                    width: 550,
                    buttons: {
                        "<fmt:message key="btnSalir" />" : function() {                    
                            $(this).dialog('close');
                        },
                        "<fmt:message key="btnAceptar" />" : function() {
                            $("#frmEliminarDetalleAsignacion").submit();
                            $(this).dialog('close');
                        }
                    },
                    close : function(){
                        $(this).dialog('close');
                    }
                });
                
                $('#divOperacionExitosa').dialog({
                    autoOpen: true,
                    modal: true,
                    width: 550,
                    buttons: {
                        "<fmt:message key="btnAceptar" />" : function() {                            
                            $(this).dialog('close');
                        }
                    },
                    close : function(){
                        window.location.href="buscarAsignacionPorEstudiante.htm";
                        $(this).dialog('close');
                    }
                });
            });
        </script>
    </head>
    <body>
        <h1><fmt:message key="detalleAsignacion.titulo"/></h1>
        <c:choose>
            <c:when test="${errorEntidad}">
                <p><fmt:message key="miscursos.asignacionCursos.noEstudiante"/></p>
            </c:when>
            <c:when test="${errorEstudianteAsignacion}">                
                <p><fmt:message key="asignacionCursos.asignacionNoEstudiante"/></p>                
            </c:when>
            <c:when test="${errorAsignacionSinDetalle}">                
                <p><fmt:message key="asignacionCursos.errorAsignacionSinDetalle"/></p>                
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
                                <td><fmt:formatDate pattern="dd/MM/yyyy HH:mm:ss"  value="${asignacion.fecha}" /></td>
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
                                    <td><fmt:formatDate pattern="HH:mm" value="${detalleAsignacion.horario.horaInicio}" /></td>
                                    <td><fmt:formatDate pattern="HH:mm" value="${detalleAsignacion.horario.horaFin}" /></td>
                                    <td>${detalleAsignacion.horario.horarioDiasAsString}</td>
                                    <c:choose>
                                        <c:when test="${periodoAsignacion}">
                                            <td>
                                                <div id="divQuitarPopup${detalleAsignacion.idDetalleAsignacion}" 
                                                     name="divQuitarPopup" 
                                                     title="<fmt:message key='modificarAsignacionEstudianteCarrera.titulo'/>" >
                                                    <form:form id="frmConfirmar${detalleAsignacion.idDetalleAsignacion}"
                                                               action="quitarDetalleAsignacion.htm?detail=${detalleAsignacion.idDetalleAsignacion}">
                                                        <p>Confirme que desea quitar el curso ${detalleAsignacion.horario.asignacionCursoPensum.curso.nombre} 
                                                            sección ${detalleAsignacion.horario.seccion} de la asignación.</p>
                                                    </form:form>
                                                </div>
                                                <input type="button" 
                                                   value="<fmt:message key='asignacionCursos.eliminar'/>"
                                                   onclick="$('#divQuitarPopup${detalleAsignacion.idDetalleAsignacion}').dialog('open')"/>
                                            </td>
                                        </c:when>
                                    </c:choose>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>                                
                <div id="divConfirmPopup" 
                     name="divConfirmPopup" 
                     title="<fmt:message key='modificarAsignacionEstudianteCarrera.titulo'/>" >
                    <form:form id="frmEliminarDetalleAsignacion"
                               action="eliminarDetalleAsignacion.htm">
                        <p><fmt:message key='detalleAsignacion.confirmaEliminacion'/></p>
                    </form:form>
                </div>
                <a href="buscarAsignacionPorEstudiante.htm"><fmt:message key="link.regresar"/></a> 
                <input type="button" 
                   value="<fmt:message key='ingresoNota.btnGuardar'/>"
                   onclick="$('#divConfirmPopup').dialog('open')"/>
                <c:choose>
                    <c:when test="${operacionExitosa}">                    
                        <div id="divOperacionExitosa"
                             title="<fmt:message key='modificarAsignacionEstudianteCarrera.titulo'/>">
                            <p><fmt:message key='detalleAsignacion.eliminacionExitosa'/></p>
                        </div>
                    </c:when>
                </c:choose>                
            </c:otherwise>
        </c:choose>
    </body>
</html>
