
<%@ taglib uri="http://java.sun.com/portlet" prefix="portlet"%>

<%@ page import="java.util.*" %>
<portlet-client-model:init>
	<portlet-client-model:require module="ibm.portal.xml.*"/>
	<portlet-client-model:require module="ibm.portal.portlet.*"/>   
</portlet-client-model:init>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href='<%= request.getContextPath() %>/resources/css/jquery-ui.css'>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/jquery-latest.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/bootpag.min.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/general.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/uno_magazine_llamadasWS.js'></script>
<script type="text/javascript" src='<%= request.getContextPath() %>/resources/js/jquery-ui.js'></script>
<link href="<%= request.getContextPath() %>/resources/css/paginacion.css" rel="stylesheet" type="text/css" /> 
<script type="text/javascript">
var generaHtml = "<%=response.encodeURL(request.getContextPath())%>" + "/GeneraHtmlServlet";
</script>
<script type="text/javascript">
var sendPush = "<%=response.encodeURL(request.getContextPath())%>" + "/SendPushServlet";
</script>

<script type="text/javascript">
var llamadaAPNS_Servlet = "<%=response.encodeURL(request.getContextPath())%>" + "/LlamadaAPNS";
</script>

<style>
	.content { 
			padding: 10px 10px 10px 10px;
			border : 1px solid #ccc;
	}
</style>

<c:if test="${empty requestScope.respuestaWSRequest}" >
<script>
$(function() {
	$( "#dialog-confirm" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		buttons: {
			"Breaking News": function() {
				$("#breaking-news").hide();
				$("#allContent").show();
				$( this ).dialog( "close" );
			},
			"En Vivo": function() {
				$("#breaking-news").show();
				$("#allContent").hide();
				$( this ).dialog( "close" );
			}
		}
	});
});
</script>
<div id="dialog-confirm">
	Favor de seleccionar una opcion
</div>

<div id="breaking-news" style="display: none; " >
<div class="content">
<table>
<tr>
	<td>T&iacute;tulo</td>
</tr>
<tr>
	<td><input type="text" id="titulo_breaking-news"> </td>
</tr>
<tr>	
	<td>Descripci&oacute;n</td>
</tr>
<tr>	
	<td><input type="text" id="descripcion_breaking-news"> </td>
</tr>
</table>
</div>
<br>
<div class="content">
	Seleccione su Carrier:
	<select id="compania_breaking-news" class="medium">
		<option value="-1">Selecciona una Opción</option>
		<option value="movistar">Movistar</option>
		<option value="telcel">Telcel</option>
		<option value="todos">Todos</option>
	</select>
</div>
<br>
<div class="content">
	Seleccione S.O:
	<select id="sistema_breaking-news" class="medium">
		<option value="-1">Selecciona una Opción</option>
		<option value="android">Android</option>
		<option value="ios">IOS</option>
		<option value="todos">Todos</option>
	</select>
</div>
<!--<form name="formPush_breaking_news" method="post" action="<portlet:actionURL/>">
	<div id="div_enviar_push_breaking-news" class="content" style="padding-top: 30px; padding-bottom: 30px;">
		<input type="button" value="Enviar Push" id="enviar_push_breaking_news" onclick="enviarNotificacionBreakingNews(document.formPush_breaking_news);" >
		<span id="error_enviar_push_breaking-news">&nbsp;</span>
		<input type="hidden" id="dispatchHiddenBreaking_news" name="dispatchHiddenBreaking_news" value=""/>
		<input type="hidden" id="companiaHiddenBreaking_news" name="companiaHiddenBreaking_news" value=""/>
		<input type="hidden" id="sistemaHiddenBreaking_news" name="sistemaHiddenBreaking_news" value=""/>
		<input type="hidden" id="tituloHiddenBreaking_news" name="tituloHiddenBreaking_news" value=""/>
		<input type="hidden" id="descripcionHiddenBreaking_news" name="descripcionHiddenBreaking_news" value=""/>
	</div>  
</form>
 -->
 <form name="formPush_breaking_news" id="formPush_breaking_news" method="post" action="<portlet:actionURL/>">
 <div id="div_enviar_push_breaking-news" class="content" style="padding-top: 30px; padding-bottom: 30px;">
		<input type="button" value="Enviar Push" id="enviar_push_breaking_news" onclick="enviarNotificacionBreakingNewsS(document.formPush_breaking_news);" >
		<span id="error_enviar_push_breaking-news">&nbsp;</span>
		<input type="hidden" id="dispatchHiddenBreaking_news" name="dispatchHiddenBreaking_news" value=""/>
		<input type="hidden" id="companiaHiddenBreaking_news" name="companiaHiddenBreaking_news" value=""/>
		<input type="hidden" id="sistemaHiddenBreaking_news" name="sistemaHiddenBreaking_news" value=""/>
		<input type="hidden" id="tituloHiddenBreaking_news" name="tituloHiddenBreaking_news" value=""/>
		<input type="hidden" id="descripcionHiddenBreaking_news" name="descripcionHiddenBreaking_news" value=""/>
		<input type="hidden" id="tipo_newsHiddenBreaking_news" name="tipo_newsHiddenBreaking_news" value="VIVO"/>
		
	</div>
