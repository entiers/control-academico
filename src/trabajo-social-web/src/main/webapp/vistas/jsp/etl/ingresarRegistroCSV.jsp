<%-- 
    Document   : ingresarRegistroCSV
    Created on : Dec 14, 2010, 10:51:22 PM
    Author     : Mario Batres
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
        <title><fmt:message key="etl.ingresarRegistroCSV.titulo"/></title>
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
        <h1><fmt:message key="etl.ingresarRegistroCSV.titulo"/></h1>

        <form:form name="form" method="post" enctype="multipart/form-data">
            <div id="divCampos">
                <label for="file"><fmt:message key="label.examinar"/>:</label>
                <input type="file" id="archivoCSV" name="archivoCSV" />                
            </div>
            <input type="submit" value='<fmt:message key="etl.btnRealizar"/>' />
        </form:form>


        <div id="divCampos">
            <label><fmt:message key="etl.label.errores" /></label>
            <textarea cols="55" rows="10"><%= RequestUtil.getValorString(request, "errores") %></textarea>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
