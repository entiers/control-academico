<%--
    Document   : buscarUsuario
    Created on : 16/15/2010, 9:44:18 PM
    Author     : Carlos Solórzano
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
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
        <title><fmt:message key="buscarUsuario.titulo"/></title>        
        <script type="text/javascript">
            function paginarAdelante() {
                document.forms[1].action = "paginarAdelanteBuscarUsuario.htm";
                document.forms[1].submit();
            }

            function paginarAtras() {
                document.forms[1].action = "paginarAtrasBuscarUsuario.htm";
                document.forms[1].submit();
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="buscarUsuario.titulo"/></h1>

        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaUsuario" method="post" action="buscarBuscarUsuario.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="nombreUsuario" path="nombreUsuario"><fmt:message key="buscarUsuario.nombreUsuario"/>:</form:label>
                    <form:input path="nombreUsuario" cssStyle="width: 250px;" />
                    <form:errors path="nombreUsuario" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="habilitado" path="habilitado"><fmt:message key="buscarUsuario.habilitado"/>:</form:label>
                    <form:checkbox path="habilitado" cssStyle="width: 250px;" />
                    <form:errors path="habilitado" cssClass="claseError" />
                </div>

                <br/>

                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <%-- tabla con el listado de usuarios --%>
        <form method="post" name="formularioTabla" action="" >
            <fieldset>
                <legend><fmt:message key="buscarUsuario.tituloListado"/></legend>              
                <display:table class="ui-widget ui-widget-content" name="listadoUsuarios" id="usuario">
                    <display:column property="nombreUsuario" titleKey="buscarUsuario.nombreUsuario" />
                    <display:column titleKey="buscarUsuario.habilitado">
                        <c:choose>
                            <c:when test="${usuario.habilitado}">SI</c:when>
                            <c:otherwise>NO</c:otherwise>
                        </c:choose>
                    </display:column>
                    <sec:authorize access="hasAnyRole('ROLE_ASIGNAR_PERFIL_USUARIO')">
                        <display:column titleKey="buscarUsuario.acciones" style="text-align:center;">
                            <a href="asignarPerfilUsuario.htm?idUsuario=${usuario.idUsuario}">
                                <fmt:message key="asignarPerfilUsuario.editar"/>
                            </a>
                        </display:column>
                    </sec:authorize>
                </display:table>

                <%-- botones para hacer paginacion de resultados --%>
                <%@include file="../../jspf/plantilla/botonesPaginacion.jspf" %>

            </fieldset>
        </form>

    </body>
</html>
