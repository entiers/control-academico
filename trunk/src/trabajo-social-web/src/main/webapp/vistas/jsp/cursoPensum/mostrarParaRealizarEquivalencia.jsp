<%-- 
    Document   : mostrarParaRealizarEquivalencia
    Created on : Jun 22, 2011, 10:05:46 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><fmt:message key="cursoPensum.titulo.realizarEquivalencia" /></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>

        <script type="text/javascript">
            $(function (){
                $("#linkRegresar").click(function(){
                    location.href = "mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${wrapperAsignacionEquivalencia.asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}";
                });
            });
        </script>

    </head>
    <body>
        <h1><fmt:message key="cursoPensum.titulo.realizarEquivalencia" /></h1>


        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>

        <fieldset>
            <legend><fmt:message key="cursoPensum.label.realizarEquivalencia"/></legend>
            <display:table class="ui-widget ui-widget-content" 
                           name="listadoEquivalencias" id="asignacionCursoPensum"
                           style="width:100%">
                <display:column property="curso.codigo" titleKey="agregarCurso.codigo"
                                style="width: 30%; align:center;" />
                <display:column property="curso.nombre" titleKey="agregarCurso.nombre" 
                                style="width: 70%"/>
            </display:table>
        </fieldset>

        <c:if test="${not empty listadoEquivalencias}">
              <fieldset>
                  <legend><fmt:message key="asignacionEquivalencia.formulario.titulo"/></legend>
                  <form:form modelAttribute="wrapperAsignacionEquivalencia"
                             action="realizarEquivalencias.htm" method="POST">

                      <%@include file="../../jspf/formularios/formularioAsignacionEquivalencia.jspf" %>

                      <input type="submit" value="<fmt:message key="btnAceptar"/>">
                  </form:form>
              </fieldset>
        </c:if>

        <a id="linkRegresar" style="cursor:pointer;"><fmt:message key="link.regresar" /></a>
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