</form>
	
</div>

<div id="allContent" style="display: none; ">
<div id="wait" style="display: none; width: 69px; height: 89px; border: 1px solid black; position: absolute; top: 50%; left: 50%; padding: 2px;">
	<img src="<%= request.getContextPath() %>/resources/images/loader.gif" width="64" height="64">
	<br>Loading..
</div>
<div class="content">	
	<p><h3>Elementos del Componente</h3></p>
	<div id="elementos_magazine">			

	</div>
</div>
	
<!-- Ini Secciones -->	
<div class="content">
	<strong>Tipo Secci&oacute;n </strong>
	<select name="tipo_seccion" id="tipo_seccion">
		<option value="-1">Seleccione</option>
	</select>
</div>
<div class="content">
	<strong>Secci&oacute;n </strong>
	<select name="seccion" id="seccion">
		<option value="-1">Seleccione</option>
	</select>
</div>
<div class="content">
	<strong>Categor&iacute;a</strong>
	<select name="categoria" id="categoria">
		<option value="-1">Seleccione</option>
	</select>
</div>
<!-- Fin Secciones -->
<br>
<div class="content">
	Seleccione su Carrier:
	<select id="compania" class="medium">
		<option value="-1">Selecciona una Opción</option>
		<option value="movistar">Movistar</option>
		<option value="telcel">Telcel</option>
		<option value="todos">Todos</option>
	</select>
</div>
<br>
<div class="content">
	Seleccione S.O:
	<select id="sistema" class="medium">
		<option value="-1">Selecciona una Opción</option>
		<option value="android">Android</option>
		<option value="ios">IOS</option>
		<option value="todos">Todos</option>
	</select>
</div>

</br>
<div class="content">
	<input type="button" value="Agregar seleccion a magazine" id="agregar_noticias"/>
	<span id="error_seleccion">&nbsp;</span>
</div>

<div id="loading" style="height:50px;display:none;">
Procesando ...
<img src="<%=request.getContextPath() %>/resources/images/loader.gif" style="vertical-align:middle; width:96px; height:25px"/>
</div> 
<div class="content">
	<p><h2><span id="titulo_seccion"></span></h2></p>
	<div id="noticias" >
		<!-- Noticias -->
	</div>
	<div id="page-selection" class="pagination bootpag" style="text-align:right;" ></div>
</div>

<!-- <form name="formPush" method="post" action="<portlet:actionURL/>">
	<div id="div_enviar_push" class="content" style="padding-top: 30px; padding-bottom: 30px;">
		<input type="button" value="Enviar Push" id="enviar_push" onclick="enviarNotificacionPush(document.formPush);" disabled="disabled">
		<span id="error_enviar_push">&nbsp;</span>
		<input type="hidden" id="dispatchHidden" name="dispatchHidden" value=""/>
		<input type="hidden" id="companiaHidden" name="companiaHidden" value=""/>
		<input type="hidden" id="sistemaHidden" name="sistemaHidden" value=""/>
		<input type="hidden" id="listNotasHidden" name="listNotasHidden" value=""/>
		<input type="hidden" id="tituloHidden" name="tituloHidden" value=""/>
		<input type="hidden" id="descripcionHidden" name="descripcionHidden" value=""/>
	</div>  
</form>
-->
<form name="formPush" id="formPush" method="post" action="<portlet:actionURL/>">
<div id="div_enviar_push" class="content" style="padding-top: 30px; padding-bottom: 30px;">
		<input type="button" value="Enviar Push" id="enviar_push" onclick="enviarNotificacionPushS();" disabled="disabled">
		<span id="error_enviar_push">&nbsp;</span>
		<input type="hidden" id="dispatchHidden" name="dispatchHidden" value=""/>
		<input type="hidden" id="companiaHidden" name="companiaHidden" value=""/>
		<input type="hidden" id="sistemaHidden" name="sistemaHidden" value=""/>
		<input type="hidden" id="listNotasHidden" name="listNotasHidden" value=""/>
		<input type="hidden" id="tituloHidden" name="tituloHidden" value=""/>
		<input type="hidden" id="descripcionHidden" name="descripcionHidden" value=""/>
		<input type="hidden" id="tipo_newsHidden" name="tipo_newsHidden" value="NEWS"/>
		
</div>
</form>

</div>



</c:if>
<c:if test="${not empty requestScope.respuestaWSRequest}" >
<!-- ini Result WS -->
<form name="formResultadoWS" method="post" action="<portlet:actionURL/>">
<input type="hidden" id="dispatchHidden" name="dispatchHidden" value="END_PUSH"/>
<div id="resultadoWS" class="content">
    <h2>El estado de la solictud es: <b><span style="background-color: #FFBABA; color: #D8000C" id="resultadoWSpan">${requestScope.respuestaWSRequest}</span></b></h2>
	<input type="button" value="Regresar" id="regresar" onclick="finEnviarPush(document.formResultadoWS);">
</div>
</form>
<!-- end Result WS -->
</c:if>
<!-- end Noticias -->

<%
request.removeAttribute("respuestaWSRequest");
%>
