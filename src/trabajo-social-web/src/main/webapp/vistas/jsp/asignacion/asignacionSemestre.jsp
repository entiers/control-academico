<%-- 
    Document   : asignacionCursos2
    Created on : 19/02/2011, 03:10:09 PM
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
        <form:form method="post" modelAttribute="datosAsignacion" action="agregarHorarioAsignacionSemestre.htm">
            <fieldset>
                <legend><fmt:message key="miscursos.asignacionCursos.horarios"/></legend>
                <div id="divCampos">
                    <form:label path="idCurso" for="idCurso">
                        <fmt:message key="curso.menu" /> : 
                    </form:label>
                    <form:select id="slcCurso"
                                 path="idCurso"
                                 items="${listaCurso}"
                                 itemLabel="nombre"
                                 itemValue="idCurso"/>
                    <form:errors path="idCurso" cssClass="claseError" />
                </div>
                <div id="divCampos">
                    <form:label path="idHorario" for="idHorario">
                        <fmt:message key="agregarHorario.seccion" /> :
                    </form:label>
                    <form:select id="slcHorario"
                                 path="idHorario"
                                 items="${listaHorario}"
                                 itemLabel="seccion"
                                 itemValue="idHorario"/>
                    <form:errors path="idHorario" cssClass="claseError" />
                </div>
                <form:hidden path="tipoAsignacion" />
                <form:hidden path="idAsignacionEstudianteCarrera" />
                <form:hidden path="totalCursos" />
                <br/>
                <input type="submit" value='<fmt:message key="miscursos.asignacionCursos.agregarCurso" />' />
            </fieldset>
        </form:form>

        <c:if test="${horarioElegido}" >
            <form:form method="POST" modelAttribute="datosAsignacion" action="realizarAsignacionSemestre.htm">
                <fieldset>
                    <legend><fmt:message key="buscarHorario.tituloListado"/></legend>
                    <table id="tablaHorarios" class="ui-widget ui-widget-content" align="center">
                        <thead>
                            <tr class="ui-widget-header ">
                                <th><fmt:message key="agregarHorario.curso"/></th>
                                <th><fmt:message key="agregarHorario.seccion"/></th>
                                <th><fmt:message key="agregarHorario.salon"/></th>
                                <th><fmt:message key="agregarHorario.horaInicio"/></th>
                                <th><fmt:message key="agregarHorario.horaFin"/></th>
                                <th><fmt:message key="agregarHorario.dia"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listadoHorarioAsignados}" var="horario">
                                <tr>
                                    <td><c:out value="${horario.curso.nombre}" /></td>
                                    <td align="center"><c:out value="${horario.seccion}" /></td>
                                    <td align="center">
                                        <c:out value="${horario.salon.edificio}" />-<c:out value="${horario.salon.numero}" />
                                    </td>
                                    <td align="center">
                                        <fmt:formatDate pattern="hh:mm" value="${horario.horaInicio}" />
                                    </td>
                                    <td align="center">
                                        <fmt:formatDate pattern="hh:mm" value="${horario.horaFin}" />
                                    </td>
                                    <td><c:out value="${horario.horarioDiasAsString}" /></td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                    <form:hidden path="idAsignacionEstudianteCarrera" />
                    <form:hidden path="tipoAsignacion" />
                    <br/>
                    <input type="submit" id="btnRealizarAsignacion" value='<fmt:message key="miscursos.asignacionCursos.realizar" />' />
                </fieldset>
            </form:form>
        </c:if>

        <script type="text/javascript">
            $(document).ready(function() {
                //Cambio combo curso
                $('#slcCurso').change(function() {
                    getHorarios($(this).val());
                });
                
            });

            function getHorarios(value) {
                $.getJSON("getHorarioAsignacionSemestre.htm", { idCurso: value }, function(lstHorario) {
                    var options = '';
                    $.each(lstHorario, function (index,value) {
                        options += "<option value='" + value.idHorario + "'>" + value.seccion + "</option>";
                    });
                    $('#slcHorario').html(options);
                });
            }
        </script>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
