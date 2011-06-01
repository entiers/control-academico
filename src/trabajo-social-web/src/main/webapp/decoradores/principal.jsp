<%--
    Document   : principal
    Created on : 22/03/2010, 12:14:38 AM
    Author     : Daniel Castillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/page" prefix="page" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
        <%-- titulo predeterminado de las paginas --%>
        <title><decorator:title default='<fmt:message key="welcome.titulo"/>' /></title>

        <%-- hoja de estilo general --%>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath()%>/css/estilo.css" />

        <%-- css y scripts necesarios para utilizar jquery --%>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath()%>/css/cupertino/jquery-ui-1.8.custom.css" />
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath()%>/css/cupertino/style.css" />
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-1.4.2.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery-ui-1.8.custom.min.js"></script>
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.ui.datepicker-es.js"></script>        
        <script type="text/javascript" src="<%= request.getContextPath()%>/js/jquery.dropdownPlain.js"></script>

        <script type="text/javascript" src="<%= request.getContextPath()%>/js/script.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.css" type="text/css" media="screen" />
    <!--[if IE 6]><link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.ie6.css" type="text/css" media="screen" /><![endif]-->
    <!--[if IE 7]><link rel="stylesheet" href="<%= request.getContextPath()%>/css/style.ie7.css" type="text/css" media="screen" /><![endif]-->

        <script type="text/javascript">
            $(function() {
                $(".datepicker").datepicker({
                    showOn: 'button',
                    changeMonth: true,
                    changeYear: true,
                    buttonImage: '<%=request.getContextPath()%>/css/images/calendar.gif',
                    buttonImageOnly: true,
                    dateFormat: 'dd-mm-yy',
                    changeMonth: true,
                    changeYear: true
                });


                $("table").attr({
                    "border" : "0",
                    "width" : "100%",
                    "cellpadding" : 0,
                    "cellspacing" : 0,
                    "class":"art-article"
                });

                $("input:submit, input:button, button").attr({
                    "class": "art-button"
                });
            });
        </script>

        <%-- se inserta el head propia de cada una de las paginas --%>
        <decorator:head />
    </head>
    <body>
        <div id="art-page-background-simple-gradient">
        </div>
        <div id="art-main">
            <div class="art-Sheet">
                <div class="art-Sheet-tl"></div>
                <div class="art-Sheet-tr"></div>
                <div class="art-Sheet-bl"></div>
                <div class="art-Sheet-br"></div>
                <div class="art-Sheet-tc"></div>
                <div class="art-Sheet-bc"></div>
                <div class="art-Sheet-cl"></div>
                <div class="art-Sheet-cr"></div>
                <div class="art-Sheet-cc"></div>
                <div class="art-Sheet-body">
                    <%@include file="../vistas/jspf/plantilla/encabezadoPagina.jspf" %>

                    <%@include file="../vistas/jspf/plantilla/menuSuperior.jspf" %>
                    <div class="art-contentLayout">
                        <div class="art-content">
                            <div class="art-Post">
                                <div class="art-Post-tl"></div>
                                <div class="art-Post-tr"></div>
                                <div class="art-Post-bl"></div>
                                <div class="art-Post-br"></div>
                                <div class="art-Post-tc"></div>
                                <div class="art-Post-bc"></div>
                                <div class="art-Post-cl"></div>
                                <div class="art-Post-cr"></div>
                                <div class="art-Post-cc"></div>
                                <div class="art-Post-body">
                                    <div id="envoltorio">
                                        <div id="contenido" class="art-Post-inner">
                                            <c:set var="pageSize" value="2" scope="session" />
                                            <decorator:body />
                                            <div class="cleared"></div>
                                        </div>
                                    </div>
                                    <div class="cleared"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="cleared"></div><div class="art-Footer">
                        <div class="art-Footer-inner">
                            <a href="#" class="art-rss-tag-icon" title="RSS"></a>
                            <div class="art-Footer-text">
                                <p>&nbsp;</p>
                            </div>
                        </div>
                        <div class="art-Footer-background"></div>
                    </div>
                    <div class="cleared"></div>
                </div>
            </div>
            <div class="cleared"></div>
            <p class="art-page-footer">&nbsp;</p>
        </div>
    </body>
</html>
