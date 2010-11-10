<%-- 
    Document   : buscarCalendarioActividades
    Created on : Jun 7, 2010, 6:07:03 PM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="buscarCalendarioActividades.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptCalendarioActividades.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="buscarCalendarioActividades.titulo"/></h1>

        <%-- formulario para ingresar los datos del calendario de actividades --%>
        <form:form modelAttribute="datosBusquedaCalendarioActividades" method="post">
            <fieldset>
                <div id="divCampos">
                    <form:label for="semestre.idSemestre" path="semestre.idSemestre">
                        <fmt:message key="agregarCalendarioActividades.semestre"/>: *
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
                <legend><fmt:message key="buscarCalendarioActividades.tituloListado"/></legend>
                <table id="tablaCalendarioActividades" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarCalendarioActividades.fechaInicio"/></th>
                            <th><fmt:message key="agregarCalendarioActividades.fechaFin"/></th>
                            <th><fmt:message key="agregarCalendarioActividades.actividad"/></th>
                            <th><fmt:message key="acciones"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoCalendarioActividades}" var="calendarioActividades">
                            <tr>
                                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${calendarioActividades.fechaInicio}" /></td>
                                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${calendarioActividades.fechaFin}" /></td>
                                <td><c:out value="${calendarioActividades.actividad}" /></td>                                
                                <td>
                                    <a href="editarCalendarioActividades.htm?idCalendarioActividades=${calendarioActividades.idCalendarioActividades}">
                                        <fmt:message key="editarCalendarioActividades.editar"/>
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
