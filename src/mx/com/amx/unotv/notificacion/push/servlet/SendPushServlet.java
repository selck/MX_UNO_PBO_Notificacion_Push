package mx.com.amx.unotv.notificacion.push.servlet;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mx.com.amx.unotv.notificacion.push.dto.ParametrosDTO;
import mx.com.amx.unotv.notificacion.push.dto.PushDTO;
import mx.com.amx.unotv.notificacion.push.dto.RespuestaBooleanDTO;
import mx.com.amx.unotv.notificacion.push.dto.WrapperPushDTO;
import mx.com.amx.unotv.notificacion.push.util.CargaProperties;
import mx.com.amx.unotv.notificacion.push.util.LlamadasWS;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class SendPushServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger=Logger.getLogger(SendPushServlet.class);
	private SimpleDateFormat formatFechaNormal = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat formatFechaAPP = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss.S");
	
	public SendPushServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.info("Intento de petición get "+this.getClass().getName());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			logger.debug("===== SendPushServlet =====");
			String compania ="", sistema_operativo = "", listNotas = "", titulo = "", descripcion = "", tipo_news, dispatch;
			dispatch=(String) (request.getParameter("dispatchHidden")==null?"":request.getParameter("dispatchHidden"));
			
			if(!dispatch.equals("")){
				compania=(String) (request.getParameter("companiaHidden")==null ?"":request.getParameter("companiaHidden"));
				sistema_operativo=(String) (request.getParameter("sistemaHidden")==null ?"":request.getParameter("sistemaHidden"));
				listNotas=(String) (request.getParameter("listNotasHidden")==null?"":request.getParameter("listNotasHidden"));
				tipo_news=(String) (request.getParameter("tipo_newsHidden")==null?"":request.getParameter("tipo_newsHidden"));
				logger.debug("dispatch: "+dispatch);
				logger.debug("compania: "+compania);
				logger.debug("sistema_operativo: "+sistema_operativo);
				logger.debug("listNotas: "+listNotas);
				logger.debug("tipo_news: "+tipo_news);
			}else{
				dispatch=(String) (request.getParameter("dispatchHiddenBreaking_news")==null?"":request.getParameter("dispatchHiddenBreaking_news"));
				compania=(String) (request.getParameter("companiaHiddenBreaking_news")==null ?"":request.getParameter("companiaHiddenBreaking_news"));
				sistema_operativo=(String) (request.getParameter("sistemaHiddenBreaking_news")==null ?"":request.getParameter("sistemaHiddenBreaking_news"));
				titulo=(String) (request.getParameter("tituloHiddenBreaking_news")==null?"":request.getParameter("tituloHiddenBreaking_news"));
				descripcion=(String) (request.getParameter("descripcionHiddenBreaking_news")==null?"":request.getParameter("descripcionHiddenBreaking_news"));
				tipo_news=(String) (request.getParameter("tipo_newsHiddenBreaking_news")==null?"":request.getParameter("tipo_newsHiddenBreaking_news"));
				logger.debug("dispatch: "+dispatch);
				logger.debug("compania: "+compania);
				logger.debug("sistema_operativo: "+sistema_operativo);
				logger.debug("titulo: "+titulo);
				logger.debug("descripcion: "+descripcion);
				logger.debug("tipo_news: "+tipo_news);
			}
			
			if(dispatch.equalsIgnoreCase("SEND_BREAKING_NEWS")){
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
			    	pushDTO.setTipo_news(tipo_news);
			    	listPush.add(pushDTO);
			    	
			    	WrapperPushDTO wrapperPushDTO=new WrapperPushDTO();
				    wrapperPushDTO.setListPush(listPush);
				    logger.info("Empezando la llamada");
				    logger.info(new Date().toString());
				    respuestaBooleanDTO=llamadasWS.sendNotificationPush(wrapperPushDTO);
				    logger.info("Terminando la llamada");
				    logger.info(new Date().toString());
					logger.info("Respuesta del WS: "+respuestaBooleanDTO.getMensaje());

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
						    	pushDTO.setTipo_news(tipo_news);
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
				}catch(Exception e){
					logger.error("Error al hacer lo del RestTemplate "+e.getMessage());
				}
			}			
		} catch (Exception e) {
			logger.error("Error processAction: ",e);
		}
	}

}
