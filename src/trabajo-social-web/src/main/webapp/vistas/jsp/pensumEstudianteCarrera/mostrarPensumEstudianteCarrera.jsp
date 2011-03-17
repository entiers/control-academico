<%-- 
    Document   : mostrarPensumEstudianteCarrera
    Created on : Mar 15, 2011, 10:50:32 AM
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
        <title><fmt:message key="mostrarPensumEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>

        <script type="text/javascript">
            $(function (){
                $("#formularioAsignarPensum").dialog({
                    autoOpen: ${autoOpenDialogAsignar},
                    modal: true,
                    width: 550,

                    buttons: {
                        "<fmt:message key="btnSalir" />" : function() {
                            $(this).dialog('close');
                        },
                        "<fmt:message key="btnAceptar" />" : function() {
                            $("form").submit();
                        }                        
                    }
                });

                $("#botonAsignarPensum").click( function(){
                    $("#formularioAsignarPensum").dialog("open");
                });

            });
        </script>

    </head>
    <body>
        <h1><fmt:message key="mostrarPensumEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <c:if test="${not empty pensum}">
            <c:set scope="request" var="legendPensum" value="pensum.informacion.valido"/>
            <%@include file="../../jspf/formularios/informacion/formularioInformacionPensum.jspf" %>
        </c:if>

        <div id="formularioAsignarPensum" title="<fmt:message key='asignarPensumEstudianteCarrera.titulo' />" >
            <form:form modelAttribute="pensumEstudianteCarrera" method="post" action="asignarPensumEstudianteCarrera.htm">
                <div id="divCampos">
                    <form:label for="pensum.idPensum" path="pensum.idPensum">
                        <fmt:message key="pensumEstudianteCarrera.pensum"/>: *
                    </form:label>
                    <form:select path="pensum.idPensum" cssStyle="width: 250px;">
                        <form:option  value="" label="Seleccionar un valor" />
                        <form:options items="${listadoPensumsNoAsignadosAEsutudianteCarrera}" itemValue="idPensum" itemLabel="codigo" />
                    </form:select>
                    <form:errors path="pensum.idPensum" cssClass="claseError" />
                </div>                
            </form:form>
        </div>


        <c:if test="${not empty listadoPensumsNoAsignadosAEsutudianteCarrera}">
            <div style="text-align:center; margin-bottom: 20px; ">
                <button id="botonAsignarPensum"><fmt:message key="mostrarPensumEstudianteCarrera.boton.asignarPensum" /></button>
            </div>
        </c:if>


        <center>
            <fieldset>
                <legend><fmt:message key="mostrarPensumEstudianteCarrera.listadoPensumsNoValidos" /></legend>
                <display:table class="ui-widget ui-widget-content" name="listadoPensumEstudianteCarreraNoValidos" id="pensumEstudianteCarrera">
                    <display:column property="pensum.codigo" titleKey="pensumEstudianteCarrera.pensum"/>
                    <display:column titleKey="pensumEstudianteCarrera.fechaFin" style="text-align:center;">
                        <fmt:formatDate value="${pensumEstudianteCarrera.fechaFin}" pattern="dd-MM-yyyy" />
                    </display:column>
                </display:table>
            </fieldset>
        </center>


        <div style="margin: 20px 0 0 0;">
            <a href="mostrarAsignacionEstudianteCarrera.htm?idEstudiante=${estudiante.idEstudiante}"><fmt:message key="mostrarPensumEstudianteCarrera.regresar"/></a>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
