<%--
    Document   : rptNotasCurso
    Created on : 31/05/2011, 09:14:37 PM
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
        <title><fmt:message key="rptNotasCurso.titulo"/></title>

        <script type="text/javascript">
            $(document).ready(function() {
                //Cambio combo curso
                $('#slcTipoHorario').change(function() {
                    getHorarios($(this).val());
                });
            });

            function getHorarios(valueTipoHorario) {
                $.get("getHorarioRpt.htm", { idTipoHorario: valueTipoHorario}, function(options) {
                    $('#valorParametro').html(options);
                });
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="rptNotasCurso.titulo" /></h1>

        <form:form method="post" id="frmBusqueda" action="generarReporte.htm">

            <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}"

            <fieldset>
                <legend><fmt:message key="ingresoNota.busquedaAsignaciones"/></legend>

                <div id="divCampos">
                    <label for="tipoHorario" ><fmt:message key="agregarHorario.tipo"/>:</label>
                    <select id="slcTipoHorario">
                        <c:forEach items="${listaTipoHorario}" var="tipoHorario">
                            <option value="${tipoHorario.id}">${tipoHorario.descripcion}</option>>
                        </c:forEach>
                    </select>
                </div>

                <div id="divCampos">
                    <input type="hidden" name="nombreParametro" value="id_horario" />

                    <label><fmt:message key="horario.menu"/></label>
                    <select name="valorParametro" id="valorParametro">
                        <c:forEach items="${listadoHorario}" var="horario">
                            <option value="${horario.idHorario}">[${horario.asignacionCursoPensum.curso.codigo}] ${horario.asignacionCursoPensum.curso.nombre} - ${horario.seccion}</option>
                        </c:forEach>
                    </select>
                    <input type="hidden" name="tipoParametro" value="integer" />
                </div>

                <input id="btnBuscar" type="submit" value='<fmt:message key="reportes.generar"/>' />
            </fieldset>
        </form:form>
    </body>
</html>

