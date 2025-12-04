package ai.gebo.architecture.documents.cache.service;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DocumentCacheAccessException extends Exception {
	List<Throwable> exceptions = null;

	public DocumentCacheAccessException() {

	}

	public DocumentCacheAccessException(String message) {
		super(message);

	}

	public DocumentCacheAccessException(Throwable cause) {
		super(cause);
		exceptions=new ArrayList<Throwable>();
		exceptions.add(cause);
	}

	public DocumentCacheAccessException(String message, Throwable cause) {
		super(message, cause);
		exceptions=new ArrayList<Throwable>();
		exceptions.add(cause);
	}

	public DocumentCacheAccessException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		exceptions=new ArrayList<Throwable>();
		exceptions.add(cause);
	}

	public DocumentCacheAccessException(String message, List<Throwable> exceptions) {
		super(message);
		this.exceptions = exceptions;
	}

}
