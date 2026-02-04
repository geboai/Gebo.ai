package ai.gebo.llms.chat.abstraction.layer.services;

public class GeboChatSessionLifecycleException extends GeboChatException {

	public GeboChatSessionLifecycleException() {
		
	}

	public GeboChatSessionLifecycleException(String message) {
		super(message);
		
	}

	public GeboChatSessionLifecycleException(Throwable cause) {
		super(cause);
		
	}

	public GeboChatSessionLifecycleException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public GeboChatSessionLifecycleException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
