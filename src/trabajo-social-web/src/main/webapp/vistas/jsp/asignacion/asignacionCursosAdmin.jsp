<%-- 
    Document   : asignacionCursosAdmin
    Created on : 27/05/2011, 12:01:31 AM
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
                            <%} else {%>
                                $(this).dialog('close');
                            <%}%>
                        }
                    }
                });
            });
        </script>
        <title><fmt:message key="admin.asignacionCursos.titulo"/></title>
    </head>
    <body>
        <h1><fmt:message key="admin.asignacionCursos.titulo"/></h1>

        <%-- fragmento que muestra la informacion del estudiante--%>
        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <form:form modelAttribute="wrapperAsignacionCursosExtemporaneas" method="post" >
            <fieldset>
                <legend><fmt:message key="admin.asignacionCursos.carrerasEstudiante"/></legend>
                <div id="divCampos">
                    <form:label for="tipoHorario" path="tipoHorario"><fmt:message key="agregarHorario.tipo"/>:</form:label>
                    <form:select path="tipoHorario"
                                 items="${listaTipoHorario}"
                                 itemLabel="descripcion"/>

                </div>
                <div id="divCampos">
                    <form:label for="asignacionEstudianteCarrera.idAsignacionEstudianteCarrera" path="asignacionEstudianteCarrera.idAsignacionEstudianteCarrera">
                        <fmt:message key="asignacionEstudianteCarrera.carrera"/>:
                    </form:label>
                    <form:select path="asignacionEstudianteCarrera.idAsignacionEstudianteCarrera"
                                 items="${listadoAEC}"
                                 itemLabel="carrera.nombre"
                                 itemValue="idAsignacionEstudianteCarrera"/>

                </div>
                <br/>
                <input type="submit" value='<fmt:message key="miscursos.asignacionCursos.carrera" />' />
            </fieldset>
            
        </form:form>
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
