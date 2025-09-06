package ai.gebo.architecture.documents.cache.service;

public class DocumentCacheAccessException extends Exception {

	public DocumentCacheAccessException() {
		
	}

	public DocumentCacheAccessException(String message) {
		super(message);
		
	}

	public DocumentCacheAccessException(Throwable cause) {
		super(cause);
		
	}

	public DocumentCacheAccessException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public DocumentCacheAccessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
