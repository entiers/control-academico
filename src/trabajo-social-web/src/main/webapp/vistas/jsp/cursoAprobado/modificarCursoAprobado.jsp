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
                    <tr>
                        <td><fmt:message key="curso.menu"/></td>
                        <td><c:out value="${cursoAprobado.asignacionCursoPensum.curso.codigo}" /> - <c:out value="${cursoAprobado.asignacionCursoPensum.curso.nombre}" /></td>
                    </tr>
                </tbody>
            </table>
        </fieldset>  
        <br/>
        <form:form method="post" modelAttribute="wrapperCursoAprobado">
            <fieldset>
                <legend><fmt:message key="admin.agregarCursoAprobado.datos"/></legend>
                <div id="divCampos">
                    <form:label for="tipoAsignacion" path="tipoAsignacion"><fmt:message key="tipoAsignacion.menu"/>: *</form:label>
                    <form:select path="tipoAsignacion"
                        items="${listaTipoAsignacion}"
                        itemLabel="descripcion" />        
                    <form:errors path="tipoAsignacion" cssClass="claseError" />
                </div>
                
               <div id="divCampos">
                    <form:label for="fechaAprobacion" path="fechaAprobacion"><fmt:message key="admin.agregarCursoAprobado.fechaAprobacion"/>: *</form:label>
                    <form:input path="fechaAprobacion" cssClass="datepicker" cssStyle="width: 250px;" />
                    <form:errors path="fechaAprobacion" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="zona" path="zona"><fmt:message key="ingresoNota.zona"/>: *</form:label>
                    <form:input path="zona" />
                    <form:errors path="zona" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="examenFinal" path="examenFinal"><fmt:message key="ingresoNota.final"/>: *</form:label>
                    <form:input path="examenFinal" />
                    <form:errors path="examenFinal" cssClass="claseError" />
                </div>

                <div id="divCampos">
                    <form:label for="observacion" path="observacion"><fmt:message key="admin.agregarCursoAprobado.observacion"/>: *</form:label>
                    <form:textarea path="observacion" />
                    <form:errors path="observacion" cssClass="claseError" />
                </div>
                <input type="submit" value='<fmt:message key="asignacionEstudianteCarrera.boton.modificar"/>' />
            </fieldset>
        </form:form>
            
        <div style="margin: 20px 0 0 0; ">
            <a href="buscarEstudiante.htm"><fmt:message key="mostrarAsignacionEstudianteCarrera.regresar"/></a>
        </div>
       <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
