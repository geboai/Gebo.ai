package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class SerializedDocumentsContent {
	private List<SerializedDocumentContent> documents = null;
	public SerializedDocumentsContent() {
	}
}