<%-- 
    Document   : mostrarCursoPensumEquivalencia
    Created on : May 24, 2011, 4:59:07 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="cursoPensum.equivalencia.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="cursoPensum.equivalencia.titulo"/></h1>
        <div>
            <c:if test="${not empty wrapperCursoPensumEquivalencia}">
                <form:form modelAttribute="wrapperCursoPensumEquivalencia" method="post">
                    <div id="divCampos">
                        <form:label for="pensumOriginal.idPensum" path="pensumOriginal.idPensum">
                            <fmt:message key="cursoPensum.label.pensumOriginal"/>: *
                        </form:label>
                        <form:select path="pensumOriginal.idPensum" cssStyle="width: 250px;">
                            <form:option  value="" label="Seleccionar un valor" />
                            <form:options items="${listadoPensums}" itemValue="idPensum" itemLabel="codigo" />
                        </form:select>

                        <form:errors path="pensumOriginal.idPensum" cssClass="claseError" />
                    </div>

                    <div id="divCampos">
                        <form:label for="pensumEquivalente.idPensum" path="pensumEquivalente.idPensum">
                            <fmt:message key="cursoPensum.label.pensumEquivalente"/>: *
                        </form:label>
                        <form:select path="pensumEquivalente.idPensum" cssStyle="width: 250px;">
                            <form:option  value="" label="Seleccionar un valor" />
                            <form:options items="${listadoPensums}" itemValue="idPensum" itemLabel="codigo" />
                        </form:select>

                        <form:errors path="pensumEquivalente.idPensum" cssClass="claseError" />
                    </div>

                    <input type="submit" value="<fmt:message key='btnBuscar' />" />


                    <sec:authorize access="hasRole('ROLE_ASIGNACION_EQUIVALENCIAS')">
                        <a href="asignarCursoPensumEquivalencia.htm">
                            <fmt:message key="cursoPensum.asignarCursoPensumEquivalencia.titulo" />
                        </a>
                    </sec:authorize>

                </form:form>
            </c:if>
        </div>

        <div style="margin-top: 20px">            
            <display:table
                class="ui-widget ui-widget-content" name="listadoAsignacionCursoPensums"
                id="cursoPensum" requestURI="mostrarCursoPensumEquivalenciaPag.htm"
                pagesize="${pageSize}" style="width: 100%;">

                <display:column property="curso.codigoNombre" titleKey="cursoPensum.label.cursoOriginal"
                                style="width: 50%;"/>
                <display:column titleKey="cursoPensum.label.cursoEquivalente" style="width: 50%;">
                    <c:forEach items="${cursoPensum.asignacionCursoPensumsForIdCursoPensumEquivalencia}"
                               var="cursoPensumEquivalente">
                        ${cursoPensumEquivalente.curso.codigoNombre}
                        <sec:authorize access="hasRole('ELIMINAR_ASIGNACION_EQUIVALENCIAS')">
                            <a href="eliminarCursoPensumEquivalencia.htm?idAsignacionCursoPensumOriginal=${cursoPensum.idAsignacionCursoPensum}&idAsignacionCursoPensumEquivalente=${cursoPensumEquivalente.idAsignacionCursoPensum}">
                                <fmt:message key="link.eliminar" />
                            </a>
                        </sec:authorize>
                        <br/>
                    </c:forEach>
                </display:column>
            </display:table>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
