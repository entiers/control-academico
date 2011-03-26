<%-- 
    Document   : mostrarAsignacionEstudianteCarrera
    Created on : Mar 15, 2011, 11:59:11 AM
    Author     : Mario Batres
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<html>
    <head>
        <title><fmt:message key="mostrarAsignacionEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
        <%@include file="../../jspf/plantilla/scriptFormularioPopup.jspf" %>
        <%@include file="../../jspf/plantilla/scriptFormularioModificarPopup.jspf" %>

        <script type="text/javascript">

            var modificar = function(idAsignacionEstudianteCarrera, fechaCierre){                
                $("#formModificar #inputIdAsignacionEstudianteCarrera").val(idAsignacionEstudianteCarrera);
                $("#formModificar #inputFechaCierre").val(fechaCierre);

                $("#divFormularioModificarPopup").dialog("open");                
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="mostrarAsignacionEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>        


        <center>
            <fieldset>
                <legend><fmt:message key="mostrarAsignacionEstudianteCarrera.listadoCarrerasAsignadas" /></legend>
                <display:table class="ui-widget ui-widget-content" name="listadoAsignacionEstudianteCarrera" id="asignacionEstudianteCarrera">
                    <display:column property="carrera.codigo" titleKey="agregarCarrera.codigo" style="text-align: center;"  />
                    <display:column property="carrera.nombre" titleKey="agregarCarrera.nombre"  />

                    <display:column titleKey="asignacionEstudianteCarrera.fechaCierre"  style="text-align:center;">
                        <fmt:formatDate value="${asignacionEstudianteCarrera.fechaCierre}" pattern="dd-MM-yyyy" var="fechaCierre" />
                        ${fechaCierre}
                    </display:column>

                    <sec:authorize access="hasAnyRole('ROLE_MOSTRAR_PENSUM_ESTUDIANTE_CARRERA')">
                        <display:column titleKey="acciones" style="text-align:center;" >
                            <sec:authorize access="hasRole('ROLE_MOSTRAR_PENSUM_ESTUDIANTE_CARRERA')">
                                <a href="mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                    <fmt:message key="mostrarPensumEstudianteCarrera.link"/>
                                </a>
                            </sec:authorize>
                            <br/>
                            <a href="mostrarHistorialAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                <fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.link"/>
                            </a>

                            <br/>
                            <a class="a-acciones" onclick="modificar(${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}, '${fechaCierre}')">
                                <fmt:message key="asignacionEstudianteCarrera.boton.modificar"/>
                            </a>

                            <br/>
                            <a href="cambiarAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                <fmt:message key="cambiarAsignacionEstudianteCarrera.link"/>
                            </a>
                        </display:column>
                    </sec:authorize>


                </display:table>
            </fieldset>
        </center>



        <c:if test="${habilitarFormulario}">
            <div id="divFormularioPopup" title="<fmt:message key='agregarAsignacionEstudianteCarrera.titulo' />">
                <form:form id="form"
                           modelAttribute="wrapperAgregarAsignacionEstudianteCarrera" method="POST"
                           action="agregarAsignacionEstudianteCarrera.htm">

                    <%@include file="../../jspf/formularios/formularioAsignacionEstudianteCarrera.jspf" %>
                </form:form>
            </div>
            <center style="margin-top: 20px;">
                <button id="botonHabilitarFormularioPopup"><fmt:message key="asignacionEstudianteCarrera.boton.agregarCarrera" /></button>
            </center>
        </c:if>

        <c:if test="${ not empty wrapperModificarAsignacionEstudianteCarrera}">
            <div id="divFormularioModificarPopup" title="<fmt:message key='modificarAsignacionEstudianteCarrera.titulo'/>" >
                <form:form id="formModificar"
                           modelAttribute="wrapperModificarAsignacionEstudianteCarrera" method="POST"
                           action="modificarAsignacionEstudianteCarrera.htm">
                    <form:hidden id="inputIdAsignacionEstudianteCarrera"
                                 path="idAsignacionEstudianteCarrera" />
                    <form:errors path="idAsignacionEstudianteCarrera" cssClass="claseError claseErrorModificar" />

                    <div id="divCampos">
                        <form:label for="fechaCierre" path="fechaCierre">
                            <fmt:message key="asignacionEstudianteCarrera.fechaCierre"/>:
                        </form:label>
                        <form:input id="inputFechaCierre" path="fechaCierre" cssStyle="width: 250px;" cssClass="datepicker"/>
                        <form:errors path="fechaCierre" cssClass="claseError" />
                    </div>                    
                </form:form>
            </div>
        </c:if>


        <div style="margin: 20px 0 0 0; ">
            <a href="buscarEstudiante.htm"><fmt:message key="mostrarAsignacionEstudianteCarrera.regresar"/></a>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
