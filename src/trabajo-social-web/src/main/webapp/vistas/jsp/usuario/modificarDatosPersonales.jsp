<%--
    Document   : modificarDatosPersonales
    Created on : 1/12/2010, 03:56:13 PM
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
        <title><fmt:message key="modificarDatosPersonales.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptUsuario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="modificarDatosPersonales.titulo"/></h1>
        <c:choose>
            <c:when test="${error}">
                <div align="center">
                    <fmt:message key="editarDatosPersonales.usuarioSinDatos"/>
                </div>
            </c:when>
            <c:otherwise>
                <table width="100%" align="center" id="tablaUsuarios" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th colspan="2"><fmt:message key="modificarDatosPersonales.DatosFijos"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:choose>
                            <c:when test="${tipoEntidad=='estudiante'}">
                                <tr>
                                    <td><fmt:message key="agregarEstudiante.carne"/></td>
                                    <td><c:out value="${estudiante.carne}" /></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="agregarEstudiante.nombre"/></td>
                                    <td><c:out value="${estudiante.nombre}" /></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="agregarEstudiante.fechaNacimiento"/></td>
                                    <td><c:out value="${estudiante.fechaNacimiento}" /></td>
                                </tr>
                            </c:when>
                            <c:when test="${tipoEntidad=='catedratico'}">
                                <tr>
                                    <td><fmt:message key="agregarCatedratico.codigo"/></td>
                                    <td><c:out value="${catedratico.codigo}" /></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="agregarCatedratico.nombre"/></td>
                                    <td><c:out value="${catedratico.nombre}" /></td>
                                </tr>
                                <tr>
                                    <td><fmt:message key="agregarCatedratico.apellido"/></td>
                                    <td><c:out value="${catedratico.apellido}" /></td>
                                </tr>
                            </c:when>
                        </c:choose>

                    </tbody>
                </table>

                <form:form modelAttribute="wrapperDatosPersonales" method="post" action="">
                    <fieldset>
                        <legend><fmt:message key="modificarDatosPersonales.DatosVariables"/></legend>
                        <div id="divCampos">
                            <form:label for="direccion" path="direccion"><fmt:message key="agregarEstudiante.direccion"/>:</form:label>
                            <form:input path="direccion" cssStyle="width: 250px;" />
                            <form:errors path="direccion" cssClass="claseError" />
                        </div>

                        <div id="divCampos">
                            <form:label for="telefono" path="telefono"><fmt:message key="agregarEstudiante.telefono"/>:</form:label>
                            <form:input path="telefono" cssStyle="width: 250px;" />
                            <form:errors path="telefono" cssClass="claseError" />
                        </div>

                        <div id="divCampos">
                            <form:label for="celular" path="celular"><fmt:message key="agregarEstudiante.celular"/>:</form:label>
                            <form:input path="celular" cssStyle="width: 250px;"  />
                            <form:errors path="celular" cssClass="claseError" />
                        </div>

                        <div id="divCampos">
                            <form:label for="email" path="email"><fmt:message key="agregarEstudiante.email"/>:</form:label>
                            <form:input path="email" cssStyle="width: 250px;" />
                            <form:errors path="email" cssClass="claseError" />
                        </div>
                        <c:if test="${tipoEntidad=='catedratico'}">
                            <div id="divCampos">
                                <form:label for="profesion" path="profesion"><fmt:message key="agregarCatedratico.profesion"/>:</form:label>
                                <form:input path="profesion" cssStyle="width: 250px;" />
                                <form:errors path="profesion" cssClass="claseError" />
                            </div>
                        </c:if>
                    </fieldset>
                    <input id="btnEditar" type="submit" value='<fmt:message key="btnEditar"/>' />
                </form:form>
            </c:otherwise>
        </c:choose>
        
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>