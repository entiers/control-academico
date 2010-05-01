<%--
    Document   : login
    Created on : 21/03/2010, 01:08:55 AM
    Author     : Daniel Castillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="login.titulo"/></title>
    </head>
    <body>
        <div id="loginForm">
            <h1><fmt:message key="login.titulo"/></h1>
            <p><fmt:message key="login.texto"/></p>

            <form action="j_spring_security_check" method="post">
                <fieldset>
                    <p>
                        <label id="lj_username" for="j_username"><fmt:message key="login.usuario"/> *:</label>
                        <input type="text" name="j_username" id="j_username" />
                    </p>

                    <p>
                        <label id="lj_password" for="j_password"><fmt:message key="login.password"/> *:</label>
                        <input type="password" name="j_password" id="j_password" />
                    </p>

                    <p>
                        <input type="submit" value='<fmt:message key="btnAcceder"/>' />
                    </p>
                </fieldset>
            </form>
        </div>
    </body>
</html>
