package mx.com.amx.unotv.notificacion.push.dto;

import java.io.Serializable;

public class RespuestaBooleanDTO extends WrapperPushDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Boolean resultado;
	private String mensaje;
	private String codigo;
	private String causa_error;
	
	/**
	 * @return el resultado
	 */
	public Boolean getResultado() {
		return resultado;
	}
	/**
	 * @param resultado el resultado a establecer
	 */
	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}
	/**
	 * @return el mensaje
	 */
	public String getMensaje() {
		return mensaje;
	}
	/**
	 * @param mensaje el mensaje a establecer
	 */
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	/**
	 * @return el codigo
	 */
	public String getCodigo() {
		return codigo;
	}
	/**
	 * @param codigo el codigo a establecer
	 */
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	/**
	 * @return el causa_error
	 */
	public String getCausa_error() {
		return causa_error;
	}
	/**
	 * @param causa_error el causa_error a establecer
	 */
	public void setCausa_error(String causa_error) {
		this.causa_error = causa_error;
	}
	
	
	
}
