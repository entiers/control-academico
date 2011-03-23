<%-- 
    Document   : desAsignacionCursos
    Created on : 15/03/2011, 11:01:29 PM
    Author     : cats
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
        <title><fmt:message key="miscursos.desAsignacionCursos.titulo"/></title>
        <script type="text/javascript">
            $(function() {
                // se crea y configura el panel popup que muestra los
                // mensajes de resultados de las operaciones
                $("#popupMensaje").dialog({
                    autoOpen: <%= RequestUtil.getValorBoolean(request, "mostrarPopupMensaje") %>,
                    modal: true,
                    buttons: {
                        '<fmt:message key="btnAceptar"/>': function() {
                            <%if( request.getAttribute("url") != null ) {%>
                                location.href = "<%= request.getAttribute("url")%>";
                            <%}else{%>
                                $(this).dialog('close');
                            <%}%>
                        }
                    }
                });
             });
        </script>
    </head>
    <body>
        <h1><fmt:message key="miscursos.desAsignacionCursos.titulo"/></h1>
        <form:form method="post" modelAttribute="datosAsignacion">
            <c:choose>
                <c:when test="totalAsignaciones==0">
                    <div id="campos">
                        <fmt:message key="miscursos.asignacionCursos.retrasada.sinAsignaciones"/>
                    </div>
                </c:when>
                <c:when test="${errorEntidad}">
                    <label><fmt:message key="miscursos.asignacionCursos.noEstudiante"/></label>
                </c:when>
                <c:when test="${periodoInvalido}">
                    <label><fmt:message key="miscursos.desAsignacionCursos.fueraPeriodo"/></label>
                </c:when>
                <c:otherwise>
                    <%-- se importan los campos --%>
                    <%@include file="../../jspf/formularios/formularioDetalleAsignacion.jspf" %>
                    <br/>
                    <input type="submit" value='<fmt:message key="miscursos.desasignacionCursos.realizar" />' />
                </c:otherwise>
            </c:choose>
        </form:form>
       <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
