<%-- 
    Document   : asignacionRetrasadas
    Created on : 5/03/2011, 11:40:55 PM
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
        <title><fmt:message key="miscursos.asignacionCursos.retrasada.titulo"/></title>
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
        <h1><fmt:message key="miscursos.asignacionCursos.retrasada.titulo"/></h1>
        <form:form method="post" modelAttribute="datosAsignacion" action="realizarAsignacionRetrasada.htm">
            <c:choose>
                <c:when test="totalAsignaciones==0">
                    <div id="campos">
                        <fmt:message key="miscursos.asignacionCursos.retrasada.sinAsignaciones"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <%-- se importan los campos --%>
                    <%@include file="../../jspf/formularios/formularioDetalleAsignacion.jspf" %>
                </c:otherwise>
            </c:choose>
        </form:form>
       <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>

