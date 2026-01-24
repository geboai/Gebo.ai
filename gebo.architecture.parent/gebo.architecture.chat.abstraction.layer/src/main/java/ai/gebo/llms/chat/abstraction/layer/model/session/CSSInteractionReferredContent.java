package ai.gebo.llms.chat.abstraction.layer.model.session;

import ai.gebo.llms.abstraction.layer.model.AIDocumentReferenceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSInteractionReferredContent<T> {
	private int interactionIndex = 0;
	private AIDocumentReferenceItem data = null;
	private T contentObject = null;
}