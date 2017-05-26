
function enviarNotificacionBreakingNews(formPush){
	
	var compania = $("#compania_breaking-news").val();
	var sistema = $("#sistema_breaking-news").val();
	var titulo=$("#titulo_breaking-news").val();
	var descripcion=$("#descripcion_breaking-news").val();
	
	var flag=false;
	
	if(compania !="-1"){
		flag=true;
	}else{
		confirm("Favor de Seleccionar Compania");
	}
	
	if(sistema !="-1"){
		flag=true;
	}else{
		confirm("Favor de Seleccionar Sistema Operativo");
		flag=false;
	}
	
	if(titulo !=""){
		flag=true;
	}else{
		confirm("Favor de colocar un titulo");
		flag=false;
	}
	
	if(descripcion !=""){
		flag=true;
	}else{
		confirm("Favor de colocar una descripcion");
		flag=false;
	}
	
	if(flag){
		$("#dispatchHiddenBreaking_news").val("SEND_BREAKING_NEWS");
		$("#tituloHiddenBreaking_news").val(titulo);
		$("#descripcionHiddenBreaking_news").val(descripcion);
		$("#companiaHiddenBreaking_news").val(compania);
		$("#sistemaHiddenBreaking_news").val(sistema);
		$("#error_enviar_push_breaking-news").html("");
		formPush.submit();
	}
}
function enviarNotificacionBreakingNewsS(){
	
	var compania = $("#compania_breaking-news").val();
	var sistema = $("#sistema_breaking-news").val();
	var titulo=$("#titulo_breaking-news").val();
	var descripcion=$("#descripcion_breaking-news").val();
	var tipo=$("#tipo_newsHiddenBreaking_news").val();
	
	var flag=false;
	
	if(compania !="-1"){
		flag=true;
	}else{
		confirm("Favor de Seleccionar Compania");
	}
	
	if(sistema !="-1"){
		flag=true;
	}else{
		confirm("Favor de Seleccionar Sistema Operativo");
		flag=false;
	}
	
	if(titulo !=""){
		flag=true;
	}else{
		confirm("Favor de colocar un titulo");
		flag=false;
	}
	
	if(descripcion !=""){
		flag=true;
	}else{
		confirm("Favor de colocar una descripcion");
		flag=false;
	}
	
	if(flag){
		$.ajax({
			type: "post",
			dataType: "text",			
			url: sendPush,
			data: {
				"dispatchHiddenBreaking_news":"SEND_BREAKING_NEWS",
				"tituloHiddenBreaking_news":titulo,
				"descripcionHiddenBreaking_news":descripcion,
				"companiaHiddenBreaking_news":compania,
				"sistemaHiddenBreaking_news":sistema,
				"tipo_newsHiddenBreaking_news":tipo
			},
	        async:true,
	        error: function(data){
				console.log("error: "+data);
				$("#dispatchHidden").val("ENVIANDO_PUSH");
                $("#formPush_breaking_news").submit();
		    },
			success: function(data) {
		    	console.log("success: "+data);
				$("#dispatchHidden").val("ENVIANDO_PUSH");
                $("#formPush_breaking_news").submit();
			},
		    timeout: 3000
		});
		
		
	}
}
function enviarNotificacionPushS(){
	
	var compania = $("#compania").val();
	var sistema = $("#sistema").val();
	var tipo=$("#tipo_newsHidden").val();
	var flag=false;
	
	if(compania !="-1"){
		flag=true;
	}else{
		confirm("Favor de Seleccionar Compania");
	}
	
	if(sistema !="-1"){
		flag=true;
	}else{
		confirm("Favor de Seleccionar Sistema Operativo");
		flag=false;
	}
	
	if(flag){
		$.ajax({
			type: "post",
			dataType: "text",			
			url: sendPush,
			data: {
				"dispatchHidden":"SEND_PUSH",
				"listNotasHidden":JSON.stringify(arrayMagazine),
				"companiaHidden":compania,
				"sistemaHidden":sistema,
				"tipo_newsHidden":tipo
			},
	        async:true,
	        error: function(data){
				console.log("error: "+data);
				$("#dispatchHidden").val("ENVIANDO_PUSH");
                $("#formPush").submit();
		    },
			success: function(data) {
		    	console.log("success: "+data);
		    	$("#dispatchHidden").val("ENVIANDO_PUSH");
                $("#formPush").submit();
			},
		    timeout: 3000
		});
		
		
		/*$("#dispatchHidden").val("SEND_PUSH");
		$("#listNotasHidden").val(JSON.stringify(arrayMagazine));
		$("#companiaHidden").val(compania);
		$("#sistemaHidden").val(sistema);
		$("#error_enviar_push").html("");
		formPush.submit();*/
	}
}
	function enviarNotificacionPush(formPush){
		
		var compania = $("#compania").val();
		var sistema = $("#sistema").val();
		var flag=false;
		
		if(compania !="-1"){
			flag=true;
		}else{
			confirm("Favor de Seleccionar Compania");
		}
		
		if(sistema !="-1"){
			flag=true;
		}else{
			confirm("Favor de Seleccionar Sistema Operativo");
			flag=false;
		}
		
		if(flag){
			$("#dispatchHidden").val("SEND_PUSH");
			$("#listNotasHidden").val(JSON.stringify(arrayMagazine));
			$("#companiaHidden").val(compania);
			$("#sistemaHidden").val(sistema);
			$("#error_enviar_push").html("");
			formPush.submit();
		}
	}

	
	function finEnviarPush(formaFC){
		formaFC.submit();
	}
	function llamadaTest(){
		var tipoLlamada=$("#tipoLlamada").val();
		$.ajax({
			type: "post",
			dataType: "text/html",
			data: jQuery.param({ tipoLlamada: tipoLlamada}) ,
			url: llamadaAPNS_Servlet,
			cache:false,
			success: function(data){
				
			}
		});
	}