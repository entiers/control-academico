<%-- 
    Document   : agregarAsignacionCatedraticoHorario
    Created on : 22/06/2011, 03:42:40 PM
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
        <script type="text/javascript">
            $(document).ready(function() {
                //Cambio combo curso
                $('#slcTipoHorario').change(function() {
                    getHorarios($(this).val());
                });

                //Validacion frmBusqueda
                $('#frmBusqueda').submit(function(){
                    if($('#slcHorario').val()==null){
                        $('#lblErrorHorario').html('Campo obligatorio');
                        return false;
                    }
                });
            });
            
            function getHorarios(valueTipoHorario) {
                $.get("getAsignacionHorarioCatedratico.htm", { idTipoHorario: valueTipoHorario}, function(options) {
                    $('#slcHorario').html(options);
                });
            }
            $(function() {
                // se crea y configura el panel popup que muestra los
                // mensajes de resultados de las operaciones
                $("#popupMensaje").dialog({
                    autoOpen: <%= RequestUtil.getValorBoolean(request, "mostrarPopupMensaje") %>,
                    modal: true,
                    buttons: {
                        '<fmt:message key="btnAceptar"/>': function() {
                            <%if( request.getAttribute("url") != null ) {%>
                                location.href = "<%= request.getAttribute("url")%>";
                            <%} else {%>
                                $(this).dialog('close');
                            <%}%>
                        }
                    }
                });
            });
        </script>
        <title><fmt:message key="admin.asignacionHorarioCatedratico.titulo" /></title>
    </head>
    <body>
        <h1><fmt:message key="admin.asignacionHorarioCatedratico.titulo" /></h1>
        
        <%-- fragmento que muestra la informacion del catedratico--%>        
        <%@include file="../../jspf/formularios/informacion/formularioInformacionCatedratico.jspf" %>
        
        <form:form modelAttribute="datosIngresoNota" method="post" >            
            <fieldset>
                <legend><fmt:message key="ingresoNota.busquedaAsignaciones"/></legend>
                <div id="divCampos">
                    <form:label for="tipoHorario" path="tipoHorario"><fmt:message key="agregarHorario.tipo"/>:</form:label>
                    <form:select path="tipoHorario"
                                 id="slcTipoHorario"
                                 items="${listaTipoHorario}"
                                 itemLabel="descripcion"/>

                </div>
                <div id="divCampos">
                    <form:label for="horario.idHorario" path="horario.idHorario"><fmt:message key="horario.menu"/>:</form:label>
                    <form:select path="horario.idHorario" id="slcHorario">
                        <c:forEach items="${listaHorario}" var="horario">
                            <form:option value="${horario.idHorario}">${horario.curso.nombre} - ${horario.seccion}</form:option>
                        </c:forEach>
                    </form:select>
                    <span id="lblErrorHorario" class="claseError" />
                </div>

                <input id="btnBuscar" type="submit" value='<fmt:message key="miscursos.asignacionCursos.realizar"/>' />
            </fieldset>
        </form:form>
        
        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
