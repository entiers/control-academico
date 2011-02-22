<%-- 
    Document   : asignacionCursos3
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
        <p><fmt:message key="miscursos.asignacionCursos.exito"/></p>
        <fieldset>
            <legend><fmt:message key="miscursos.asignacionCursos.cursosAsignados"/></legend>
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
                        <td><c:out value="${asignacion.fecha}" /></td>
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
            <table id="tablaHorarios" class="ui-widget ui-widget-content">
                <thead>
                    <tr class="ui-widget-header ">
                        <th><fmt:message key="agregarHorario.curso"/></th>
                        <th><fmt:message key="agregarHorario.dia"/></th>
                        <th><fmt:message key="agregarHorario.horaInicio"/></th>
                        <th><fmt:message key="agregarHorario.horaFin"/></th>
                        <th><fmt:message key="agregarHorario.seccion"/></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${listaAsignacion}" var="horario">
                        <tr>
                            <td><c:out value="${horario.curso.nombre}" /></td>
                            <td><c:out value="${horario.dia}" /></td>
                            <td align="center">
                                <fmt:formatDate pattern="hh:mm" value="${horario.horaInicio}" />
                            </td>
                            <td align="center">
                                <fmt:formatDate pattern="hh:mm" value="${horario.horaFin}" />
                            </td>
                            <td align="center"><c:out value="${horario.seccion}" /></td>                            
                        </tr>
                    </c:forEach>
                </tbody>
            </table>            
        </fieldset>
                        
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
