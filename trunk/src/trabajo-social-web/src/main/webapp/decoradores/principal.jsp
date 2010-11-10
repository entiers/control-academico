<%--
    Document   : principal
    Created on : 22/03/2010, 12:14:38 AM
    Author     : Daniel Castillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

        <%-- titulo predeterminado de las paginas --%>
        <title><decorator:title default='<fmt:message key="welcome.titulo"/>' /></title>

        <%-- hoja de estilo general --%>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/estilo.css" />

        <%-- css y scripts necesarios para utilizar jquery --%>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/cupertino/jquery-ui-1.8.custom.css" />
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/cupertino/style.css" />
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery-ui-1.8.custom.min.js"></script>

        <!--[if IE 6]>
            <style>
                #pitch .infoline {margin-top:-74px;}
                .post-options {margin:-55px 0 40px 138px;}
            </style>
	<![endif]-->

        <%-- css y scripts necesarios para crear el menu --%>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/estiloMenu.css" media="screen, projection" />
        <!--[if lte IE 7]>
            <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/estiloMenuIE.css" media="screen" />
        <![endif]-->
        <script type="text/javascript" src="<%= request.getContextPath() %>/js/jquery.dropdownPlain.js"></script>

        <%-- se inserta el head propia de cada una de las paginas --%>
        <decorator:head />
    </head>
    <body>
        <div id="envoltorio">
            <%-- se inserta el encabezado para todas las paginas --%>
            <%@include file="../vistas/jspf/plantilla/encabezadoPagina.jspf" %><br/>

            <div id="contenido">
                <%-- se inserta el menu superior --%>
                <%@include file="../vistas/jspf/plantilla/menuSuperior.jspf" %>

                <%-- se inserta el contenido propio de cada pagina --%>
                <decorator:body />

                <%-- se inserta el menu derecho --%>
                <%@include file="../vistas/jspf/plantilla/menuLateral.jspf" %>
            </div>

            <%-- se inserta el pie de pagina --%>
            <%@include file="../vistas/jspf/plantilla/piePagina.jspf" %>
        </div>
    </body>
</html>
