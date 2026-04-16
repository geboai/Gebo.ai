package ai.gebo.llms.chat.pipelines.service;

public class ChatPipelineException extends Exception {

	public ChatPipelineException() {
		
	}

	public ChatPipelineException(String message) {
		super(message);
		
	}

	public ChatPipelineException(Throwable cause) {
		super(cause);
		
	}

	public ChatPipelineException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ChatPipelineException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
