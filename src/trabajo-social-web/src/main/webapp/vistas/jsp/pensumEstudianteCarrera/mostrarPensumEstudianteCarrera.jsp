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
        <%@include file="../../jspf/plantilla/scriptFormularioPopup.jspf" %>

        <script type="text/javascript">

            $(function(){
                $("#divForm2").dialog({
                    autoOpen: ${autoOpenDialog},
                    modal: true,
                    width: 550,

                    buttons: {
                        "<fmt:message key="btnSalir" />" : function() {
                            $("#form2 #idPensumOriginal").val("");
                            $("#form2 #codigoPensumOriginal").val("");
                            $(this).dialog('close');
                        },
                        "<fmt:message key="btnAceptar" />" : function() {
                            $("#form2").submit();
                        }
                    }
                });

            });
            var llenarFormularioParaEquivalencias = function(idPensumOriginal, codigoPensumOriginal){

                $("#divForm2").dialog("open");
                $("#form2 #idPensumOriginal").val(idPensumOriginal);
                $("#form2 #codigoPensumOriginal").val(codigoPensumOriginal);
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="mostrarPensumEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        
        <c:if test="${pensum.idPensum != 0}">
            <c:set scope="request" var="legendPensum" value="pensum.informacion.valido"/>
            <%@include file="../../jspf/formularios/informacion/formularioInformacionPensum.jspf" %>
        </c:if>

        
        <div style="text-align:center; margin-bottom: 20px; ">
          <sec:authorize access="hasRole('ROLE_AGREGAR_PENSUM_ESTUDIANTE_CARRERA')"> 
                <c:if test="${not empty listadoPensumsNoAsignadosAEsutudianteCarrera}">
                    <c:if test="${not empty pensumEstudianteCarrera}">
                        <div id="divFormularioPopup" title="<fmt:message key='asignarPensumEstudianteCarrera.titulo' />" >
                            <form:form id="form" modelAttribute="pensumEstudianteCarrera" method="post" action="asignarPensumEstudianteCarrera.htm">
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
                        <button id="botonHabilitarFormularioPopup"><fmt:message key="mostrarPensumEstudianteCarrera.boton.asignarPensum" /></button>
                    </c:if>
                </c:if>
          </sec:authorize> 

            <c:if test="${not empty pensum}">
                <sec:authorize access="hasRole('ROLE_ELIMINAR_PENSUM_ESTUDIANTE_CARRERA')">
                    <button onclick="location.href='eliminarPensumEstudianteCarrera.htm?idPensumEstudianteCarrera=${idPensumEstudianteCarreraValido}'">
                        <fmt:message key="mostrarPensumEstudianteCarrera.boton.eliminarPensum" />
                    </button>
                </sec:authorize>
            </c:if>
        </div>



        <center>
            <fieldset>
                <legend><fmt:message key="mostrarPensumEstudianteCarrera.listadoPensumsNoValidos" /></legend>
                <display:table class="ui-widget ui-widget-content"
                               name="listadoPensumEstudianteCarreraNoValidos" id="pensumEstudianteCarrera">
                    <display:column property="pensum.codigo" titleKey="pensumEstudianteCarrera.pensum" />
                    <display:column titleKey="pensumEstudianteCarrera.fechaFin" style="text-align:center;">
                        <fmt:formatDate value="${pensumEstudianteCarrera.fechaFin}" pattern="dd-MM-yyyy" />
                    </display:column>
                    <sec:authorize access="hasAnyRole('ROLE_ELIMINAR_PENSUM_ESTUDIANTE_CARRERA', 'ROLE_REALIZAR_EQUIVALENCIAS')">
                        <display:column titleKey="acciones" style="text-align:center;">
                            <sec:authorize access="hasRole('ROLE_ELIMINAR_PENSUM_ESTUDIANTE_CARRERA')">
                                <a href="eliminarPensumEstudianteCarrera.htm?idPensumEstudianteCarrera=${pensumEstudianteCarrera.idPensumEstudianteCarrera}">
                                    <fmt:message key="mostrarPensumEstudianteCarrera.link.eliminar"/>
                                </a> <br/>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_REALIZAR_EQUIVALENCIAS')">
                                <a style="cursor: pointer"
                                   onclick="javascript:llenarFormularioParaEquivalencias(${pensumEstudianteCarrera.pensum.idPensum}, '${pensumEstudianteCarrera.pensum.codigo}')">
                                    <fmt:message key="cursoPensum.link.equivalencias.pensums" />
                                </a>
                            </sec:authorize>
                        </display:column>
                    </sec:authorize>
                </display:table>
            </fieldset>
        </center>

        <c:if test="${wrapperEquivalenciaPorPensum != null}">
            <div id="divForm2" title="<fmt:message key="cursoPensum.link.equivalencias.pensums" />">
                <form:form id="form2" action="mostrarParaRealizarEquivalenciaPorPensum.htm"
                           method="post" modelAttribute="wrapperEquivalenciaPorPensum">
                    <div id="divCampos">
                        <form:label for="pensumOriginal.idPensum" path="pensumOriginal.idPensum">
                            <fmt:message key="pensumEstudianteCarrera.pensum"/>: *
                        </form:label>
                        <form:hidden path="pensumOriginal.idPensum" id="idPensumOriginal" />
                        <input type="text" readonly="true" id="codigoPensumOriginal" />
                        <c:set scope="session" var="estudiante" value="${estudiante}" />
                        <form:hidden path="asignacionEstudianteCarrera.idAsignacionEstudianteCarrera"/>
                        <form:hidden path="pensumEquivalencia.idPensum" />
                    </div>
                </form:form>
            </div>
        </c:if>

        <div style="margin: 20px 0 0 0;">
            <a href="mostrarAsignacionEstudianteCarrera.htm?idEstudiante=${estudiante.idEstudiante}">
                <fmt:message key="mostrarPensumEstudianteCarrera.regresar"/>
            </a>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
