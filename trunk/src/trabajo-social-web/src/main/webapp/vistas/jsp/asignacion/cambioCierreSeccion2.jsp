<%-- 
    Document   : cambioCierreSeccion2
    Created on : 29/01/2011, 10:32:14 PM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
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
        <form:form modelAttribute="wrapperCambioSeccion" method="POST">
            <fieldset>
                <legend><fmt:message key="cambioCierreSeccion.secciones"/></legend>
                <form:select path="horario.idHorario"
                            items="${listadoHorarios}"
                            itemLabel="seccion"
                            itemValue="idHorario"/>
            </fieldset>
            <fieldset>
                <legend><fmt:message key="cambioCierreSeccion.estudiantes"/></legend>
                <table align="center">
                    <thead>
                        <tr>
                            <th><fmt:message key="agregarEstudiante.carne"/></th>
                            <th><fmt:message key="agregarEstudiante.nombre"/></th>
                            <th><fmt:message key="cambioCierreSeccion.seleccionar"/></th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoEstudiantes}" var="detAsign">
                            <tr>
                                <td style="text-align:center">
                                    ${detAsign.asignacion.asignacionEstudianteCarrera.estudiante.carne}
                                </td>
                                <td>
                                    ${detAsign.asignacion.asignacionEstudianteCarrera.estudiante.nombre}
                                </td>
                                <td style="text-align:center">
                                    <form:checkbox path="detalleAsignacion"
                                                   value="${detAsign.idDetalleAsignacion}"/>                                    
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </fieldset>
            <br/>
            <input type="submit" value='<fmt:message key="cambioCierreSeccion.btnRealizar"/>' />
        </form:form>
        
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
