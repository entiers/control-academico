<%-- 
    Document   : buscarHorario
    Created on : May 12, 2010, 10:23:53 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="buscarHorario.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
        <%@include file="../../jspf/scripts/scriptPopupReportes.jspf" %>

        <script type="text/javascript">
             var validar = function(){
                if($("#valorParametro").attr("value") != ""){
                    return true;
                }
                
                $("#errorFormulario").dialog("open");
                return false;
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="buscarHorario.titulo"/></h1>

        <div class="contenedor-reporte-arriba">
            <div id="errorFormulario" title="Error"><fmt:message key="horario.errorFormulario" /></div>

            <div id="verReporte" title="<fmt:message key="horario.verReporte" />">
                <form:form  id="formVerReporte" action="generarReporte.htm" method="POST" target="_BLANK">
                    <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}" />

                    <div id="divCampos">
                        <input type="hidden" name="nombreParametro" value="ID_SEMESTRE" />

                        <label><fmt:message key="agregarHorario.semestre"/>: *</label>
                        <select name="valorParametro" id="valorParametro" style="width: 250px;">
                            <option value="">Seleccionar un valor</option>
                            <c:forEach items="${listadoSemestres}" var="semestre">
                                <option value="${semestre.idSemestre}">${semestre.anyoNumero}</option>>
                            </c:forEach>
                        </select>                        
                        <input type="hidden" name="tipoParametro" value="integer" />
                    </div>

                    
                </form:form>
            </div>
            <button id="botonVerReporte"> <fmt:message key="horario.verReporte" /></button>
        </div>



        <%-- formulario para ingresar los datos del horario --%>
        <form:form modelAttribute="datosBusquedaHorario" method="post">
            <fieldset>
                <legend><fmt:message key="legendaFormularioBusqueda" /></legend>
                <div id="divCampos">
                    <form:label for="salon.idSalon" path="salon.idSalon">
                        <fmt:message key="agregarHorario.salon"/>: *
                    </form:label>

                    <form:select path="salon.idSalon" cssStyle="width: 250px;">
                        <form:option  value="" label="Seleccionar un valor" />
                        <form:options items="${listadoSalones}" itemValue="idSalon" itemLabel="numeroEdificio" />
                    </form:select>
                    <form:errors path="salon.idSalon" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="semestre.idSemestre" path="semestre.idSemestre">
                        <fmt:message key="agregarHorario.semestre"/>: *
                    </form:label>

                    <form:select path="semestre.idSemestre" cssStyle="width: 250px;">
                        <form:option  value="" label="Seleccionar un valor" />
                        <form:options items="${listadoSemestres}" itemLabel="anyoNumero" itemValue="idSemestre" />
                    </form:select>
                    <form:errors path="semestre.idSemestre" cssClass="claseError" />
                </div>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>

        <fieldset>
            <legend><fmt:message key="buscarHorario.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content" name="listadoHorarios" id="horario" requestURI="buscarHorarioPag.htm"
                           pagesize="${pageSize}" >
                <display:column property="curso.nombre" titleKey="agregarHorario.curso" />
                <display:column property="horarioDiasAsString" titleKey="agregarHorario.dia" />
                <display:column property="seccion" titleKey="agregarHorario.seccion" style="text-align: center;"/>
                <display:column titleKey="agregarHorario.horaInicio" >
                    <fmt:formatDate pattern="hh:mm" value="${horario.horaInicio}" />
                </display:column>
                <display:column titleKey="agregarHorario.horaFin">
                    <fmt:formatDate pattern="hh:mm" value="${horario.horaFin}" />
                </display:column>
                <display:column titleKey="agregarHorario.habilitado">
                    <c:choose>
                        <c:when test="${horario.habilitado}">SI</c:when>
                        <c:otherwise>NO</c:otherwise>
                    </c:choose>                    
                </display:column>

                <sec:authorize access="hasAnyRole('ROLE_EDITAR_HORARIO')">
                    <display:column titleKey="acciones" style="text-align:center;">
                        <a href="editarHorario.htm?idHorario=${horario.idHorario}">
                            <fmt:message key="editarPersona.editar"/>
                        </a>
                    </display:column>
                </sec:authorize>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>