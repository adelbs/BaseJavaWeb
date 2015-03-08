package obi1.fi.business.exception;

public class FiBusinessException extends RuntimeException {
	
	private static final long serialVersionUID = 4681835865745236493L;
	
	private final String message;

	public FiBusinessException(Throwable e, String message) {
		super(e);
		this.message = message;
	}
	
	public FiBusinessException(String message) {
		this.message = message;
	}

	public final String getMessage() {
		return message;
	}
}
