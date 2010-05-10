<%-- 
    Document   : agregarCatedratico
    Created on : 5/05/2010, 02:54:09 PM
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
        <title><fmt:message key="agregarCatedratico.titulo"/></title>
        <%@include file="../../jspf/catedratico/scriptsCatedratico.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="agregarCatedratico.titulo"/></h1>

        <%-- formulario para ingresar los datos del catedratico --%>
        <form:form modelAttribute="wrapperCatedratico" method="post">
            <fieldset>
                <%-- campo para ingresar el codigo de catedratico --%>
                <div id="divCampos">
                    <form:label for="codigo" path="codigo"><fmt:message key="agregarCatedratico.codigo"/>: *</form:label>
                    <form:input path="codigo" cssStyle="width: 250px;" />
                    <form:errors path="codigo" cssClass="claseError" />
                </div>

                <%-- se importan los demas campos --%>
                <%@include file="../../jspf/catedratico/formularioCatedratico.jspf" %>

                <%-- selector para escoger la escuela --%>
                <div id="divCampos">
                    <form:label for="idEscuela" path="idEscuela"><fmt:message key="agregarCatedratico.escuela"/>: *</form:label>
                    <form:select path="idEscuela" cssStyle="width: 250px;">
                        <form:option  value="0" label="Seleccionar un valor" />
                        <form:options items="${escuelas}" itemValue="idEscuela" itemLabel="nombre" />
                    </form:select>
                    <form:errors path="idEscuela" cssClass="claseError" />
                </div>

                <%-- boton --%>
                <input type="submit" value='<fmt:message key="btnAgregar"/>' />
            </fieldset>
        </form:form>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/mensaje/resultado.jspf" %>
    </body>
</html>