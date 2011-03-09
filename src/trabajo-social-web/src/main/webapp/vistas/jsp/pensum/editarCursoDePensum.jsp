<%-- 
    Document   : editarCursoDePensum
    Created on : Mar 8, 2011, 9:39:47 AM
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
        <title><fmt:message key="editarCursoDePensum.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptCursoPensum.jspf" %>
    </head>
    <body>
         <h1><fmt:message key="editarCursoDePensum.titulo"/></h1>

         <%@include file="../../jspf/formularios/informacion/formularioInformacionPensum.jspf" %>

        <%-- formulario para ingresar los datos del pensum --%>
        <form:form modelAttribute="wrapperAsignacionCursoPensum" method="post">
            <fieldset>
                <div id="divCampos">
                    <label><fmt:message key="editarCursoDePensum.curso"/>:</label> <span>${wrapperAsignacionCursoPensum.curso.codigoNombre}</span>
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioAsignarCursoPensum.jspf" %>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
                <a href="asignarCursoPensum.htm?idPensum=${pensum.idPensum}"><fmt:message key="asignacionCursoPensum.regresar" /></a>
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
