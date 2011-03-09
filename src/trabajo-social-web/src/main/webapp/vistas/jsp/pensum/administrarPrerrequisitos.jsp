<%-- 
    Document   : administrarPrerrequisitos
    Created on : Mar 8, 2011, 11:33:40 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
    <head>        
        <title><fmt:message key="administrarPrerrequisitos.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="administrarPrerrequisitos.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionPensum.jspf" %>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionAsignacionCursoPensum.jspf" %>

        <div style="margin-bottom: 20px;">
            <c:choose>
                <c:when test="${not empty listadoCursosDePensum}">
                    <fieldset>
                        <legend><fmt:message key="administracionPrerrequisitos.cursos.prerrequisitos" /></legend>
                        <form:form modelAttribute="asignacionCursoPensum" method="post">

                            <div style="margin-bottom: 20px;">
                                <form:checkboxes path="asignacionCursoPensumsForIdCursoPensumPrerequisito" items="${listadoCursosDePensum}"
                                                 itemLabel="cursoNumeroSemestre" itemValue="idAsignacionCursoPensum" delimiter="<br/>" />

                            </div>
                            <input type="submit" value="<fmt:message key="btnActualizar"/>"/>

                        </form:form>
                    </fieldset>
                </c:when>
                <c:otherwise>
                    <b><fmt:message key="administrarPrerrequisitos.listadoCursosDePensumVacio" /></b>
                </c:otherwise>
            </c:choose>
        </div>

        <a href="asignarCursoPensum.htm?idPensum=${pensum.idPensum}"><fmt:message key="asignacionCursoPensum.regresar"/></a>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
