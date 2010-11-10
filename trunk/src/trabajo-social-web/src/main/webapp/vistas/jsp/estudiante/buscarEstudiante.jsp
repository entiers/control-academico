<%--
    Document   : buscarEstudiante
    Created on : 3/05/2010, 11:34:08 AM
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
        <title><fmt:message key="buscarEstudiante.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptEstudiante.jspf" %>
        <script type="text/javascript">
            function paginarAdelante() {
                document.forms[1].action = "paginarAdelanteBuscarEstudiante.htm";
                document.forms[1].submit();
            }

            function paginarAtras() {
                document.forms[1].action = "paginarAtrasBuscarEstudiante.htm";
                document.forms[1].submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="buscarEstudiante.titulo"/></h1>

        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaEstudiante" method="post" action="buscarBuscarEstudiante.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="carneBusqueda" path="carneBusqueda"><fmt:message key="agregarEstudiante.carne"/>:</form:label>
                    <form:input path="carneBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="carneBusqueda" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="nombreBusqueda" path="nombreBusqueda"><fmt:message key="agregarEstudiante.nombre"/>:</form:label>
                    <form:input path="nombreBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="nombreBusqueda" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="apellidoBusqueda" path="apellidoBusqueda"><fmt:message key="agregarEstudiante.apellido"/>:</form:label>
                    <form:input path="apellidoBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="apellidoBusqueda" cssClass="claseError" />
                </div>
                <br/>

                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <%-- tabla con el listado de estudiantes --%>
        <form method="post" name="formularioTabla" action="">
            <fieldset>
                <legend><fmt:message key="buscarEstudiante.tituloListado"/></legend>
                <table id="tablaEstudiantes" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarEstudiante.carne"/></th>
                            <th><fmt:message key="agregarEstudiante.nombre"/></th>
                            <th><fmt:message key="agregarEstudiante.apellido"/></th>
                            <th><fmt:message key="agregarEstudiante.direccion"/></th>
                            <th><fmt:message key="agregarEstudiante.telefono"/></th>
                            <th><fmt:message key="agregarEstudiante.celular"/></th>
                            <th><fmt:message key="agregarEstudiante.email"/></th>
                            <th><fmt:message key="agregarEstudiante.fechaNacimiento"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoEstudiantes}" var="estudiante">
                            <tr>
                                <td><c:out value="${estudiante.carne}" /></td>
                                <td><c:out value="${estudiante.nombre}" /></td>
                                <td><c:out value="${estudiante.apellido}" /></td>
                                <td><c:out value="${estudiante.direccion}" /></td>
                                <td><c:out value="${estudiante.telefono}" /></td>
                                <td><c:out value="${estudiante.celular}" /></td>
                                <td><c:out value="${estudiante.email}" /></td>
                                <td><fmt:formatDate pattern="dd-MM-yyyy" value="${estudiante.fechaNacimiento}" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>

                <%-- botones para hacer paginacion de resultados --%>
                <%@include file="../../jspf/plantilla/botonesPaginacion.jspf" %>
            </fieldset>
        </form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
