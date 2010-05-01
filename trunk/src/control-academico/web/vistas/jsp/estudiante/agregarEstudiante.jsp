<%--
    Document   : agregarEstudiante
    Created on : 28/04/2010, 03:31:57 PM
    Author     : Daniel Castillo
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
        <title><fmt:message key="agregarEstudiante.titulo"/></title>
        <script type="text/javascript">
	$(function() {

            // agrega un panel popup para seleccionar fecha al input
            // de fecha de nacimiento
            $("#fechaNacimiento").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: 'dd-mm-yy'
            });

            // se crea y configura el panel popup que muestra los
            // mensajes de resultados de las operaciones
            $("#mensajePopup").dialog({
                autoOpen: <%= Boolean.parseBoolean(request.getAttribute("mostrarPopup").toString()) %>,
                modal: true,
                buttons: {
                    <fmt:message key="btnAceptar"/>: function() {
                        $("#carne").val("");
                        $("#nombre").val("");
                        $("#apellido").val("");
                        $("#direccion").val("");
                        $("#telefono").val("");
                        $("#celular").val("");
                        $("#email").val("");
                        $("#fechaNacimiento").val("");
                        $(this).dialog('close');
                    }
                }
            });

	});
	</script>
    </head>
    <body>
        <h1><fmt:message key="agregarEstudiante.titulo"/></h1>
        <form:form modelAttribute="estudiante" method="post">
            <fieldset>
                <%-- campo para ingresar el numero de carne --%>
                <div id="divCampos">
                    <form:label for="carne" path="carne"><fmt:message key="agregarEstudiante.carne"/>: *</form:label>
                    <form:input path="carne" cssStyle="width: 250px;" />
                    <form:errors path="carne" cssClass="claseError" />
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/estudiante/formularioEstudiante.jspf" %>
                
                <%-- selector para escoger la carrera --%>
                <div id="divCampos">
                    <form:label for="idCarrera" path="idCarrera"><fmt:message key="agregarEstudiante.carrera"/>: *</form:label>
                    <form:select path="idCarrera" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <form:options items="${carreras}" itemValue="idCarrera" itemLabel="nombre" />
                    </form:select>
                    <form:errors path="idCarrera" cssClass="claseError" />
                </div>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="agregarEstudiante.btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/mensaje/resultado.jspf" %>
    </body>
</html>