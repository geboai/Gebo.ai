package ai.gebo.security.services;

public class UserWorkflowException extends Exception {

	public UserWorkflowException() {
		
	}

	public UserWorkflowException(String message) {
		super(message);
		
	}

	public UserWorkflowException(Throwable cause) {
		super(cause);
		
	}

	public UserWorkflowException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public UserWorkflowException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
