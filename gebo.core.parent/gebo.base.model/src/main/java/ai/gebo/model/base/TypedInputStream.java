package ai.gebo.model.base;

import java.io.IOException;
import java.io.InputStream;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface TypedInputStream {
	String getContentType();

	String getExtension();

	InputStream getInputStream() throws UnsupportedOperationException, IOException;

	@Getter
	@AllArgsConstructor
	static class TypedInputStreamImpl implements TypedInputStream {
		final InputStream inputStream;
		final String contentType;
		final String extension;
	}

	public static TypedInputStream of(InputStream inputStream, String contentType, String extension) {
		return new TypedInputStreamImpl(inputStream, contentType, extension);
	}
}