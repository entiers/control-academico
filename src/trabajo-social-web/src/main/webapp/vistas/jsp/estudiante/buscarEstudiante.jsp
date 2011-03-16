<%--
    Document   : buscarEstudiante
    Created on : 3/05/2010, 11:34:08 AM
    Author     : Daniel Castillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="buscarEstudiante.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptEstudiante.jspf" %>        
    </head>
    <body>
        <h1><fmt:message key="buscarEstudiante.titulo"/></h1>

        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaEstudiante" method="post" action="buscarEstudiante.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="carneBusqueda" path="carneBusqueda"><fmt:message key="agregarEstudiante.carne"/>:</form:label>
                    <form:input path="carneBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="carneBusqueda" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="nombreBusqueda" path="nombreBusqueda"><fmt:message key="agregarEstudiante.nombre"/>:</form:label>
                    <form:input path="nombreBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="nombreBusqueda" cssClass="claseError" />
                </div>               
                <br/>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <fieldset>
            <legend><fmt:message key="buscarEstudiante.tituloListado"/></legend>
            
            <display:table class="ui-widget ui-widget-content" name="listadoEstudiantes"
                           id="estudiante" requestURI="buscarEstudiante.htm" pagesize="15" >
                <display:column property="carne" titleKey="agregarEstudiante.carne"  />
                <display:column property="nombre" titleKey="agregarEstudiante.nombre"  />
                <display:column titleKey="acciones" style="text-align: center;" >
                    <a href="mostrarAsignacionEstudianteCarrera.htm?idEstudiante=${estudiante.idEstudiante}">
                    <fmt:message key="mostrarAsignacionEstudianteCarrera.link"/>
                </a>
                </display:column>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
