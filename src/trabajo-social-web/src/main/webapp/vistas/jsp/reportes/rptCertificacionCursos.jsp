<%--
    Document   : rptNotasCurso
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
        <title><fmt:message key="rptCertificacionCursos.titulo"/></title>        
    </head>
    <body>
        <h1><fmt:message key="rptCertificacionCursos.titulo" /></h1>

        <form:form method="post" id="frmBusqueda" action="generarReporte.htm">

            <input type="hidden" name="nombreControlReporte" value="${nombreControlReporte}"/>
            <fieldset>  
                
                <div id="divCampos">
                    <input type="hidden" name="nombreParametro" value="id_carrera" />
                    <label><fmt:message key="carrera.nombreEntidad"/></label>
                     <select name="valorParametro" id="slcCarrera">
                        <c:forEach items="${listadoCarrera}" var="carrera">
                            <option value="${carrera.idCarrera}">${carrera.nombre}</option>
                        </c:forEach>
                    </select>                    
                    <input type="hidden" name="tipoParametro" value="integer" />
                </div>
                    
                <div id="divCampos">
                    <input type="hidden" name="nombreParametro" value="carne" />
                    <c:choose>      
                    <c:when test="${empty mostrarCarne}">      
                    <label><fmt:message key="agregarEstudiante.carne"/></label>
                    <input name="valorParametro" id="valorParametro" />                    
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="valorParametro" 
                               id="valorParametro" value="${carne}" />                    
                    </c:otherwise>
                    </c:choose>
                    <input type="hidden" name="tipoParametro" value="string" />
                </div>
                <input id="btnBuscar" type="submit" value='<fmt:message key="reportes.generar"/>' />
            </fieldset>
        </form:form>
    </body>
</html>

