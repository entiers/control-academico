<%-- 
    Document   : mostrarParaRealizarEquivalencia
    Created on : Jun 22, 2011, 10:05:46 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="cursoPensum.titulo.realizarEquivalencias" /></title>
    </head>
    <body>
        <h1><fmt:message key="cursoPensum.titulo.realizarEquivalencias" /></h1>


        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <fieldset>
            <legend><fmt:message key="cursoPensum.label.realizarEquivalencias"/></legend>
            <display:table class="ui-widget ui-widget-content" 
                           name="listadoEquivalencias" id="asignacionCursoPensum"
                           style="width:100%">
                <display:column property="curso.codigo" titleKey="agregarCurso.codigo"
                                style="width: 30%; align:center;" />
                <display:column property="curso.nombre" titleKey="agregarCurso.nombre" 
                                style="width: 70%"/>
            </display:table>
        </fieldset>

        <fieldset>
            <form:form modelAttribute="wrapperAsignacionEquivalencia"
                       action="realizarEquivalencias.htm" method="POST">
                <div class="divCampos">
                    <form:label for="acuerdoNumero" path="acuerdoNumero">
                        <fmt:message key="asignacionEsquivalencia.acuerdoNumero"/>: *
                    </form:label>
                    <form:input path="acuerdoNumero" cssStyle="width: 250px;" />
                    <form:errors path="acuerdoNumero" cssClass="claseError" />
                </div>
                <div class="divCampos">
                    <form:label for="observaciones" path="observaciones">
                        <fmt:message key="asignacionEsquivalencia.observaciones"/>: *
                    </form:label>
                    <form:textarea path="observaciones" cssStyle="width: 250px;" />
                    <form:errors path="observaciones" cssClass="claseError" />
                </div>
                <form:hidden path="asignacionEstudianteCarrera.idAsignacionEstudianteCarrera" />

                <input type="submit" value="<fmt:message key="btnAceptar"/>">
            </form:form>
        </fieldset>
    </body>
</html>
