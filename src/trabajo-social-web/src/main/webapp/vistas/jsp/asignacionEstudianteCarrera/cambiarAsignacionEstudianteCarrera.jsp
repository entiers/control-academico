<%-- 
    Document   : cambiarAsignacionEstudianteCarrera
    Created on : 25/03/2011, 09:47:55 PM
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
        <title><fmt:message key="cambiarAsignacionEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="cambiarAsignacionEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <fieldset>
            <legend></legend>
            <div id="divCampos">
            <label>Carrera Antigua:</label> <span>${asignacionEstudianteCarrera.carrera.codigoNombre}</span>
        </div>
        </fieldset>

        <fieldset>
            <legend></legend>
            <form:form id="form"
                       modelAttribute="wrapperAgregarAsignacionEstudianteCarrera" method="POST"
                       action="cambiarAsignacionEstudianteCarrera.htm">

                <%@include file="../../jspf/formularios/formularioAsignacionEstudianteCarrera.jspf" %>

                <input type="submit" value="<fmt:message key="btnAceptar"/>" />
            </form:form>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
