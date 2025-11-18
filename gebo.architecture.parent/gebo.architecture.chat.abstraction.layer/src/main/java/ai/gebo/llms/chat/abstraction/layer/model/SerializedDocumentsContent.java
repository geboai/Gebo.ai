package ai.gebo.llms.chat.abstraction.layer.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SerializedDocumentsContent {
	List<SerializedDocumentContent> documents = null;
}