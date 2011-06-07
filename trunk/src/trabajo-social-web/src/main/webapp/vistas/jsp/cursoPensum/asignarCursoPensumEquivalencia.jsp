<%-- 
    Document   : asignarCursoPensumEquivalencia
    Created on : May 23, 2011, 5:35:52 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="cursoPensum.asignarCursoPensumEquivalencia.titulo" /></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>

        <%@include file="../../jspf/scripts/scriptCursoPensumEquivalencia.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="cursoPensum.asignarCursoPensumEquivalencia.titulo" /></h1>

        <div>
            <form:form modelAttribute="wrapperCursoPensumEquivalencia" method="post">
                <div id="divCampos">
                    <form:label for="pensumOriginal.idPensum" path="pensumOriginal.idPensum">
                        <fmt:message key="cursoPensum.label.pensumOriginal"/>: *
                    </form:label>
                    <form:select id="slcPensumOriginal" path="pensumOriginal.idPensum" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <form:options items="${listadoPensums}" itemValue="idPensum" itemLabel="codigoCarreraNombre" />
                    </form:select>

                    <form:errors path="pensumOriginal.idPensum" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="cursoOriginal.idCurso" path="cursoOriginal.idCurso">
                        <fmt:message key="cursoPensum.label.cursoOriginal"/>: *
                    </form:label>
                    <form:select id="slcCursoOriginal" path="cursoOriginal.idCurso" cssStyle="width: 250px;" disabled="true"  cssClass="classDisabled">
                        <form:option  value="0" label="Seleccionar un valor" />
                    </form:select>

                    <form:errors path="cursoOriginal.idCurso" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="pensumEquivalente.idPensum" path="pensumEquivalente.idPensum">
                        <fmt:message key="cursoPensum.label.pensumEquivalente"/>: *
                    </form:label>
                    <form:select id="slcPensumEquivalente" path="pensumEquivalente.idPensum" cssStyle="width: 250px;" disabled="true" cssClass="classDisabled" >
                        <form:option  value="0" label="Seleccionar un valor" />
                    </form:select>

                    <form:errors path="pensumEquivalente.idPensum" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="cursoEquivalente.idCurso" path="cursoEquivalente.idCurso">
                        <fmt:message key="cursoPensum.label.cursoEquivalente"/>: *
                    </form:label>
                    <form:select id="slcCursoEquivalente" path="cursoEquivalente.idCurso" cssStyle="width: 250px;" disabled="true" cssClass="classDisabled">
                        <form:option  value="" label="Seleccionar un valor" />
                    </form:select>

                    <form:errors path="cursoEquivalente.idCurso" cssClass="claseError" />
                </div>

                <input type="submit" value="<fmt:message key='btnAceptar' />" />
            </form:form>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
