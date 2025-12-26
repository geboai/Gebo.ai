package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface TypedInputStream {
	String getContentType();

	InputStream getInputStream() throws UnsupportedOperationException, IOException;

	@Getter
	@AllArgsConstructor
	static class TypedInputStreamImpl implements TypedInputStream {
		final InputStream inputStream;
		final String contentType;
	}

	public static TypedInputStream of(InputStream inputStream, String contentType) {
		return new TypedInputStreamImpl(inputStream, contentType);
	}
}