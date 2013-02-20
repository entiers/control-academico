<%-- 
    Document   : agregarCursoAprobado
    Created on : Feb 18, 2013, 3:47:02 PM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
        <title><fmt:message key="admin.modificarCursoAprobado.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptEstudiante.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="admin.modificarCursoAprobado.titulo"/></h1>
        <c:choose>
            <c:when test="${estudianteNoExiste}">
                <label><fmt:message key="buscarEstudiante.noExiste"/></label>
            </c:when>
            <c:when test="${estudianteSinCarrera}">
                <label><fmt:message key="admin.agregarCursoAprobado.error.estudianteSinCarrera"/></label>
            </c:when>            
            <c:otherwise>                
                <form:form method="post" modelAttribute="wrapperCursoAprobado">
                    <fieldset>
                        <legend><fmt:message key="admin.agregarCursoAprobado.datos"/></legend>
                        <div id="divCampos">
                            <form:label path=""><fmt:message key="agregarEstudiante.carne"/></form:label>
                            <form:label path=""><c:out value="${estudiante.carne}" /></form:label>
                        </div>
                        
                        <div id="divCampos">
                            <form:label path=""><fmt:message key="agregarEstudiante.nombre"/></form:label>
                            <form:label path=""><c:out value="${estudiante.nombre}" /></form:label>
                        </div>
                        
                        <div id="divCampos">
                            <form:label for="idAsignacionEstudianteCarrera" path="idAsignacionEstudianteCarrera">
                                <fmt:message key="asignacionEstudianteCarrera.carrera"/>: *
                            </form:label>
                            <form:select path="idAsignacionEstudianteCarrera"
                                items="${listaAEC}"
                                itemLabel="carrera.nombre"
                                itemValue="idAsignacionEstudianteCarrera"/> 
                            <form:errors path="idAsignacionEstudianteCarrera" cssClass="claseError" />
                        </div>                        
                        <input type="submit" value='<fmt:message key="btnBuscar"/>' />
                    </fieldset>
                </form:form>
                <fieldset>
                    <legend><fmt:message key="admin.agregarCursoAprobado.listado"/></legend>
                    <display:table class="ui-widget ui-widget-content" name="listaCursoAprobado"
                           id="cursoAprobado" pagesize="${pageSize}" >
                        <display:column property="asignacionCursoPensum.curso.codigo" titleKey="agregarCurso.codigo"  />
                        <display:column property="asignacionCursoPensum.curso.nombre" titleKey="curso.menu"  />
                        <display:column titleKey="acciones" style="text-align: center;" >
                            <a href="modificarCursoAprobado.htm?idCA=${cursoAprobado.idCursoAprobado}">
                                <fmt:message key="asignacionEstudianteCarrera.boton.modificar"/>
                            </a>
                        </display:column>
                    </display:table>
                </fieldset>
            </c:otherwise>
        </c:choose>
                
        <div style="margin: 20px 0 0 0; ">
            <a href="buscarEstudiante.htm"><fmt:message key="mostrarAsignacionEstudianteCarrera.regresar"/></a>
        </div>
        
       <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
