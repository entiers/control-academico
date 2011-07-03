<%-- 
    Document   : buscarAsignacionCatedraticoHorario
    Created on : 22/06/2011, 03:39:45 PM
    Author     : Carlos Solorzano
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
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <script type="text/javascript">
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

            function eliminarACH(idACH,idCat){
                var conf = confirm('<fmt:message key="asignacionHorarioCatedratico.desasignar.confirmar"/>');
                    if (conf){
                    $.get("eliminarACH.htm", {idACH: idACH}, function(value){
                        if(value="ok"){
                            alert('<fmt:message key="asignacionHorarioCatedratico.desasignado"/>');
                            location.href = 'buscarAsignacionCatedraticoHorario.htm?idCatedratico=' + idCat;
                        }
                        else
                            alert(value);
                    });
                }
            }

        </script>
        <title><fmt:message key="admin.asignacionHorarioCatedratico.titulo" /></title>
    </head>
    <body>
        <h1><fmt:message key="admin.asignacionHorarioCatedratico.titulo" /></h1>

        <%-- fragmento que muestra la informacion del catedratico--%>
        <%@include file="../../jspf/formularios/informacion/formularioInformacionCatedratico.jspf" %>

        <fieldset>
            <legend><fmt:message key="buscarHorario.tituloListado"/></legend>
            <display:table class="ui-widget ui-widget-content" name="listaACH" id="ACH" requestURI="buscarHorarioPag.htm"
                           pagesize="50" >
                <display:column property="horario.asignacionCursoPensum.curso.nombre" titleKey="agregarHorario.curso" />
                <display:column property="horario.asignacionCursoPensum.pensum.codigo" titleKey="pensum.menu" />
                <display:column property="horario.seccion" titleKey="agregarHorario.seccion" />
                <display:column property="horario.tipo" titleKey="agregarHorario.tipo" />
                <display:column titleKey="acciones" style="text-align:center;">
                    <a href="javascript:eliminarACH(${ACH.idAsignacionCatedraticoHorario},${ACH.catedratico.idCatedratico})">
                        <fmt:message key="asignacionHorarioCatedratico.desasignar"/>
                    </a>
                </display:column>
            </display:table>
        </fieldset>

        <%-- fragmento que muestra como mensaje popup el resultado de las operaciones --%>
        <%@include file="../../jspf/plantilla/popupMensaje.jspf" %>

    </body>
</html>

