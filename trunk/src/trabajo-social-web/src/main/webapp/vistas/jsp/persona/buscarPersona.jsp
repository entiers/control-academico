<%-- 
    Document   : buscarPersona
    Created on : Feb 22, 2011, 11:44:16 PM
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
        <title><fmt:message key="buscarPersona.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarPersona.titulo"/></h1>
        <%-- formulario para ingresar los datos del calendario de actividades --%>
        <form:form modelAttribute="datosBusquedaPersona" method="post"
                   action="buscarPersona.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="registroPersonal" path="registroPersonal">
                        <fmt:message key="agregarPersona.registroPersonal"/>:
                    </form:label>
                    <form:input path="registroPersonal" cssStyle="width:250px;"/>                        
                    <form:errors path="registroPersonal" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="nombre" path="nombre">
                        <fmt:message key="agregarPersona.nombre"/>:
                    </form:label>
                    <form:input path="nombre" cssStyle="width:250px;"/>
                    <form:errors path="nombre" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="habilitado" path="habilitado">
                        <fmt:message key="editarPersona.habilitado"/>:
                    </form:label>
                    <form:checkbox path="habilitado" />
                    <form:errors path="habilitado" cssClass="claseError" />
                </div>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>'/>
            </fieldset>
        </form:form>


        <fieldset>
            <legend><fmt:message key="buscarPersona.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content" name="listadoPersonas" id="persona" requestURI="buscarPersonaPag.htm" pagesize="2">
                <display:column property="nombre" titleKey="agregarPersona.nombre" />
                <display:column property="usuario.nombreUsuario" titleKey="usuario.nombreUsuario" />
                <display:column property="registroPersonal" titleKey="agregarPersona.registroPersonal" />
                <display:column property="direccion" titleKey="agregarPersona.direccion" />
                <display:column property="telefono" titleKey="agregarPersona.telefono" />
                <display:column property="celular" titleKey="agregarPersona.celular" />


                <sec:authorize access="hasAnyRole('ROLE_EDITAR_PERSONA')">
                    <display:column titleKey="acciones" style="text-align:center;">
                        <a href="editarPersona.htm?idPersona=${persona.idPersona}">
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
