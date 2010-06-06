<%-- 
    Document   : controlPensum
    Created on : 29/05/2010, 10:50:24 PM
    Author     : daniel
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
        <title><fmt:message key="controlPensum.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPensum.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="controlPensum.titulo"/></h1>

        <%-- boton que muestra el popup para agregar pensum --%>
        <button id="btnPopupAgregar"><fmt:message key="controlPensum.btnAgregar"/></button><br/><br/>

        <%-- listado de todos los pensum registrados en el sistema --%>
        <form method="post" name="formularioTabla" action="">
            <fieldset>
                <legend><fmt:message key="controlPensum.tituloListado"/></legend>
                <table id="tablaPensum" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="controlPensum.codigo"/></th>
                            <th><fmt:message key="agregarEstudiante.carrera"/></th>
                            <th><fmt:message key="controlPensum.estado"/></th>
                            <th><fmt:message key="controlPensum.fechaInicio"/></th>
                            <th><fmt:message key="controlPensum.fechaFin"/></th>
                            <th><fmt:message key="btnBorrar"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoPensum}" var="pensum">
                            <tr>
                                <td><c:out value="${pensum.codigo}" /></td>
                                <td><c:out value="${pensum.carrera.nombre}" /></td>
                                <td><c:out value="${pensum.estado}" /></td>
                                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${pensum.fechaInicio}" /></td>
                                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${pensum.fechaFin}" /></td>
                                <td></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </fieldset>
        </form>

        <%-- panel popup para agregar nuevos pensum --%>
        <div id="popupAgregarPensum" title='<fmt:message key="controlPensum.agregarPensum"/>' style="width: 500px">
            <form:form modelAttribute="wrapperPensum" method="post" action="agregarControlPensum.htm">
                <fieldset>
                    <div id="divCampos">
                        <form:label for="codigo" path="codigo"><fmt:message key="controlPensum.codigo"/> *:</form:label>
                        <form:input path="codigo" cssStyle="width: 250px;" />
                        <form:errors id="errorCodigo" path="codigo" cssClass="claseError" />
                    </div>

                    <%-- selector para escoger la carrera --%>
                    <div id="divCampos">
                        <form:label for="idCarrera" path="idCarrera"><fmt:message key="agregarEstudiante.carrera"/>: *</form:label>
                        <form:select path="idCarrera" cssStyle="width: 250px;">
                            <form:option  value="0" label="Seleccione un valor" />
                            <form:options items="${listadoCarreras}" itemValue="idCarrera" itemLabel="nombre" />
                        </form:select>
                        <form:errors id="errorIdCarrera" path="idCarrera" cssClass="claseError" />
                    </div>
                </fieldset>
            </form:form>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>