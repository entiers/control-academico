<%--
    Document   : rptNotasCursosAsignados
    Created on : 31/05/2011, 09:14:37 PM
    Author     : Carlos Solorzano
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
        <title><fmt:message key="rptNotasCursosAsignados.titulo"/></title>        
    </head>
    <body>
        <h1><fmt:message key="rptNotasCursosAsignados.titulo" /></h1>

        <form:form method="post" id="frmBusqueda" action="generarReporte.htm">

            <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}"/>
            <fieldset>                  
                <div id="divCampos">
                    <input type="hidden" name="nombreParametro" value="carne" />
                    <label><fmt:message key="agregarEstudiante.carne"/></label>
                    <input name="valorParametro" id="valorParametro" />                    
                    <input type="hidden" name="tipoParametro" value="string" />
                </div>
                <input id="btnBuscar" type="submit" value='<fmt:message key="reportes.generar"/>' />
            </fieldset>
        </form:form>
    </body>
</html>

