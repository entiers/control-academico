<%-- 
    Document   : editarHorario
    Created on : May 13, 2010, 1:24:53 AM
    Author     : Mario Batres
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="editarHorario.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptHorario.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarHorario.titulo"/></h1>

        <%-- formulario para ingresar los datos del horario --%>
        <form:form modelAttribute="wrapperHorario" method="post">
            <fieldset>
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioHorario.jspf" %>

                <%-- botones --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
                <a  href="buscarHorario.htm"><fmt:message key="editarHorario.regresarABusqueda"/></a>
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>