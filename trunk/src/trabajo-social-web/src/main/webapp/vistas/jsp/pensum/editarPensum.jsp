<%-- 
    Document   : editarPensum
    Created on : Mar 4, 2011, 11:35:53 PM
    Author     : Mario Batres
--%>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="editarPensum.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptPensum.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarPensum.titulo"/></h1>

        <%-- formulario para ingresar los datos del pensum --%>
        <form:form modelAttribute="wrapperPensum" method="post">
            <fieldset>
                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/formularios/formularioPensum.jspf" %>

                <%-- botones --%>
                <input type="submit" value='<fmt:message key="btnEditar"/>' />
                <a  href="javascript:history.back();"><fmt:message key="editarPensum.regresarABusqueda"/></a>
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>