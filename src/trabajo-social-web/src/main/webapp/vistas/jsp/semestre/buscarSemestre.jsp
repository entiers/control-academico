<%-- 
    Document   : buscarSemestre
    Created on : Feb 19, 2011, 12:48:02 AM
    Author     : Mario Batres
--%>
<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="buscarSemestre.titulo"/></title>
        <script type="text/javascript">
            $(function() {
                $("#popupMensaje").dialog({
                    autoOpen: false
                });
            });
        </script>
    </head>
    <body>
        <h1><fmt:message key="buscarSemestre.titulo"/></h1>
        <fieldset>
            <legend><fmt:message key="buscarSemestre.tituloListado"/></legend>

            <display:table class="ui-widget ui-widget-content" name="listadoSemestres" id="semestre" requestURI="buscarSemestre.htm">
                <display:column property="anio" titleKey="agregarSemestre.anio" style="text-align:center;"/>
                <display:column property="numero" titleKey="agregarSemestre.numero" style="text-align:center;"/>
                <display:column property="observacion" titleKey="agregarSemestre.observacion" style="text-align:left;"/>
                <display:column titleKey="agregarSemestre.activo" style="text-align:center;">
                    <c:choose>
                        <c:when test="${semestre.activo}">
                            <b><fmt:message key="semestre.anio.true"/></b>
                        </c:when>
                        <c:otherwise>
                            <b><fmt:message key="semestre.anio.false"/></b>
                        </c:otherwise>
                    </c:choose>
                </display:column>

                <sec:authorize access="hasAnyRole('ROLE_EDITAR_SEMESTRE')">
                    <display:column titleKey="acciones" style="text-align:center;">
                        <a href="editarSemestre.htm?idSemestre=${semestre.idSemestre}">
                            <fmt:message key="semestre.accion.editar"/>
                        </a>
                    </display:column>
                </sec:authorize>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
