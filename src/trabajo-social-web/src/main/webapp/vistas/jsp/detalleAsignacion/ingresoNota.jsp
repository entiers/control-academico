<%-- 

    Document   : ingresoNota
    Created on : 17/05/2011, 05:37:10 PM
    Author     : Carlos Solorzano
    Modificado : Maria Castillo

--%>

<%@page contentType="text/html" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>


<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script type="text/javascript">
            $(document).ready(function() {
              $("#fechaNotas").datepicker({
                changeMonth: true,
                changeYear: true,
                dateFormat: 'dd-mm-yy'
                }).datepicker('setDate','${fechaNotas}');    
            
                

                    
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
                if (valueTipoHorario !== ""){
                    $.get("getHorarioCatedratico.htm", { idTipoHorario: valueTipoHorario}, function(options, result, reqobj) {
                       $('#slcHorario').html(options);
                    });
                   

                }else{
                    $('#slcHorario').html("");
                }
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

            function guardar(value, url, pagina){
               
                var zona = ${limiteZona};

                var examenFinal = ${limiteExamenFinal}; 
                var isOk = true;
                $("#hdOficializar").val(value);
                $('#frmGuardar input:text').each(function(index,item){                    
                    if(index!==0 && isNaN(item.value)){
                        alert('<fmt:message key="ingresoNota.validaciones.notasNumericas"/>');
                        item.focus();
                        isOk = false;
                        return false;
                    }
                });                
                $('#frmGuardar input[name="listZona"]').each(function(i,item){                    
                    
                    
                    if(item.value<0 | item.value>zona){
                        alert('<fmt:message key="ingresoNota.validaciones.rango.zona"/> ' + 
                            zona + 
                            ' <fmt:message key="ingresoNota.validaciones.puntos"/>');
                        item.focus();
                        isOk = false;
                        return false;
                    }                    
                });

                $('#frmGuardar input[name="listFinal"]').each(function(index,item){                    
                    if(item.value<0 | item.value>examenFinal){
                        alert('<fmt:message key="ingresoNota.validaciones.rango.examenFinal"/> ' + 
                            examenFinal +
                            ' <fmt:message key="ingresoNota.validaciones.puntos"/>');
                        item.focus();
                        isOk = false;
                        return false;
                    }                    
                });
                
                if (isOk){
                    $("#linkValue").val(url);
                    $("#pagina").val(pagina);
                    $("#frmGuardar").submit();
                }else{
                    return false;
                }

            }
  
            
        </script>
                        


                      
                        
        <title><fmt:message key="ingresoNota.titulo"/></title>
    </head>
    <body>
        <h1><fmt:message key="ingresoNota.titulo"/></h1>
        
        <c:if test="${validacionesOK}">
            <form:form modelAttribute="datosIngresoNota" method="post" id="frmBusqueda" action="ingresoNota.htm">
                <fieldset>
                    <legend><fmt:message key="ingresoNota.busquedaAsignaciones"/></legend>
                    <div id="divCampos1">
                        <form:label for="tipoHorario" path="tipoHorario"><fmt:message key="agregarHorario.tipo"/>:</form:label>
                        <form:select path="tipoHorario"
                                     id="slcTipoHorario"
                                    cssStyle="width: 350px;">
                                    
                              <form:option  value="" label="Seleccionar Tipo Horario" />
                              <form:options items="${listaTipoHorario}" 
                                     itemLabel="descripcion"/>
                        </form:select>
                        
    
                    </div>
                    <div id="divCampos2">
                        <form:label for="horario.idHorario" path="horario.idHorario"><fmt:message key="horario.menu"/>:</form:label>
                        <form:select path="horario.idHorario" id="slcHorario">
                          
                        </form:select>
                        <span id="lblErrorHorario" class="claseError" />
                    </div>
                   
                    <input id="btnBuscar" type="submit" value='<fmt:message key="btnBuscar"/>' />
                </fieldset>
            </form:form>
        </c:if>
        
        <c:if test="${mostrarEstudiantes}">
            <form:form action="guardarOficializar.htm" id="frmGuardar" method="POST" modelAttribute="wrapperIngresoNota" >
               <div id="divCampos">
                    <form:label for="fechaNotas" path="fechaNotas">Fecha Acta*:</form:label>
                    <form:input path="fechaNotas" cssStyle="width:250px;" />
                    <form:errors path="fechaNotas" cssClass="claseError" />
                </div>
                <%@include file="../../jspf/formularios/formularioIngresoNotas.jspf" %>
            </form:form>
        </c:if>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>
    </body>
</html>
