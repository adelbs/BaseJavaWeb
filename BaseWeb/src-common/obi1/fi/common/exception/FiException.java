package obi1.fi.common.exception;

public class FiException extends RuntimeException {
	
	private static final long serialVersionUID = 5904197633152922647L;

	private String message;

	public FiException(String message) {
		this.message = message;
	}
	
	public FiException(Throwable cause) {
		super(cause);
	}

	public FiException(Throwable cause, String message) {
		super(cause);
		this.message = message;
	}

	public final String getMessage() {
		return message;
	}
}
