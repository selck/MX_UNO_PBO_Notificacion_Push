package mx.com.amx.unotv.notificacion.push.portlet;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.*;

import mx.com.amx.unotv.notificacion.push.dto.ParametrosDTO;
import mx.com.amx.unotv.notificacion.push.dto.PushDTO;
import mx.com.amx.unotv.notificacion.push.dto.RespuestaBooleanDTO;
import mx.com.amx.unotv.notificacion.push.dto.WrapperPushDTO;
import mx.com.amx.unotv.notificacion.push.util.CargaProperties;
import mx.com.amx.unotv.notificacion.push.util.LlamadasWS;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;


public class MX_UNO_PBO_Notificacion_PushPortlet extends javax.portlet.GenericPortlet {
	
	private Logger logger=Logger.getLogger(MX_UNO_PBO_Notificacion_PushPortlet.class);
	
	private SimpleDateFormat formatFechaNormal = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat formatFechaAPP = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
	
	public void init() throws PortletException{
		super.init();
	}
	
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		logger.debug("===== doView =====");
		String dispatch=(String) (request.getPortletSession().getAttribute("dispatch")==null || request.getPortletSession().getAttribute("dispatch").equals("")?"inicio":request.getPortletSession().getAttribute("dispatch"));
		String respuestaWSRequest=(String) (request.getPortletSession().getAttribute("respuestaWSRequest")==null || request.getPortletSession().getAttribute("respuestaWSRequest").equals("")?"vacio":request.getPortletSession().getAttribute("respuestaWSRequest"));
		
