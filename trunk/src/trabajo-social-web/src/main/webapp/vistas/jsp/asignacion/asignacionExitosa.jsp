<%-- 
    Document   : asignacionExitosa
    Created on : 21/02/2011, 03:24:53 PM
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
        <title><fmt:message key="miscursos.asignacionCursos.titulo"/></title>
        <script type="text/javascript">
            $(function() {
                // se crea y configura el panel popup que muestra los
                // mensajes de resultados de las operaciones
                $("#popupMensaje").dialog({
                    autoOpen: <%= RequestUtil.getValorBoolean(request, "mostrarPopupMensaje") %>,
                    modal: true,
                    buttons: {
                        '<fmt:message key="btnAceptar"/>': function() {
                            $(this).dialog('close');
                        }
                    }
                });
             });
        </script>
    </head>
    <body>
        <h1><fmt:message key="miscursos.asignacionCursos.titulo"/></h1>
        <h2><fmt:message key="miscursos.asignacionCursos.exito"/></h2>
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
            <legend><fmt:message key="miscursos.asignacionCursos.cursosAsignados"/></legend>
            <table id="tablaHorarios" class="ui-widget ui-widget-content" align="center">
                <thead>
                    <tr class="ui-widget-header ">
                        <th><fmt:message key="agregarHorario.curso"/></th>
                        <th><fmt:message key="agregarHorario.seccion"/></th>
                        <th><fmt:message key="agregarHorario.horaInicio"/></th>
                        <th><fmt:message key="agregarHorario.horaFin"/></th>
                        <th><fmt:message key="agregarHorario.dia"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listaAsignacion}" var="asignacion">
                        <tr>
                            <td><c:out value="${asignacion.horario.asignacionCursoPensum.curso.nombre}" /></td>
                            <td align="center"><c:out value="${asignacion.horario.seccion}" /></td>
                            <td align="center">
                                <fmt:formatDate pattern="HH:mm" value="${asignacion.horario.horaInicio}" />
                            </td>
                            <td align="center">
                                <fmt:formatDate pattern="HH:mm" value="${asignacion.horario.horaFin}" />
                            </td>
                            <td><c:out value="${asignacion.horario.horarioDiasAsString}" /></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>            
        </fieldset>
                        
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
