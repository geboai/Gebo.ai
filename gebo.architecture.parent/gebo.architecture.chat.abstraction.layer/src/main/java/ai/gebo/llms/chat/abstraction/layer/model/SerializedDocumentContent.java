package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SerializedDocumentContent {

	private String id = null;
	private String content = null;
	private Map<String, Object> metaData = null;

	public SerializedDocumentContent() {
	}
}