package obi1.fi.web.exception;

public class FiAjaxException extends RuntimeException {
	
	private static final long serialVersionUID = 5904197633152922648L;

	private String customMessage;
	
	public FiAjaxException(Throwable cause) {
		super(cause);
	}

	public FiAjaxException(String customMessage) {
		this.customMessage = customMessage;
	}
	
	public String getCustomMessage() {
		return customMessage;
	}

	public void setCustomMessage(String customMessage) {
		this.customMessage = customMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
