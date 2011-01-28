<%-- 
    Document   : buscarCalendarioActividades
    Created on : Jun 7, 2010, 6:07:03 PM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
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
        <title><fmt:message key="buscarCalendarioActividades.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptCalendarioActividades.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarCalendarioActividades.titulo"/></h1>

        <%-- formulario para ingresar los datos del calendario de actividades --%>
        <form:form modelAttribute="datosBusquedaCalendarioActividades" method="post">
            <fieldset>
                <div id="divCampos">
                    <form:label for="semestre.idSemestre" path="semestre.idSemestre">
                        <fmt:message key="agregarCalendarioActividades.semestre"/>: *
                    </form:label>

                    <form:select path="semestre.idSemestre" cssStyle="width: 250px;">
                        <form:option value="" label="Seleccionar un valor" />
                        <form:options items="${listadoSemestres}" itemValue="idSemestre" itemLabel="anyoNumero" />
                    </form:select>
                        
                    <form:errors path="semestre.idSemestre" cssClass="claseError" />
                    
                </div>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>'/>
            </fieldset>
        </form:form>


        <fieldset>
            <legend><fmt:message key="buscarCalendarioActividades.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content" name="listadoCalendarioActividades" id="calendarioActividades" requestURI="buscarCalendarioActividades.htm">
                <display:column titleKey="agregarCalendarioActividades.fechaInicio" style="text-align:center;">
                    <fmt:formatDate value="${calendarioActividades.fechaInicio}" pattern="dd-MM-yyyy" />
                </display:column>
                <display:column titleKey="agregarCalendarioActividades.fechaFin" style="text-align:center;">
                    <fmt:formatDate value="${calendarioActividades.fechaFin}" pattern="dd-MM-yyyy" />
                </display:column>
                <display:column property="actividad" titleKey="agregarCalendarioActividades.actividad" />

                <display:column property="tipoActividad.descripcion" titleKey="agregarCalendarioActividades.tipoActividad" />

                <sec:authorize access="hasAnyRole('ROLE_EDITAR_CALENDARIO_ACTIVIDAD')">
                    <display:column titleKey="acciones" style="text-align:center;">
                        <a href="editarCalendarioActividades.htm?idCalendarioActividades=${calendarioActividades.idCalendarioActividades}">
                            <fmt:message key="editarCalendarioActividades.editar"/>
                        </a>
                    </display:column>
                </sec:authorize>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
