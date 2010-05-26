<%-- 
    Document   : editarEstudiante
    Created on : 30/04/2010, 08:43:01 PM
    Author     : Daniel Castillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="editarEstudiante.titulo"/></title>
        <%@include file="../../jspf/estudiante/scripts.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarEstudiante.titulo"/></h1>
        
        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaEstudiante" method="post" action="buscarEditarEstudiante.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="carneBusqueda" path="carneBusqueda"><fmt:message key="agregarEstudiante.carne"/>:</form:label>
                    <form:input path="carneBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="carneBusqueda" cssClass="claseError" />
                </div>
                <br/>

                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <%-- formulario para editar los datos del estudiante --%>
        <form:form modelAttribute="wrapperEstudiante" method="post" action="editarEstudiante.htm">
            <fieldset>
                <%-- se deshabilita para evitar su edicion --%>
                <div id="divCampos">
                    <form:label for="carne" path="carne"><fmt:message key="agregarEstudiante.carne"/>: *</form:label>
                    <form:input path="carne" cssStyle="width: 250px;" readonly="true" />
                    <form:errors path="carne" cssClass="claseError" />
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/estudiante/formularioEstudiante.jspf" %>

                <%-- boton --%>
                <input id="btnEditar" type="submit" value='<fmt:message key="btnEditar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/mensaje/resultado.jspf" %>
    </body>
</html>
