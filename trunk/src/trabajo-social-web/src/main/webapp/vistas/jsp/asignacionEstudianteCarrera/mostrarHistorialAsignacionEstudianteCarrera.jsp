<%-- 
    Document   : mostrarHistorialAsignacionEstudianteCarrera
    Created on : Mar 21, 2011, 7:07:40 PM
    Author     : Mario Batres
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
        <title><fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
        <%@include file="../../jspf/plantilla/scriptFormularioPopup.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionAsignacionEstudianteCarrera.jspf" %>        

        <center>
            <fieldset>
                <legend><fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.listado" /></legend>
                <display:table class="ui-widget ui-widget-content" name="asignacionEstudianteCarrera.historialAsignacionEstudianteCarreras"
                               id="historialAsignacionEstudianteCarrera">
                    <display:column titleKey="historialAsignacionEstudianteCarrera.fechaInscripcion"  style="text-align:center;">
                        <fmt:formatDate value="${historialAsignacionEstudianteCarrera.fechaInscripcion}" pattern="dd-MM-yyyy" />
                    </display:column>
                    <display:column property="situacion.codigoNombre" titleKey="historialAsignacionEstudianteCarrera.situacion" style="text-align:center;"/>
                    <display:column property="semestre.anyoNumero" titleKey="historialAsignacionEstudianteCarrera.semestre" style="text-align:center;" />
                </display:table>
            </fieldset>
        </center>

        <div id="divFormularioPopup" title="<fmt:message key='agregarHistorialAsignacionEstudianteCarrera.titulo' />">
            <form:form id="form"
                       modelAttribute="wrapperHistorialAsignacionEstudianteCarrera" method="POST"
                       action="agregarHistorialAsignacionEstudianteCarrera.htm">


                <div id="divCampos">
                    <form:label for="fechaInscripcion" path="fechaInscripcion">
                        <fmt:message key="historialAsignacionEstudianteCarrera.fechaInscripcion"/>:
                    </form:label>
                    <form:input path="fechaInscripcion" cssStyle="width: 250px;" cssClass="datepicker"/>
                    <form:errors path="fechaInscripcion" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="situacion.idSituacion" path="situacion.idSituacion">
                        <fmt:message key="historialAsignacionEstudianteCarrera.situacion"/>: *
                    </form:label>
                    <form:select path="situacion.idSituacion" cssStyle="width: 250px;">
                        <form:option  value="" label="Seleccionar un valor" />
                        <form:options items="${listadoSituaciones}" itemValue="idSituacion" itemLabel="codigoNombre" />
                    </form:select>
                    <form:errors path="situacion.idSituacion" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="semestre.idSemestre" path="semestre.idSemestre">
                        <fmt:message key="historialAsignacionEstudianteCarrera.semestre"/>: *
                    </form:label>
                    <form:select path="semestre.idSemestre" cssStyle="width: 250px;">
                        <form:option  value="" label="Seleccionar un valor" />
                        <form:options items="${listadoSemestres}" itemValue="idSemestre" itemLabel="anyoNumero" />
                    </form:select>
                    <form:errors path="semestre.idSemestre" cssClass="claseError" />
                </div>
            </form:form>
        </div>
        <center style="margin-top: 20px;">
            <button id="botonHabilitarFormularioPopup"><fmt:message key="historialAsignacionEstudianteCarrera.boton.agregar" /></button>
        </center>


        <div style="margin: 20px 0 0 0; ">
            <a href="mostrarAsignacionEstudianteCarrera.htm?idEstudiante=${estudiante.idEstudiante}">
                <fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.regresar"/></a>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>


