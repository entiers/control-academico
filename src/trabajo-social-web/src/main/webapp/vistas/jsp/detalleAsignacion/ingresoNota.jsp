<%-- 
    Document   : ingresoNota
    Created on : 17/05/2011, 05:37:10 PM
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

            function guardar(value){
                $("#hdOficializar").val(value);
                $("form").submit();
                
            }
        </script>
        <title><fmt:message key="ingresoNota.titulo"/></title>
    </head>
    <body>
        <h1><fmt:message key="ingresoNota.titulo"/></h1>
        <form:form modelAttribute="wrapperIngresoNota" method="POST">
            <c:choose>
                <c:when test="${not empty listadoDetalleAsignacion}">
                    <table>
                        <thead>
                            <tr>
                                <th><fmt:message key="agregarEstudiante.carne"/></th>
                                <th><fmt:message key="agregarEstudiante.nombre"/></th>
                                <th><fmt:message key="ingresoNota.zona"/></th>
                                <th><fmt:message key="ingresoNota.laboratorio"/></th>
                                <th><fmt:message key="ingresoNota.final"/></th>
                            </tr>
                        </thead>
                        <c:forEach items="${listadoDetalleAsignacion}" var="detAsign">
                            <tbody>
                                <tr>
                                    <td style="text-align:center">
                                        ${detAsign.asignacion.asignacionEstudianteCarrera.estudiante.carne}
                                    </td>
                                    <td style="text-align:center">
                                        ${detAsign.asignacion.asignacionEstudianteCarrera.estudiante.nombre}
                                    </td>
                                    <td style="text-align:center">
                                        <form:input path="listZona" value="${detAsign.zona}"
                                                    cssStyle="width:50px;"
                                                    disabled="${detAsign.oficializado}"/>
                                    </td>
                                    <td style="text-align:center">
                                        <form:input path="listLaboratorio" value="${detAsign.laboratorio}"
                                                    cssStyle="width:50px;"
                                                    disabled="${detAsign.oficializado}"/>
                                    </td>
                                    <td style="text-align:center">
                                        <form:input path="listFinal" value="${detAsign.examenFinal}"
                                                    cssStyle="width:50px;"
                                                    disabled="${detAsign.oficializado}" />
                                    </td>
                                </tr>
                            </tbody>
                        </c:forEach>
                    </table>
                </c:when>
                <c:otherwise>
                    <label><fmt:message key="ingresoNota.sinAsignaciones"/></label>
                </c:otherwise>
            </c:choose>
            <br/>
            <form:hidden path="oficializar" id="hdOficializar" />
            <input type="button" value='<fmt:message key="ingresoNota.btnGuardar"/>' onclick="javascript:guardar(false);" />
            <input type="button" value='<fmt:message key="ingresoNota.btnOficializar"/>' onclick="javascript:guardar(true);" />
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
