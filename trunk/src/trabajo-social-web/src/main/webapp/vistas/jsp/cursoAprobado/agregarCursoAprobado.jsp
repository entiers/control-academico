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
        <title><fmt:message key="admin.agregarCursoAprobado.titulo"/></title>
        <%@include file="../../jspf/scripts/scriptEstudiante.jspf" %>
    </head>
    <body>
        <h1><fmt:message key="admin.agregarCursoAprobado.titulo"/></h1>
        <c:choose>
            <c:when test="${estudianteNoExiste}">
                <label><fmt:message key="buscarEstudiante.noExiste"/></label>
            </c:when>
            <c:when test="${estudianteSinCarrera}">
                <label><fmt:message key="admin.agregarCursoAprobado.error.estudianteSinCarrera"/></label>
            </c:when>
            <c:when test="${estudianteSinCursos}">
                <label><fmt:message key="admin.agregarCursoAprobado.error.estudianteSinCursos"/></label>
            </c:when>
            <c:otherwise>
                <fieldset>
                    <legend><fmt:message key="modificarDatosPersonales.DatosFijos"/></legend>
                    <table align="center">
                        <tbody>
                            <tr>
                                <td><fmt:message key="agregarEstudiante.carne"/></td>
                                <td><c:out value="${estudiante.carne}" /></td>
                            </tr>
                            <tr>
                                <td><fmt:message key="agregarEstudiante.nombre"/></td>
                                <td><c:out value="${estudiante.nombre}" /></td>
                            </tr>
                        </tbody>
                    </table>
                </fieldset>  
                <br/>
                <form:form method="post" modelAttribute="wrapperCursoAprobado">
                    <fieldset>
                        <legend><fmt:message key="admin.agregarCursoAprobado.datos"/></legend>
                        <%@include file="../../jspf/formularios/formularioCursoAprobado.jspf" %>
                        <input type="submit" value='<fmt:message key="btnAgregar"/>' />
                    </fieldset>
                </form:form>
            </c:otherwise>
        </c:choose>
        <div style="margin: 20px 0 0 0; ">
            <a href="buscarEstudiante.htm"><fmt:message key="mostrarAsignacionEstudianteCarrera.regresar"/></a>
        </div>                
                
       <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
        
        <script type="text/javascript">
            $(document).ready(function() {
                //Cambio combo asignacionEstudianteCarrera
                $('#slcAsignacionEstudianteCarrera').change(function() {
                    getCursosValidos($(this).val());                    
                });

            });

            function getCursosValidos(valueAEC) {                
                $.getJSON("getListaCursosSinAprobarValidos.htm", { idAEC: valueAEC}, function(lstACP) {
                    var options = '';
                    $.each(lstACP, function (index,value) {
                        options += "<option value='" + value.idAsignacionCursoPensum + "'>" + value.curso.codigoNombre + "</option>";
                    });
                    $('#slcAsignacionCursoPensum').html(options);
                });
            }
        </script>
    </body>
</html>
