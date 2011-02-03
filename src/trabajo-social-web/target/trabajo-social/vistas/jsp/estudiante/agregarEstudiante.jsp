<%--
    Document   : agregarEstudiante
    Created on : 28/04/2010, 03:31:57 PM
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
        <title><fmt:message key="agregarEstudiante.titulo"/></title>        
        <%@include file="../../jspf/scripts/scriptEstudiante.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="agregarEstudiante.titulo"/></h1>

        <%-- formulario para ingresar los datos del estudiante --%>
        <form:form modelAttribute="wrapperEstudiante" method="post">
            <fieldset>
                <%-- campo para ingresar el numero de carne --%>
                <div id="divCampos">
                    <form:label for="carne" path="carne"><fmt:message key="agregarEstudiante.carne"/>: *</form:label>
                    <form:input path="carne" cssStyle="width: 250px;" />
                    <form:errors path="carne" cssClass="claseError" />
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioEstudiante.jspf" %>
                
                <%-- selector para escoger la carrera --%>
                <%--
                <div id="divCampos">
                    <form: label for="asignacionEstudianteCarrera.carrera" path="asignacionEstudianteCarrera.carrera.idCarrera"><fmt:message key="agregarEstudiante.carrera"/>: *</form:label>
                    <form:select path="asignacionEstudianteCarrera.carrera.idCarrera" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <form:options items="${carreras}" itemValue="idCarrera" itemLabel="nombre" />
                    </form:select>
                    <form:errors path="asignacionEstudianteCarrera.carrera.idCarrera" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="asignacionEstudianteCarrera.semestre" path="asignacionEstudianteCarrera.semestre.idSemestre"><fmt:message key="agregarEstudiante.semestre"/>: *</form:label>
                    <form:select path="asignacionEstudianteCarrera.semestre.idSemestre" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <form:options items="${listadoSemestres}" itemValue="idSemestre" itemLabel="anio" />
                    </form:select>
                    <form:errors path="asignacionEstudianteCarrera.semestre.idSemestre" cssClass="claseError" />
                </div>--%>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>