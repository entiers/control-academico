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
        <%@include file="../../jspf/plantilla/scriptFormularioModificarPopup.jspf" %>

        <script type="text/javascript">
            var modificar = function(idHistorialAsignacionEstudianteCarrera, fechaInscripcion, idSituacion, idSemestre){                
                $("#formModificar #idHistorialAsignacionEstudianteCarrera").val(idHistorialAsignacionEstudianteCarrera);
                $("#formModificar #fechaInscripcion").val(fechaInscripcion);
                $("#formModificar #situacion").val(idSituacion);
                $("#formModificar #semestre").val(idSemestre);

                $("#divFormularioModificarPopup").dialog("open");
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionAsignacionEstudianteCarrera.jspf" %>        

        <%--  Listado --%>
        <center>
            <fieldset>
                <legend><fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.listado" /></legend>
                <display:table class="ui-widget ui-widget-content" name="asignacionEstudianteCarrera.historialAsignacionEstudianteCarreras"
                               id="historialAsignacionEstudianteCarrera">
                    <display:column titleKey="historialAsignacionEstudianteCarrera.fechaInscripcion"  style="text-align:center;">
                        <fmt:formatDate value="${historialAsignacionEstudianteCarrera.fechaInscripcion}" pattern="dd-MM-yyyy" var="fechaInscripcion" />
                        ${fechaInscripcion}
                    </display:column>
                    <display:column property="situacion.codigoNombre" titleKey="historialAsignacionEstudianteCarrera.situacion" style="text-align:center;"/>
                    <display:column property="semestre.anyoNumero" titleKey="historialAsignacionEstudianteCarrera.semestre" style="text-align:center;" />
                    <display:column titleKey="acciones" style="text-align:center;" >
                        <a class="a-acciones"onclick="modificar(${historialAsignacionEstudianteCarrera.idHistorialAsignacionEstudianteCarrera}, '${fechaInscripcion}', ${historialAsignacionEstudianteCarrera.situacion.idSituacion}, ${historialAsignacionEstudianteCarrera.semestre.idSemestre})">
                            <fmt:message key="historialAsignacionEstudianteCarrera.boton.modificar" />
                        </a>

                        <br/>
                        <a href="eliminarHistorialAsignacionEstudianteCarrera.htm?idHistorialAsignacionEstudianteCarrera=${historialAsignacionEstudianteCarrera.idHistorialAsignacionEstudianteCarrera}&idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                            <fmt:message key="btnBorrar" />
                        </a>
                    </display:column>
                </display:table>
            </fieldset>
        </center>

        <%--  Formulario para agregar  --%>
        <c:if test="${not empty wrapperHistorialAsignacionEstudianteCarrera}">
            <div id="divFormularioPopup" title="<fmt:message key='agregarHistorialAsignacionEstudianteCarrera.titulo' />">
                <form:form id="form"
                           modelAttribute="wrapperHistorialAsignacionEstudianteCarrera" method="POST"
                           action="agregarHistorialAsignacionEstudianteCarrera.htm">

                    <%@include file="../../jspf/formularios/formularioHistorialAsignacionEstudianteCarrera.jspf" %>
                </form:form>
            </div>
            <center style="margin-top: 20px;">
                <button id="botonHabilitarFormularioPopup"><fmt:message key="historialAsignacionEstudianteCarrera.boton.agregar" /></button>
            </center>
        </c:if>

        <%--  Formulario para modificar --%>
        <c:if test="${not empty wrapperModificarHistorialAsignacionEstudianteCarrera}">
            <div id="divFormularioModificarPopup" title="<fmt:message key='modificarHistorialAsignacionEstudianteCarrera.titulo' />">
                <form:form id="formModificar"
                           modelAttribute="wrapperModificarHistorialAsignacionEstudianteCarrera" method="POST"
                           action="modificarHistorialAsignacionEstudianteCarrera.htm">

                    <input type="hidden" name="idHistorialAsignacionEstudianteCarrera" id="idHistorialAsignacionEstudianteCarrera"
                           value="${idHistorialAsignacionEstudianteCarrera}"/>

                    <%@include file="../../jspf/formularios/formularioHistorialAsignacionEstudianteCarrera.jspf" %>


                </form:form>
            </div>
        </c:if>


        <div style="margin: 20px 0 0 0; ">
            <a href="mostrarAsignacionEstudianteCarrera.htm?idEstudiante=${estudiante.idEstudiante}">
                <fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.regresar"/></a>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>


