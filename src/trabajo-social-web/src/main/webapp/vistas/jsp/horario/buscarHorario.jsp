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
    </head>
    <body>
        <h1><fmt:message key="buscarHorario.titulo"/></h1>

        <%-- formulario para ingresar los datos del horario --%>
        <form:form modelAttribute="datosBusquedaHorario" method="post">
            <fieldset>
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

                <display:table class="ui-widget ui-widget-content" name="listadoHorarios" id="horario" requestURI="buscarHorario.htm" pagesize="10" >
                <display:column property="curso.nombre" titleKey="agregarHorario.curso" />
                <display:column property="horarioDiasAsString" titleKey="agregarHorario.dia" />
                <display:column property="seccion" titleKey="agregarHorario.seccion" />
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