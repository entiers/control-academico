<%--
    Document   : buscarCatedratico
    Created on : 10/05/2010, 11:57:15 AM
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
        <title><fmt:message key="buscarCatedratico.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptCatedratico.jspf" %>
        <script type="text/javascript">
            function paginarAdelante() {
                document.forms[1].action = "paginarAdelanteBuscarCatedratico.htm";
                document.forms[1].submit();
            }

            function paginarAtras() {
                document.forms[1].action = "paginarAtrasBuscarCatedratico.htm";
                document.forms[1].submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="buscarCatedratico.titulo"/></h1>

        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaCatedratico" method="post" action="buscarBuscarCatedratico.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="codigoBusqueda" path="codigoBusqueda"><fmt:message key="agregarCatedratico.codigo"/>:</form:label>
                    <form:input path="codigoBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="codigoBusqueda" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="nombreBusqueda" path="nombreBusqueda"><fmt:message key="agregarCatedratico.nombre"/>:</form:label>
                    <form:input path="nombreBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="nombreBusqueda" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="apellidoBusqueda" path="apellidoBusqueda"><fmt:message key="agregarCatedratico.apellido"/>:</form:label>
                    <form:input path="apellidoBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="apellidoBusqueda" cssClass="claseError" />
                </div>
                <br/>

                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <%-- tabla con el listado de Catedraticos --%>
        <form method="post" name="formularioTabla" action="">
            <fieldset>
                <legend><fmt:message key="buscarCatedratico.tituloListado"/></legend>
                <table id="tablaCatedraticos" class="ui-widget ui-widget-content">
                    <thead>
                        <tr class="ui-widget-header ">
                            <th><fmt:message key="agregarCatedratico.codigo"/></th>
                            <th><fmt:message key="agregarCatedratico.nombre"/></th>
                            <th><fmt:message key="agregarCatedratico.apellido"/></th>
                            <th><fmt:message key="agregarCatedratico.profesion"/></th>
                            <th><fmt:message key="agregarCatedratico.direccion"/></th>
                            <th><fmt:message key="agregarCatedratico.telefono"/></th>
                            <th><fmt:message key="agregarCatedratico.celular"/></th>
                            <th><fmt:message key="agregarCatedratico.email"/></th>
                            <sec:authorize access="hasRole('ROLE_ASIGNACION_HORARIO_CATEDRATICO')">
                                  <th><fmt:message key="admin.asignacionHorarioCatedratico.titulo"/></th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoCatedraticos}" var="catedratico">
                            <tr>
                                <td><c:out value="${catedratico.codigo}" /></td>
                                <td><c:out value="${catedratico.nombre}" /></td>
                                <td><c:out value="${catedratico.apellido}" /></td>
                                <td><c:out value="${catedratico.profesion}" /></td>
                                <td><c:out value="${catedratico.direccion}" /></td>
                                <td><c:out value="${catedratico.telefono}" /></td>
                                <td><c:out value="${catedratico.celular}" /></td>
                                <td><c:out value="${catedratico.email}" /></td>
                                <%--Operacion asignacion de cursos de administracion--%>
                                <sec:authorize access="hasRole('ROLE_ASIGNACION_HORARIO_CATEDRATICO')">
                                    <td>
                                        <a href="buscarAsignacionCatedraticoHorario.htm?idCatedratico=${catedratico.idCatedratico}">
                                            <fmt:message key="admin.asignacionHorarioCatedratico.consultar"/>
                                        </a>
                                        <br/>
                                        <a href="agregarAsignacionCatedraticoHorario.htm?idCatedratico=${catedratico.idCatedratico}">
                                            <fmt:message key="admin.asignacionHorarioCatedratico.realizar"/>
                                        </a>
                                    </td>
                                </sec:authorize>
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
