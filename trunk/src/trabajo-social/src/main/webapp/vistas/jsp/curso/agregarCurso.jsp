<%-- 
    Document   : agregarCurso
    Created on : May 3, 2010, 12:59:36 PM
    Author     : Mario Batres
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
        <title><fmt:message key="agregarCurso.titulo"/></title>
        <%@include file="../../jspf/curso/scriptsCurso.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="agregarCurso.titulo"/></h1>
        <%-- formulario para ingresar los datos del estudiante --%>
        <form:form modelAttribute="wrapperCurso" method="post">
            <fieldset>
                <%-- campo para ingresar el codigo del curso--%>
                <div id="divCampos">
                    <form:label for="codigo" path="codigo"><fmt:message key="agregarCurso.codigo"/>: *</form:label>
                    <form:input path="codigo" cssStyle="width: 250px;" />
                    <form:errors path="codigo" cssClass="claseError" />
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/curso/formularioCurso.jspf" %>
                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/mensaje/resultado.jspf" %>
    </body>
</html>
