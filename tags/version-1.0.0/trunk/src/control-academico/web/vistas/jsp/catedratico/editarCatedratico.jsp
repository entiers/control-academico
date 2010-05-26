<%-- 
    Document   : editarCatedratico
    Created on : 6/05/2010, 04:50:36 PM
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
        <title><fmt:message key="editarCatedratico.titulo"/></title>
        <%@include file="../../jspf/catedratico/scriptsCatedratico.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="editarCatedratico.titulo"/></h1>

        <%-- formulario para realizar busquedas --%>
        <form:form modelAttribute="datosBusquedaCatedratico" method="post" action="buscarEditarCatedratico.htm">
            <fieldset>
                <div id="divCampos">
                    <form:label for="codigoBusqueda" path="codigoBusqueda"><fmt:message key="agregarCatedratico.codigo"/>:</form:label>
                    <form:input path="codigoBusqueda" cssStyle="width: 250px;" />
                    <form:errors path="codigoBusqueda" cssClass="claseError" />
                </div>
                <br/>

                <%-- boton --%>
                <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
            </fieldset>
        </form:form>
        <br/><br/><br/>

        <%-- formulario para editar los datos del estudiante --%>
        <form:form modelAttribute="wrapperCatedratico" method="post" action="editarCatedratico.htm">
            <fieldset>
                <%-- se deshabilita para evitar su edicion --%>
                <div id="divCampos">
                    <form:label for="codigo" path="codigo"><fmt:message key="agregarCatedratico.codigo"/>: *</form:label>
                    <form:input path="codigo" cssStyle="width: 250px;" readonly="true" />
                    <form:errors path="codigo" cssClass="claseError" />
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/catedratico/formularioCatedratico.jspf" %>

                <%-- boton --%>
                <input id="btnEditar" type="submit" value='<fmt:message key="btnEditar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/mensaje/resultado.jspf" %>
    </body>
</html>
