<%-- 
    Document   : realizarAsignacionExtemporanea
    Created on : 27/05/2011, 06:45:12 PM
    Author     : Carlos Solorzano
--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title><fmt:message key="miscursos.asignacionCursos.titulo"/></title>
        <script type="text/javascript">
            $(function() {
                // se crea y configura el panel popup que muestra los
                // mensajes de resultados de las operaciones
                $("#popupMensaje").dialog({
                    autoOpen: <%= RequestUtil.getValorBoolean(request, "mostrarPopupMensaje") %>,
                    modal: true,
                    buttons: {
                        '<fmt:message key="btnAceptar"/>': function() {
                            $(this).dialog('close');
                        }
                    }
                });
             });
        </script>
    </head>
    <body>
        <h1><fmt:message key="admin.asignacionCursos.titulo"/></h1>

        <%-- fragmento que muestra la informacion del estudiante--%>
        <%@include file="../../jspf/formularios/informacion/formularioInformacionEstudiante.jspf" %>
        
        <form:form method="post" modelAttribute="datosAsignacion" action="agregarHorarioAsignacionExtemporanea.htm">
            <fieldset>
                <legend><fmt:message key="miscursos.asignacionCursos.horarios"/></legend>
                <%-- se importan los campos --%>
                <%@include file="../../jspf/formularios/formularioAsignacionHorario.jspf" %>
                <input type="submit" value='<fmt:message key="miscursos.asignacionCursos.agregarCurso" />' />
            </fieldset>
        </form:form>

        <c:if test="${horarioElegido}" >
            <form:form method="POST" modelAttribute="datosAsignacion" action="realizarAsignacionExtemporanea.htm">
                <fieldset>
                    <legend><fmt:message key="buscarHorario.tituloListado"/></legend>
                    <%-- se importan los campos --%>
                    <%@include file="../../jspf/formularios/formularioAsignacionHorarioElegido.jspf" %>
                    <input type="submit" id="btnRealizarAsignacion" value='<fmt:message key="miscursos.asignacionCursos.realizar" />' />
                </fieldset>
            </form:form>
        </c:if>

        <script type="text/javascript">
            $(document).ready(function() {
                //Cambio combo curso
                $('#slcCurso').change(function() {
                    getHorarios($(this).val(),$('#tipoHorario').val());
                });

            });


            
            // otro
            function getHorarios(valueCurso,valueHorario) {   
                if ($('#slcCurso').val() != ""){
                    $.getJSON("getHorarioAsignacion.htm", { idAsignacionCursoPensum: valueCurso, idTipoHorario: valueHorario}, function(lstHorario) {
                        var options = '';
                        $.each(lstHorario, function (index,value) {
                            options += "<option value='" + value.seccion + "'>" + value.seccion+ "</option>";
                        });
                        $('#slcHorario').html(options);
                    });
                } else {
                    $('#slcHorario').html("");
                }
                
            }
        </script>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
