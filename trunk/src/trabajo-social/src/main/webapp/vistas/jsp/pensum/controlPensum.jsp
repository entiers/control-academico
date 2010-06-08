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
                            <th><fmt:message key="controlPensum.btnActivar"/></th>
                            <th><fmt:message key="controlPensum.btnCaducar"/></th>
                            <th><fmt:message key="controlPensum.btnAgregarCurso"/></th>
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
                                <td>
                                    <c:if test="${pensum.estado == 0}">
                                        <input id="btnBorrar" type="button" value='<fmt:message key="btnBorrar"/>'
                                               onclick="document.getElementById('idPensumBorrar').value = ${pensum.idPensum}; $('#popupBorrar').dialog('open')"/>
                                        <input type="hidden" value="" name="idPensumBorrar" id="idPensumBorrar" >
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${pensum.estado == 0}">
                                        <input id="btnActivar" type="button" value='<fmt:message key="controlPensum.btnActivar"/>'
                                               onclick="document.getElementById('idPensumActivar').value = ${pensum.idPensum}; $('#popupActivar').dialog('open')"/>
                                        <input type="hidden" value="" name="idPensumActivar" id="idPensumActivar" >
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${pensum.estado == 1}">
                                        <input id="btnCaducar" type="button" value='<fmt:message key="controlPensum.btnCaducar"/>'
                                               onclick="document.getElementById('idPensumCaducar').value = ${pensum.idPensum}; $('#popupCaducar').dialog('open')"/>
                                        <input type="hidden" value="" name="idPensumCaducar" id="idPensumCaducar" >
                                    </c:if>
                                </td>
                                <td>
                                    <c:if test="${pensum.estado == 0}">
                                        <input id="btnAgregarCurso" type="button" value='<fmt:message key="controlPensum.btnAgregarCurso"/>'
                                               onclick='document.getElementById("idPensumAgregarCurso").value = ${pensum.idPensum}; document.forms[0].action = "popupAgregarCursoControlPensum.htm"; document.forms[0].submit();'/>
                                        <input type="hidden" value="" name="idPensumAgregarCurso" id="idPensumAgregarCurso" >
                                    </c:if>
                                </td>
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

        <%-- panel popup de confirmacion de eliminacion de pensum --%>
        <div id="popupBorrar" title='<fmt:message key="controlPensum.tituloBorrarPensum"/>' >
            <p>
                <span class="ui-icon ui-icon-trash" style="float:left; margin:0 7px 50px 0;"></span>
                <fmt:message key="controlPensum.textoBorrarPensum" />
            </p>
        </div>

        <%-- panel popup para activar pensum --%>
        <div id="popupActivar" title='<fmt:message key="controlPensum.btnActivar"/>' >
            <p>
                <span class="ui-icon ui-icon-transferthick-e-w" style="float:left; margin:0 7px 50px 0;"></span>
                <fmt:message key="controlPensum.textoActivarPensum" />
            </p>
        </div>

        <%-- panel popup para caducar pensum --%>
        <div id="popupCaducar" title='<fmt:message key="controlPensum.btnCaducar"/>' >
            <p>
                <span class="ui-icon ui-icon-arrowthick-1-s" style="float:left; margin:0 7px 50px 0;"></span>
                <fmt:message key="controlPensum.textoCaducarPensum" />
            </p>
        </div>

        <%-- panel popup para agregar cursos al pensum --%>
        <div id="popupAgregarCurso" title='<fmt:message key="controlPensum.btnAgregarCurso"/>' >
            <p>
                <span class="ui-icon ui-icon-note" style="float:left; margin:0 7px 50px 0;"></span>
                <fmt:message key="controlPensum.textoAgregarCurso" />
            </p>
            <form:form modelAttribute="wrapperCursoPensum" method="post" action="agregarCursoControlPensum.htm">
                <fieldset>
                    <div id="divCampos">
                        <form:label for="obligatorio" path="obligatorio"><fmt:message key="controlPensum.cursoObligatorio"/> *:</form:label>
                        <form:checkbox path="obligatorio" cssStyle="width: 250px;" />
                    </div>

                    <%-- selector para escoger cursos --%>
                    <div id="divCampos">
                        <form:label for="idCurso" path="idCurso"><fmt:message key="controlPensum.curso"/>: *</form:label>
                        <form:select path="idCurso" cssStyle="width: 250px;">
                            <form:option  value="0" label="Seleccione un valor" />
                            <form:options items="${listadoCursos}" itemValue="idCurso" itemLabel="nombre" />
                        </form:select>
                        <form:errors id="errorIdCurso" path="idCurso" cssClass="claseError" />
                    </div>
                </fieldset>
            </form:form>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>