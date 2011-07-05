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
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title><fmt:message key="mostrarAsignacionEstudianteCarrera.titulo"/></title>
        <%@include file="../../jspf/plantilla/scriptPopupMensajeDefault.jspf" %>
        <%@include file="../../jspf/plantilla/scriptFormularioPopup.jspf" %>
        <%@include file="../../jspf/plantilla/scriptFormularioModificarPopup.jspf" %>

        <script type="text/javascript">

            var modificar = function(idAsignacionEstudianteCarrera, fechaCierre, inscrito){
                $("#formModificar #inputIdAsignacionEstudianteCarrera").val(idAsignacionEstudianteCarrera);
                $("#formModificar #inputFechaCierre").val(fechaCierre);
                $("#formModificar #checkboxInscrito").attr({"checked" : inscrito == true ? "checked" : ""});

                $("#divFormularioModificarPopup").dialog("open");                
            }
        </script>
    </head>
    <body>
        <h1><fmt:message key="mostrarAsignacionEstudianteCarrera.titulo"/></h1>

        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>        



        <fieldset>
            <legend><fmt:message key="mostrarAsignacionEstudianteCarrera.listadoCarrerasAsignadas" /></legend>
            <center>
                <display:table class="ui-widget ui-widget-content" name="listadoAsignacionEstudianteCarrera" id="asignacionEstudianteCarrera">
                    <display:column property="carrera.codigo" titleKey="agregarCarrera.codigo" style="text-align: center;"  />
                    <display:column property="carrera.nombre" titleKey="agregarCarrera.nombre"  />

                    <display:column titleKey="asignacionEstudianteCarrera.fechaCierre"  style="text-align:center;">
                        <fmt:formatDate value="${asignacionEstudianteCarrera.fechaCierre}" pattern="dd-MM-yyyy" var="fechaCierre" />
                        ${fechaCierre}
                    </display:column>

                    <sec:authorize access="hasAnyRole('ROLE_MOSTRAR_PENSUM_ESTUDIANTE_CARRERA', 'ROLE_MOSTRAR_HISTORIAL_ASIGNACION_ESTUDIANTE_CARRERA', 'ROLE_MODIFICAR_ASIGNACION_ESTUDIANTE_CARRERA', 'ROLE_CAMBIAR_ASIGNACION_ESTUDIANTE_CARRERA')">
                        <display:column titleKey="acciones" style="text-align:center;" >
                            <sec:authorize access="hasRole('ROLE_MOSTRAR_PENSUM_ESTUDIANTE_CARRERA')">
                                <a href="mostrarPensumEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                    <fmt:message key="mostrarPensumEstudianteCarrera.link"/>
                                </a>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_MOSTRAR_HISTORIAL_ASIGNACION_ESTUDIANTE_CARRERA')">
                                <br/>
                                <a href="mostrarHistorialAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                    <fmt:message key="mostrarHistorialAsignacionEstudianteCarrera.link"/>
                                </a>
                            </sec:authorize>

                            <sec:authorize access="hasRole('ROLE_MODIFICAR_ASIGNACION_ESTUDIANTE_CARRERA')">
                                <br/>
                                <a style="cursor: pointer;"
                                   class="a-acciones" onclick="modificar(${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}, '${fechaCierre}', ${asignacionEstudianteCarrera.inscrito})">
                                    <fmt:message key="asignacionEstudianteCarrera.boton.modificar"/>
                                </a>
                            </sec:authorize>

                            <sec:authorize access="hasRole('ROLE_CAMBIAR_ASIGNACION_ESTUDIANTE_CARRERA')">
                                <br/>
                                <a href="cambiarAsignacionEstudianteCarrera.htm?idAsignacionEstudianteCarrera=${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}">
                                    <fmt:message key="cambiarAsignacionEstudianteCarrera.link"/>
                                </a>
                            </sec:authorize>
                        </display:column>
                    </sec:authorize>


                </display:table>
            </center>
        </fieldset>



        <sec:authorize access="hasRole('ROLE_AGREGAR_ASIGNACION_ESTUDIANTE_CARRERA')">
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
        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_MODIFICAR_ASIGNACION_ESTUDIANTE_CARRERA')">
            <c:if test="${ not empty wrapperModificarAsignacionEstudianteCarrera}">
                <div id="divFormularioModificarPopup" title="<fmt:message key='modificarAsignacionEstudianteCarrera.titulo'/>" >
                    <form:form id="formModificar"
                               modelAttribute="wrapperModificarAsignacionEstudianteCarrera" method="POST"
                               action="modificarAsignacionEstudianteCarrera.htm">
                        <form:hidden id="inputIdAsignacionEstudianteCarrera"
                                     path="idAsignacionEstudianteCarrera" />
                        <form:errors path="idAsignacionEstudianteCarrera" cssClass="claseError claseErrorModificar" />

                        <div id="divCampos">
                            <form:label for="inscrito" path="inscrito">
                                <fmt:message key="asignacionEstudianteCarrera.inscrito"/>: *
                            </form:label>
                            <form:checkbox id="checkboxInscrito" path="inscrito" />
                            <form:errors path="inscrito" cssClass="claseError" />
                        </div>

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
        </sec:authorize>


        <fieldset>
            <legend><fmt:message key="mostrarAsignacionEstudianteCarrera.listadoCarrerasNoAsignadas" /></legend>
            <center>
                <display:table class="ui-widget ui-widget-content" name="listadoAsignacionEstudianteCarreraNoAsignadas" id="asignacionEstudianteCarrera">
                    <display:column property="carrera.codigo" titleKey="agregarCarrera.codigo" style="text-align: center;"  />
                    <display:column property="carrera.nombre" titleKey="agregarCarrera.nombre"  />

                    <display:column titleKey="asignacionEstudianteCarrera.fechaCierre"  style="text-align:center;">
                        <fmt:formatDate value="${asignacionEstudianteCarrera.fechaCierre}" pattern="dd-MM-yyyy" var="fechaCierre" />
                        ${fechaCierre}
                    </display:column>
                    <display:column titleKey="acciones" style="text-align:center">
                        <a style="cursor:pointer;" onclick="llenarFormularioEquivalencia(${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera});">  		    
                            <fmt:message key="cursoPensum.link.equivalencias.carreras"/>
                        </a>
                    </display:column>
                </display:table>
            </center>
        </fieldset>

        <form:form id="formEquivalencia" modelAttribute="wrapperEquivalenciaPorCarrera" method="POST" 
                   action="mostrarParaRealizarEquivalenciaPorCarreras.htm">
            <div id="divCampos">
                <form:label for="asignacionEstudianteCarreraOriginal.idAsignacionEstudianteCarrera"
                            path="asignacionEstudianteCarreraOriginal.idAsignacionEstudianteCarrera">
                    <fmt:message key="cursoPensum.label.carreraOriginal" />
                </form:label>

                <c:choose>
                    <c:when test="${fn:length(listadoAsignacionEstudianteCarrera) > 1}">
                        <form:select path="asignacionEstudianteCarreraOriginal.idAsignacionEstudianteCarrera"
                                     items="${listadoAsignacionEstudianteCarrera}"
                                     itemValue="idAsignacionEstudianteCarrera" itemLabel="carrera.codigoNombre"    />
                    </c:when>
                    <c:otherwise>
                        <form:hidden path="asignacionEstudianteCarreraOriginal.idAsignacionEstudianteCarrera" 
                                    id="idAsignacionEstudianteCarreraOriginal"/>:
                        
                        <c:forEach items="${listadoAsignacionEstudianteCarrera}" var="asignacionEstudianteCarrera">
                            ${asignacionEstudianteCarrera.carrera.codigoNombre}
                            <c:set scope="request" var="idAsignacionEstudianteCarreraOriginal" 
                                   value="${asignacionEstudianteCarrera.idAsignacionEstudianteCarrera}" />
                        </c:forEach>
                        
                        <script type="text/javascript">
                            $(function (){
                                $("#idAsignacionEstudianteCarreraOriginal").val("${idAsignacionEstudianteCarreraOriginal}");
                            });
                        </script>
                    </c:otherwise>
                </c:choose>
            </div>
            <div id="divCampos">
                <form:label for="asignacionEstudianteCarreraEquivalencia.idAsignacionEstudianteCarrera"
                            path="asignacionEstudianteCarreraEquivalencia.idAsignacionEstudianteCarrera">
                    <fmt:message key="cursoPensum.label.carreraEquivalencia" />:
                </form:label>
                <form:input path="asignacionEstudianteCarreraEquivalencia.idAsignacionEstudianteCarrera" />
            </div>            
            <blockquote>
                <fmt:message key="cursoPensum.mensaje.equivalencia.carrera" />
            </blockquote>
        </form:form>


        <div style="margin: 20px 0 0 0; ">
            <a href="buscarEstudiante.htm"><fmt:message key="mostrarAsignacionEstudianteCarrera.regresar"/></a>
        </div>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
