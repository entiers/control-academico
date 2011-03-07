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
            <c:when test="${cursoComputacion}">
                <label><fmt:message key="miscursos.asignacionCursos.cursoCompuNoAprobado"/></label>
            </c:when>
            <c:otherwise>
                <form:form method="post" modelAttribute="datosAsignacion">
                    <fieldset>
                        <legend><fmt:message key="agregarEstudiante.carrera"/></legend>
                        <div id="divCampos">
                            <form:select path="idAsignacionEstudianteCarrera"
                                         items="${listaAEC}"
                                         itemLabel="carrera.nombre"
                                         itemValue="idAsignacionEstudianteCarrera"/>
                            <form:errors path="idAsignacionEstudianteCarrera" cssClass="claseError" />
                            <form:hidden path="tipoAsignacion" />
                        </div>
                        <br/>
                        <input type="submit" value='<fmt:message key="miscursos.asignacionCursos.carrera" />' />
                    </fieldset>
                </form:form>
            </c:otherwise>
        </c:choose>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
