<%-- 
    Document   : buscarDocumento
    Created on : 21/03/2011, 12:47:02 AM
    Author     : Carlos Solorzano
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
        <title><fmt:message key="buscarDocumento.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarDocumento.titulo"/></h1>
        <%-- formulario para ingresar los datos del calendario de actividades --%>
        <form:form modelAttribute="datosBusquedaDocumento" method="post">
            <fieldset>
                <div id="divCampos">
                    <form:label for="nombre" path="nombre">
                        <fmt:message key="agregarPersona.nombre"/>:
                    </form:label>
                    <form:input path="nombre" cssStyle="width:250px;"/>
                    <form:errors path="nombre" cssClass="claseError" />
                </div>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>'/>
            </fieldset>
        </form:form>


        <fieldset>
            <legend><fmt:message key="buscarDocumento.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content"
                           name="listadoDocumentos"
                           id="documento" requestURI="buscarDocumentoPag.htm" pagesize="15">
                <display:column property="nombre" titleKey="agregarPersona.nombre" />
                <display:column property="descripcion" titleKey="agregarTipoAsignacion.descripcion" />


                <sec:authorize access="hasAnyRole('ROLE_EDITAR_DOCUMENTO')">
                    <display:column titleKey="acciones" style="text-align:center;">
                        <a href="editarDocumento.htm?idDoc=${documento.idDocumento}">
                            <fmt:message key="editarDocumento.editar"/>
                        </a>
                    </display:column>
                </sec:authorize>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>

    </body>
</html>
