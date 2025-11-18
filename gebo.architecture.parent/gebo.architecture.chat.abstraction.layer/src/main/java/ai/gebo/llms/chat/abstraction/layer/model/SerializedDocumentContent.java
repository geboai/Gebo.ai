package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SerializedDocumentContent {
	String id = null;
	String content = null;
	Map<String, Object> metaData = null;
}