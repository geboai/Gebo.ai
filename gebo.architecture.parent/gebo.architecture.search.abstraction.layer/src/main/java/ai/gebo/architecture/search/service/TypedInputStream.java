package ai.gebo.architecture.search.service;

import java.io.IOException;
import java.io.InputStream;

public interface TypedInputStream {
	String getContentType();
	
	InputStream getInputStream() throws UnsupportedOperationException, IOException;
}