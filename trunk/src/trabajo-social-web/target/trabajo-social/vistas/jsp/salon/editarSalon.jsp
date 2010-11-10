<%-- 
    Document   : editarSalon
    Created on : 8/05/2010, 05:57:44 PM
    Author     : Mario Batres
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
        <title><fmt:message key="editarSalon.titulo"/></title>
        <c:set var="readOnly" scope="page" value="true" />
        <%@include file="../../jspf/scripts/scriptSalon.jspf" %>
        
    </head>
    <body>
        <h1><fmt:message key="editarSalon.titulo"/></h1>
        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaSalon" method="post" action="buscarEditarSalon.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="numero" path="numero"><fmt:message key="agregarSalon.numero"/>: *</form:label>
                    <form:input path="numero" cssStyle="width: 250px;" />
                    <form:errors path="numero" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="edificio" path="edificio"><fmt:message key="agregarSalon.edificio"/>: *</form:label>
                    <form:input path="edificio" cssStyle="width: 250px;" />
                    <form:errors path="edificio" cssClass="claseError" />
                </div>

                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>

        <br/><br/><br/>

        <%-- formulario para editar los datos del salon --%>
        <form:form modelAttribute="wrapperSalon" method="post" action="editarSalon.htm">
            <fieldset>
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioSalon.jspf" %>

                <%-- boton --%>
                <input id="btnEditar" type="submit" value='<fmt:message key="btnEditar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>

