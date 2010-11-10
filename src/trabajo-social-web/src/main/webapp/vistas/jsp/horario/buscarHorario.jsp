<%-- 
    Document   : buscarHorario
    Created on : May 12, 2010, 10:23:53 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="buscarHorario.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptHorario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarHorario.titulo"/></h1>

        <%-- formulario para ingresar los datos del horario --%>
        <form:form modelAttribute="datosBusquedaHorario" method="post">
            <fieldset>
                <div id="divCampos">
                    <form:label for="salon.idSalon" path="salon.idSalon">
                        <fmt:message key="agregarHorario.salon"/>: *
                    </form:label>

                    <form:select path="salon.idSalon" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <c:forEach items="${salones}" var="salon">
                            <form:option value="${salon.idSalon}" >
                                ${salon.numero}  (${salon.edificio})
                            </form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="salon.idSalon" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="semestre.idSemestre" path="semestre.idSemestre">
                        <fmt:message key="agregarHorario.semestre"/>: *
                    </form:label>

                    <form:select path="semestre.idSemestre" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <c:forEach items="${semestres}" var="semestre">
                            <form:option value="${semestre.idSemestre}" >
                                ${semestre.numero} - ${semestre.anio}
                            </form:option>
                        </c:forEach>
                    </form:select>
                    <form:errors path="semestre.idSemestre" cssClass="claseError" />
                </div>
                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>

        <fieldset>
                <legend><fmt:message key="buscarHorario.tituloListado"/></legend>
                <table id="tablaHorarios" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarHorario.curso"/></th>
                            <th><fmt:message key="agregarHorario.dia"/></th>
                            <th><fmt:message key="agregarHorario.horaInicio"/></th>
                            <th><fmt:message key="agregarHorario.horaFin"/></th>
                            <th><fmt:message key="agregarHorario.seccion"/></th>
                            <th><fmt:message key="agregarHorario.estado"/></th>
                            <th><fmt:message key="acciones"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${horarios}" var="horario">
                            <tr>
                                <td><c:out value="${horario.curso.nombre}" /></td>
                                <td><c:out value="${horario.dia}" /></td>
                                <td>
                                    <fmt:formatDate pattern="hh:mm" value="${horario.horaInicio}" />
                                </td>
                                <td>
                                    <fmt:formatDate pattern="hh:mm" value="${horario.horaFin}" />
                                </td>
                                <td><c:out value="${horario.seccion}" /></td>
                                <td><c:out value="${horario.estado}" /></td>
                                <td>
                                    <a href="editarHorario.htm?idHorario=${horario.idHorario}">
                                        <fmt:message key="editarHorario.editar"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>