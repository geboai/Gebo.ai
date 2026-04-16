package ai.gebo.llms.chat.abstraction.layer.session.model;

import ai.gebo.architecture.ai.model.ITokensCountable;
import ai.gebo.architecture.rag.support.layer.model.AIDocumentReferenceItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CSSInteractionReferredContent<T> implements ITokensCountable {
	private int interactionIndex = 0;
	private AIDocumentReferenceItem aiDocument = null;
	private T appReference = null;

	@Override
	public int getTokensSize() {
		return aiDocument != null ? aiDocument.getTokensSize() : 0;
	}
}