<%--
    Document   : asignacionSemestre
    Created on : 14/02/2011, 03:29:32 PM
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
        <c:choose>
            <c:when test="${errorEntidad}">
                <label><fmt:message key="miscursos.asignacionCursos.noEstudiante"/></label>
            </c:when>
            <c:when test="${estudianteNoInscrito}">
                <label><fmt:message key="miscursos.asignacionCursos.noInscrito"/></label>
            </c:when>
            <c:when test="${periodoInvalido}">
                <label><fmt:message key="asignacionPrimerIngreso.fueraPeriodo"/></label>
            </c:when>
            <c:otherwise>
                <form:form method="POST" modelAttribute="wrapperAsignacionCursos">
                    <fieldset>
                        <div id="divCampos">
                            <form:label path="asignEstCarrera" for="asignEstCarrera"><fmt:message key="agregarEstudiante.carrera"/> : </form:label>
                            <form:select id="slcAEC"
                                         path="asignEstCarrera"
                                         items="${listaAEC}"
                                         itemLabel="carrera.nombre"
                                         itemValue="idAsignacionEstudianteCarrera"/>
                            <form:errors path="asignEstCarrera" cssClass="claseError" />
                        </div>
                    </fieldset>
                    <br/>
                    <fieldset>
                        <div id="divCampos">
                            <form:label path="curso" for="curso"><fmt:message key="curso.menu"/> : </form:label>
                            <form:select id="slcCurso"
                                         path="curso"
                                         items="${listaCurso}"
                                         itemLabel="nombre"
                                         itemValue="idCurso" />
                            <form:errors path="curso" cssClass="claseError" />
                        </div>
                        <div id="divCampos">
                            <form:label path="horario" for="horario"><fmt:message key="agregarHorario.seccion"/> : </form:label>
                            <form:select id="slcHorario"
                                         path="horario"
                                         items="${listaHorario}"
                                         itemLabel="seccion"
                                         itemValue="idHorario"/>
                            <form:errors path="horario" cssClass="claseError" />
                        </div>
                        <br/>
                        <input type="submit" value='<fmt:message key="agregarCurso.titulo" />' />
                    </fieldset>
                </form:form>
            </c:otherwise>
        </c:choose>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
        <script type="text/javascript">
            //Cambio combo AsignacionEstudianteCarrera
            $(document).ready(function() {
                $('#slcAEC').change(function() {
                    alert('AEC: ' + $(this).val());
                });

                //Cambio combo curso
                $('#slcCurso').change(function() {
                    getHorarios($(this).val());
                });

                //Cambio combo curso
                $('#slcHorario').change(function() {
                    alert('HORARIO: ' + $(this).val());
                });
            });

            function getHorarios(value) {
                $.getJSON("getHorario.htm", { idCurso: value }, function(lstHorario) {
                    var options = '';
                    $.each(lstHorario, function () {
                        options += "<option value='" + lstHorario[0].idHorario + "'>" + lstHorario[0].seccion + "</option>";
                    });
                    $('#slcHorario').html(options);
                });
            }
        </script>
    </body>
</html>
