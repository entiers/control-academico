<%--
    Document   : asignacionPorEstudiante
    Created on : 27/01/2011, 09:48:37 PM
    Author     : Carlos Solorzano
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
        <title><fmt:message key="miscursos.asignados"/></title>
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="rptAsignacionPrimerIngreso.titulo" /></h1>

        <form:form modelAttribute="datosRptAsignacionPrimerIngreso" method="post" >
            <fieldset>
                <div id="divCampos">
                    <form:label for="nombreUsuario" path="nombreUsuario"><fmt:message key="buscarUsuario.nombreUsuario"/>: </form:label>
                    <form:input path="nombreUsuario" cssStyle="width:250px;" />
                    <form:errors path="nombreUsuario" cssClass="claseError" />
                </div>
                <div id="divCampos">
                    <form:label for="fechaInicio" path="fechaInicio"><fmt:message key="rptAsignacionPrimerIngreso.fechaIncio"/>: </form:label>
                    <form:input path="fechaInicio" cssClass="datepicker" cssStyle="width:250px;" readonly="true" />
                    <form:errors path="fechaInicio" cssClass="claseError" />
                </div>
                <div id="divCampos">
                    <form:label for="fechaFin" path="fechaFin"><fmt:message key="rptAsignacionPrimerIngreso.fechaFin"/>: </form:label>
                    <form:input path="fechaFin" cssClass="datepicker" cssStyle="width:250px;" readonly="true" />
                    <form:errors path="fechaFin" cssClass="claseError" />
                </div>
                <input type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <c:choose>
            <c:when test="${post}">
                <fieldset>
                    <legend><fmt:message key="reportes.resultado"/></legend>
                    <table align="center">
                        <thead>
                            <tr>
                                <th><fmt:message key="asingacionPrimerIngreso.codigo"/></th>
                                <th><fmt:message key="buscarUsuario.nombreUsuario"/></th>
                                <th><fmt:message key="rptAsignacionPrimerIngreso.fechaIncio"/></th>
                                <th><fmt:message key="rptAsignacionPrimerIngreso.fechaFin"/></th>
                                <th><fmt:message key="asignacion.detalle"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listadoAPI}" var="api">
                                <tr>
                                    <td>${api.idAsignacionPrimerIngreso}</td>
                                    <td>${api.usuario.nombreUsuario}</td>
                                    <td>${api.fechaInicio}</td>
                                    <td>${api.fechaFin}</td>
                                    <td align="center">
                                        <form:form action="generarReporte.htm" method="post">
                                            <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}" />

                                            <input type="hidden" name="nombreParametro" value="idAsignacionPrimerIngreso" />
                                            <input type="hidden" name="valorParametro" value="${api.idAsignacionPrimerIngreso}" />
                                            <input type="hidden" name="tipoParametro" value="integer" />

                                            <input type="submit" value="Imprimir"/>
                                        </form:form>                                        
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </fieldset>
            </c:when>
        </c:choose>
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>

