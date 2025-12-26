package ai.gebo.architecture.search.model;

public class SearchServiceException extends Exception {

	public SearchServiceException() {

	}

	public SearchServiceException(String message) {
		super(message);

	}

	public SearchServiceException(Throwable cause) {
		super(cause);

	}

	public SearchServiceException(String message, Throwable cause) {
		super(message, cause);

	}

	public SearchServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);

	}

}
