<%--
    Document   : login
    Created on : 21/03/2010, 01:08:55 AM
    Author     : Daniel Castillo
--%>
<%@page import="org.springframework.security.authentication.DisabledException" %>
<%@page import="org.springframework.security.authentication.BadCredentialsException" %>
<%@page import="org.springframework.security.core.AuthenticationException" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="login.titulo"/></title>
        <link type="text/css" rel="stylesheet" href="<%= request.getContextPath() %>/css/estilo.css" />
    </head>
    <body>
        <div id="loginForm">
            <h1><fmt:message key="login.titulo"/></h1>
            <p><fmt:message key="login.texto"/></p>

            <%-- formulario de logeo --%>
            <form action="j_spring_security_check" method="post">
                <fieldset>
                    <%-- se controlan las excepciones de logeo--%>
                    <c:if test="${not empty sessionScope['SPRING_SECURITY_LAST_EXCEPTION']}">
                        <div id="divErrorLogin">
                            <%
                            Object excepcion = session.getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                            if(excepcion instanceof DisabledException) {
                                session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                            %>
                                <span class="loginError"><fmt:message key="login.usuarioDeshabilitado"/></span>
                            <%
                            } else if(excepcion instanceof BadCredentialsException) {
                                session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                            %>
                                <span class="loginError"><fmt:message key="login.loginIncorrecto"/></span>
                            <%
                            } else if(excepcion instanceof AuthenticationException) {
                                session.removeAttribute("SPRING_SECURITY_LAST_EXCEPTION");
                            %>
                                <span class="loginError"><fmt:message key="login.loginExcepcion"/></span>
                            <%
                            }
                            %>
                        </div>
                    </c:if>

                    <div id="divCampos">
                        <label id="lj_username" for="j_username"><fmt:message key="login.usuario"/> *:</label>
                        <input type="text" name="j_username" id="j_username" />
                    </div>

                    <div id="divCampos">
                        <label id="lj_password" for="j_password"><fmt:message key="login.password"/> *:</label>
                        <input type="password" name="j_password" id="j_password" />
                    </div>

                    <input type="submit" value='<fmt:message key="btnAcceder"/>' />
                </fieldset>
            </form>
        </div>
    </body>
</html>
