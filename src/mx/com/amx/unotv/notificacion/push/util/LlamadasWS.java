package mx.com.amx.unotv.notificacion.push.util;

import java.net.URLConnection;

import mx.com.amx.unotv.notificacion.push.dto.RespuestaBooleanDTO;
import mx.com.amx.unotv.notificacion.push.dto.WrapperPushDTO;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;


public class LlamadasWS {
	
	private final Logger logger = Logger.getLogger(this.getClass().getName());
	private RestTemplate restTemplate;
	private String URL_WS_BASE="";
	private HttpHeaders headers = new HttpHeaders();
	
	public LlamadasWS(String urlWS) {
		super();
		restTemplate = new RestTemplate();
		ClientHttpRequestFactory factory = restTemplate.getRequestFactory();

	        if ( factory instanceof SimpleClientHttpRequestFactory)
	        {
	            ((SimpleClientHttpRequestFactory) factory).setConnectTimeout( 300 * 1000 );
	            ((SimpleClientHttpRequestFactory) factory).setReadTimeout( 300 * 1000 );
	            logger.info("Inicializando rest template");
	        }
	        else if ( factory instanceof HttpComponentsClientHttpRequestFactory)
	        {
	            ((HttpComponentsClientHttpRequestFactory) factory).setReadTimeout( 300 * 1000);
	            ((HttpComponentsClientHttpRequestFactory) factory).setConnectTimeout( 300 * 1000);
	            logger.info("Inicializando rest template");
	        }
	        restTemplate.setRequestFactory( factory );
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        
			URL_WS_BASE = urlWS;
	}
	
	public RespuestaBooleanDTO sendNotificationPush(WrapperPushDTO wrapperPushDTO) {
		String metodo = "sendNotificationPush";
		String URL_WS = URL_WS_BASE + metodo;
		logger.debug("URLWS= " + URL_WS);
		RespuestaBooleanDTO respuestaBooleanDTO=null;
		try {
			HttpEntity<WrapperPushDTO> entity = new HttpEntity<WrapperPushDTO>( wrapperPushDTO);
			//List<MyModelClass> myModelClass=(List<MyModelClass>) restTemplate.postForObject(url,mvm,List.class);
			//return respuestaBooleanDTO =restTemplate.postForObject(URL_WS,PushDTO[].class,RespuestaBooleanDTO.class);
			//return respuestaBooleanDTO =restTemplate.postForObject(URL_WS,PushDTO[].class,RespuestaBooleanDTO.class);
			return respuestaBooleanDTO=restTemplate.postForObject(URL_WS, entity, RespuestaBooleanDTO.class);
		} catch (Exception e) {
			logger.error("Error sendNotificationPush: ",e);
			respuestaBooleanDTO=new RespuestaBooleanDTO();
			respuestaBooleanDTO.setCausa_error(e.getCause() == null ?"":e.getMessage());
			respuestaBooleanDTO.setCodigo("-1");
			respuestaBooleanDTO.setMensaje("ERROR");
			respuestaBooleanDTO.setResultado(false);
			return respuestaBooleanDTO;
		}
	}
}