		try {
			logger.debug("dispatch: "+dispatch);
			logger.debug("respuestaWSRequest: "+respuestaWSRequest);
			
			if(dispatch.equalsIgnoreCase("inicio")){
				dispatch="/resources/jsp/inicio_push.jsp";
			}
			if(!respuestaWSRequest.equals("vacio")){
				request.setAttribute("respuestaWSRequest",respuestaWSRequest);
				/*if(respuestaWSRequest.equalsIgnoreCase("ok")){
					request.setAttribute("respuestaWSRequest", "Se enviaron las notificaciones Push con exito");
				}else{
					request.setAttribute("respuestaWSRequest", "Hubo un problema al enviar las notificaciones push, favor de contactar al administrador del sistema");
				}*/
			}
			response.setContentType(request.getResponseContentType());
			PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(dispatch);
			rd.include(request,response);
		} catch (Exception e) {
			logger.error("Error DoView: ",e);
		}
	}
	
	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, java.io.IOException {
		try {
			logger.debug("===== processAction =====");
			String compania , sistema_operativo, listNotas = "", titulo = "", descripcion = "", dispatch;
			dispatch=(String) (request.getParameter("dispatchHidden")==null?"":request.getParameter("dispatchHidden"));
			logger.debug("dispatch: "+dispatch);
			if(dispatch.equalsIgnoreCase("ENVIANDO_PUSH")){
				request.getPortletSession().setAttribute("respuestaWSRequest","Se esta enviando la notificacion a los dispositivos...");
			}else if(dispatch.equalsIgnoreCase("END_PUSH")){
				request.getPortletSession().removeAttribute("respuestaWSRequest");
			}
			/*if(!dispatch.equals("")){
				compania=(String) (request.getParameter("companiaHidden")==null ?"":request.getParameter("companiaHidden"));
				sistema_operativo=(String) (request.getParameter("sistemaHidden")==null ?"":request.getParameter("sistemaHidden"));
				listNotas=(String) (request.getParameter("listNotasHidden")==null?"":request.getParameter("listNotasHidden"));
				logger.debug("dispatch: "+dispatch);
				logger.debug("compania: "+compania);
				logger.debug("sistema_operativo: "+sistema_operativo);
				logger.debug("listNotas: "+listNotas);
				
			}else{
				dispatch=(String) (request.getParameter("dispatchHiddenBreaking_news")==null?"":request.getParameter("dispatchHiddenBreaking_news"));
				compania=(String) (request.getParameter("companiaHiddenBreaking_news")==null ?"":request.getParameter("companiaHiddenBreaking_news"));
				sistema_operativo=(String) (request.getParameter("sistemaHiddenBreaking_news")==null ?"":request.getParameter("sistemaHiddenBreaking_news"));
				titulo=(String) (request.getParameter("tituloHiddenBreaking_news")==null?"":request.getParameter("tituloHiddenBreaking_news"));
				descripcion=(String) (request.getParameter("descripcionHiddenBreaking_news")==null?"":request.getParameter("descripcionHiddenBreaking_news"));
				logger.debug("dispatch: "+dispatch);
				logger.debug("compania: "+compania);
				logger.debug("sistema_operativo: "+sistema_operativo);
				logger.debug("titulo: "+titulo);
				logger.debug("descripcion: "+descripcion);
			}
			
			
			if(dispatch.equalsIgnoreCase("END_PUSH")){
				request.getPortletSession().removeAttribute("respuestaWSRequest");
			}if(dispatch.equalsIgnoreCase("SEND_BREAKING_NEWS")){
				CargaProperties cargaProperties=new CargaProperties();
				ParametrosDTO param=cargaProperties.obtenerPropiedades();
				
				LlamadasWS llamadasWS=new LlamadasWS(param.getURL_WS_BASE());
				try{
					RespuestaBooleanDTO respuestaBooleanDTO=new RespuestaBooleanDTO();
					List<PushDTO> listPush=new ArrayList<PushDTO>();
					PushDTO pushDTO=new PushDTO();
			    	pushDTO.setCompania(compania);
			    	pushDTO.setSistema_operativo(sistema_operativo);
			    	pushDTO.setId_tag("");
			    	pushDTO.setId_contenido("");
			    	pushDTO.setTitulo(titulo);
			    	pushDTO.setDescripcion(descripcion);
			    	Date date = new Date();
			    	pushDTO.setFecha_publicacion(formatFechaAPP.format(date));
			    	pushDTO.setId_tipo_nota("");
			    	pushDTO.setImagen_principal("");
			    	pushDTO.setImagen_miniatura("");
			    	pushDTO.setId_categoria("");
			    	listPush.add(pushDTO);
			    	
			    	WrapperPushDTO wrapperPushDTO=new WrapperPushDTO();
				    wrapperPushDTO.setListPush(listPush);
				    logger.info("Empezando la llamada");
				    logger.info(new Date().toString());
				    respuestaBooleanDTO=llamadasWS.sendNotificationPush(wrapperPushDTO);
				    logger.info("Terminando la llamada");
				    logger.info(new Date().toString());
					logger.info("Respuesta del WS: "+respuestaBooleanDTO.getMensaje());
					request.getPortletSession().setAttribute("respuestaWSRequest", respuestaBooleanDTO.getMensaje());
					//request.getPortletSession().setAttribute("respuestaWSRequest","Se estan enviando todas las notificacionesa los dispositivos...");
				}catch(Exception e){
					logger.error("Error al hacer lo del RestTemplate "+e.getMessage());
				}
			}else if(dispatch.equalsIgnoreCase("SEND_PUSH")){
				
				CargaProperties cargaProperties=new CargaProperties();
				ParametrosDTO param=cargaProperties.obtenerPropiedades();
				
				LlamadasWS llamadasWS=new LlamadasWS(param.getURL_WS_BASE());
				try{
					RespuestaBooleanDTO respuestaBooleanDTO=new RespuestaBooleanDTO();
					if(!listNotas.equals("")){
						List<PushDTO> listPush=new ArrayList<PushDTO>();
						JSONArray listJson = new JSONArray(listNotas);
						if(listJson!=null && listJson.length()>0){
						    for (int i = 0; i < listJson.length(); i++) {
						    	JSONObject object = listJson.getJSONObject(i);
						    	PushDTO pushDTO=new PushDTO();
						    	pushDTO.setCompania(compania);
						    	pushDTO.setSistema_operativo(sistema_operativo);
						    	pushDTO.setId_tag("");
						    	pushDTO.setId_contenido(object.getString("fcIdContenido"));
						    	pushDTO.setTitulo(object.getString("fcTitulo"));
						    	pushDTO.setDescripcion(object.getString("fcDescripcion"));
						    	String fecha=object.getString("fcFechaPublicacion");
						    	Date date = formatFechaNormal.parse(fecha);
						    	pushDTO.setFecha_publicacion(formatFechaAPP.format(date));
						    	pushDTO.setId_tipo_nota(object.getString("fcIdTipoNota"));
						    	pushDTO.setImagen_principal(object.getString("fcImgPrincipal"));
						    	pushDTO.setImagen_miniatura(object.getString("fcImgPrincipal").replace("Principal", "Miniautra"));
						    	pushDTO.setId_categoria(object.getString("fcIdCategoria"));
						    	listPush.add(pushDTO);	   
						    }
						    WrapperPushDTO wrapperPushDTO=new WrapperPushDTO();
						    wrapperPushDTO.setListPush(listPush);
						    logger.info("Empezando la llamada");
						    logger.info(new Date().toString());
						    respuestaBooleanDTO=llamadasWS.sendNotificationPush(wrapperPushDTO);
						    logger.info("Terminando la llamada");
						    logger.info(new Date().toString());
						}
					}
					logger.info("Respuesta del WS: "+respuestaBooleanDTO.getMensaje());
					//request.getPortletSession().setAttribute("respuestaWSRequest", respuestaBooleanDTO.getMensaje());
					request.getPortletSession().setAttribute("respuestaWSRequest",respuestaBooleanDTO.getMensaje());
				}catch(Exception e){
					logger.error("Error al hacer lo del RestTemplate "+e.getMessage());
				}
			}*/			
		} catch (Exception e) {
			logger.error("Error processAction: ",e);
		}

	}

}

