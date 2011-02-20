<%-- 
    Document   : asignacionCursos2
    Created on : 19/02/2011, 03:10:09 PM
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
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title><fmt:message key="miscursos.asignacionCursos.titulo"/></title>
    </head>
    <body>
        <h1><fmt:message key="miscursos.asignacionCursos.titulo"/></h1>
        <form:form method="post" modelAttribute="datosBusquedaCarrera" action="agregarHorario.htm">
            <fieldset>
                <legend><fmt:message key="miscursos.asignacionCursos.horarios"/></legend>
                <div id="divCampos">
                    <form:label path="idCurso" for="idCurso">
                        <fmt:message key="curso.menu" /> : 
                    </form:label>
                    <form:select id="slcCurso"
                                 path="idCurso"
                                 items="${listaCurso}"
                                 itemLabel="nombre"
                                 itemValue="idCurso"/>
                    <form:errors path="idCurso" cssClass="claseError" />
                </div>
                <div id="divCampos">
                    <form:label path="idHorario" for="idHorario">
                        <fmt:message key="agregarHorario.seccion" /> :
                    </form:label>
                    <form:select id="slcHorario"
                                 path="idHorario"
                                 items="${listaHorario}"
                                 itemLabel="seccion"
                                 itemValue="idHorario"/>
                    <form:errors path="idHorario" cssClass="claseError" />
                </div>
                <br/>
                <input type="submit" value='<fmt:message key="miscursos.asignacionCursos.agregarCurso" />' />
            </fieldset>
        </form:form>

        <c:if test="${horarioElegido}" >
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
                            <sec:authorize access="hasAnyRole('ROLE_EDITAR_HORARIO')">
                                <th><fmt:message key="acciones"/></th>
                            </sec:authorize>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${listadoHorarioAsignados}" var="horario">
                            <tr>
                                <td><c:out value="${horario.curso.nombre}" /></td>
                                <td><c:out value="${horario.dia}" /></td>
                                <td align="center">
                                    <fmt:formatDate pattern="hh:mm" value="${horario.horaInicio}" />
                                </td>
                                <td align="center">
                                    <fmt:formatDate pattern="hh:mm" value="${horario.horaFin}" />
                                </td>
                                <td align="center"><c:out value="${horario.seccion}" /></td>
                                <td>
                                    <a href="quitarHorario.htm?idHorario=${horario.idHorario}">
                                        <fmt:message key="miscursos.asignacionCursos.quitarHorario"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <br/>
                <input type="button" id="btnRealizarAsignacion" value='<fmt:message key="miscursos.asignacionCursos.realizar" />' />
            </fieldset>
        </c:if>

        <script type="text/javascript">
            $(document).ready(function() {
                //Cambio combo curso
                $('#slcCurso').change(function() {
                    getHorarios($(this).val());
                });

                //Llamada asignacion de cursos
                $('#btnRealizarAsignacion').click(function(){
                    alert('HOLA')
                    $.ajax({
                      url: "realizarAsignacion.htm",
                      success: function(){
                        alert("done");
                      }
                    });
                });
            });

            function getHorarios(value) {
                $.getJSON("getHorario.htm", { idCurso: value }, function(lstHorario) {
                    var options = '';
                    $.each(lstHorario, function (index,value) {
                        options += "<option value='" + value.idHorario + "'>" + value.seccion + "</option>";
                    });
                    $('#slcHorario').html(options);
                });
            }
        </script>
    </body>
</html>
