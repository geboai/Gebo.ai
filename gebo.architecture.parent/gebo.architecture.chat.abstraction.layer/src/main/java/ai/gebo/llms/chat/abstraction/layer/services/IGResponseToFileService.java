package ai.gebo.llms.chat.abstraction.layer.services;

import java.io.IOException;
import java.io.OutputStream;

public interface IGResponseToFileService {
	public static enum ResponseFormat {
		EXCEL, WORD, PDF
	}

	public void convert(String userContextCode, String responseId, ResponseFormat format, OutputStream os)
			throws IOException;
}
