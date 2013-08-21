<%-- 
    Document   : cambioCierreSeccion
    Created on : 29/01/2011, 12:56:06 PM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="cambioCierreSeccion.titulo"/></title>
        <script type="text/javascript">
            $(function() {
                // se crea y configura el panel popup que muestra los
                // mensajes de resultados de las operaciones
                $("#popupMensaje").dialog({
                    autoOpen: <%= RequestUtil.getValorBoolean(request, "mostrarPopupMensaje") %>,
                    modal: true,
                    buttons: {
                        '<fmt:message key="btnAceptar"/>': function() {
                            $(this).dialog('close');
                        }
                    }
                });
            });
        </script>
    </head>
    <body>
        <h1><fmt:message key="cambioCierreSeccion.titulo"/></h1>
        <form:form modelAttribute="datosBusquedaHorario" method="POST">
            <fieldset>
                <div id="divCampos">
                    <form:label for="asignacionCursoPensum.idAsignacionCursoPensum" 
                                path="asignacionCursoPensum.idAsignacionCursoPensum"><fmt:message key="agregarHorario.curso"/>: *</form:label>
                    <form:select path="asignacionCursoPensum.idAsignacionCursoPensum" cssStyle="width: 250px;"
                            items="${listadoCursos}"
                            itemLabel="curso.codigoNombre"
                            itemValue="idAsignacionCursoPensum"/>
                    <form:errors path="asignacionCursoPensum.idAsignacionCursoPensum" 
                                 cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="semestre.idSemestre" path="semestre.idSemestre"><fmt:message key="agregarHorario.semestre"/>: *</form:label>
                    <form:select path="semestre.idSemestre" cssStyle="width: 250px;">
                        <c:forEach items="${listadoSemestres}" var="semestre">
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
        <c:choose>
            <c:when test="${post}">
                <fieldset>
                    <legend><fmt:message key="cambioCierreSeccion.disponibles"/></legend>
                    <table align="center">
                        <thead>
                            <tr>
                                <th><fmt:message key="agregarCurso.codigo"/></th>
                                <th><fmt:message key="curso.menu"/></th>
                                <th><fmt:message key="agregarHorario.seccion"/></th>
                                <th></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${listadoHorario}" var="horario">
                                <tr>
                                    <td>${horario.asignacionCursoPensum.curso.codigo}</td>
                                    <td>${horario.asignacionCursoPensum.curso.nombre}</td>
                                    <td>${horario.seccion}</td>
                                    <td align="center">
                                        <a href="cambioCierreSeccion2.htm?idHorario=${horario.idHorario}">
                                            <fmt:message key="cambioCierreSeccion.cambio"/>
                                        </a>
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
