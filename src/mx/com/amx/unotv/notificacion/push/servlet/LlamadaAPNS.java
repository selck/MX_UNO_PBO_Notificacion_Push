package mx.com.amx.unotv.notificacion.push.servlet;

import java.io.IOException;

import javapns.Push;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;


public class LlamadaAPNS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final static String pathCertificado="/var/dev-repos/unotv/ios/ProductPush.p12";
    private static final Logger logger=Logger.getLogger(LlamadaAPNS.class);
    public LlamadaAPNS() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String tipoLlamada=request.getParameter("tipoLlamada");
		logger.info("TipoLlamada: "+tipoLlamada);
		try {
			if(tipoLlamada.equalsIgnoreCase("java-apns")){
				llamadaJava_APNS();
			}else if(tipoLlamada.equalsIgnoreCase("javapns")){
				llamadaJAVAPNS();
			}
		} catch (Exception e) {
			logger.error("");
		}
	}
	private static void llamadaJAVAPNS() {
		
		String devideTokenFer="30b21ecd14661fe1ca40be1ce1b81854545d74a6590bfa64a3276611f09613f4";
		String devideTokenEliss="c596d31a3997bdc19365efbdd9baf173dcef49dd966d6ea4216424e6ded42cfd";
		String devideTokenIsai="5361fa1ddbaca1973fdece897ec5b927c8f8c7f4f74e57b657cb3e36b464ed8e";
		String deviceTokenProductivos="c596d31a3997bdc19365efbdd9baf173dcef49dd966d6ea4216424e6ded42cfd|" //Eliss
				+ "5361fa1ddbaca1973fdece897ec5b927c8f8c7f4f74e57b657cb3e36b464ed8e|"//Isai
				+ "48cd475e33842a2a5976a04cbfc4cf4acec2b6cb95371a1776be424696d2253c|"//Tona
				+ "be73c39b007606cfbc0c433dfec02bdc298f91c6f045ce515d5326b546881fce|"// Fer
				+ "66977ed6e3aeb23320c0f67c31e89d185c53ca5e0e87f118f4fbac97a37082db";//Ruth
		try {
			StringBuffer payload_cadena = new StringBuffer();
			payload_cadena.append("{");
			payload_cadena.append("	\"aps\": {");
			payload_cadena.append("		\"alert\": {");
			payload_cadena.append("			\"action-loc-key\": \"PLAY\",");
			payload_cadena.append("			\"body\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe&ntilde;a Body\",");
			payload_cadena.append("			\"noticia\": {");
			payload_cadena.append("				\"id_tag\": \"\",");
			payload_cadena.append("				\"id_seccion\": \"negocios\",");
			payload_cadena.append("				\"id_noticia\": \"50f04e40-2044-4103-9a67-ab5e03abd74f\",");
			payload_cadena.append("				\"titulo\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe&ntilde;a Tit\",");
			payload_cadena.append("				\"descripcion\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe&ntilde;a Desc\"");
			payload_cadena.append("			}");
			payload_cadena.append("		},");
			payload_cadena.append("			\"sound\": \"default\"");
			payload_cadena.append("	}");
			payload_cadena.append("}");
	   		
	   		BasicConfigurator.configure();
	   		Push.alert("saludo", pathCertificado, "", true, devideTokenFer);
	   		
	   		
	   		
	   		
	   		//PushNotificationPayload payload = PushNotificationPayload.complex();
	   		//PushNotificationPayload payload=PushNotificationPayload.fromJSON(payload_cadena.toString());
	   		
	        //System.out.println(payload.toString());
	        //Push.payload(payload, pathCertificado, "", true, devideTokenIsai);
	   		/*for (String devideToken : deviceTokenProductivos.split("\\|")) {
	   			Push.alert(notificationBody.toString(), pathCertificado1, "", false, devideToken);
	   			System.out.println("-----------------------------------------------------------------------");
			} */
		    
		} catch (Exception e) {
			logger.error("Error llamadaJAVAPNS",e);
		}
	}
	private static void llamadaJava_APNS() {

		try {
			logger.debug("TestPushiOS...");
			StringBuffer payload = new StringBuffer();
			payload.append("{");
			payload.append("	\"aps\": {");
			payload.append("		\"alert\": {");
			payload.append("			\"action-loc-key\": \"PLAY\",");
			payload.append("			\"body\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe&ntilde;a Body\",");
			payload.append("			\"noticia\": {");
			payload.append("				\"id_tag\": \"\",");
			payload.append("				\"id_seccion\": \"negocios\",");
			payload.append("				\"id_noticia\": \"50f04e40-2044-4103-9a67-ab5e03abd74f\",");
			payload.append("				\"titulo\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe&ntilde;a Tit\",");
			payload.append("				\"descripcion\": \"Costo de luz ha disminuido 10% desde 2014 gracias a reforma: Pe&ntilde;a Desc\"");
			payload.append("			}");
			payload.append("		},");
			payload.append("			\"sound\": \"default\"");
			payload.append("	}");
			payload.append("}");

			String deviceTokenProductivos = "c596d31a3997bdc19365efbdd9baf173dcef49dd966d6ea4216424e6ded42cfd|" // Eliss
					+ "5361fa1ddbaca1973fdece897ec5b927c8f8c7f4f74e57b657cb3e36b464ed8e|"// Isai
					+ "48cd475e33842a2a5976a04cbfc4cf4acec2b6cb95371a1776be424696d2253c|"// Tona
					+ "30b21ecd14661fe1ca40be1ce1b81854545d74a6590bfa64a3276611f09613f4|"// Fer
					+ "66977ed6e3aeb23320c0f67c31e89d185c53ca5e0e87f118f4fbac97a37082db";// Ruth
			logger.debug("Generando PayLoad");
			ApnsService service = APNS.newService()
					.withCert(pathCertificado, "").withProductionDestination()
					.build();
			
			logger.debug("Generando PayLoad");
			logger.debug("-------->" + payload.toString());
			
			for (String token : deviceTokenProductivos.split("\\|")) {
				logger.debug("Sending push notification...");
					service.push(token, payload.toString());
					logger.debug("Ok...");
					logger.debug("----------------------------------------"); 
				}
			

		} catch (Exception e) {
			logger.error("Error llamadaJava_APNS",e);
		}
	}
}
