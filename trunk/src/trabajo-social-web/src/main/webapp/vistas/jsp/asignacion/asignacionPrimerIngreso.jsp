<%--
    Document   : asignacionPrimerIngreso
    Created on : 15/01/2011, 12:09:51 PM
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
        <title><fmt:message key="asignacionPrimerIngreso.titulo"/></title>
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
        <h1><fmt:message key="asignacionPrimerIngreso.titulo"/></h1>

        <form:form name="form" method="post" enctype="multipart/form-data">
                <c:choose>
                    <c:when test="${procesoEjecutado}">
                        <div align="center">
                            <label for="sumbit">
                                <fmt:message key="asignacionPrimerIngreso.ejecutado1"/>
                                ${idAsignacionPrimerIngreso}
                                <fmt:message key="asignacionPrimerIngreso.ejecutado2"/>
                            </label>
                        </div>
                    </c:when>
                    <c:when test="${periodoValido}">
                        <label for="sumbit"><fmt:message key="asignacionPrimerIngreso.confirmar"/></label>
                        <br/>
                        <br/>
                        <input type="submit" value='<fmt:message key="etl.btnRealizar"/>' />
                    </c:when>
                    <c:otherwise>
                        <label for="sumbit"><fmt:message key="asignacionPrimerIngreso.fueraPeriodo"/></label>
                    </c:otherwise>
                </c:choose>             
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>